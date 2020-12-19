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
import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage {
    private WebDriver driver;

    private By sizeButtonBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']/form/ul/li[9]/div/div[1]/a[@href='javascript:void(0);']");
    private By sizeChoiceBy = By.xpath("//*[@id='comp_44aaac3231a04d82737353aee8debb62']/form/ul/li[9]/div/div[2]/ul/li[1]/label");

    @FindBy(xpath="//*[@data-entity='item']")
    private List<WebElement> items;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CatalogPage chooseFilter() {
        ElementWaiting.waitForElementToBeClickableBy(driver, By.xpath("//*[@id='arrFilter_217_3233089245-styler']"));
        int size = items.size();

        ElementInteraction.clickOnSource(driver, sizeButtonBy);
        ElementInteraction.clickOnSource(driver, sizeChoiceBy);
        new WebDriverWait(driver, 10)
                .until((d) -> size != items.size());
        return this;
    }

    public boolean collectItems(String stringSize) {
        List<String> sizeList = new ArrayList<>();
        for (WebElement e : items) {
            sizeList.addAll(e.findElements(By.xpath(".//*[@class='catalog-size__item']/label/span"))
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList()));
        }

        return sizeList.stream().filter(s -> s.equals(stringSize)).count() == items.size();
    }
}
