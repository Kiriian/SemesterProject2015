/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.RequestFacade;
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
@Path("flightinfo/")
public class Request
{

    @Context
    private UriInfo context;
    RequestFacade rf = new RequestFacade();

    /**
     * Creates a new instance of Request
     */
    public Request()
    {
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{airport}/{date}/{numberOfTickets}")
    public String getJson(@PathParam("airport") String airport, @PathParam("date")String date, @PathParam("numberOfTickets") int numberOfTickets)
    {
        rf.getFlights(airport, date, numberOfTickets);
    }

    /**
     * PUT method for updating or creating an instance of Request
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content)
    {
    }
}
