/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Reservation;
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
import java.util.List;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
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
    
    private Gson gson;
    
    @POST
    @Path("{airlineName}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getJson(String jsonString, @PathParam("airlineName") String airlineName) throws MalformedURLException, ProtocolException, IOException
    {
        gson = new Gson();
        ReservationFacade rf = new ReservationFacade();
        UserFacade uf= new UserFacade();
        Reservation r = new Reservation();
        
        JsonObject temp = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray temp2 = temp.get("Passengers").getAsJsonArray();
        
        
        if (temp2.size() == 0)
        {
            JsonObject empty = new JsonObject();
            temp2.add(empty);
            temp.add("Passengers", temp2);
            jsonString = temp.toString();
        }
        String baseUrl = rf.getAirlinesByAirlineName(airlineName);
        String reservationResponse = checkReservation(baseUrl, jsonString);
        System.out.println(reservationResponse);
        JsonObject json = new JsonParser().parse(reservationResponse).getAsJsonObject();
 
        r.setFlightID(json.get("flightID").getAsString());
        r.setOrigin(json.get("Origin").getAsString());
        r.setDestination(json.get("Destination").getAsString());
        r.setDato(json.get("Date").getAsString());
        r.setFlightTime(json.get("FlightTime").getAsInt());
        r.setNumberOfSeats(json.get("numberOfSeats").getAsInt());
        r.setReserveeName(json.get("ReserveeName").getAsString());
        String userName = securityContext.getUserPrincipal().getName();
        System.out.println("username"+ userName);
        User user= uf.getUserByUserId(userName);
        r.setUser(user);
        rf.saveReservation(r);
        
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
        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
            out.write(reservation);
        }

        String jsonStr;
        con.getInputStream();
        try (Scanner scan = new Scanner(con.getInputStream())) {
            jsonStr = "";
            while (scan.hasNext()) {
                jsonStr = jsonStr + scan.nextLine();
            }
        }
        
        return jsonStr;
    }
}