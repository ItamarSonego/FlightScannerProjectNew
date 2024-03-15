package Sites;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.ExpandVetoException;

import org.checkerframework.checker.units.qual.min;
import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Main.Flight;
import Main.Passenger;
import Main.UsefulFunctions;
import Main.ageRange;

public class Momondo extends FlightSearchSite{
    public static ageRange seniorAgeRange = new ageRange(65, 120);
    public static ageRange adultAgeRange = new ageRange(18, 64);
    public static ageRange youthAgeRange = new ageRange(12, 17);
    public static ageRange childAgeRange = new ageRange(2, 11);
    public static ageRange infantAgeRange = new ageRange(0, 1);
    static WebDriver browser = new ChromeDriver();


    //combines all the functions to one main function
    public static void searchFlight(Flight flight) throws InterruptedException {
        enterPlaces(flight);
        choosePassengersAges(flight);
        chooseDates(flight);
        chooseStops(flight);
        chooseBestFlight(flight);
    }
    //enters the departure city and landing city that the user entered
    public static void enterPlaces(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.get(FlightSearchSite.momondoUrl);
        browser.manage().window().maximize();
        WebElement closeFlightSuggest = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("vvTc-item-close")));
        
        while (true) {
            try {
                closeFlightSuggest.click();
            }
            catch (org.openqa.selenium.StaleElementReferenceException e) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("[placeholder='From?']")))).sendKeys(flight.getDepartureCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='yWDF yWDF-mod-spacing-base']")));
        browser.findElements(By.cssSelector("[class='JyN0-item JyN0-pres-item-mcfly']")).get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='To?'"))).sendKeys(flight.getLandingCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='yWDF yWDF-mod-spacing-base']")));
        browser.findElements(By.cssSelector("[class='JyN0-item JyN0-pres-item-mcfly']")).get(0).click();
    }
    
    //chooses age for each passenger according to the user input
    public static void choosePassengersAges(Flight flight) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.findElements(By.cssSelector("[class='S9tW-chevron']")).get(0).click();

        WebElement plusIconSeniors = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(5);
        WebElement plusIconAdults = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(1);
        WebElement minusIconAdults = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(0);
        WebElement plusIconYouth = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(7);
        WebElement plusIconChildren = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(9);
        WebElement plusIconInfants = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(11);

        for (Passenger passenger : flight.getPassengers()) {
            if (seniorAgeRange.contains(passenger.getAge())) {
                plusIconSeniors.click();
            }
            else if (adultAgeRange.contains(passenger.getAge())) {
                plusIconAdults.click();
            }
            else if (youthAgeRange.contains(passenger.getAge())) {
                plusIconYouth.click();
            }
            else if (childAgeRange.contains(passenger.getAge())) {
                plusIconChildren.click();
            }
            else if (infantAgeRange.contains(passenger.getAge())) {
                plusIconInfants.click();
            }
        }
        minusIconAdults.click();
    }

    //chooses the dates that the user entered 
    public static void chooseDates(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        String startDateToSearch = UsefulFunctions.dateFormatterForMomondo(flight.getStartDate());
        String endDateToSearch = UsefulFunctions.dateFormatterForMomondo(flight.getEndDate());
        
        browser.findElement(By.cssSelector("[aria-label='Start date calendar input']")).click();
        
        List<WebElement> monthNavigaionButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class='c1fvi c1fvi-mod-theme-button']")));
        monthNavigaionButtons.get(0).click();
        boolean foundStartDate = false;
        
        for (int i = 1; i <= 12; i++) {
            try {
                if (!foundStartDate) {
                    browser.findElement(By.cssSelector("[aria-label=" + startDateToSearch + "]")).click();
                    foundStartDate = true;
                }
                else {
                    browser.findElement(By.cssSelector("[aria-label=" + endDateToSearch + "]")).click();
                    break;
                }
            } 
            catch (org.openqa.selenium.NoSuchElementException e) {
                monthNavigaionButtons = browser.findElements(By.cssSelector("[class='c1fvi c1fvi-mod-theme-button']"));
                monthNavigaionButtons.get(monthNavigaionButtons.size() - 1).click();
            }
        }
        
        //clicking the 'search' button
        browser.findElement(By.cssSelector("[title='Search']")).click();
    }



    //picks the number of stops that the user entered
    public static void chooseStops(Flight flight) throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        ArrayList<String> tabs = new ArrayList<>(browser.getWindowHandles());
        browser.switchTo().window(tabs.get(1));
        switch (flight.getMaxStops()) {
            case 1:
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#valueSetFilter-vertical-stops-2"))).click();
                break;
            case 0:
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#valueSetFilter-vertical-stops-1"))).click();
                browser.findElement(By.cssSelector("#valueSetFilter-vertical-stops-2")).click();
                break;
        }
    }

    public static void chooseBestFlight(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.findElement(By.cssSelector("[aria-label='Navigation menu']")).click();
        browser.findElement(By.cssSelector("[aria-label='United States dollar ']")).click();
        browser.findElement(By.cssSelector("[placeholder='Search for a currency']")).sendKeys("Israeli new shekel");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='KmfS KmfS-mod-variant-pill']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='close']"))).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Cheapest']"))).click();
        Thread.sleep(2000);
        String price = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='f8F1-price-text']"))).get(0).getAttribute("innerText");
        System.out.println(price);
    }

    }

