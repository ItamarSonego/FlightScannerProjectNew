package Main;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.security.auth.kerberos.KeyTab;
import java.time.Duration;
import java.time.LocalDate;
import org.checkerframework.checker.units.qual.g;
import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bytebuddy.asm.Advice.Local;
import java.util.logging.Level;
import java.util.logging.Logger;
import Sites.*;
import Utils.*;

public class App {
    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        
        HashMap<FlightScanners, Double> websiteToPrice= new HashMap<>();
        HashMap<FlightScanners, String> websiteToLink = new HashMap<>();
        
        // ArrayList<Passenger> passengers = new ArrayList<>();
        // passengers.add(new Passenger(60));
        // passengers.add(new Passenger(23));
        // Flight flight = new Flight("buenos aires", "vienna", 100000, 2, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 11), passengers);
        
        Flight flight = Flight.buildFlight();

        Object[] priceAndLinkGoogle = GoogleFlights.searchFlight(flight);
        Object[] priceAndLinkMomondo  = Momondo.searchFlight(flight);
        Object[] priceAndLinkKiwi = Kiwi.searchFlight(flight);
        
        websiteToPrice.put(FlightScanners.GOOGLEFLIGHTS, (double) priceAndLinkGoogle[0]);
        websiteToPrice.put(FlightScanners.MOMONDO, (double) priceAndLinkMomondo[0]);
        websiteToPrice.put(FlightScanners.KIWI, (double) priceAndLinkKiwi[0]);

        websiteToLink.put(FlightScanners.GOOGLEFLIGHTS, (String) priceAndLinkGoogle[1]);
        websiteToLink.put(FlightScanners.MOMONDO, (String) priceAndLinkMomondo[1]);
        websiteToLink.put(FlightScanners.KIWI, (String) priceAndLinkKiwi[1]);

        double bestPrice = Double.MAX_VALUE;
        String link = "";
        FlightScanners flightScanner = null;

        for (Map.Entry<FlightScanners, Double> entry : websiteToPrice.entrySet()) {
            if (entry.getValue() < bestPrice && entry.getValue() != 0) {
                bestPrice = entry.getValue();
                link = websiteToLink.get(entry.getKey());
                flightScanner = entry.getKey();
            }
        }

        if (bestPrice != Double.MAX_VALUE) {
            System.out.println("\nBest Price is in " + flightScanner.toString() + ", Price: " + bestPrice);
            System.out.println("Link: " + link);
        }
        else {
            System.out.println("\nNo flight available at this price.");
        }
    }
}

