/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Flight;
import entity.Passengers;
import entity.Reservation;
import facades.MPJReservation;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author martamiszczyk
 */
@Path("reservation")
public class MPJReservationAPI
{

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of MPJReservation
     */
    public MPJReservationAPI()
    {
    }

    private MPJReservation mpjr;
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String sendResponse(String reservation)
    {
        mpjr = new MPJReservation();
        JsonObject json = new JsonParser().parse(reservation).getAsJsonObject();
        String flightID = json.get("flightID").getAsString();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        System.out.println(flightID + ", " + numberOfSeats);
        Flight f = mpjr.checkReservation(flightID, numberOfSeats);
        JsonObject reservationResponse = new JsonObject();
        
        reservationResponse.addProperty("flightID", f.getFlightID());
        reservationResponse.addProperty("Origin", f.getOrigin());
        reservationResponse.addProperty("Destination", f.getDestination());
        reservationResponse.addProperty("Date", f.getDato());
        reservationResponse.addProperty("FlightTime", f.getTraveltime());
        reservationResponse.addProperty("numberOfSeats", json.get("numberOfSeats").getAsInt());
        reservationResponse.addProperty("ReserveeName", json.get("ReserveeName").getAsString());
        reservationResponse.add("Passengers", json.getAsJsonArray("Passengers"));
        return reservationResponse.toString();
    }

}
