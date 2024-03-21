package Sites;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import Main.*;
import Utils.WebDriverManager;

public class Kiwi extends FlightSearchSite{
    public static ageRange adultAgeRange = new ageRange(12, 120);
    public static ageRange childAgeRange = new ageRange(2, 11);
    public static ageRange infantAgeRange = new ageRange(0, 1);
 
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

    public static void enterPlaces(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.get(FlightSearchSite.kiwiUrl);
        browser.manage().window().maximize();
        ProgressBar.updateProgressBar(43, 68,50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='ModalCloseButton']"))).click();
        browser.findElements(By.cssSelector("[data-test='PlacePickerInputPlace-close']")).get(0).click();
        ProgressBar.updateProgressBar(44, 68,50);

        WebElement departureBox = browser.findElements(By.cssSelector("[data-test='SearchField-input']")).get(0);
        departureBox.sendKeys(flight.getDepartureCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='PlacePickerRow-city']"))).click();
        ProgressBar.updateProgressBar(45, 68,50);

        WebElement landingBox = browser.findElements(By.cssSelector("[data-test='SearchField-input']")).get(1);
        landingBox.sendKeys(flight.getLandingCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='PlacePickerRow-city']"))).click();
        ProgressBar.updateProgressBar(46, 68,50);
    }

    public static void choosePassengersAges(WebDriver browser, Flight flight) {
        browser.findElement(By.cssSelector("[data-test='PassengersButton']")).click();
        ProgressBar.updateProgressBar(47, 68,50);
        WebElement plusIconAdults = browser.findElements(By.cssSelector("[aria-label='increment']")).get(0);
        WebElement minusIconAdults = browser.findElements(By.cssSelector("[aria-label='decrement']")).get(0);
        WebElement plusIconChildren = browser.findElements(By.cssSelector("[aria-label='increment']")).get(1);
        WebElement plusIconInfants = browser.findElements(By.cssSelector("[aria-label='increment']")).get(2);
        ProgressBar.updateProgressBar(48, 68,50);

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
        minusIconAdults.click();
        ProgressBar.updateProgressBar(49, 68,50);
    }

    public static void chooseDates(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));

        String startDateToSearch = flight.getStartDate().toString();
        String endDateToSearch = flight.getEndDate().toString();
        ProgressBar.updateProgressBar(50, 68,50);
        
        browser.findElements(By.cssSelector("[data-test='SearchFieldDateInput']")).get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='SearchFormDoneButton']"))).click();
        ProgressBar.updateProgressBar(51, 68,50);
        browser.findElements(By.cssSelector("[data-test='SearchFieldDateInput']")).get(0).click();
        WebElement nextMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='CalendarMoveNextButton']")));
        ProgressBar.updateProgressBar(52, 68,50);
        
        boolean foundStartDate = false;
        for (int i = 1; i <= 13; i++) {
            try {
                if (!foundStartDate) {
                    browser.findElement(By.cssSelector("[data-value=" +  "'" + startDateToSearch +  "'" + "]")).click();
                    foundStartDate = true;
                }
                else {
                    browser.findElement(By.cssSelector("[data-value=" +  "'" + endDateToSearch +  "'" + "]")).click();
                    break;
                }
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                nextMonth.click();
            }
        }
        ProgressBar.updateProgressBar(53, 68,50);
        browser.findElement(By.cssSelector("[data-test='SearchFormDoneButton']")).click();
        browser.findElement(By.cssSelector("[data-test='LandingSearchButton']")).click();
        ProgressBar.updateProgressBar(54, 68,50);
    }

    public static void chooseStops(WebDriver browser, Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        ArrayList<String> tabs = new ArrayList<>(browser.getWindowHandles());
        browser.switchTo().window(tabs.get(tabs.size() - 1));
        ProgressBar.updateProgressBar(55, 68,50);
        try {
            browser.findElement(By.cssSelector("[data-test='SearchFormFilters-button-stops']")).click();
        } catch (Exception e) {
        }
        ProgressBar.updateProgressBar(56, 68,50);
        
        switch (flight.getMaxStops()) {
            case 0:
                browser.findElements(By.cssSelector("[class='orbit-radio-icon-container relative box-border flex flex-none items-center justify-center size-icon-medium rounded-full duration-fast scale-100 transition-all ease-in-out lm:border border-[2px] border-solid active:scale-95 peer-focus:outline-blue-normal peer-focus:outline peer-focus:outline-2 bg-form-element-background']")).get(1).click();
                break;
            case 1:
                browser.findElements(By.cssSelector("[class='orbit-radio-icon-container relative box-border flex flex-none items-center justify-center size-icon-medium rounded-full duration-fast scale-100 transition-all ease-in-out lm:border border-[2px] border-solid active:scale-95 peer-focus:outline-blue-normal peer-focus:outline peer-focus:outline-2 bg-form-element-background']")).get(2).click();
                break;
            case 2:
                browser.findElements(By.cssSelector("[class='orbit-radio-icon-container relative box-border flex flex-none items-center justify-center size-icon-medium rounded-full duration-fast scale-100 transition-all ease-in-out lm:border border-[2px] border-solid active:scale-95 peer-focus:outline-blue-normal peer-focus:outline peer-focus:outline-2 bg-form-element-background']")).get(3).click();
                break;
        }
        ProgressBar.updateProgressBar(57, 68,50);
        
        try {
            browser.findElement(By.cssSelector("[data-test='SearchFormFilters-button-stops']")).click();
        } catch (Exception e) {
        } 
        ProgressBar.updateProgressBar(58, 68,50);   
    } 

    public static double chooseBestFlight(WebDriver browser, Flight flight) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        try {
            //clicking on the flight image after choosing the stops number to get to the flights page
            browser.findElement(By.cssSelector("[class='group relative flex h-full w-full flex-col justify-end overflow-hidden rounded-large p-sm shadow-action hover:shadow-action-active']")).click();
        } catch (Exception e) {
        }
        ProgressBar.updateProgressBar(59, 68,50);
        
        waitForLoadingOverlayToPass(browser);
        
        //filtering to the cheapest
        browser.findElement(By.cssSelector("[data-test='SortBy-price']")).click();
        ProgressBar.updateProgressBar(60, 68,50);
        waitForLoadingOverlayToPass(browser);

        browser.findElement(By.cssSelector("[data-test='TopNav-RegionalSettingsButton']")).click();
        browser.findElement(By.cssSelector("[data-test='CurrencySelect']")).click();
        browser.findElement(By.cssSelector("[value='ils']")).click();
        browser.findElement(By.cssSelector("[data-test='SubmitRegionalSettingsButton']")).click();
        ProgressBar.updateProgressBar(61, 68,50);

        //picking the cheapest flight   
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("booking-button"))).get(0).click();
        ProgressBar.updateProgressBar(62, 68,50);
        //some flights open a little window before redirecting to the new page
        try {
            browser.findElement(By.cssSelector("[data-test='DetailBookingButton']")).click();
        } catch (Exception e) {
        }
        ProgressBar.updateProgressBar(63, 68,50);
        browser.findElement(By.cssSelector("[data-test='MagicLogin-GuestTextLink']")).click();
        String price = "";
        
        wait = new WebDriverWait(browser, Duration.ofSeconds(4));

        try {
            price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='text-[22px] font-bold leading-[28px]']"))).getAttribute("innerText");
        }
        catch (Exception e) {
            price = browser.findElement(By.cssSelector("[class='ReservationBillTotal__TotalPriceWrapper-sc-1h93z9j-0 iYJIpy']")).getAttribute("innerText");
        }
        
        ProgressBar.updateProgressBar(64, 68,50);
        price = price.substring(2);
        price = price.replace(",", "");
        ProgressBar.updateProgressBar(65, 68,50);

        try {
            double priceDouble =  Double.parseDouble(price);
            if (priceDouble <= flight.getMaxPrice()) {
                return priceDouble;
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Error, price is not a number!");
        }
        return 0;
    }  

    public static String getBestFlightUrl(WebDriver browser) {
        ProgressBar.updateProgressBar(66, 68,50);
        String link = browser.getCurrentUrl();
        ProgressBar.updateProgressBar(67, 68,50);
        WebDriverManager.quitBrowser();
        ProgressBar.updateProgressBar(68, 68,50);
        return link;
    }

    public static void waitForLoadingOverlayToPass(WebDriver browser) {
        try {
            while (browser.findElement(By.cssSelector("[data-test='LoadingOverlay']")).isDisplayed()) {
                continue;
            }
        } catch (Exception e) {
        }
    }
}


