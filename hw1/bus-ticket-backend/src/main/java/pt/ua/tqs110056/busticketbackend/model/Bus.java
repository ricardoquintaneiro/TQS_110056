package pt.ua.tqs110056.busticketbackend.model;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plate;
    private String model;

    @OneToMany
    private List<BusSeat> seats;

    private boolean[] seatsAvailability;

    public Bus() {
    }

    public Bus(String plate, String model, List<BusSeat> seats) {
        this.plate = plate;
        this.model = model;
        this.seats = seats;

        // initialize seats availability array with all seats available
        this.seatsAvailability = new boolean[seats.size()];
        Arrays.fill(this.seatsAvailability, true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<BusSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<BusSeat> seats) {
        this.seats = seats;
        this.seatsAvailability = new boolean[seats.size()];
    }

    public boolean[] getSeatsAvailability() {
        return seatsAvailability;
    }

    public void setSeatsAvailability(boolean[] seatsAvailability) {
        this.seatsAvailability = seatsAvailability;
        if (seatsAvailability.length != seats.size()) {
            throw new IllegalArgumentException("Seats availability array must have the same length as the seats list");
        }
    }

    public boolean getSeatAvailability(int index) {
        return seatsAvailability[index];
    }

    public void setSeatAvailability(int index, boolean availability) {
        seatsAvailability[index] = availability;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((plate == null) ? 0 : plate.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((seats == null) ? 0 : seats.hashCode());
        result = prime * result + Arrays.hashCode(seatsAvailability);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bus other = (Bus) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (plate == null) {
            if (other.plate != null)
                return false;
        } else if (!plate.equals(other.plate))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (seats == null) {
            if (other.seats != null)
                return false;
        } else if (!seats.equals(other.seats))
            return false;
        if (!Arrays.equals(seatsAvailability, other.seatsAvailability))
            return false;
        return true;
    }

}
