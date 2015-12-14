/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Pernille
 */
@Entity
public class Flight implements Serializable
{
    @Id
    private String fligthID;
    private String airlineName;
    private String date;
    private int numberOfSeats;
    private double totalPrice;
    private int traveltime;
    private String destination;
    private String origin;

    public Flight()
    {
    }
    
    public Flight(String airlineName, String date, int numberOfSeats, double totalPrice, String fligthID, int traveltime, String destination, String origin)
    {
        this.airlineName = airlineName;
        this.date = date;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = totalPrice;
        this.fligthID = fligthID;
        this.traveltime = traveltime;
        this.destination = destination;
        this.origin = origin;
    }

    public String getAirlineName()
    {
        return airlineName;
    }

    public void setAirlineName(String airlineName)
    {
        this.airlineName = airlineName;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getFligthID()
    {
        return fligthID;
    }

    public void setFligthID(String fligthID)
    {
        this.fligthID = fligthID;
    }

    public int getTraveltime()
    {
        return traveltime;
    }

    public void setTraveltime(int traveltime)
    {
        this.traveltime = traveltime;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    @Override
    public String toString()
    {
        return "Flight{" + "airlineName=" + airlineName + ", date=" + date + ", numberOfSeats=" + numberOfSeats + ", totalPrice=" + totalPrice + ", fligthID=" + fligthID + ", traveltime=" + traveltime + ", destination=" + destination + ", origin=" + origin + '}';
    }
}
