/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import Util.GetFlight;
import deploy.DeploymentConfiguration;
import entity.Flight;
import exceptions.NoSuchFlightFoundException;
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
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public List<String> getAirlines()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        urls = em.createQuery("SELECT u.url from Airline u").getResultList();
        em.getTransaction().commit();
        return urls;
    }

    public List<Flight> getFlights(String airport, String date, int numberOfTickets) throws InterruptedException, ExecutionException, NoSuchFlightFoundException
    {
        String finalUrl;
        urls = getAirlines();
        List<Flight> flights = new ArrayList();
        List<Future<List<Flight>>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (String url : urls)
        {
            finalUrl = url + "api/flightinfo/" + airport + "/" + date + "/" + numberOfTickets + "";
            System.out.println(finalUrl);
            Future<List<Flight>> future = executor.submit(new GetFlight(finalUrl));
            list.add(future);
        }

        for (Future<List<Flight>> future : list)
        {
            List<Flight> temp = future.get();
            for (Flight temp1 : temp)
            {
                flights.add(temp1);
            }
        }
        return flights;
    }

    public List<Flight> getFlights(String airport, String destination, String date, int numberOfTickets) throws InterruptedException, ExecutionException, NoSuchFlightFoundException
    {
        System.out.println("hello?");
        String finalUrl;
        urls = getAirlines();
        List<Flight> flights = new ArrayList();
        List<Future<List<Flight>>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (String url : urls)
        {
            finalUrl = url + "api/flightinfo/" + airport + "/" + destination + "/" + date + "/" + numberOfTickets + "";
            System.out.println(finalUrl);
            Future<List<Flight>> future = executor.submit(new GetFlight(finalUrl));
            list.add(future);
        }

        for (Future<List<Flight>> future : list)
        {
            List<Flight> temp = future.get();
            for (Flight temp1 : temp)
            {
                flights.add(temp1);
            }
        }
        return flights;
    }
}
