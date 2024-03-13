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
        // Flight flight = Flight.buildFlight();
        HashMap<FlightScanners, Integer> websiteToPrice = new HashMap<>();
        
        // ArrayList<Passenger> passengers = new ArrayList<>();
        // passengers.add(new Passenger(1));
        // passengers.add(new Passenger(14));
        // passengers.add(new Passenger(17));
        // passengers.add(new Passenger(20));
        // Flight flight = new Flight("New York", "Pyongyang", 800, 1, LocalDate.of(2024, 8, 4), LocalDate.of(2024, 8, 8), passengers);
        // GoogleFlights.searchFlight(flight);

        

    }
    
}
