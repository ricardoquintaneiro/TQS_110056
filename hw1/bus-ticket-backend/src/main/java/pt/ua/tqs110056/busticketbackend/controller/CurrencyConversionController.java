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
            @RequestParam Currency from,
            @RequestParam Currency to) {
        logger.info("Converting {} from {} to {}", amount, from, to);
        BigDecimal convertedAmount = currencyConversionService.convertCurrency(amount, from, to);
        logger.info("Converted amount is {}", convertedAmount);
        return ResponseEntity.ok(convertedAmount);
    }

    @GetMapping("/rate")
    public ResponseEntity<BigDecimal> getCurrencyRate(
            @RequestParam Currency from,
            @RequestParam Currency to) {
        logger.info("Getting rate from {} to {}", from, to);
        BigDecimal rate = currencyConversionService.getCurrencyRate(from, to);
        logger.info("Rate from {} to {} is {}", from, to, rate);
        return ResponseEntity.ok(rate);
    }

}
