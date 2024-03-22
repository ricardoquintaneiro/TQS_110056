package tqs.flight;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookFlightSteps {

	private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        ChromeOptions options = new ChromeOptions().setBinary("/usr/bin/brave-browser");
        driver = new ChromeDriver(options);
        driver.get(url);
    }

	@And("I select the origin as {string}")
	public void iSelectTheOriginAs(String origin) {
		driver.findElement(By.name("fromPort")).click();
		driver.findElement(By.xpath("//option[. = '" + origin + "']")).click();
	}
	
	@And("I select the destination as {string}")
	public void iSelectTheDestinationAs(String destination) {
		driver.findElement(By.name("toPort")).click();
		driver.findElement(By.xpath("//option[. = '" + destination +"']")).click();
	}

	@And("I click find flights")
	public void iClickFindFlights() {
		driver.findElement(By.cssSelector(".btn-primary")).click();
	}

	@Then("I should see a list of available flights")
	public void iShouldSeeAListOfFlights() {
		assertThat(driver.findElements(By.cssSelector("table")).size(), equalTo(1));
	}

	@When("I select flight {int}")
	public void iSelectFlight(int flightNumber) {
		String cssSelector = "tr:nth-child(" + flightNumber + ") .btn";
		driver.findElement(By.cssSelector(cssSelector)).click();
	}

	@And("I click purchase flight")
	public void iClickPurchaseFlight() {
		driver.findElement(By.cssSelector(".btn-primary")).click();
	}

	@Then("I should see a confirmation page")
	public void iShouldSeeThePurchaseConfirmation() {
		assertThat(driver.getTitle(), equalTo("BlazeDemo Confirmation"));
		driver.quit();
	}

}