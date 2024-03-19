package Main;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import javax.security.auth.kerberos.KeyTab;

import java.time.Duration;
import java.time.LocalDate;

import org.checkerframework.checker.units.qual.g;
import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


import Sites.*;
import net.bytebuddy.asm.Advice.Local;
public class App {
    public static void main(String[] args) throws Exception {
        
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\Programming\\ImportantFiles\\chromedriver.exe");
        // HashMap<FlightScanners, Integer> websiteToPrice = new HashMap<>();
        // HashMap<FlightScanners, String> websiteToLink = new HashMap<>();

        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(60));
        passengers.add(new Passenger(23));
        Flight flight = new Flight("buenos aires", "vienna", 100000, 2, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 11), passengers);
        WebDriver browser = new ChromeDriver();
        browser.close();
        // Flight flight = Flight.buildFlight();
        
        Object[] priceAndLinkGoogle = new Object[2];
        Object[] priceAndLinkMomondo = new Object[2];
        Object[] priceAndLinkKiwi = new Object[2];
        
        //#region searching in every site
        while (priceAndLinkGoogle[0] == null || priceAndLinkGoogle[1] == null) {
            try {
                priceAndLinkGoogle = GoogleFlights.searchFlight(flight);
            } catch (Exception e) {
                System.out.println("failed searching in GoogleFlights, retrying...");
            }
        }

        while (priceAndLinkMomondo[0] == null || priceAndLinkMomondo[1] == null) {
            try {
                priceAndLinkMomondo = Momondo.searchFlight(flight);
            } catch (Exception e) {
                System.out.println("failed searching in Momondo, retrying...");
            }
        }

        while (priceAndLinkKiwi[0] == null || priceAndLinkKiwi[1] == null) {
            try {
                priceAndLinkKiwi = Kiwi.searchFlight(flight);
            } catch (Exception e) {
                System.out.println("failed searching in Kiwi, retrying...");
            }
        }
        //#endregion

        ArrayList<Double> validPrices = new ArrayList<>();
        
        double GoogleFlightsPrice = (double) priceAndLinkGoogle[0];
        double momondoPrice = (double) priceAndLinkMomondo[0];
        double kiwiPrice = (double) priceAndLinkKiwi[0];

        if (GoogleFlightsPrice != 0) validPrices.add(GoogleFlightsPrice);
        if (momondoPrice != 0) validPrices.add(momondoPrice);
        if (kiwiPrice != 0) validPrices.add(kiwiPrice);

        double bestPrice = UsefulFunctions.findMin(validPrices);
        String link = "";
        
        if (bestPrice == GoogleFlightsPrice) link = (String) priceAndLinkGoogle[1];
        if (bestPrice == momondoPrice) link = (String) priceAndLinkMomondo[1];
        if (bestPrice == kiwiPrice) link = (String) priceAndLinkKiwi[1];

        System.out.println("Best price: " + bestPrice);
        System.out.println("Link: " + link);


                            
                            
                            
        



    }
    
    
}
