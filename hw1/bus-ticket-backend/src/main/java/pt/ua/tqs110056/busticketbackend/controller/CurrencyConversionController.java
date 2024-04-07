package pt.ua.tqs110056.busticketbackend.controller;

import java.math.BigDecimal;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.service.CurrencyConversionService;

@RestController
@RequestMapping("/currency")
public class CurrencyConversionController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/convert")
    public ResponseEntity<BigDecimal> convertCurrency(
            @RequestParam BigDecimal amount,
            @RequestParam Currency sourceCurrency,
            @RequestParam Currency targetCurrency) {
        logger.info("Converting {} from {} to {}", amount, sourceCurrency, targetCurrency);
        BigDecimal convertedAmount = currencyConversionService.convertCurrency(amount, sourceCurrency, targetCurrency);
        logger.info("Converted amount is {}", convertedAmount);
        return ResponseEntity.ok(convertedAmount);
    }

    @GetMapping("/rate")
    public ResponseEntity<BigDecimal> getCurrencyRate(
            @RequestParam Currency sourceCurrency,
            @RequestParam Currency targetCurrency) {
        logger.info("Getting rate from {} to {}", sourceCurrency, targetCurrency);
        BigDecimal rate = currencyConversionService.getCurrencyRate(sourceCurrency, targetCurrency);
        logger.info("Rate from {} to {} is {}", sourceCurrency, targetCurrency, rate);
        return ResponseEntity.ok(rate);
    }

}
