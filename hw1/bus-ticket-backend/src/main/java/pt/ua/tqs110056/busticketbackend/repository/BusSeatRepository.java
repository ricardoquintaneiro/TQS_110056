package pt.ua.tqs110056.busticketbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;

@Repository
public interface BusSeatRepository extends JpaRepository<BusSeat, Long> {

    BusSeat findById(long id);

    List<BusSeat> findByType(BusSeatType type);
    
}
