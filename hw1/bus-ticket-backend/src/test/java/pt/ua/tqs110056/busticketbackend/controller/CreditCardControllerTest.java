package pt.ua.tqs110056.busticketbackend.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.model.CreditCardType;
import pt.ua.tqs110056.busticketbackend.service.CreditCardService;

@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @MockBean
    private CreditCardService creditCardService;

    @Autowired
    private MockMvc mockMvc;

    private CreditCard creditCard;

    @BeforeEach
    void setUp() throws Exception {
        creditCard = new CreditCard(CreditCardType.VISA, "4111222233334444", "123", LocalDate.now().plusYears(1));
        creditCard.setId(1L);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetCreditCardById_thenReturnCreditCard() throws Exception {
        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.of(creditCard));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/credit-cards/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("type", Matchers.equalTo(creditCard.getType().toString()))
                    .body("number", Matchers.equalTo(creditCard.getNumber()))
                    .body("cvv", Matchers.equalTo(creditCard.getCvv()))
                    .body("expirationDate", Matchers.equalTo(creditCard.getExpirationDate().toString()));
    }

    @Test
    void whenGetCreditCardByUnknownId_thenReturnNotFound() throws Exception {
        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/credit-cards/1")
                .then()
                    .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenSaveCreditCard_thenReturnCreditCard() throws Exception {
        Mockito.when(creditCardService.saveCreditCard(Mockito.any())).thenReturn(creditCard);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(creditCard)
                .when()
                    .post("/credit-cards")
                .then()
                    .status(HttpStatus.CREATED)
                    .body("type", Matchers.equalTo(creditCard.getType().toString()))
                    .body("number", Matchers.equalTo(creditCard.getNumber()))
                    .body("cvv", Matchers.equalTo(creditCard.getCvv()))
                    .body("expirationDate", Matchers.equalTo(creditCard.getExpirationDate().toString()));
    }

    @Test
    void whenDeleteCreditCardById_thenNoContent() throws Exception {
        Mockito.doNothing().when(creditCardService).deleteCreditCardById(1L);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .delete("/credit-cards/1")
                .then()
                    .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void whenValidateCreditCardNumber_thenReturnHttpOK() throws Exception {
        Mockito.when(creditCardService.validateCreditCardNumber(creditCard.getNumber())).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/credit-cards/number/" + creditCard.getNumber() + "/validate")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("true"));
    }

    @Test
    void whenValidateCVV_thenReturnHttpOK() throws Exception {
        Mockito.when(creditCardService.validateCVV(creditCard.getCvv())).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/credit-cards/cvv/" + creditCard.getCvv() + "/validate")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("true"));
    }
    
}