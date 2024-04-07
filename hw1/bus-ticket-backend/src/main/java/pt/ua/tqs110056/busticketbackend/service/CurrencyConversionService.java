package pt.ua.tqs110056.busticketbackend.service;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConversionService {

    BigDecimal convertCurrency(BigDecimal amount, Currency sourceCurrency, Currency targetCurrency);

    BigDecimal getCurrencyRate(Currency sourceCurrency, Currency targetCurrency);

}
