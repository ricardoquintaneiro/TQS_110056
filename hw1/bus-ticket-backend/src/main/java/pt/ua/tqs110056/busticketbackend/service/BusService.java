package pt.ua.tqs110056.busticketbackend.service;

import java.util.List;
import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;

public interface BusService {

    Optional<Bus> getBusById(long id); 

    List<Bus> getAllBuses();

    Optional<List<BusSeat>> getBusSeats(long busId);

}
