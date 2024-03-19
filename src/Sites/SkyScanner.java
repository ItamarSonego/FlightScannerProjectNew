package Sites;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Main.*;
public class SkyScanner extends FlightSearchSite{
    public static ageRange adultAgeRange = new ageRange(16, 120);
    public static ageRange childAgeRange = new ageRange(0, 15);
    static WebDriver browser = new ChromeDriver();


    public static void enterPlaces(Flight flight) {
        
    }


}
