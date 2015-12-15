package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Flight;
import facades.MPJRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("flightinfo/")
public class MPJFlightInfoAPI
{

    MPJRequest mpj = new MPJRequest();
    private Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public String searchFlightsToAll(@PathParam("from") String airport, @PathParam("date") String date, @PathParam("numTickets") int numberOfTickets)
    {
        List<Flight> flights = mpj.getFlights(airport, date, numberOfTickets);
        JsonArray json = new JsonArray();
        JsonObject flightcollection = new JsonObject();
        flightcollection.addProperty("airline", "MPJ Air");
        for (Flight f : flights)
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("date", f.getDato());
            jo.addProperty("numberOfSeats", f.getNumberOfSeats());
            jo.addProperty("totalPrice", f.getTotalPrice());
            jo.addProperty("flightID", f.getFlightID());
            jo.addProperty("traveltime", f.getTraveltime());
            jo.addProperty("destination", f.getDestination());
            jo.addProperty("origin", f.getOrigin());
            json.add(jo);
        }
        flightcollection.add("flights", json);
        System.out.println("er der noget her?"+ flightcollection.toString());
        String jsonStr = gson.toJson(flightcollection);
        return jsonStr;
    }

}
