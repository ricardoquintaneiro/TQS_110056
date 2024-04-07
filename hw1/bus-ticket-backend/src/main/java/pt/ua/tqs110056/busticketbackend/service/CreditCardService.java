package pt.ua.tqs110056.busticketbackend.service;

import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.CreditCard;

public interface CreditCardService {

    Optional<CreditCard> getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard creditCard);

    void deleteCreditCardById(Long id);

    boolean validateCreditCardNumber(String number);

    boolean validateCVV(String cvv);

}
