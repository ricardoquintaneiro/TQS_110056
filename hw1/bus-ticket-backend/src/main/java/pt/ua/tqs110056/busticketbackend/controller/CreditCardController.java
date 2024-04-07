package pt.ua.tqs110056.busticketbackend.controller;

import java.util.Optional;

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

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
        Optional<CreditCard> creditCard = creditCardService.getCreditCardById(id);
        return creditCard.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreditCard> saveCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard savedCreditCard = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreditCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCardById(@PathVariable Long id) {
        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{number}/validate")
    public ResponseEntity<Boolean> validateCreditCardNumber(@PathVariable String number) {
        boolean isValid = creditCardService.validateCreditCardNumber(number);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/{cvv}/validate")
    public ResponseEntity<Boolean> validateCVV(@PathVariable String cvv) {
        boolean isValid = creditCardService.validateCVV(cvv);
        return ResponseEntity.ok(isValid);
    }
}
