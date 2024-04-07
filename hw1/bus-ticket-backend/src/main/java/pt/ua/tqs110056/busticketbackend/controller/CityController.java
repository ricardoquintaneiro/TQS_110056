package pt.ua.tqs110056.busticketbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {
        Optional<City> city = cityService.getCityById(id);
        return city.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<City> getCityByNameAndCountry(@RequestParam String name, @RequestParam String country) {
        Optional<City> city = cityService.getCityByNameAndCountry(name, country);
        return city.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
