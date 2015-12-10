/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Reservation;
import facades.ReservationFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Pernille
 */
@Path("reservation")
//@RolesAllowed("User")
public class ReservationApi
{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Request
     */
    public ReservationApi()
    {
    }
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getJson(String jsonString)
    {
        ReservationFacade f = new ReservationFacade();
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        Reservation r = new Reservation();
        r.setFlightID(json.get("flightID").getAsString());
        
//        r.set(json.get("password").getAsString());
        f.saveReservation(r);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("fligthID", r.getFlightID());         
        return Response.ok(new Gson().toJson(responseJson)).build();
    }
}