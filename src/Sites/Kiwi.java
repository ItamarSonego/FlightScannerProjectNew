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

public class Kiwi extends FlightSearchSite{
    public static ageRange adultAgeRange = new ageRange(12, 120);
    public static ageRange childAgeRange = new ageRange(2, 11);
    public static ageRange infantAgeRange = new ageRange(0, 1);

    public static Object[] searchFlight(Flight flight) throws InterruptedException {
        enterPlaces(flight);
        choosePassengers(flight);
        chooseDates(flight);
        chooseStops(flight);
        
        Object[] priceAndLink = new Object[2];
        priceAndLink[0] = chooseBestFlight(flight);
        priceAndLink[1] = getBestFlightUrl();
        return priceAndLink;
    }

    public static void enterPlaces(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        browser.switchTo().newWindow(WindowType.TAB);
        browser.get(FlightSearchSite.kiwiUrl);
        browser.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='ModalCloseButton']"))).click();
        browser.findElements(By.cssSelector("[data-test='PlacePickerInputPlace-close']")).get(0).click();
        
        WebElement departureBox = browser.findElements(By.cssSelector("[data-test='SearchField-input']")).get(0);
        departureBox.sendKeys(flight.getDepartureCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='PlacePickerRow-city']"))).click();


        WebElement landingBox = browser.findElements(By.cssSelector("[data-test='SearchField-input']")).get(1);
        landingBox.sendKeys(flight.getLandingCity());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='PlacePickerRow-city']"))).click();
    }

    public static void choosePassengers(Flight flight) {
        browser.findElement(By.cssSelector("[data-test='PassengersButton']")).click();
        WebElement plusIconAdults = browser.findElements(By.cssSelector("[aria-label='increment']")).get(0);
        WebElement minusIconAdults = browser.findElements(By.cssSelector("[aria-label='decrement']")).get(0);
        WebElement plusIconChildren = browser.findElements(By.cssSelector("[aria-label='increment']")).get(1);
        WebElement plusIconInfants = browser.findElements(By.cssSelector("[aria-label='increment']")).get(2);

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
    }

    public static void chooseDates(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));

        String startDateToSearch = flight.getStartDate().toString();
        String endDateToSearch = flight.getEndDate().toString();

        browser.findElements(By.cssSelector("[data-test='SearchFieldDateInput']")).get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='SearchFormDoneButton']"))).click();
        browser.findElements(By.cssSelector("[data-test='SearchFieldDateInput']")).get(0).click();
        WebElement nextMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='CalendarMoveNextButton']")));
        
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
        browser.findElement(By.cssSelector("[data-test='SearchFormDoneButton']")).click();
        browser.findElement(By.cssSelector("[data-test='LandingSearchButton']")).click();
    }

    public static void chooseStops(Flight flight) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        ArrayList<String> tabs = new ArrayList<>(browser.getWindowHandles());
        browser.switchTo().window(tabs.get(tabs.size() - 1));

        try {
            browser.findElement(By.cssSelector("[data-test='SearchFormFilters-button-stops']")).click();
        } catch (Exception e) {
        }

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

        try {
            browser.findElement(By.cssSelector("[data-test='SearchFormFilters-button-stops']")).click();
        } catch (Exception e) {
        }    
    } 

    public static double chooseBestFlight(Flight flight) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        
        try {
            //clicking on the flight image after choosing the stops number to get to the flights page
            browser.findElement(By.cssSelector("[class='group relative flex h-full w-full flex-col justify-end overflow-hidden rounded-large p-sm shadow-action hover:shadow-action-active']")).click();
        } catch (Exception e) {
        }
        
        waitForLoadingOverlayToPass();
        
        //filtering to the cheapest
        browser.findElement(By.cssSelector("[data-test='SortBy-price']")).click();

        waitForLoadingOverlayToPass();

        browser.findElement(By.cssSelector("[data-test='TopNav-RegionalSettingsButton']")).click();
        browser.findElement(By.cssSelector("[data-test='CurrencySelect']")).click();
        browser.findElement(By.cssSelector("[value='ils']")).click();
        browser.findElement(By.cssSelector("[data-test='SubmitRegionalSettingsButton']")).click();

        //picking the cheapest flight   
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("booking-button"))).get(0).click();

        //some flights open a little window before redirecting to the new page
        try {
            browser.findElement(By.cssSelector("[data-test='DetailBookingButton']")).click();
        } catch (Exception e) {
        }

        browser.findElement(By.cssSelector("[data-test='MagicLogin-GuestTextLink']")).click();
        String price = "";
        
        wait = new WebDriverWait(browser, Duration.ofSeconds(4));

        try {
            price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='text-[22px] font-bold leading-[28px]']"))).getAttribute("innerText");
        }
        catch (Exception e) {
            price = browser.findElement(By.cssSelector("[class='ReservationBillTotal__TotalPriceWrapper-sc-1h93z9j-0 iYJIpy']")).getAttribute("innerText");
        }
        
        price = price.substring(2);
        price = price.replace(",", "");
        

        try {
            double priceDouble =  Double.parseDouble(price);
            if (priceDouble <= flight.getMaxPrice()) {
                return priceDouble;
            }
            else {
                System.out.println("Price is too high in Kiwi.");
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Error, price is not a number!");
        }
        return 0;
    }  

    public static String getBestFlightUrl() {
        String link = browser.getCurrentUrl();
        browser.quit();
        return link;
    }

    public static void waitForLoadingOverlayToPass() {
        try {
            while (browser.findElement(By.cssSelector("[data-test='LoadingOverlay']")).isDisplayed()) {
                continue;
            }
        } catch (Exception e) {
        }
    }
}


