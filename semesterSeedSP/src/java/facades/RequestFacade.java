/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import Util.GetFlight;
import Util.Utils;
import deploy.DeploymentConfiguration;
import entity.Flight;
import entity.LoggerSearchData;
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

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public List<String> getAirlines() throws NoSuchFlightFoundException
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            urls = em.createQuery("SELECT u.url from Airline u").getResultList();
            em.getTransaction().commit();
            return urls;
        } finally
        {
            em.close();
        }
    }

    public List<Flight> getFlights(String airport, String date, int numberOfTickets) throws NoSuchFlightFoundException, InterruptedException
    {
        String finalUrl;
        int counter = 0;
        urls = getAirlines();
        List<Flight> flights = new ArrayList();
        List<Future<List<Flight>>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try
        {
            for (String url : urls)
            {
                finalUrl = url + "api/flightinfo/" + airport + "/" + date + "/" + numberOfTickets + "";

                Future<List<Flight>> future = executor.submit(new GetFlight(finalUrl));

                list.add(future);
            }

            for (Future<List<Flight>> future : list)
            {
                List<Flight> temp = future.get();
                for (Flight temp1 : temp)
                {
                    try
                    {
                        flights.add(temp1);
                    } catch (Exception e)
                    {
                        counter++;
                    }
                }
            }
        } catch (ExecutionException e)
        {
            if (flights.size()==counter)
            {
                throw new NoSuchFlightFoundException(e.getMessage());
            }
        }
        return flights;
    }

    public List<Flight> getFlights(String airport, String destination, String date, int numberOfTickets) throws InterruptedException, ExecutionException, NoSuchFlightFoundException
    {
        String finalUrl;
        urls = getAirlines();
        List<Flight> flights = new ArrayList();
        List<Future<List<Flight>>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (String url : urls)
        {
            finalUrl = url + "api/flightinfo/" + airport + "/" + destination + "/" + date + "/" + numberOfTickets + "";
            Future<List<Flight>> future = executor.submit(new GetFlight(finalUrl));
            list.add(future);
        }

        for (Future<List<Flight>> future : list)
        {
            if (future.get() != null)
            {
                List<Flight> temp = future.get();
                for (Flight temp1 : temp)
                {

                    flights.add(temp1);
                }
            }
        }
        return flights;
    }

    public static void main(String[] args)
    {
        String loggerFile = "LogFile.txt";

        Utils.setLogFile(loggerFile, RequestFacade.class.getName());
    }

    public void logSearchCritieria(LoggerSearchData lsd)
    {
        EntityManager em = getEntityManager();

        try
        {
            em.getTransaction().begin();
            em.persist(lsd);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }
}
