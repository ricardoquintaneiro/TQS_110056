package pt.ua.tqs110056.busticketbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findById(long id);

    Optional<Bus> findByPlate(String plate);
    
    Optional<Bus> findBySeatId(long seatId);

}
