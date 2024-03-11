package tqs.pageobject.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "address")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    @FindBy(id = "cardType")
    private WebElement cardTypeDropdown;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement creditCardMonth;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYear;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(css = ".checkbox")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseFlightButton;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setInputName(String name) {
        this.inputName.click();
        this.inputName.sendKeys(name);
    }

    public void setAddress(String address) {
        this.address.sendKeys(address);
    }

    public void setCity(String city) {
        this.city.sendKeys(city);
    }

    public void setState(String state) {
        this.state.sendKeys(state);
    }

    public void setZipCode(String zipCode) {
        this.zipCode.sendKeys(zipCode);
    }

    public void selectCardType(String cardType) {
        this.cardTypeDropdown.click();
        this.cardTypeDropdown.findElement(By.xpath("//option[. = '" + cardType + "']")).click();
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber.click();
        this.creditCardNumber.sendKeys(creditCardNumber);
    }

    public void setCreditCardMonth(String creditCardMonth) {
        this.creditCardMonth.click();
        this.creditCardMonth.sendKeys(creditCardMonth);
    }

    public void setCreditCardYear(String creditCardYear) {
        this.creditCardYear.click();
        this.creditCardYear.sendKeys(creditCardYear);
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard.sendKeys(nameOnCard);
    }

    public void clickRememberMeCheckbox() {
        this.rememberMeCheckbox.click();
    }

    public void clickPurchaseFlight() {
        this.purchaseFlightButton.click();
    }

}
