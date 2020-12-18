package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.reporters.jq.Main;
import util.ElementWaiting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPage {
    private WebDriver driver;

    private By formIdBy = By.id("header-search-form");
    private By searchFormBy = By.xpath("//*[@id='header-search-form']/div/input[1]");
    private By resultsBy = By.className("zag");
    private By buttonBy = By.xpath("//*[@title='След.']");
    private final int PAGES_AMOUNT = 16;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchPage openSearchForm() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='promo']")));
        new MainPage(driver).goToPage(formIdBy);
        return this;
    }

    public SearchPage inputSearchData(String correctQuery) {
        ElementWaiting
                .waitForElementToBeClickableBy(driver, searchFormBy)
                .sendKeys(correctQuery + "\n");
        return this;
    }

    public List<String> gatherResults() {
        List<String> searchResults = new ArrayList<>();
        MainPage page = new MainPage(driver);
        for (int i = 0; i < PAGES_AMOUNT; i++) {
            searchResults
                    .addAll(ElementWaiting.waitForElementsLocatedBy(driver, resultsBy)
                            .stream()
                            .map(s -> s.getAttribute("title").toLowerCase())
                            .collect(Collectors.toList()));
            if (i != PAGES_AMOUNT - 1) {
                page.goToPage(buttonBy);
            }
        }

        return searchResults;
    }
}
