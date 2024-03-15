package Main;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import javax.security.auth.kerberos.KeyTab;

import java.time.Duration;
import java.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Sites.*;
import net.bytebuddy.asm.Advice.Local;
public class App {
    public static void main(String[] args) throws Exception {
        
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\Programming\\ImportantFiles\\chromedriver.exe");
        // HashMap<FlightScanners, Integer> websiteToPrice = new HashMap<>();
        // websiteToPrice.put(FlightScanners.GOOGLEFLIGHTS, GoogleFlights.searchFlight(flight));
        // websiteToPrice.put(FlightScanners.KIWI, Kiwi.searchFlight(flight));
        // websiteToPrice.put(FlightScanners.ELAL, Elal.searchFlight(flight));
        // websiteToPrice.put(FlightScanners.SKYSCANNER, SkyScanner.searchFlight(flight));
        // websiteToPrice.put(FlightScanners.MOMONDO, Momondo.searchFlight(flight));
        

        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(1));
        passengers.add(new Passenger(14));
        passengers.add(new Passenger(17));
        passengers.add(new Passenger(20));
        Flight flight = new Flight("Tel Aviv", "Vienna", 800, 1, LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 16), passengers);
        GoogleFlights.searchFlight(flight);
        Momondo.searchFlight(flight);

    }
    
    
}
