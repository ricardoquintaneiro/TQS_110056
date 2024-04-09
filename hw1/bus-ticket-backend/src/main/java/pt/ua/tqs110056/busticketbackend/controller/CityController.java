package pt.ua.tqs110056.busticketbackend.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/cities")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        logger.info("Fetching all cities");
        List<City> cities = cityService.getAllCities();
        logger.info("Returning {} cities", cities.size());
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {
        logger.info("Fetching city with id {}", id);
        Optional<City> city = cityService.getCityById(id);
        if (city.isPresent()) {
            logger.info("City found with id {}", id);
        } else {
            logger.warn("No city found with id {}", id);
        }
        return city.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<City> getCityByNameAndCountry(@RequestParam String name, @RequestParam String country) {
        logger.info("Fetching city");
        Optional<City> city = cityService.getCityByNameAndCountry(name, country);
        if (city.isPresent()) {
            logger.info("City found with name '{}' and country '{}'", city.get().getName(), city.get().getCountry());
        } else {
            logger.warn("No city found with name and country provided");
        }
        return city.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
