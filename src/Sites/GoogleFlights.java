package Sites;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//#region imports
import Main.Flight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//#endregion


public class GoogleFlights implements FlightSearchSite{
    WebDriver browser = new ChromeDriver();
    public void enterPlaces(Flight flight) {
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\Programming\\ImportantFiles\\chromedriver.exe");
        browser.get(FlightSearchSite.googleFlightsUrl);
        browser.findElement(By.cssSelector("input[type=text]")).
        sendKeys(flight.getDepartureCountry().getCountryString());
    }
}
