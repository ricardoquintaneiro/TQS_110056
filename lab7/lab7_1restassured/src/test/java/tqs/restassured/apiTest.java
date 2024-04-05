package tqs.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

/**
 * apiTest
 */
public class apiTest {

    @Test
    public void verifyToDosEndpointAvailable() {
        get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .statusCode(200);
    }

    @Test
    public void verifyQueryIsReturningTheCorrectObject() {
        get("https://jsonplaceholder.typicode.com/todos/4")
                .then()
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void verifyListingAllToDos() {
        get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .body("id", hasItems(198, 199));
    }

    @Test
    public void verifySpeedOfListAllToDos() {
        get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .time(lessThan(2000L));
    }
}