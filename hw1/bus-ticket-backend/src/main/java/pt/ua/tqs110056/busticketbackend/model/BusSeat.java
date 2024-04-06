package pt.ua.tqs110056.busticketbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BusSeatType type;
    private String seatNumber;

    public BusSeat() {
    }

    public BusSeat(BusSeatType type, String seatNumber) {
        this.type = type;
        this.seatNumber = seatNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusSeatType getType() {
        return type;
    }

    public void setType(BusSeatType type) {
        this.type = type;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((seatNumber == null) ? 0 : seatNumber.hashCode());
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
        BusSeat other = (BusSeat) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type != other.type)
            return false;
        if (seatNumber == null) {
            if (other.seatNumber != null)
                return false;
        } else if (!seatNumber.equals(other.seatNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BusSeat [id=" + id + ", type=" + type + ", seatNumber=" + seatNumber + "]";
    }
}
