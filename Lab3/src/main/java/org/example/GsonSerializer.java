package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonSerializer {
    public static void saveSportsmenToJson(ArrayList<Sportsman> sportsmen, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Створення об'єкта Gson з форматуванням JSON
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(sportsmen, writer); // Запис JSON у файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для зчитування масиву спортсменів з JSON файлу
    public static ArrayList<Sportsman> loadSportsmenFromJson(String filePath) {
        Gson gson = new Gson();
        ArrayList<Sportsman> sportsmen = new ArrayList<>();
        Type sportsmanListType = new TypeToken<ArrayList<Sportsman>>() {}.getType();

        try (FileReader reader = new FileReader(filePath)) {
            sportsmen = gson.fromJson(reader, sportsmanListType); // Зчитування JSON у масив об'єктів Sportsman
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sportsmen;
    }
}
