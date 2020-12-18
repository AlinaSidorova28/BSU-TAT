package test;

import driver.DriverSingleton;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class CustomConditions {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.setProxy(null);
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = DriverSingleton.getDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        DriverSingleton.closeDriver();
    }

    public static ExpectedCondition<Boolean> jQueryAjaxCompleted() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor) driver)
                        .executeScript(
                                "return (window.jQuery != null) && (jQuery.active == 0);"
                        );
            }
        };
    }
}