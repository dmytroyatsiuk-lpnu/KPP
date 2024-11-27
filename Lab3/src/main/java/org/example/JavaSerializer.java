package org.example;

import java.io.*;
import java.util.ArrayList;

public class JavaSerializer {
    public static void saveSportsman(ArrayList<Sportsman> sportsmans, String sportsmanFilePath, String medalFilePath) {
        try(FileOutputStream fileOut = new FileOutputStream(sportsmanFilePath);
            BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
            ObjectOutputStream objectOut = new ObjectOutputStream(bufferedOut)){

            for(Sportsman sportsman : sportsmans){
                objectOut.writeObject(sportsman);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try(FileOutputStream medalsOut = new FileOutputStream(medalFilePath)){
            for(Sportsman sportsman : sportsmans){
                medalsOut.write(sportsman.getMedalCount());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Sportsman> loadSportsman(String sportsmanFilePath, String medalFilePath) {
        ArrayList<Sportsman> sportsmans = new ArrayList<>();

        // Десеріалізація об'єктів Sportsman
        try (FileInputStream fileIn = new FileInputStream(sportsmanFilePath);
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
             ObjectInputStream objectIn = new ObjectInputStream(bufferedIn)) {

            while (true) {
                try {
                    Sportsman sportsman = (Sportsman) objectIn.readObject();
                    sportsmans.add(sportsman);
                } catch (EOFException e) {
                    break; // Кінець файлу
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Відновлення поля medalCount
        try (FileInputStream medalsIn = new FileInputStream(medalFilePath)) {
            for (Sportsman sportsman : sportsmans) {
                sportsman.medalCount = medalsIn.read(); // Присвоєння значення з файлу
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sportsmans;
    }

}
