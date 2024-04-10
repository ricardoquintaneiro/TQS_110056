package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.model.CreditCardType;
import pt.ua.tqs110056.busticketbackend.repository.CreditCardRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class CreditCardRestControllerIT {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.1")
            .withUsername("ricardo")
            .withPassword("homework1")
            .withDatabaseName("bustickettestcontainers");
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CreditCardRepository repository;

    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        repository.deleteAll();
        creditCard = new CreditCard(CreditCardType.VISA, "4111222233334444", "123", LocalDate.now().plusYears(1));
        repository.saveAndFlush(creditCard);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenGetCreditCardById_thenReturnCreditCard() {
        RestAssured
            .given()
                .pathParam("id", creditCard.getId().intValue())
            .when()
                .get("/api/credit-cards/{id}")
            .then()
                .statusCode(200)
                .body("type", equalTo(creditCard.getType().toString()))
                .body("number", equalTo(creditCard.getNumber()))
                .body("cvv", equalTo(creditCard.getCvv()))
                .body("expirationDate", equalTo(creditCard.getExpirationDate().toString()));
    }

    @Test
    void whenGetCreditCardByUnknownId_thenReturnNotFound() {
        RestAssured
            .given()
                .pathParam("id", 999)
            .when()
                .get("/api/credit-cards/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    void whenSaveCreditCard_thenReturnCreditCard() {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(creditCard)
            .when()
                .post("/api/credit-cards")
            .then()
                .statusCode(201);
    }

    @Test
    void whenDeleteCreditCardById_thenNoContent() {
        RestAssured
            .given()
                .pathParam("id", creditCard.getId().intValue())
            .when()
                .delete("/api/credit-cards/{id}")
            .then()
                .statusCode(204);
    }

    @Test
    void whenValidateCreditCardNumber_thenReturnHttpOK() {
        RestAssured
            .given()
                .pathParam("number", creditCard.getNumber())
            .when()
                .get("/api/credit-cards/number/{number}/validate")
            .then()
                .statusCode(200)
                .body(equalTo("true"));
    }

    @Test
    void whenValidateCVV_thenReturnHttpOK() {
        RestAssured
            .given()
                .pathParam("cvv", "123")
            .when()
                .get("/api/credit-cards/cvv/{cvv}/validate")
            .then()
                .statusCode(200)
                .body(equalTo("true"));
    }
}