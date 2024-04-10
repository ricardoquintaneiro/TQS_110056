package pt.ua.tqs110056.busticketbackend.initializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.TripRepository;

@Component
@Profile("!test")
public class TripDataInitializer implements CommandLineRunner {
   
    @Autowired
    private TripRepository tripRepository;

    @Override
    public void run(String... args) throws Exception {
        clearDatabase();
        City city1 = new City("Aveiro", "Portugal");
        City city2 = new City("Lisboa", "Portugal");
        BusSeat seat1 = new BusSeat(BusSeatType.REGULAR, "1");
        BusSeat seat2 = new BusSeat(BusSeatType.PREMIUM, "2");        
        Bus bus1 = new Bus("AAAAAA", "Mercedes-Benz Tourismo", List.of(seat1, seat2));
        LocalDateTime departureTime = LocalDateTime.now().plusMinutes(45);
        LocalDateTime arrivalTime = LocalDateTime.now().plusMinutes(82);
        BigDecimal ticketPriceInEuro = new BigDecimal("12.50");
        tripRepository.save(new Trip(city1, city2, bus1, departureTime, arrivalTime, ticketPriceInEuro));

        City city3 = new City("Coimbra", "Portugal");
        City city4 = new City("Porto", "Portugal");
        BusSeat seat3 = new BusSeat(BusSeatType.REGULAR, "1");
        BusSeat seat4 = new BusSeat(BusSeatType.PREMIUM, "2");        
        Bus bus2 = new Bus("AAAAAB", "Mercedes-Benz Tourismo", List.of(seat3, seat4));
        LocalDateTime departureTime2 = LocalDateTime.now().plusMinutes(45);
        LocalDateTime arrivalTime2 = LocalDateTime.now().plusMinutes(82);
        BigDecimal ticketPriceInEuro2 = new BigDecimal("9.25");
        tripRepository.save(new Trip(city3, city4, bus2, departureTime2, arrivalTime2, ticketPriceInEuro2));
    }

    private void clearDatabase() {
        tripRepository.deleteAll();
    }

}
