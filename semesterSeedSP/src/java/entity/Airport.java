/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jeanette
 */
@Entity
public class Airport implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    private String IATAcode;
    private String airportName;
    private String city;
    private String timezone;

    public Airport()
    {
    }

    public Airport(String IATAcode, String airportName, String city, String timezone)
    {
        this.IATAcode = IATAcode;
        this.airportName = airportName;
        this.city = city;
        this.timezone = timezone;
    }
    
    public String getIATAcode()
    {
        return IATAcode;
    }

    public void setIATAcode(String IATAcode)
    {
        this.IATAcode = IATAcode;
    }

    public String getAirportName()
    {
        return airportName;
    }

    public void setAirportName(String airportName)
    {
        this.airportName = airportName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone(String timezone)
    {
        this.timezone = timezone;
    }
}
