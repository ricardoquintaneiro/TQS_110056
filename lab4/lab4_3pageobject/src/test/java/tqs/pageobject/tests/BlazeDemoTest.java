package tqs.pageobject.tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import tqs.pageobject.webpages.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoTest {
  JavascriptExecutor js;

  @Test
  public void blazeDemo(ChromeDriver driver) {
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
