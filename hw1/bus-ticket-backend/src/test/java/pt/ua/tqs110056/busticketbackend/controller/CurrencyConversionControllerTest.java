package pt.ua.tqs110056.busticketbackend.controller;

import java.math.BigDecimal;
import java.util.Currency;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import pt.ua.tqs110056.busticketbackend.service.CurrencyConversionService;

@WebMvcTest(CurrencyConversionController.class)
public class CurrencyConversionControllerTest {

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenConvertingAmount_thenReturnStatus200() throws Exception {
        Mockito.when(currencyConversionService.convertCurrency(BigDecimal.valueOf(1.0), Currency.getInstance("EUR"), Currency.getInstance("USD"))).thenReturn(BigDecimal.valueOf(1.2));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/currency/convert?from=EUR&to=USD&amount=1.0")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("1.2"));
    }

    @Test
    void whenGettingRateOfKnownCurrencies_thenReturnStatus200() throws Exception {
        Mockito.when(currencyConversionService.getCurrencyRate(Currency.getInstance("EUR"), Currency.getInstance("USD"))).thenReturn(BigDecimal.valueOf(1.2));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/currency/rate?from=EUR&to=USD")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("1.2"));
    }

    @Test
    void whenGettingRateOfUnknownCurrencies_thenReturnStatus400() throws Exception {
        Currency unknownCurrency = Mockito.mock(Currency.class);
        Mockito.when(currencyConversionService.getCurrencyRate(unknownCurrency, Currency.getInstance("USD"))).thenReturn(BigDecimal.valueOf(2));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/currency/rate?from=ABC&to=USD")
                .then()
                    .status(HttpStatus.BAD_REQUEST);
    }
    
}