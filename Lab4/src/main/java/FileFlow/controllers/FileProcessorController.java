package main.java.FileFlow.controllers;

import main.java.FileFlow.models.FileProcessResult;
import main.java.FileFlow.models.FileProcessor;
import main.java.FileFlow.views.FileProcessorView;
import main.java.FileFlow.views.ThreadPanel;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class FileProcessorController {
    private final FileProcessorView view;
    private ScheduledThreadPoolExecutor executor;
    private final Semaphore semaphore;
    private String pathToFiles;


    public FileProcessorController(FileProcessorView view, String path) {
        this.view = view;
        this.executor = new ScheduledThreadPoolExecutor(5);
        this.semaphore = new Semaphore(5);
        this.pathToFiles = path;

        initController();
    }

    private void initController() {
        view.getStartButton().addActionListener(e -> startFileProcessing());
    }
    private void startFileProcessing() {
        new Thread(() -> {
            try {
                int countThread = Integer.parseInt(view.getCountThreadText().getText());
                int restrictThread = Integer.parseInt(view.getRestrictThreadText().getText());

                if (countThread <= 0 || restrictThread <= 0) {
                    SwingUtilities.invokeLater(() ->
                            view.getResultLabel().setText("Число потоків та обмеження повинні бути більшими за нуль."));
                    return;
                }

                File[] files = new File(pathToFiles).listFiles();
                if (files == null || files.length == 0) {
                    SwingUtilities.invokeLater(() ->
                            view.getResultLabel().setText("Папка з файлами пуста або не існує."));
                    return;
                }

                Map<String, ThreadPanel> ThreadsPanels = new HashMap<>();

                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(countThread);
                for(int i = 0; i < countThread; i++) {
                    String threadName = "pool-2-thread-" + (i+1);
                    ThreadPanel thPanel = new ThreadPanel(threadName);

                    ThreadsPanels.put(threadName, thPanel);
                }


                view.getThreadsBoard().setThreadsPanels(ThreadsPanels);

                Semaphore semaphore = new Semaphore(restrictThread);

                long startTime = System.currentTimeMillis();

                for (File file : files) {
                    if (file.isFile()) {
                        semaphore.acquire();
                        executorService.schedule(() -> {
                            try {
                                String threadName = Thread.currentThread().getName();
                                System.out.println(threadName);
                                SwingUtilities.invokeLater(() -> {
                                    //JLabel threadLabel = new JLabel("Обробка файлу: " + file.getName());

                                    ThreadsPanels.get(threadName).updateStatus("Виконується");

                                    //view.getThreadsResults().add(threadLabel);
                                    view.getThreadsPanel().revalidate();
                                    view.getThreadsPanel().repaint();
                                });

                                FileProcessor processor = new FileProcessor(file);
                                Future<FileProcessResult> FutureResult = executor.submit(processor);
                                FileProcessResult result = FutureResult.get();


                                FileProcessor.saveResultToFile(result, threadName);

                                SwingUtilities.invokeLater(() -> {
                                    JLabel resultLabel = new JLabel(file.getName() + ": " + result.getResult());

                                    ThreadsPanels.get(threadName).updateStatus("Очікує");
                                    ThreadsPanels.get(threadName).updateTime(result.getTime());

                                    view.getThreadsResults().add(resultLabel);
                                    view.getThreadsPanel().revalidate();
                                    view.getThreadsPanel().repaint();
                                });
                            } catch (Exception e) {
                                SwingUtilities.invokeLater(() ->
                                        view.getResultLabel().setText("Помилка обробки файлу: " + e.getMessage()));
                            } finally {
                                semaphore.release();
                            }
                        }, 1, TimeUnit.SECONDS);
                    }
                }

                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.HOURS);

                long endTime = System.currentTimeMillis();
                SwingUtilities.invokeLater(() ->
                        view.getResultLabel().setText("Загальний час виконання: " + (endTime - startTime) + " ms"));

            } catch (NumberFormatException | InterruptedException e) {
                SwingUtilities.invokeLater(() ->
                        view.getResultLabel().setText("Введіть коректні значення!"));
            }
        }).start();
    }




}
