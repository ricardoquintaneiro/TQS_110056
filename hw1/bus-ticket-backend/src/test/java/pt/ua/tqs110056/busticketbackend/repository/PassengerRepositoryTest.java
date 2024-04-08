package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.Passenger;

@DataJpaTest
public class PassengerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    public void findById_ShouldReturnPassenger_WhenIdExists() {
        Passenger passenger = new Passenger();
        entityManager.persistAndFlush(passenger);

        Optional<Passenger> found = passengerRepository.findById(passenger.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(passenger));
    }

    @Test
    public void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<Passenger> found = passengerRepository.findById(Long.valueOf(-1L));

        assertThat(found, is(Optional.empty()));
    }
    
}