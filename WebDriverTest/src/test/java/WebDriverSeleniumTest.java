import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebDriverSeleniumTest {

    WebDriver driver;
    final String URL="https://markformelle.by/";
    final String CORRECT_QUERY="колготки";
    final String FORM_ID="header-search-form";
    final String SEARCH_XPATH = "//*[@id='header-search-form']/div/input[1]";
    final String BUTTON_XPATH="//*[@title='След.']";
    final String RESULTS_CLASSNAME = "zag";

    @BeforeMethod(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.setProxy(null);
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }

    @Test (description = "test search results to match the correct query")
    public void commonSearchTermResultsMatchCorrectQuery() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        driver.manage().deleteAllCookies();
        driver.get(URL);
        new WebDriverWait(driver, 10)
                .until(CustomConditions.jQueryAjaxCompleted());

        WebElement form = waitForElementLocatedBy(driver, By.id(FORM_ID));
        form.click();
        WebElement searchInput = waitForElementLocatedBy(driver, By.xpath(SEARCH_XPATH));
        searchInput.sendKeys(CORRECT_QUERY + "\n");

        List<String> searchResults = driver
                .findElements(By.className(RESULTS_CLASSNAME))
                .stream()
                .map(s -> s.getAttribute("title").toLowerCase())
                .collect(Collectors.toList());
        List<WebElement> pages = driver.findElements(By.xpath("/html/body/section/div/div[2]/div[2]/section/div/ul/li/a"));
        int amountOfPages = Integer.parseInt(pages.get(pages.size() - 1).getText());

        for (int i = 1; i < amountOfPages; i++) {
            List<WebElement> arrow = waitForElementsLocatedBy(driver, By.xpath(BUTTON_XPATH));
            if (arrow.size() == 1) {
                arrow.get(0).click();
            } else {
                arrow.get(1).click();
            }
            searchResults
                    .addAll(waitForElementsLocatedBy(driver, By.className(RESULTS_CLASSNAME))
                            .stream()
                            .map(s -> s.getAttribute("title").toLowerCase())
                            .collect(Collectors.toList()));
        }

        System.out.println("Search results number: " + searchResults.size());
        Assert.assertTrue(
                searchResults
                        .stream()
                        .allMatch(s -> s.contains(CORRECT_QUERY))
        );
    }

    private static WebElement waitForElementLocatedBy(WebDriver driver, By by) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private static List<WebElement> waitForElementsLocatedBy(WebDriver driver, By by) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}