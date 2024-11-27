package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public class Sportsman implements Serializable {
    private String name;
    private String SportType;
    private int age;
    public transient int medalCount;
    private ArrayList<MedalType> medals;

    public Sportsman(String name, String SportType, int age, ArrayList<MedalType> medals) {
        this.name = name;
        this.SportType = SportType;
        this.age = age;
        this.medals = medals;
        medalCount = medals.size();
    }

    public String getName() {
        return name;
    }
    public String getSportType() {
        return SportType;
    }
    public int getAge() {
        return age;
    }
    public int getMedalCount() {
        return medalCount;
    }
    public ArrayList<MedalType> getMedals() {
        return medals;
    }

    @Override
    public String toString() {
        return "Sportsman [name=" + name + ", Sport type=" + SportType + ", age=" + age + ", medals=" + medals + "]";
    }
}
