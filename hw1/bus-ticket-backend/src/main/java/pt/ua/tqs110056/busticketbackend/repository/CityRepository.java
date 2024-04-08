package pt.ua.tqs110056.busticketbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    Optional<City> findByNameAndCountry(String name, String country);

}
