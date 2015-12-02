/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import Util.GetFlight;
import entity.Flight;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RequestFacade
{

    private List<String> urls;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");

    public List<String> getAirlines()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        urls = em.createQuery("SELECT u.url from Airline u").getResultList();
        em.getTransaction().commit();
        return urls;
    }

    public List<Flight> getFlights(String airport, String date, int numberOfTickets) throws InterruptedException, ExecutionException
    {
        String finalUrl;
        List<Flight> flights = new ArrayList();
        List<Future<Flight>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (String url : urls)
        {
            finalUrl = url + "api/flightinfo/" + airport + "/" + date + "/" + numberOfTickets + "";
            Future<List<Flight>> future = executor.submit(new GetFlight(finalUrl));
            list.add(future);
        }

        for (Future<Flight> future : list)
        {

            if (future.get() != null)
            {
                flights.add(future.get());
            }
        }

        return flights;
    }

}
