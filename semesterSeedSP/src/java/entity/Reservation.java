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
    private String flightID;
    private String fullname;
    private int numberOfSeats;
    private String phone;
    private String email;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "reservation")
    private List<Passengers> passengers;
    
    
   
 public Reservation()
    {
    }

    public Reservation(String flightID, String fullname, int numberOfSeats, String phone, String email)
    {
        this.flightID = flightID;
        this.fullname = fullname;
        this.numberOfSeats = numberOfSeats;
        this.phone = phone;
        this.email = email;
    }
 
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "entity.Reservation[ id=" + id + " ]";
    }

    public String getFlightID()
    {
        return flightID;
    }

    public void setFlightID(String flightID)
    {
        this.flightID = flightID;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
