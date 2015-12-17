/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Flight;
import exceptions.NoSuchFlightFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pernille
 */
public class MPJRequest
{

    private List<Flight> flights;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public List<Flight> getFlights(String airport, String date, int numberOfTickets) throws NoSuchFlightFoundException
    {
        EntityManager em = emf.createEntityManager();
        String[] splitDate = date.split("T");
        String newDate = splitDate[0] + "%";
        em.getTransaction().begin();
        flights = em.createQuery("SELECT f from Flight f where f.origin=:airport and f.dato LIKE :dato and f.numberOfSeats>=:numberOfTickets").setParameter("airport", airport).setParameter("dato", newDate).setParameter("numberOfTickets", numberOfTickets).getResultList();
        em.getTransaction().commit();
        if (flights.isEmpty())
        {
            throw new NoSuchFlightFoundException("No available flights");
        }
        return flights;
    }

    public List<Flight> getFlights(String airport, String destination, String date, int numberOfTickets) throws NoSuchFlightFoundException
    {
        EntityManager em = emf.createEntityManager();
        String[] splitDate = date.split("T");
        String newDate = splitDate[0] + "%";
        em.getTransaction().begin();
        flights = em.createQuery("SELECT f from Flight f where f.origin=:airport and f.destination=:destination and f.dato LIKE :dato and f.numberOfSeats>=:numberOfTickets").setParameter("airport", airport).setParameter("destination", destination).setParameter("dato", newDate).setParameter("numberOfTickets", numberOfTickets).getResultList();
        em.getTransaction().commit();
        if (flights.isEmpty())
        {
            throw new NoSuchFlightFoundException("No available flights");
        }
        return flights;
    }
}
