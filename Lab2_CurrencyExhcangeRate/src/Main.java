import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //data format YYYY-MM-DD
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter date for currency prediction in format yyyy-mm-dd: ");
        String date = sc.nextLine();
        if(!checkFormat(date) || !checkIsFuture(date)) {
            System.out.println("Invalid date");
            return;
        }
        List<Double> ratesUSD = new ArrayList<Double>();
        ratesUSD = CurrencyDataFetcher.getCurrencyRates(date,"USD");
        List<Double> ratesEUR = new ArrayList<Double>();
        ratesEUR = CurrencyDataFetcher.getCurrencyRates(date,"EUR");
        List<Double> ratesCZK = new ArrayList<Double>();
        ratesCZK = CurrencyDataFetcher.getCurrencyRates(date,"CZK");
        System.out.printf("""
                        
                        USD | UAH
                        1   | %4.3f
                        EUR | UAH
                        1   | %4.3f
                        CZK | UAH
                        1   | %4.3f""", CurrencyDataFetcher.predictNextRate(ratesUSD),
                                                    CurrencyDataFetcher.predictNextRate(ratesEUR),
                                                    CurrencyDataFetcher.predictNextRate(ratesCZK));

    }
    public static boolean checkFormat(String date){
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
    public static boolean checkIsFuture(String date){
        LocalDate today = LocalDate.now();
        LocalDate futureDate = LocalDate.parse(date);
        return futureDate.isAfter(today);
    }
}