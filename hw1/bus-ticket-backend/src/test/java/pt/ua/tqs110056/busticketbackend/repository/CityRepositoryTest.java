package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.City;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void findById_ShouldReturnCity_WhenCityExists() {
        City city = new City("Lisbon", "Portugal");
        entityManager.persistAndFlush(city);

        Optional<City> found = cityRepository.findById(city.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(city));
    }

    @Test
    public void findById_ShouldReturnEmptyOptional_WhenCityDoesNotExist() {
        Optional<City> found = cityRepository.findById(-1L);

        assertThat(found.isPresent(), is(false));
    }

    @Test
    public void findByNameAndCountry_ShouldReturnCity_WhenCityExists() {
        City city = new City("Paris", "France");
        entityManager.persistAndFlush(city);

        Optional<City> found = cityRepository.findByNameAndCountry("Paris", "France");

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(city));
    }

    @Test
    public void findByNameAndCountry_ShouldReturnEmptyOptional_WhenCityDoesNotExist() {
        Optional<City> found = cityRepository.findByNameAndCountry("Nonexistent City", "Nonexistent Country");

        assertThat(found.isPresent(), is(false));
    }

    @Test
    public void findAll_ShouldReturnAllCities_WhenCitiesExist() {
        City lisbon = new City("Lisbon", "Portugal");
        City paris = new City("Paris", "France");
        entityManager.persistAndFlush(lisbon);
        entityManager.persistAndFlush(paris);

        List<City> cities = cityRepository.findAll();

        assertThat(cities, containsInAnyOrder(lisbon, paris));
        assertThat(cities, hasSize(2));
    }

    @Test
    public void findAll_ShouldReturnEmptyList_WhenNoCitiesExist() {
        List<City> cities = cityRepository.findAll();

        assertThat(cities, is(empty()));
    }
    
}
