package pt.ua.tqs110056.busticketbackend.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.service.CreditCardService;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
        logger.info("Fetching credit card with id {}", id);
        Optional<CreditCard> creditCard = creditCardService.getCreditCardById(id);
        if (creditCard.isPresent()) {
            logger.info("Credit card found with id {}", id);
        } else {
            logger.warn("No credit card found with id {}", id);
        }
        return creditCard.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreditCard> saveCreditCard(@RequestBody CreditCard creditCard) {
        logger.info("Saving credit card with number {}", creditCard.getNumber());
        CreditCard savedCreditCard = creditCardService.saveCreditCard(creditCard);
        logger.info("Credit card saved with id {}", savedCreditCard.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreditCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCardById(@PathVariable Long id) {
        logger.info("Deleting credit card with id {}", id);
        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/number/{number}/validate")
    public ResponseEntity<Boolean> validateCreditCardNumber(@PathVariable String number) {
        logger.info("Validating credit card number {}", number);
        boolean isValid = creditCardService.validateCreditCardNumber(number);
        if (isValid) {
            logger.info("Credit card number {} is valid", number);
        } else {
            logger.warn("Credit card number {} is invalid", number);
        }
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/cvv/{cvv}/validate")
    public ResponseEntity<Boolean> validateCVV(@PathVariable String cvv) {
        logger.info("Validating CVV {}", cvv);
        boolean isValid = creditCardService.validateCVV(cvv);
        if (isValid) {
            logger.info("CVV {} is valid", cvv);
        } else {
            logger.warn("CVV {} is invalid", cvv);
        }
        return ResponseEntity.ok(isValid);
    }
}
