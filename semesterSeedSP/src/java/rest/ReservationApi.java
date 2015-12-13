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
import entity.Role;
import entity.User;
import facades.RequestFacade;
import facades.ReservationFacade;
import facades.UserFacade;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * REST Web Service
 *
 * @author Pernille
 */
@Path("reservation/")
@RolesAllowed(
{
    "User", "Admin"
})

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

    private Gson gson;

    @POST
    @Path("{airlineName}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getReservationsResponse(String jsonString, @PathParam("airlineName") String airlineName) throws MalformedURLException, ProtocolException, IOException
    {
        gson = new Gson();
        ReservationFacade rf = new ReservationFacade();
        UserFacade uf = new UserFacade();
        Reservation r = new Reservation();

        String baseUrl = rf.getAirlinesByAirlineName(airlineName);
        String reservationResponse = checkReservation(baseUrl, jsonString);
        JsonObject json = new JsonParser().parse(reservationResponse).getAsJsonObject();
        r.setAirline(airlineName);
        r.setFlightID(json.get("flightID").getAsString());
        r.setOrigin(json.get("Origin").getAsString());
        r.setDestination(json.get("Destination").getAsString());
        r.setDato(json.get("Date").getAsString());
        r.setFlightTime(json.get("FlightTime").getAsInt());
        r.setNumberOfSeats(json.get("numberOfSeats").getAsInt());
        r.setReserveeName(json.get("ReserveeName").getAsString());
        String userName = securityContext.getUserPrincipal().getName();
        User user = uf.getUserByUserId(userName);
        r.setUser(user);
        List<Passengers> passengers = new ArrayList<>();
        JsonArray jp = json.getAsJsonArray("Passengers");
        for (JsonElement jp1 : jp)
        {
            Passengers p = new Passengers();
            p.setFirstName(jp1.getAsJsonObject().get("firstName").getAsString());
            p.setLastName(jp1.getAsJsonObject().get("lastName").getAsString());
            p.setReservation(r);
            passengers.add(p);
        }

        rf.saveReservation(r, passengers);

        return Response.ok(new Gson().toJson(json)).build();
    }

    public String checkReservation(String baseUrl, String reservation) throws MalformedURLException, ProtocolException, IOException
    {
        String finalUrl = baseUrl + "api/flightreservation";
        URL url = new URL(finalUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream()))
        {
            out.write(reservation);
        }

        String jsonStr;
        con.getInputStream();
        try (Scanner scan = new Scanner(con.getInputStream()))
        {
            jsonStr = "";
            while (scan.hasNext())
            {
                jsonStr = jsonStr + scan.nextLine();
            }
        }

        return jsonStr;
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public String getReservationsByUsername()
    {
        ReservationFacade rf = new ReservationFacade();
        String userName = securityContext.getUserPrincipal().getName();
        List<Reservation> resList = null; 
        if (securityContext.isUserInRole("Admin") == true)
        {
            resList = rf.getAllReservations();
        } else
        {
            resList = rf.getUsersReservations(userName);
        }
        JsonArray json = new JsonArray();
        for (Reservation r : resList)
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("airline", r.getAirline());
            jo.addProperty("flightID", r.getFlightID());
            jo.addProperty("origin", r.getOrigin());
            jo.addProperty("destination", r.getDestination());
            jo.addProperty("date", r.getDato());
            jo.addProperty("flightTime", r.getFlightTime());
            jo.addProperty("numberOfSeats", r.getNumberOfSeats());
            jo.addProperty("ReserveeName", r.getReserveeName());

            JsonArray json1 = new JsonArray();
            List<Passengers> pasList = rf.getPassengersByReservationID(r.getId());
            for (Passengers p : pasList)
            {
                JsonObject jo2 = new JsonObject();
                jo2.addProperty("firstName", p.getFirstName());
                jo2.addProperty("lastName", p.getLastName());
                json1.add(jo2);
            }
            jo.add("Passengers", json1);
            json.add(jo);

        }
        String jsonStr = json.toString();
        return jsonStr;

    }
}
