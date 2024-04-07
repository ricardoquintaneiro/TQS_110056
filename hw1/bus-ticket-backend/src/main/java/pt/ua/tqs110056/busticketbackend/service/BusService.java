package pt.ua.tqs110056.busticketbackend.service;

import java.util.List;
import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;

public interface BusService {

    Optional<Bus> getBusById(long id); 

    List<Bus> getAllBuses();

    Optional<List<BusSeat>> getAllBusSeats(long busId);

    Optional<List<BusSeat>> getBusSeatsByType(long busId, BusSeatType type);

    boolean reserveSeat(long busId, String seatNumber);

    boolean makeSeatAvailable(long busId, String seatNumber);

    boolean makeAllSeatsAvailable(long busId);

    boolean isSeatAvailable(long busId, String seatNumber);

}
