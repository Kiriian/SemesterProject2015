/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Flight;
import exceptions.NoSuchFlightFoundException;
import facades.RequestFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Pernille
 */
@Path("reservation")
@RolesAllowed("User")
public class Reservation
{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Request
     */
    public Reservation()
    {
    }
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public String getJson(String jsonString)
    {
        jsonString = "hej";
        return jsonString;
    }
}