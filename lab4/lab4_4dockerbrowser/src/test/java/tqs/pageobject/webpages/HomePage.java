package tqs.pageobject.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_URL = "https://blazedemo.com/";

    @FindBy(name = "fromPort")
    private WebElement fromPortDropdown;

    @FindBy(name = "toPort")
    private WebElement toPortDropdown;

    @FindBy(css = ".btn-primary")
    private WebElement btnPrimary;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void selectFromPort(String fromPort) {
        this.fromPortDropdown.click();
        this.fromPortDropdown.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();
    }

    public void selectToPort(String toPort) {
        this.toPortDropdown.click();
        this.toPortDropdown.findElement(By.xpath("//option[. = '" + toPort + "']")).click();
    }

    public void clickFindFlights() {
        this.btnPrimary.click();
    }

}
