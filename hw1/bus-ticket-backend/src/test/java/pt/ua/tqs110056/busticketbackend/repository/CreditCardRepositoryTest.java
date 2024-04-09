package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.CreditCard;

@DataJpaTest
class CreditCardRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    void findById_ShouldReturnCreditCard_WhenIdExists() {
        CreditCard creditCard = new CreditCard();
        entityManager.persistAndFlush(creditCard);

        Optional<CreditCard> found = creditCardRepository.findById(creditCard.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(creditCard));
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<CreditCard> found = creditCardRepository.findById(Long.valueOf(-1L));

        assertThat(found, is(Optional.empty()));
    }
}
