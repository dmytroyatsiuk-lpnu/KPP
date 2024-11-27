package main.java.FileFlow.views;

import javax.swing.*;
import java.awt.*;

public class FileProcessorView {
    private final JFrame frame;
    private final JTextField countThreadText;
    private final JTextField restrictThreadText;
    private final JPanel threadsPanel;
    private final ThreadBoard threadsBoard;
    private final JPanel threadsResults;
    private final JLabel resultLabel;
    private final JButton startButton;

    public FileProcessorView() {
        frame = new JFrame("Обробка файлів");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Поля вводу
        JPanel countThreadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel countThreadLabel = new JLabel("Введіть кількість потоків: ");
        countThreadText = new JTextField(10);
        countThreadPanel.add(countThreadLabel);
        countThreadPanel.add(countThreadText);

        JPanel restrictThreadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel restrictThreadLabel = new JLabel("Введіть обмеження для пулу потоків: ");
        restrictThreadText = new JTextField(10);
        restrictThreadPanel.add(restrictThreadLabel);
        restrictThreadPanel.add(restrictThreadText);

        formPanel.add(countThreadPanel);
        formPanel.add(restrictThreadPanel);

        // Кнопка запуску
        startButton = new JButton("Запустити обробку файлів");
        formPanel.add(startButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Панель для потоків
        threadsPanel = new JPanel();
        threadsPanel.setLayout(new GridLayout(2, 1));
        // Панель з потоками
        threadsBoard = new ThreadBoard();
        threadsResults = new JPanel();

        threadsResults.setLayout(new BoxLayout(threadsResults, BoxLayout.Y_AXIS));

        JScrollPane scrollPaneBoard = new JScrollPane(threadsBoard);
        threadsPanel.add(scrollPaneBoard, BorderLayout.CENTER);

        JScrollPane scrollPaneResult = new JScrollPane(threadsResults);
        threadsPanel.add(scrollPaneResult, BorderLayout.CENTER);

        mainPanel.add(threadsPanel);

        // Результат
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel resultTextLabel = new JLabel("Загальний час обробки файлів: ");
        resultLabel = new JLabel("");
        resultPanel.add(resultTextLabel);
        resultPanel.add(resultLabel);

        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getCountThreadText() {
        return countThreadText;
    }

    public JTextField getRestrictThreadText() {
        return restrictThreadText;
    }

    public JPanel getThreadsPanel() {
        return threadsPanel;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public ThreadBoard getThreadsBoard() {return threadsBoard;}
    public JPanel getThreadsResults() {return threadsResults;}

    public void show() {
        frame.setVisible(true);
    }
}
