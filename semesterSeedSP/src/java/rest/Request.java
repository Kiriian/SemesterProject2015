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
import entity.LoggerSearchData;
import exceptions.NoSuchFlightFoundException;
import facades.RequestFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
@Path("request/")
public class Request
{

    @Context
    private UriInfo context;
    private RequestFacade rf = new RequestFacade();
    private Gson gson = new Gson();

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
    public String getInfoRequestToAny(@PathParam("airport") String airport, @PathParam("date") String date, @PathParam("numberOfTickets") int numberOfTickets) throws InterruptedException, NoSuchFlightFoundException
    {
        List<Flight> flights = rf.getFlights(airport, date, numberOfTickets);
        LoggerSearchData lsd = new LoggerSearchData(airport, "Any", date);
        rf.logSearchCritieria(lsd);
        JsonArray json = new JsonArray();
        for (Flight f : flights)
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("airline", f.getAirlineName());
            jo.addProperty("date", f.getDato());
            jo.addProperty("numberOfSeats", f.getNumberOfSeats());
            jo.addProperty("totalPrice", f.getTotalPrice());
            jo.addProperty("flightID", f.getFlightID());
            jo.addProperty("traveltime", f.getTraveltime());
            jo.addProperty("destination", f.getDestination());
            jo.addProperty("origin", f.getOrigin());
            json.add(jo);
        }
        String jsonStr = gson.toJson(json);
        return jsonStr;

    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{airport}/{destination}/{date}/{numberOfTickets}")
    public String getInfoRequestToAndFrom(@PathParam("airport") String airport, @PathParam("destination") String destination, @PathParam("date") String date, @PathParam("numberOfTickets") int numberOfTickets) throws InterruptedException, NoSuchFlightFoundException
    {
        List<Flight> flights = rf.getFlights(airport, destination, date, numberOfTickets);
        JsonArray json = new JsonArray();
        LoggerSearchData lsd = new LoggerSearchData(airport, destination, date);
        rf.logSearchCritieria(lsd);
        for (Flight f : flights)
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("airline", f.getAirlineName());
            jo.addProperty("date", f.getDato());
            jo.addProperty("numberOfSeats", f.getNumberOfSeats());
            jo.addProperty("totalPrice", f.getTotalPrice());
            jo.addProperty("flightID", f.getFlightID());
            jo.addProperty("traveltime", f.getTraveltime());
            jo.addProperty("destination", f.getDestination());
            jo.addProperty("origin", f.getOrigin());
            json.add(jo);
        }
        String jsonStr = gson.toJson(json);
        System.out.println("Her er hvad vi returner til html siden: " + jsonStr);
        return jsonStr;
    }
}
