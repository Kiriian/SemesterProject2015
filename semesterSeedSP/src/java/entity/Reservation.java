/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Pernille
 */
@Entity
public class Reservation implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airline;
    private String flightID;
    private String origin;
    private String destination;
    private String dato;
    private int flightTime;
    private int numberOfSeats;
    private String reserveeName;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "reservation")
    private List<Passengers> passengers;

    public Reservation()
    {
    }

    public Reservation(String airline, String flightID, String origin, String destination, String dato, int flightTime, int numberOfSeats, String reserveeName, User user, List<Passengers> passengers)
    {
        this.airline = airline;
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.dato = dato;
        this.flightTime = flightTime;
        this.numberOfSeats = numberOfSeats;
        this.reserveeName = reserveeName;
        this.user = user;
        this.passengers = passengers;
    }

    public String getAirline()
    {
        return airline;
    }

    public void setAirline(String airline)
    {
        this.airline = airline;
    }

    
    
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFlightID()
    {
        return flightID;
    }

    public void setFlightID(String flightID)
    {
        this.flightID = flightID;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getDato()
    {
        return dato;
    }

    public void setDato(String dato)
    {
        this.dato = dato;
    }

    public int getFlightTime()
    {
        return flightTime;
    }

    public void setFlightTime(int flightTime)
    {
        this.flightTime = flightTime;
    }

    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    public String getReserveeName()
    {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName)
    {
        this.reserveeName = reserveeName;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<Passengers> getPassengers()
    {
        return passengers;
    }

    public void setPassengers(List<Passengers> passengers)
    {
        this.passengers = passengers;
    }

    
}
