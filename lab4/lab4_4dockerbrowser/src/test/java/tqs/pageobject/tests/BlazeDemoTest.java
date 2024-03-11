package tqs.pageobject.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.seljup.BrowserType;
import tqs.pageobject.webpages.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoTest {
  JavascriptExecutor js;

  @Test
  public void testWithChrome(@DockerBrowser(type = BrowserType.CHROME) WebDriver driver) {
    blazeDemo(driver);
  }

  @Test
  public void testWithFirefox(@DockerBrowser(type = BrowserType.FIREFOX) WebDriver driver) {
    blazeDemo(driver);
  }

  public void blazeDemo(WebDriver driver) {
    HomePage home = new HomePage(driver);
    home.selectFromPort("Portland");
    home.selectToPort("Cairo");
    home.clickFindFlights();

    ChooseFlightPage chooseFlight = new ChooseFlightPage(driver);
    chooseFlight.clickThirdFlightButton();

    PurchasePage purchase = new PurchasePage(driver);
    purchase.setInputName("Teste");
    purchase.setAddress("End");
    purchase.setCity("Aveiro");
    purchase.setState("Estado");
    purchase.setZipCode("123");
    purchase.selectCardType("American Express");
    purchase.setCreditCardNumber("1231123");
    purchase.setCreditCardMonth("12");
    purchase.setCreditCardYear("2024");
    purchase.setNameOnCard("John Wick");
    purchase.clickRememberMeCheckbox();
    purchase.clickPurchaseFlight();

    ConfirmationPage confirmation = new ConfirmationPage(driver);
    assertThat(confirmation.getTitle()).isEqualTo("BlazeDemo Confirmation");
  }
}
