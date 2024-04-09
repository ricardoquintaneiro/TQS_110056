package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import pt.ua.tqs110056.busticketbackend.service.impl.CurrencyConversionResponse;
import pt.ua.tqs110056.busticketbackend.service.impl.CurrencyConversionServiceImpl;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConversionServiceImpl currencyConversionService;

    private static Currency sourceCurrency;
    private static Currency targetCurrency;
    private static BigDecimal conversionRate;
    private static CurrencyConversionResponse response;

    @BeforeAll
    static void setUp() {
        sourceCurrency = Currency.getInstance("EUR");
        targetCurrency = Currency.getInstance("USD");
        conversionRate = BigDecimal.valueOf(1.08);
        response = new CurrencyConversionResponse();
        response.setRates(Map.of(targetCurrency.getCurrencyCode(), conversionRate));
    }

    @Test
    void whenConvertCurrency_ThenItShouldReturnConvertedValue() {
        BigDecimal amount = BigDecimal.valueOf(13.37);
        Mockito.when(restTemplate.getForObject(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);

        BigDecimal convertedValue = currencyConversionService.convertCurrency(amount, sourceCurrency, targetCurrency);

        assertThat(convertedValue, equalTo(amount.multiply(conversionRate)));
    }

    @Test
    void whenGetCurrencyRate_ThenItShouldReturnCurrencyRate() {
        Mockito.when(restTemplate.getForObject(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);

        BigDecimal currencyRate = currencyConversionService.getCurrencyRate(sourceCurrency, targetCurrency);

        assertThat(currencyRate, equalTo(conversionRate));
    }

}