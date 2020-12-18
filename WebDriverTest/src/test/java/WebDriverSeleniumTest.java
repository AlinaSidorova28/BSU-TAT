import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WebDriverSeleniumTest {

    WebDriver driver;
    final String URL="https://markformelle.by/";
    final String CORRECT_QUERY="колготки";
    final String FORM_ID="header-search-form";
    final String SEARCH_XPATH = "//*[@id='header-search-form']/div/input[1]";
    final String BUTTON_XPATH="//*[@title='След.']";
    final String RESULTS_CLASSNAME = "zag";
    final int PAGES_AMOUNT = 16;

    @BeforeMethod(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.setProxy(null);
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }

    @Test (description = "test search results to match the correct query", invocationCount = 3)
    public void commonSearchTermResultsMatchCorrectQuery() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        driver.manage().deleteAllCookies();
        driver.get(URL);
        new WebDriverWait(driver, 10)
                .until(CustomConditions.jQueryAjaxCompleted());

        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='promo']")));

        WebElement form = waitForElementLocatedBy(driver, By.id(FORM_ID));
        form.click();

        WebElement searchInput = waitForElementLocatedBy(driver, By.xpath(SEARCH_XPATH));
        searchInput.sendKeys(CORRECT_QUERY + "\n");

        List<String> searchResults = new ArrayList<>();
        for (int i = 0; i < PAGES_AMOUNT; i++) {
            searchResults
                    .addAll(waitForElementsLocatedBy(driver, By.className(RESULTS_CLASSNAME))
                            .stream()
                            .map(s -> s.getAttribute("title").toLowerCase())
                            .collect(Collectors.toList()));
            if (i != PAGES_AMOUNT - 1) {
                waitForElementLocatedBy(driver, By.xpath(BUTTON_XPATH)).click();
            }
        }

        System.out.println("Search results number: " + searchResults.size());
        Assert.assertTrue(searchResults.stream().allMatch(s -> s.contains(CORRECT_QUERY)));
    }

    private static WebElement waitForElementLocatedBy(WebDriver driver, By by) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Element was not found")
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    private static List<WebElement> waitForElementsLocatedBy(WebDriver driver, By by) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Elements were not found")
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}