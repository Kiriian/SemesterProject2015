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
import entity.Passengers;
import entity.Reservation;
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
@Path("reservation/")
public class MPJReservation
{

    @Context
    private UriInfo context;
        private Gson gson;

    /**
     * Creates a new instance of MPJReservation
     */
    public MPJReservation()
    {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String sendResponse(String reservation)
    {
        JsonObject json = new JsonParser().parse(reservation).getAsJsonObject();
        Reservation r = new Reservation();
        r.setAirline(json.get("airline").getAsString());
        r.setDato(json.get("dato").getAsString());
        r.setDestination(json.get("destination").getAsString());
        r.setFlightID(json.get("flightID").getAsString());
        r.setFlightTime(json.get("flightTime").getAsInt());
        r.setNumberOfSeats(json.get("numberOfSeats").getAsInt());
        r.setOrigin(json.get("origin").getAsString());
        return null;
//        return Response.ok(new Gson().toJson(responseJson)).build();
    }

}
