package main.java.FileFlow.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class FileProcessor implements Callable<FileProcessResult> {
    private final File file;

    public FileProcessor(File file) {
        this.file = file;
    }

    @Override
    public FileProcessResult call() throws Exception {
        long startTime = System.currentTimeMillis();
        Thread.sleep(200); // Емуляція затримки обробки
        Map<String, Integer> urlCounts = new HashMap<>();

        processFile(urlCounts);

        Map.Entry<String, Integer> maxEntry = urlCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        long endTime = System.currentTimeMillis();
        String result = (maxEntry != null) ? maxEntry.getKey() + " " + maxEntry.getValue() : "No data";

        return new FileProcessResult(result, endTime - startTime);
    }

    private void processFile(Map<String, Integer> urlCounts) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines()
                    .map(line -> line.split("\""))
                    .filter(parts -> parts.length > 1)
                    .map(parts -> parts[1].split(" "))
                    .filter(requestParts -> requestParts.length > 1)
                    .map(requestParts -> requestParts[1])
                    .forEach(url -> urlCounts.put(url, urlCounts.getOrDefault(url, 0) + 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void saveResultToFile(FileProcessResult result, String threadName) {
        String filePath = "C:\\NULP\\Lab4\\src\\main\\resources\\threadResult.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.printf("Thread: %s%nResult: %s%nProcessing Time: %d ms%n%n",
                    threadName, result.getResult(), result.getTime());
            System.out.println("Результат успішно записано у файл " + filePath);
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
