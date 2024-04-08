package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.model.CreditCardType;
import pt.ua.tqs110056.busticketbackend.repository.CreditCardRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.CreditCardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;
    
    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        creditCard = new CreditCard(CreditCardType.MASTERCARD, "5555555555554444", "123", LocalDate.now().plusYears(1));
        creditCard.setId(1L);
    }

    @Test
    public void whenGetCreditCardById_ThenItShouldReturnCreditCard() {
        Mockito.when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

        Optional<CreditCard> foundCreditCard = creditCardService.getCreditCardById(1L);

        assertThat(foundCreditCard.get(), equalTo(creditCard));
    }

    @Test
    public void whenGetCreditCardByIdAndNoCreditCardExists_ThenItShouldReturnEmptyOptional() {
        Mockito.when(creditCardRepository.findById(-1L)).thenReturn(Optional.empty());

        Optional<CreditCard> foundCreditCard = creditCardService.getCreditCardById(-1L);

        assertThat(foundCreditCard.isEmpty(), is(true));
    }

    @Test
    public void whenSaveCreditCard_ThenItShouldReturnSavedCreditCard() {
        Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);

        CreditCard savedCreditCard = creditCardService.saveCreditCard(creditCard);

        assertThat(savedCreditCard, equalTo(creditCard));
    }

    @Test
    public void whenDeleteCreditCardById_ThenItShouldDeleteCreditCard() {
        creditCardService.deleteCreditCardById(1L);

        Mockito.verify(creditCardRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenValidateCreditCardNumber_ThenItShouldReturnTrue() {
        boolean isValid = creditCardService.validateCreditCardNumber("5555555555554444");

        assertThat(isValid, is(true));
    }

    @Test
    public void whenValidateCreditCardNumber_ThenItShouldReturnFalse() {
        boolean isValid = creditCardService.validateCreditCardNumber("1234567890123456");

        assertThat(isValid, is(false));
    }

    @Test
    public void whenValidateCVV_ThenItShouldReturnTrue() {
        boolean isValid = creditCardService.validateCVV("123");

        assertThat(isValid, is(true));
    }

    @Test
    public void whenValidateCVV_ThenItShouldReturnFalse() {
        boolean isValid = creditCardService.validateCVV("12345");

        assertThat(isValid, is(false));
    }
}