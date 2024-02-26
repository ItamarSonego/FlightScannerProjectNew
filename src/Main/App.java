package Main;
import java.util.ArrayList;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import Sites.FlightSearchSite;
public class App {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\Programming\\ImportantFiles\\chromedriver.exe");
        WebDriver browser = new ChromeDriver();
        // ArrayList<FlightScanners> sitesToSearch = Messages.welcome();
        // Flight flight = Flight.buildFlight();
        browser.get(FlightSearchSite.googleFlightsUrl);
        browser.manage().window().maximize();
        WebElement fromTextBox = browser.findElement(By.cssSelector("input.II2One.j0Ppje.zmMKJ.LbIaRd"));
        fromTextBox.clear();
        fromTextBox.sendKeys("Switzerland");
        Thread.sleep(2000);
        fromTextBox = browser.findElement(By.cssSelector("li#c3"));
        fromTextBox.click();
        WebElement ToTextBox = browser.findElements(By.cssSelector("input.II2One.j0Ppje.zmMKJ.LbIaRd")).get(2);
        ToTextBox.sendKeys("Israel");
        Thread.sleep(2000);
        ToTextBox = browser.findElement(By.cssSelector("li#c58"));
        Thread.sleep(2000);
        ToTextBox.click();



        


    }
    
}
