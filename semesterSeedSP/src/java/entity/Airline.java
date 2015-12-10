/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Pernille
 */
@Entity
public class Airline
{
    @Id
    private String url;
    private String airlineName;

    public Airline()
    {
    }

    public Airline(String url, String airlineName)
    {
        this.url = url;
        this.airlineName = airlineName;
    }

    public String getAirlineName()
    {
        return airlineName;
    }

    public void setAirlineName(String airlineName)
    {
        this.airlineName = airlineName;
    }
    
  public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
}
