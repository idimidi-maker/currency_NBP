import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class Currency {
    public Rate[] rates;
}
class Rate {
    @SerializedName("nameFromJSON")
    public String effectiveDate;
    public double mid;
}

public class NBP {
    public static void main(String[] args) {
        String nbp = downloadJsonFromURL("http://api.nbp.pl/api/exchangerates/rates/a/gbp/?format=json");
        Gson gson = new Gson();
        Currency gbpCur = gson.fromJson(nbp, Currency.class);

        double gbpValue = gbpCur.rates[0].mid;
        String chose;
        double gbp1, pln1;
        do {
            Scanner scanObject = new Scanner(System.in);
            scanObject.useDelimiter("(\\p{javaWhitespace}|\\.|,)+");
            System.out.println("\nType in 1 to calculate PLN to GBP, or 2 to calculate GBP to PLN.");
            chose = scanObject.nextLine();

            switch (chose) {
                case "1" -> {
                    System.out.printf("Today's GBP course is %.2f PLN. %n", gbpValue);
                    System.out.println("Type in the value you send in PLN: ");
                    double pln = scanObject.nextDouble();
                    gbp1 = (pln / gbpValue);
                    System.out.printf("They receive %.2f GBP. %n", gbp1);
                }
                case "2" -> {
                    System.out.printf("Today's GBP course is %.2f PLN. %n", gbpValue);
                    System.out.println("Type in the value you send in GBP: ");
                    double gbp = scanObject.nextDouble();
                    pln1 = (gbp * gbpValue);
                    System.out.printf("They receive %.2f PLN. %n", pln1);
                }
                default -> System.out.println("Wrong value, please type in 1 or 2.");
            }
        } while (chose !="1");
    }

    public static String downloadJsonFromURL(String urlText) {
        try {
            URL u = new URL(urlText);
            StringBuilder jsonText = new StringBuilder();
            try (InputStream myInputStream = u.openStream();
                 Scanner scanner = new Scanner(myInputStream)) {
                while (scanner.hasNextLine()) {
                    jsonText.append(scanner.nextLine());
                }
                return jsonText.toString();
            }
        } catch (IOException e) {
            System.err.println("Failed to get content from URL " + urlText + " due to exception: " + e.getMessage());
            return null;
        }
    }
}

