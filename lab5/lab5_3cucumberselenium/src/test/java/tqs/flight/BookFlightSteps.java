package tqs.flight;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BookFlightSteps {

	private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(url);
    }

	@And("I select the origin as {string}")
	public void iSelectTheOriginAs(String origin) {
		driver.findElement(By.name("fromPort")).click();
		driver.findElement(By.id("Boston")).click();
	}
	
	@And("I select the destination as {string}")
	public void iSelectTheDestinationAs(String destination) {
		driver.findElement(By.name("toPort")).click();
		driver.findElement(By.id("Dublin")).click();
	}

	@And("I click find flights")
	public void iClickFindFlights() {
		driver.findElement(By.cssSelector(".btn-primary")).click();
	}

}