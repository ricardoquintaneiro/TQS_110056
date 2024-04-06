package pt.ua.tqs110056.busticketbackend.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CreditCardType type;
    private String number;
    private String cvv;
    private LocalDate expirationDate;

    public CreditCard() {
    }

    public boolean isValidCreditCardNumber(String creditCardNumber) {
        String creditCardNumberPattern = "^((4\\d{3})|(5[1-5]\\d{2})|(6011)|(7\\d{3}))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13}$";
        Pattern pattern = Pattern.compile(creditCardNumberPattern);
        return pattern.matcher(creditCardNumber).matches();
    }
    
    public boolean isValidCVV(String cvv) {
        String cvvPattern = "^[0-9]{3,4}$";
        Pattern pattern = Pattern.compile(cvvPattern);
        return pattern.matcher(cvv).matches();
    }
}
