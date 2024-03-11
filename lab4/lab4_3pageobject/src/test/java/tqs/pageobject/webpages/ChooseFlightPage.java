package tqs.pageobject.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChooseFlightPage {
    private WebDriver driver;

    @FindBy(css = "tr:nth-child(3) .btn")
    private WebElement thirdFlightButton;

    public ChooseFlightPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickThirdFlightButton() {
        this.thirdFlightButton.click();
    }

}
