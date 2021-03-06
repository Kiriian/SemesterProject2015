/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Airline;
import entity.Flight;
import exceptions.NoSuchFlightFoundException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Pernille
 */
public class GetFlight implements Callable<List<Flight>>
{

    private URL url = null;
    private String finalUrl = "";
    private HttpURLConnection urlConn = null;
    private InputStreamReader in = null;
    private final StringBuilder sb = new StringBuilder();
    private BufferedReader bufferedReader = null;
    private String airlineName;
    private List<Flight> flights = null;
    private Gson gson = null;
    private JsonObject object;
    private JsonArray jsonArray;

    public GetFlight(String finalUrl)
    {
        this.finalUrl = finalUrl;
    }

    //kalde metoden getAirlines i facade
    //callable - sende get ud til alle i databasen
    //modtage json resultater
    //videresende json resultater
    @Override
    public List<Flight> call() throws NoSuchFlightFoundException
    {
        try
        {
            flights = null;
            object = new JsonObject();
            gson = new Gson();
            url = new URL(finalUrl);
            System.out.println("her er vi før urlConn..." + url);
            urlConn = (HttpURLConnection) url.openConnection();
            System.out.println("Her er error kode: " + urlConn.getResponseCode());
            if (urlConn != null && urlConn.getResponseCode() < 400)
            {
                System.out.println("Hvis error code er mindre en 400 burde vi komme her ind");
                flights = new ArrayList<>();
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1)
                {
                    sb.append((char) cp);
                }
                System.out.println("Her er vi før vi laver jsonObjectet: " + sb.toString());
                object = new JsonParser().parse(sb.toString()).getAsJsonObject();
                System.out.println("Her er vores jsonobject: " + object.toString());
                airlineName = object.get("airline").getAsString();
                jsonArray = object.get("flights").getAsJsonArray();

                for (int i = 0; i < jsonArray.size(); i++)
                {
                    JsonObject json = (JsonObject) jsonArray.get(i);

                    Flight f = new Flight(
                            airlineName,
                            json.get("date").getAsString(),
                            json.get("numberOfSeats").getAsInt(),
                            json.get("totalPrice").getAsDouble(),
                            json.get("flightID").getAsString(),
                            json.get("traveltime").getAsInt(),
                            json.get("destination").getAsString(),
                            json.get("origin").getAsString());
                    flights.add(f);
                }
            }
            return flights;
        } catch (Exception e)
        {
            throw new NoSuchFlightFoundException("No flights available");

        }
    }
}
