package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ElementInteraction;
import util.ElementWaiting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage {
    private WebDriver driver;
    private List<Double> oldPriceListSorted;

    private By additionalElementBy = By.id("arrFilter_217_3233089245-styler");
    private By sizeButtonBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']/form/ul/li[9]/div/div[1]/a[@href='javascript:void(0);']");
    private By sizeChoiceBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']/form/ul/li[9]/div/div[2]/ul/li[1]/label");
    private By sortButtonBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']//div[@class='jq-selectbox__trigger-arrow']");
    private By sortChoiceBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']//li[@data-sort='up']");
    private By priceBy = By.xpath(".//div[contains(@class, 'catalog-cost')]/span[@class='_price']");

    @FindBy(xpath="//*[@data-entity='item']")
    private List<WebElement> items;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CatalogPage chooseFilter() {
        ElementWaiting.waitForElementToBeClickableBy(driver, additionalElementBy);
        int size = items.size();

        ElementInteraction.clickOnSource(driver, sizeButtonBy);
        ElementInteraction.clickOnSource(driver, sizeChoiceBy);
        new WebDriverWait(driver, 10)
                .until((d) -> size != items.size());
        return this;
    }

    public CatalogPage chooseSort() {
        ElementWaiting.waitForElementToBeClickableBy(driver, additionalElementBy);
        oldPriceListSorted = getPrices(collectItems(priceBy));
        oldPriceListSorted.sort(Double::compareTo);
        int last = items.size() - 1;
        String oldItem = items.get(last).getText();

        ElementInteraction.clickOnSource(driver, sortButtonBy);
        ElementInteraction.clickOnSource(driver, sortChoiceBy);

        new WebDriverWait(driver, 10)
                .until((d) -> !oldItem.equals(items.get(last).getText()));
        return this;
    }

    private List<String> collectItems(By by) {
        List<String> res = new ArrayList<>();
        for (WebElement e : items) {
            res.addAll(e.findElements(by)
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList()));
        }
        return res;
    }

    private List<Double> getPrices(List<String> priceList) {
        return priceList
                .stream()
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .filter(i -> !i.equals("BYN"))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public boolean checkSort() {
        return getPrices(collectItems(priceBy)).equals(oldPriceListSorted);
    }

    public boolean checkSizes(String sizeValue) {
        List<String> sizeList = collectItems(By.xpath(".//div[@class='catalog-size__item']/label/span"));
        return sizeList.stream().filter(s -> s.equals(sizeValue)).count() == items.size();
    }
}
