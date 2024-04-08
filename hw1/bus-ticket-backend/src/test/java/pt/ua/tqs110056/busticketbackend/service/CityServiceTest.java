package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.repository.CityRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.CityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock(lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private City aveiro;

    @BeforeEach
    public void setUp() {
        aveiro = new City("Aveiro", "Portugal");
        aveiro.setId(1L);
    }
    
    @Test
    public void whenGetAllCities_ThenItShouldReturnAllCities() {
        City coimbra = new City("Coimbra", "Portugal");
        List<City> cities = new ArrayList<>();
        cities.add(aveiro);
        cities.add(coimbra);
        Mockito.when(cityRepository.findAll()).thenReturn(cities);

        List<City> foundCities = cityService.getAllCities();

        assertThat(foundCities, equalTo(cities));
    }

    @Test
    public void whenGetAllCitiesAndNoCityExists_ThenItShouldReturnEmptyList() {
        List<City> cities = new ArrayList<>();
        Mockito.when(cityRepository.findAll()).thenReturn(cities);

        List<City> foundCities = cityService.getAllCities();

        assertThat(foundCities.isEmpty(), is(true));
    }

    @Test
    public void whenGetCityById_ThenItShouldReturnCity() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(aveiro));

        Optional<City> foundCity = cityService.getCityById(1L);

        assertThat(foundCity.isPresent(), is(true));
        assertThat(foundCity.get(), equalTo(aveiro));
    }

    @Test
    public void whenGetCityByIdAndCityDoesNotExist_ThenItShouldReturnEmptyOptional() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<City> foundCity = cityService.getCityById(1L);

        assertThat(foundCity.isEmpty(), is(true));
    }

    @Test
    public void whenGetCityByNameAndCountry_ThenItShouldReturnCity() {
        Mockito.when(cityRepository.findByNameAndCountry("Aveiro", "Portugal")).thenReturn(Optional.of(aveiro));

        Optional<City> foundCity = cityService.getCityByNameAndCountry("Aveiro", "Portugal");

        assertThat(foundCity.isPresent(), is(true));
        assertThat(foundCity.get(), equalTo(aveiro));
    }

    @Test
    public void whenGetCityByNameAndCountryAndCityDoesNotExist_ThenItShouldReturnEmptyOptional() {
        Mockito.when(cityRepository.findByNameAndCountry("Aveiro", "Portugal")).thenReturn(Optional.empty());

        Optional<City> foundCity = cityService.getCityByNameAndCountry("Aveiro", "Portugal");

        assertThat(foundCity.isEmpty(), is(true));
    }

}