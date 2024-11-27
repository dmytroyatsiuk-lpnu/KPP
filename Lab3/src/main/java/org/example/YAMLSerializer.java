package org.example;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YAMLSerializer {
    public static void saveSportsmenToYaml(ArrayList<Sportsman> sportsmen, String filePath) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Блоковий стиль YAML
        Yaml yaml = new Yaml(options);

        List<Map<String, Object>> sportsmenData = new ArrayList<>();

        for (Sportsman sportsman : sportsmen) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", sportsman.getName());
            data.put("age", sportsman.getAge());
            data.put("medals", sportsman.getMedals());
            if (sportsman.getAge() <= 30) {
                data.put("SportType", sportsman.getSportType()); // Додаємо SportType лише якщо вік <= 30
            }
            sportsmenData.add(data);
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(sportsmenData, writer); // Записуємо список спортсменів у YAML файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для десеріалізації масиву спортсменів з YAML файлу
    public static ArrayList<Sportsman> loadSportsmenFromYaml(String filePath) {
        Yaml yaml = new Yaml(new Constructor(ArrayList.class));
        ArrayList<Sportsman> sportsmen = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            List<Map<String, Object>> sportsmenData = yaml.load(reader);

            for (Map<String, Object> data : sportsmenData) {
                String name = (String) data.get("name");
                String sportType = (String) data.getOrDefault("SportType", "Unknown"); // Якщо SportType відсутній
                int age = (int) data.get("age");
                ArrayList<MedalType> medals = (ArrayList<MedalType>) data.get("medals");

                Sportsman sportsman = new Sportsman(name, sportType, age, medals);
                sportsmen.add(sportsman);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sportsmen;
    }
}
