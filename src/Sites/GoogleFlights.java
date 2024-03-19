package Sites;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.errorprone.annotations.Keep;

//#region imports
import Main.Flight;
import Main.Passenger;
import Main.ageRange;
import io.opentelemetry.extension.incubator.propagation.PassThroughPropagator;

import java.security.spec.ECField;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//#endregion


public class GoogleFlights extends FlightSearchSite{

    public static ageRange adultAgeRange = new ageRange(12, 120);
    public static ageRange childAgeRange = new ageRange(2, 11);
    public static ageRange infantAgeRange = new ageRange(0, 1);

    //combines all the functions to one main function
    public static Object[] searchFlight(Flight flight) throws InterruptedException {
        enterPlaces(flight);
        choosePassengerAges(flight);
        chooseDates(flight);
        chooseStops(flight);
        
        Object[] priceAndLink = new Object[2];
        priceAndLink[0] = chooseBestFlight(flight);
        priceAndLink[1] = getBestFlightUrl();
        return priceAndLink;
    }


    //enters the departure city and landing city that the user entered
    public static void enterPlaces(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(5));
        browser.get(FlightSearchSite.googleFlightsUrl);
        browser.manage().window().maximize();

        WebElement fromBox = browser.findElement(By.cssSelector("input.II2One.j0Ppje.zmMKJ.LbIaRd[aria-label='Where from?']"));
        fromBox.clear();
        fromBox.sendKeys(flight.getDepartureCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[class='n4HaVc sMVRZe pIWVuc']"))).click();

        WebElement toBox = browser.findElement(By.xpath("//input[@aria-label=\"Where to? \" and @class=\"II2One j0Ppje zmMKJ LbIaRd\"]"));
        toBox.sendKeys(flight.getLandingCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[class='n4HaVc sMVRZe pIWVuc']"))).click();   
    }

    //chooses age for each passenger according to the user input
    public static void choosePassengerAges(Flight flight) {

        //open passengers age select bar
        browser.findElement(By.cssSelector("svg[class='FcFFRb G7l2Hb NMm5M']")).click();

        WebElement plusIconAdults = browser.findElement(By.cssSelector("button[class='VfPpkd-LgbsSe VfPpkd-LgbsSe-OWXEXe-Bz112c-M1Soyc VfPpkd-LgbsSe-OWXEXe-dgl2Hf ksBjEc lKxP2d LQeN7 rGF6nb J5hQ8b'][data-tooltip-id='tt-i11']"));
        WebElement plusIconChildren = browser.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div/div[2]/ul/li[2]/div/div/span[3]/button"));
        WebElement plusIconInfants = browser.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div/div[2]/ul/li[3]/div/div/span[3]/button"));
        WebElement minusIconAdults = browser.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div/div[2]/ul/li[1]/div/div/span[1]/button"));
        
        for (Passenger passenger : flight.getPassengers()) {

            if (adultAgeRange.contains(passenger.getAge())) {
                plusIconAdults.click();
            }
            else if (childAgeRange.contains(passenger.getAge())) {
                plusIconChildren.click();
            }
            else {
                plusIconInfants.click();
            }
        }

        //adult number starts from 1, so we need to subtract 1
        minusIconAdults.click();

        //clicking the "done" button
        browser.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div/div[2]/div[2]/button[1]")).click();
    }

    //chooses the dates that the user entered 
    public static void chooseDates(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(5));
        Thread.sleep(500);
        WebElement departureDateBox = browser.findElement(By.cssSelector("input[placeholder='Departure']"));
        departureDateBox.sendKeys(flight.getStartDate().toString());
        Thread.sleep(200);
        WebElement returnDateBox = browser.findElement(By.cssSelector("input[placeholder='Return']"));
        Thread.sleep(200);
        returnDateBox.sendKeys(flight.getEndDate().toString());
        returnDateBox.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[1]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/div[3]/div/button"))).click();
        
        //clicking the "search" button
        browser.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[1]/div[1]/div[2]/div/button")).click();
    }

    //picks the number of stops that the user entered
    public static void chooseStops(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg[class='cqEepf NMm5M']"))).click();
        Thread.sleep(2000);
        switch (flight.getMaxStops()) {
            case 0:
                browser.findElement(By.cssSelector("input[aria-label='Nonstop only']")).click();
                break;
            case 1:
                browser.findElement(By.cssSelector("input[aria-label='1 stop or fewer']")).click();
                break;
            case 2:
                browser.findElement(By.cssSelector("input[aria-label='2 stops or fewer']")).click();
                break;    
        }
        browser.findElements(By.cssSelector("svg[class='Auu9lc NMm5M']")).get(4).click();
        Thread.sleep(2000);
    }   


    //picks the cheapest flight
    public static double chooseBestFlight(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(15));
        
        //checking if there is something that covers the 'sort by' button
        try {
            browser.findElement(By.cssSelector("[class='I0Kcef']")).click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }

        //clicking on 'sort by' button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='VfPpkd-LgbsSe VfPpkd-LgbsSe-OWXEXe-Bz112c-UbuQg VfPpkd-LgbsSe-OWXEXe-dgl2Hf ksBjEc lKxP2d LQeN7 zZJEBe']"))).click();
        
        //adding 'price' filter
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li.VfPpkd-StrnGf-rymPhb-ibnC6b"))).get(1).click();
        
        Thread.sleep(2000);

        //waiting until the flight board is visible and clicking on it to get to the returning flights page. checking it by looking for the 'round trip' element.
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='pIav2d']"))).click();
        
        //waiting again for a list item (flight) to be visible and clicking it.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='pIav2d']"))).click();

        //waiting for the new page to be loaded and then taking the price element
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='QORQHb']")));
        
        //taking the price from the text
        String price = priceElement.getAttribute("innerText").substring(1);
        price = price.replace(",", "");
        
        // converting the price text to integer and returning it
        try {
            double priceDouble = Double.parseDouble(price);
            if (priceDouble <= flight.getMaxPrice()) {
                return priceDouble;
            }
            else {
                System.out.println("Price is too high in GoogleFlights.");
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Error, price is not a number!");
        }
        return 0;
    }

    public static String getBestFlightUrl() {
        return browser.getCurrentUrl();
    }

    




}
