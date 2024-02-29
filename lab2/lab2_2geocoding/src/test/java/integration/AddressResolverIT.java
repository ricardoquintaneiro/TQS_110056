package integration;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolverService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressResolverIT {

    private AddressResolverService resolver;

    @BeforeEach
    public void init(){
        resolver = new AddressResolverService(new TqsBasicHttpClient());
    }


    @Test
    @DisplayName("When a valid location is provided, then it should return the correct address object")
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);
        Address expectedAdress = new Address("Avenida da Universidade", "Aveiro", "3810-489", "");
        assertTrue(result.isPresent());
        assertEquals(expectedAdress, result.get());

    }

    @Test
    @DisplayName("When a invalid location is provided, then it shouldn't return any address object")
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(-361, -361);
        assertFalse(result.isPresent());
        
    }

}
