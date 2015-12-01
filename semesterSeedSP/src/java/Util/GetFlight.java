/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import entity.Flight;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Pernille
 */
public class GetFlight implements Callable<Flight>
{

    //kalde metoden getAirlines i facade
    //callable - sende get ud til alle i databasen
    //modtage json resultater
    //videresende json resultater
    public List<Flight> getFlights(String airport, String date, int numberOfTickets)
    {
        //
        return null;
    }

    @Override
    public Flight call() throws Exception
    {
        try
        {
            
        }

        return new Flight(date, numberOfSeats, totalPrice, flightID, traveltime, destination, origin);

    }
    catch (Exception e) {
            //System.out.println("Error trying to fetch - " + e);
        }


return null;
    }
    }
}
