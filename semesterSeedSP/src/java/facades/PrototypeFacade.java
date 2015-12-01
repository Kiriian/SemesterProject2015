/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Flight;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pernille
 */
public class PrototypeFacade
{

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");
    private static URLConnection urlConn = null;
    private static InputStreamReader in = null;
    private static StringBuilder sb = new StringBuilder();

    public static String flightsFromAirlines(String airport, String date, int numberOfPassengers) throws MalformedURLException, IOException
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            List<String> urls = em.createQuery("SELECT u.url from Airline u").getResultList();
            em.getTransaction().commit();
            String url1 = urls.get(0);
            String url2 = url1+"api/flightinfo/"+airport+ "/" + date + "/" + numberOfPassengers;
            URL url = new URL(url2);
            urlConn = url.openConnection();
            if (urlConn != null)
            {
                urlConn.setReadTimeout(60 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null)
            {
                in = new InputStreamReader(urlConn.getInputStream(),
               Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null)
                {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1)
                    {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();

        } finally
        {
            em.close();
                    }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException
    {
//        PrototypeFacade rf = new PrototypeFacade();
        System.out.println(flightsFromAirlines("CPH","2016-01-04T23:00:00.000Z", 3));
    }
}
