package pt.ua.tqs110056.busticketbackend.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class BusRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusRepository busRepository;

    @Test
    void findById_ShouldReturnBus_WhenIdExists() {
        Bus bus = new Bus();
        bus.setPlate("ABC123");
        entityManager.persistAndFlush(bus);

        Optional<Bus> found = busRepository.findById(bus.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(bus));
    }

    @Test
    public void findByPlate_ShouldReturnBus_WhenPlateExists() {
        Bus bus = new Bus();
        bus.setPlate("ABC123");
        entityManager.persistAndFlush(bus);

        Optional<Bus> found = busRepository.findByPlate(bus.getPlate());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(bus));
    }

    @Test
    public void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<Bus> found = busRepository.findById(Long.valueOf(-1));

        assertThat(found, is(Optional.empty()));
    }

    @Test
    public void findByPlate_ShouldReturnEmptyOptional_WhenPlateDoesNotExist() {
        Optional<Bus> found = busRepository.findByPlate("NonExistentPlate");

        assertThat(found, is(Optional.empty()));
    }

}
