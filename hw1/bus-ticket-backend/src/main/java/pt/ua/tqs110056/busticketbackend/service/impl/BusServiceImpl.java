package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.repository.BusRepository;
import pt.ua.tqs110056.busticketbackend.service.BusService;

@Service
public class BusServiceImpl implements BusService {

    private BusRepository busRepository;

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
    public Optional<List<BusSeat>> getBusSeats(long busId) {
        Optional<Bus> bus = busRepository.findById(busId);
        if (bus.isPresent()) {
            return Optional.of(bus.get().getSeats());
        }
        return Optional.empty();
    }

    
}
