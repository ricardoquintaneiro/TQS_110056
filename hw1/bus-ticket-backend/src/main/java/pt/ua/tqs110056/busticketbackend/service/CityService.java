package pt.ua.tqs110056.busticketbackend.service;

import java.util.List;
import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.City;

public interface CityService {

    List<City> getAllCities();

    Optional<City> getCityById(long id);

    Optional<City> getCityByNameAndCountry(String name, String country);

}