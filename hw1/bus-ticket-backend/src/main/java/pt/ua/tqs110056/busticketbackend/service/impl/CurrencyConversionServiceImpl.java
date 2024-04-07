package pt.ua.tqs110056.busticketbackend.service.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pt.ua.tqs110056.busticketbackend.service.CurrencyConversionService;

@Service
@EnableCaching
public class CurrencyConversionServiceImpl implements CurrencyConversionService {


    private static final String API_URL = "https://api.frankfurter.app/latest?from=%s&to=%s";
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionServiceImpl.class);

    private final RestTemplate restTemplate;

    public CurrencyConversionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal convertCurrency(BigDecimal amount, Currency sourceCurrency, Currency targetCurrency) {
        BigDecimal rate = getCurrencyRate(sourceCurrency, targetCurrency);
        return amount.multiply(rate);
    }

    @Override
    @Cacheable("currencyRates")
    public BigDecimal getCurrencyRate(Currency sourceCurrency, Currency targetCurrency) {
        try {
            URI uri = new URI(String.format(API_URL, sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode()));
            logger.debug("Fetching currency conversion rate from: {}", uri);
            CurrencyConversionResponse response = restTemplate
                    .getForObject(uri, CurrencyConversionResponse.class);

            if (response == null || !response.getRates().containsKey(targetCurrency.getCurrencyCode())) {
                logger.error("Failed to get currency conversion rate.");
                throw new CurrencyConversionException("Failed to get currency conversion rate.");
            }

            logger.info("Currency conversion rate retrieved successfully: {} to {}", sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
            return response.getRates().get(targetCurrency.getCurrencyCode());
        } catch (URISyntaxException e) {
            logger.error("Failed to get currency conversion rate due to invalid URI.", e);
            throw new CurrencyConversionException("Failed to get currency conversion rate due to invalid URI.");
        }
    }
}