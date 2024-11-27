import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.math3.stat.regression.SimpleRegression;


public class CurrencyDataFetcher {
    // Базова URL для запитів до Національного банку України (НБУ)
    private static final String API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json&valcode=";

    // Метод для отримання курсів валют і повернення їх у вигляді списку
    public static List<Double> getCurrencyRates(String futureDate, String currencyCode) {
        LocalDate futDate = LocalDate.parse(futureDate);
        LocalDate date = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(date, futDate);
        LocalDate pastDate = date.minusDays(daysBetween * 2);
        List<Double> rates = new ArrayList<>();

        for (int i = 0; i < daysBetween * 2 + 1; ++i) {
            LocalDate middleDate = pastDate.plusDays(i);
            String strURL = API_URL + currencyCode + "&date=" + middleDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            try {
                JSONArray jsonArray = getObjects(strURL);
                if (!jsonArray.isEmpty()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double rate = jsonObject.getDouble("rate");
                    rates.add(rate); // Додаємо курс до списку
                } else {
                    rates.add(null); // Якщо дані відсутні для дати, додаємо null або обробляємо іншим чином
                }

            } catch (Exception e) {
                e.printStackTrace();
                rates.add(null); // Якщо виникає помилка, додаємо null
            }
        }

        return rates; // Повертаємо список курсів
    }

    private static JSONArray getObjects(String strURL) throws URISyntaxException, IOException {
        URI uri = new URI(strURL);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return new JSONArray(content.toString());
    }

    public static double predictNextRate(List<Double> rates) {
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < rates.size(); i++) {
            regression.addData(i, rates.get(i));
        }
        return regression.predict((double)rates.size() / 2);
    }
}

