package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;

@DataJpaTest
public class BusSeatRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusSeatRepository busSeatRepository;

    @Test
    public void findById_ShouldReturnBusSeat_WhenIdExists() {
        BusSeat busSeat = new BusSeat(BusSeatType.PRIORITY, "1A");
        entityManager.persistAndFlush(busSeat);

        Optional<BusSeat> found = busSeatRepository.findById(busSeat.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(busSeat));
    }

    @Test
    public void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<BusSeat> found = busSeatRepository.findById(Long.valueOf(-1));

        assertThat(found, is(Optional.empty()));
    }

    @Test
    public void findByType_ShouldReturnListOfBusSeats_WhenTypeExists() {
        BusSeat busSeat1 = new BusSeat();
        busSeat1.setType(BusSeatType.PREMIUM);
        BusSeat busSeat2 = new BusSeat();
        busSeat2.setType(BusSeatType.PRIORITY);
        entityManager.persistAndFlush(busSeat1);
        entityManager.persistAndFlush(busSeat2);

        List<BusSeat> found = busSeatRepository.findByType(BusSeatType.PREMIUM);

        assertThat(found, equalTo(Arrays.asList(busSeat1)));
    }
}