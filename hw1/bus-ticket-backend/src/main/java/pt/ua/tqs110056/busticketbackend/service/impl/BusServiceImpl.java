package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.repository.BusRepository;
import pt.ua.tqs110056.busticketbackend.service.BusService;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Optional<Bus> getBusById(long id) {
        return busRepository.findById(id);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Optional<List<BusSeat>> getAllBusSeats(long busId) {
        Optional<Bus> bus = busRepository.findById(busId);
        if (bus.isPresent()) {
            return Optional.of(bus.get().getSeats());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<BusSeat>> getBusSeatsByType(long busId, BusSeatType type) {
        Optional<Bus> bus = busRepository.findById(busId);
        if (bus.isPresent()) {
            List<BusSeat> seats = bus.get().getSeats();
            return Optional.of(seats.stream().filter(seat -> seat.getType().equals(type)).toList());
        }
        return Optional.empty();
    }

    @Override
    public boolean reserveSeat(long busId, String seatNumber) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            List<BusSeat> seats = bus.getSeats();
            int seatIndex = findSeatIndex(seats, seatNumber);
            if (seatIndex != -1) {
                bus.setSeatAvailability(seatIndex, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean makeSeatAvailable(long busId, String seatNumber) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            List<BusSeat> seats = bus.getSeats();
            int seatIndex = findSeatIndex(seats, seatNumber);
            if (seatIndex != -1) {
                bus.setSeatAvailability(seatIndex, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean makeAllSeatsAvailable(long busId) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            Arrays.fill(bus.getSeatsAvailability(), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSeatAvailable(long busId, String seatNumber) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            List<BusSeat> seats = bus.getSeats();
            int seatIndex = findSeatIndex(seats, seatNumber);
            if (seatIndex != -1) {
                return bus.getSeatAvailability(seatIndex);
            }
        }
        return false;
    }

    private int findSeatIndex(List<BusSeat> seats, String seatNumber) {
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getNumber().equals(seatNumber)) {
                return i;
            }
        }
        return -1;
    }

}
