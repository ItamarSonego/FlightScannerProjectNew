package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {
    private static WebDriver browser;

    public static WebDriver getBrowser() {
        if (browser == null) {
            ChromeOptions options = new ChromeOptions();
            // options.addArguments("--headless=new");
            // options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
            browser = new ChromeDriver(options);
        }
        return browser;
    }

    public static void quitBrowser() {
        if (browser != null) {
            browser.quit();
            browser = null;
        }
    }
}
