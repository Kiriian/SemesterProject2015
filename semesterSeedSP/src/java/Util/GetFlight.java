/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import entity.Flight;
import facades.RequestFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import us.monoid.web.Resty;

/**
 *
 * @author Pernille
 */
public class GetFlight implements Callable<List<Flight>>
{

//    private URL url = null;
    private String finalUrl = "";
//    private URLConnection urlConn = null;
//    private InputStreamReader in = null;
//    private StringBuilder sb = new StringBuilder();
//    private BufferedReader bufferedReader = null;
//    private String needToBecomeFlight;
    private JSONArray jsonArray = null;
    private List<Flight> flights = null;
    private JSONObject object = null;
    private Resty resty = null;

    public GetFlight(String finalUrl)
    {
        this.finalUrl = finalUrl;
    }

    //kalde metoden getAirlines i facade
    //callable - sende get ud til alle i databasen
    //modtage json resultater
    //videresende json resultater
    @Override
    public List<Flight> call() throws Exception
    {
//        url = new URL(finalUrl);
//        urlConn = url.openConnection();
//        if (urlConn != null && urlConn.getInputStream() != null)
//        {
//            in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
//            bufferedReader = new BufferedReader(in);
//            while ((needToBecomeFlight = bufferedReader.readLine()) != null)
//            {
//                sb.append(needToBecomeFlight);
//                jsonArray.add(sb.toString());
//            }
//        }
//        in.close();
        
        resty = new Resty();
        
        jsonArray = (JSONArray) resty.json(finalUrl).get("flights");
        
        flights = new ArrayList();
        
        for (Object json : jsonArray)
        {
            object = (JSONObject) json;
            flights.add(new Flight(
                    (String) object.get("date"),
                    (int) object.get("numberOfSeats"),
                    (Double) object.get("priceTotal"),
                    (String) object.get("flightId"),
                    (int) object.get("travelTime"),
                    (String) object.get("destination"),
                    (String) object.get("origin")));
        }
        
        return flights;
        
    }
}
