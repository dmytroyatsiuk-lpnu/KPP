import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //ArrayList<String> list = findNumbers("123 some -45.67 words 8.9e10 between 3.1415 numbers -2.7e+3 +42 1k.2e3 2e3.4r -9");
        System.out.println(findNearLastWord("jkfh skdfhjskjfh skdfj sd sd hjty df"));
//        for(String number : list){
//            System.out.println(number);
//        }
    }
    public static ArrayList<String> findNumbers(String s) {
        ArrayList<String> numbers = new ArrayList<>();

        String regex = "(?<!\\S)([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)(?!\\S)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
//знайти передостаннє слово в реченні
        while(matcher.find()) {
            numbers.add(matcher.group());
        }
        return numbers;
    }

    public static String findNearLastWord(String s) {
        String regex = "\\b(\\w+)\\b(?=\\W*\\b\\w+\\b\\W*$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}