package org.example;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<MedalType> medals1 = new ArrayList<MedalType>(List.of(MedalType.GOLD, MedalType.SILVER, MedalType.BRONZE));
        ArrayList<MedalType> medals2 = new ArrayList<MedalType>(List.of(MedalType.GOLD, MedalType.GOLD, MedalType.GOLD));
        ArrayList<MedalType> medals3 = new ArrayList<MedalType>(List.of(MedalType.BRONZE));
        ArrayList<MedalType> medals4 = new ArrayList<MedalType>(List.of(MedalType.BRONZE, MedalType.BRONZE));
        ArrayList<Sportsman> sportsmen = new ArrayList<Sportsman>(List.of(new Sportsman("Oleksander", "Running", 30, medals1), new Sportsman("Dmytro", "Boxing", 25, medals2)));
        ArrayList<Sportsman> deserializedSp = new ArrayList<>();

        JavaSerializer.saveSportsman(sportsmen, "Sportsmen.dat", "Medals.dat");
        deserializedSp = JavaSerializer.loadSportsman("Sportsmen.dat", "Medals.dat");

        System.out.println("Java Serialization:");
        for(Sportsman s : deserializedSp) {
            System.out.println(s);
        }

        System.out.println("\n\nGSON Serialization:");

        deserializedSp.clear();
        sportsmen.add(new Sportsman("Oleg", "Swimming", 30, medals3));
        GsonSerializer.saveSportsmenToJson(sportsmen, "Sportsmen.json");
        deserializedSp = GsonSerializer.loadSportsmenFromJson("Sportsmen.json");
        for(Sportsman s : deserializedSp) {
            System.out.println(s);
        }

        System.out.println("\n\nYAML Serialization:");

        deserializedSp.clear();
        sportsmen.add(new Sportsman("Olena", "Jumping", 35, medals4));
        YAMLSerializer.saveSportsmenToYaml(sportsmen, "Sportsmen.yaml");
        deserializedSp = YAMLSerializer.loadSportsmenFromYaml("Sportsmen.yaml");
        for(Sportsman s : deserializedSp) {
            System.out.println(s);
        }
    }
}