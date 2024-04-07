package pt.ua.tqs110056.busticketbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    Optional<City> findById(long id);

    Optional<City> findByNameAndCountry(String name, String country);

    @NonNull
    List<City> findAll();
}
