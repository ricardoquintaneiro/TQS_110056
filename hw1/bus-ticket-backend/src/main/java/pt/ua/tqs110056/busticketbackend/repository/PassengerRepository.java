package pt.ua.tqs110056.busticketbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Passenger findById(long id);

}
