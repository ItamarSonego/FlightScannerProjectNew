package Sites;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.ExpandVetoException;

import org.checkerframework.checker.units.qual.min;
import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import Main.*;
import Utils.*;

public class Momondo extends FlightSearchSite{
    public static ageRange seniorAgeRange = new ageRange(65, 120);
    public static ageRange adultAgeRange = new ageRange(18, 64);
    public static ageRange youthAgeRange = new ageRange(12, 17);
    public static ageRange childAgeRange = new ageRange(2, 11);
    public static ageRange infantAgeRange = new ageRange(0, 1);

    //combines all the functions to one main function
    public static Object[] searchFlight(Flight flight) throws InterruptedException {
        WebDriver browser = WebDriverManager.getBrowser();
        Object[] priceAndLink = new Object[2];
        int count = 0;
        
        while ((priceAndLink[0] == null || priceAndLink[1] == null) && (count < 5)) {
            try {
                enterPlaces(browser, flight);
                choosePassengersAges(browser, flight);
                chooseDates(browser, flight);
                chooseStops(browser, flight);
                
                priceAndLink[0] = chooseBestFlight(browser, flight);
                priceAndLink[1] = getBestFlightUrl(browser);
            } catch (Exception e) {
                WebDriverManager.quitBrowser();
                browser = WebDriverManager.getBrowser();
            }
            count++;
        }
        return priceAndLink;
    }
    
    //enters the departure city and landing city that the user entered
    public static void enterPlaces(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.get(FlightSearchSite.momondoUrl);
        browser.manage().window().maximize();
        ProgressBar.updateProgressBar(22, 68,50);
        Thread.sleep(1000);
        boolean removedFlightSuggest = false;
        
        try {
            browser.findElement(By.className("vvTc-item-close")).click();
            removedFlightSuggest = true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }

        ProgressBar.updateProgressBar(23, 68,50);
        if (!removedFlightSuggest) {
            browser.findElement(By.className("c_neb-item-close")).click();
        }

        ProgressBar.updateProgressBar(24, 68,50);
        wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("[placeholder='From?']")))).sendKeys(flight.getDepartureCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='yWDF yWDF-mod-spacing-base']")));
        browser.findElements(By.cssSelector("[class='JyN0-item JyN0-pres-item-mcfly']")).get(0).click();
        ProgressBar.updateProgressBar(24, 68,50);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='To?'"))).sendKeys(flight.getLandingCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='yWDF yWDF-mod-spacing-base']")));
        browser.findElements(By.cssSelector("[class='JyN0-item JyN0-pres-item-mcfly']")).get(0).click();
        ProgressBar.updateProgressBar(25, 68,50);
    }
    
    //chooses age for each passenger according to the user input
    public static void choosePassengersAges(WebDriver browser, Flight flight) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.findElements(By.cssSelector("[class='S9tW-chevron']")).get(0).click();
        ProgressBar.updateProgressBar(26, 68,50);
        
        WebElement plusIconSeniors = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(5);
        WebElement plusIconAdults = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(1);
        WebElement minusIconAdults = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(0);
        WebElement plusIconYouth = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(7);
        WebElement plusIconChildren = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(9);
        WebElement plusIconInfants = browser.findElements(By.cssSelector("button.bCGf-mod-theme-default")).get(11);
        ProgressBar.updateProgressBar(27, 68,50);
        
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
            else {
                plusIconInfants.click();
            }
        }
        ProgressBar.updateProgressBar(28, 68,50);
        minusIconAdults.click();
    }

    //chooses the dates that the user entered 
    public static void chooseDates(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        String startDateToSearch = UsefulFunctions.dateFormatterForMomondo(flight.getStartDate());
        String endDateToSearch = UsefulFunctions.dateFormatterForMomondo(flight.getEndDate());
        
        browser.findElement(By.cssSelector("[aria-label='Start date calendar input']")).click();
        ProgressBar.updateProgressBar(29, 68,50);
        
        List<WebElement> monthNavigaionButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class='c1fvi c1fvi-mod-theme-button']")));
        monthNavigaionButtons.get(0).click();
        boolean foundStartDate = false;
        ProgressBar.updateProgressBar(30, 68,50);
        
        for (int i = 1; i <= 13; i++) {
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
        ProgressBar.updateProgressBar(31, 68,50);

        //clicking the 'search' button
        browser.findElement(By.cssSelector("[title='Search']")).click();
        ProgressBar.updateProgressBar(32, 68,50);
    }



    //picks the number of stops that the user entered
    public static void chooseStops(WebDriver browser, Flight flight) throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        ArrayList<String> tabs = new ArrayList<>(browser.getWindowHandles());
        browser.switchTo().window(tabs.get(tabs.size() - 1));
        ProgressBar.updateProgressBar(33, 68,50);
        
        switch (flight.getMaxStops()) {
            case 1:
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#valueSetFilter-vertical-stops-2"))).click();
                break;
            case 0:
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#valueSetFilter-vertical-stops-1"))).click();
                browser.findElement(By.cssSelector("#valueSetFilter-vertical-stops-2")).click();
                break;
        }
        ProgressBar.updateProgressBar(34, 68,50);
    }

    //chooses the best flight
    public static double chooseBestFlight(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.findElement(By.cssSelector("[aria-label='Navigation menu']")).click();
        browser.findElement(By.cssSelector("[aria-label='United States dollar ']")).click();
        browser.findElement(By.cssSelector("[placeholder='Search for a currency']")).sendKeys("Israeli new shekel");
        ProgressBar.updateProgressBar(35, 68,50);
        
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='KmfS KmfS-mod-variant-pill']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='close']"))).click();
        
        ProgressBar.updateProgressBar(36, 68,50);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Cheapest']"))).click();
        ProgressBar.updateProgressBar(37, 68,50);
        
        Thread.sleep(2000);
        String price = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='f8F1-price-text']"))).get(0).getAttribute("innerText");
        price = price.substring(1);
        price = price.replace(",", "");
        browser.findElements(By.cssSelector("[class='dOAU-best']")).get(0).click();
        ProgressBar.updateProgressBar(38, 68,50);

        try {
            double priceDouble = Double.parseDouble(price) * flight.getPassengers().size();
            if (priceDouble <= flight.getMaxPrice()) {
                ProgressBar.updateProgressBar(39, 68,50);
                return priceDouble;
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Error, price is not a number!");
        }
        return 0;
    }

    //returns the link of the best flight
    public static String getBestFlightUrl(WebDriver browser) {
        ArrayList<String> tabs = new ArrayList<>(browser.getWindowHandles());
        browser.switchTo().window(tabs.get(tabs.size() - 1));
        ProgressBar.updateProgressBar(40, 68,50);
        
        int durationInSeconds = 15;
        long endTime = System.currentTimeMillis() + (durationInSeconds * 1000);
        
        while (browser.getTitle().contains("momondo") && System.currentTimeMillis() < endTime) {
            continue;
        }

        ProgressBar.updateProgressBar(41, 68,50);

        String link = browser.getCurrentUrl();
        WebDriverManager.quitBrowser();
        ProgressBar.updateProgressBar(42, 68,50);
        return link;
    }
}

