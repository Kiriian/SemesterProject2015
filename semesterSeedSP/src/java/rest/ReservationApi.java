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
import entity.User;
import facades.ReservationFacade;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * REST Web Service
 *
 * @author Pernille
 */
@Path("reservation")
@RolesAllowed("User")

public class ReservationApi
{

    @Context
    private UriInfo context;
    @Context
    SecurityContext securityContext;
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
        UserFacade uf= new UserFacade();
        System.out.println(""+ jsonString);
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        Reservation r = new Reservation();
        r.setEmail(json.get("email").getAsString());
        r.setFlightID(json.get("flightID").getAsString());
        r.setFullname(json.get("ReserveeName").getAsString());
        r.setNumberOfSeats(json.get("numberOfSeats").getAsInt());
        r.setPhone(json.get("phone").getAsString());
        
        String userName = securityContext.getUserPrincipal().getName();
        System.out.println("username"+ userName);
        User user= uf.getUserByUserId(userName);
        r.setUser(user);
        f.saveReservation(r);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("fligthID", r.getFlightID());         
        return Response.ok(new Gson().toJson(responseJson)).build();
    }
}