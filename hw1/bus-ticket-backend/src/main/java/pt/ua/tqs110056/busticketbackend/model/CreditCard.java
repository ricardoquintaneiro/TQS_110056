package pt.ua.tqs110056.busticketbackend.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CreditCardType type;
    private String number;
    private String cvv;
    private LocalDate expirationDate;

    public CreditCard() {
    }

    public CreditCard(CreditCardType type, String number, String cvv, LocalDate expirationDate) {
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public boolean isValidCreditCardNumber(String creditCardNumber) {
        String creditCardNumberPattern = "^((4\\d{3})|(5[1-5]\\d{2})|(6011)|(7\\d{3}))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13}$";
        Pattern pattern = Pattern.compile(creditCardNumberPattern);
        return pattern.matcher(creditCardNumber).matches();
    }
    
    public boolean isValidCVV(String cvv) {
        String cvvPattern = "^[0-9]{3,4}$";
        Pattern pattern = Pattern.compile(cvvPattern);
        return pattern.matcher(cvv).matches();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((cvv == null) ? 0 : cvv.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
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
        CreditCard other = (CreditCard) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type != other.type)
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (cvv == null) {
            if (other.cvv != null)
                return false;
        } else if (!cvv.equals(other.cvv))
            return false;
        if (expirationDate == null) {
            if (other.expirationDate != null)
                return false;
        } else if (!expirationDate.equals(other.expirationDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CreditCard [id=" + id + ", type=" + type + ", number=" + number + ", cvv=" + cvv + ", expirationDate="
                + expirationDate + "]";
    }

}
