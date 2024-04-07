package pt.ua.tqs110056.busticketbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Bus findById(long id);

    Bus findByPlate(String plate);
    
    Bus findBySeatId(long seatId);

}
