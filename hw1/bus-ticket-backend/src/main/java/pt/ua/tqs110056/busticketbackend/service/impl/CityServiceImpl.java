package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.repository.CityRepository;
import pt.ua.tqs110056.busticketbackend.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> getCityById(long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Optional<City> getCityByNameAndCountry(String name, String country) {
        return cityRepository.findByNameAndCountry(name, country);
    }

}