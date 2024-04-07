package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.repository.CreditCardRepository;
import pt.ua.tqs110056.busticketbackend.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Optional<CreditCard> getCreditCardById(Long id) {
        return creditCardRepository.findById(id);
    }

    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCardById(Long id) {
        creditCardRepository.deleteById(id);
    }

    @Override
    public boolean validateCreditCardNumber(String number) {
        return CreditCard.isValidCreditCardNumber(number);
    }

    @Override
    public boolean validateCVV(String cvv) {
        return CreditCard.isValidCVV(cvv);
    }
    
}
