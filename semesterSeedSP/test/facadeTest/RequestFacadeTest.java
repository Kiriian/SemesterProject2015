/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadeTest;

import entity.Flight;
import exceptions.NoSuchFlightFoundException;
import facades.RequestFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jeanette
 */
public class RequestFacadeTest
{

    private RequestFacade rf;
    private String origin;
    private String date;
    private String destination;
    private int numberOfPassengers;
    private List<Flight> f;

    public RequestFacadeTest()
    {
    }

//    @Test
//    public void getFlightWithoutDestination() throws InterruptedException, ExecutionException, NoSuchFlightFoundException
//    {
//        rf = new RequestFacade();
//        origin = "CPH";
//        date = "2016-01-04T23:00:00.000Z";
//        numberOfPassangers = 3;
//
//        f = rf.getFlights(origin, date, numberOfPassangers);
//        assertEquals("[Flight{airlineName=AngularJS Airline, date=2016-01-04T15:00:00.000Z, numberOfSeats=3, totalPrice=210.0, fligthID=COL2216x100x2016-01-04T15:00:00.000Z, traveltime=60, destination=SXF, origin=CPH}, Flight{airlineName=AngularJS Airline, date=2016-01-04T19:00:00.000Z, numberOfSeats=3, totalPrice=150.0, fligthID=COL3256x100x2016-01-04T19:00:00.000Z, traveltime=90, destination=STN, origin=CPH}, Flight{airlineName=AngularJS Airline, date=2016-01-04T10:00:00.000Z, numberOfSeats=3, totalPrice=195.0, fligthID=COL3256x100x2016-01-04T10:00:00.000Z, traveltime=90, destination=STN, origin=CPH}, Flight{airlineName=AngularJS Airline, date=2016-01-04T06:00:00.000Z, numberOfSeats=3, totalPrice=225.0, fligthID=COL2214x100x2016-01-04T06:00:00.000Z, traveltime=60, destination=SXF, origin=CPH}, Flight{airlineName=MPJ Air, date=2016-01-04T12:00:00.000Z, numberOfSeats=3, totalPrice=1500.0, fligthID=hej, traveltime=695, destination=TYO, origin=CPH}]", f.toString());
//    }
//
//    @Test
//    public void getFlightWithDestination() throws InterruptedException, ExecutionException, NoSuchFlightFoundException
//    {
//        rf = new RequestFacade();
//        origin = "CPH";
//        destination = "STN";
//        date = "2016-01-04T23:00:00.000Z";
//        numberOfPassangers = 3;
//
//        f = rf.getFlights(origin, destination, date, numberOfPassangers);
//        assertEquals("[Flight{airlineName=AngularJS Airline, date=2016-01-04T19:00:00.000Z, numberOfSeats=3, totalPrice=150.0, fligthID=COL3256x100x2016-01-04T19:00:00.000Z, traveltime=90, destination=STN, origin=CPH}, Flight{airlineName=AngularJS Airline, date=2016-01-04T10:00:00.000Z, numberOfSeats=3, totalPrice=195.0, fligthID=COL3256x100x2016-01-04T10:00:00.000Z, traveltime=90, destination=STN, origin=CPH}]", f.toString());
//    }

    @Test(expected=NoSuchFlightFoundException.class)
    public void getFlightWithoutDestinationWrongData() throws InterruptedException, ExecutionException, NoSuchFlightFoundException
    {
        rf = new RequestFacade();
        origin = "hej";
        date = "2016-01-04T23:00:00.000Z";
        numberOfPassengers = 3;

        f = rf.getFlights(origin, date, numberOfPassengers);
    }

    @Test(expected=NoSuchFlightFoundException.class)
    public void getFlightWithDestinationWrongData() throws NoSuchFlightFoundException, InterruptedException
    {
        rf = new RequestFacade();
        origin = "CPH";
        destination = "hej";
        date = "2016-01-04T23:00:00.000Z";
        numberOfPassengers = 3;

        f = rf.getFlights(origin, destination, date, numberOfPassengers);
    }
}
