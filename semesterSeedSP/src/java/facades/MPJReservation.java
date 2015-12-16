/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Flight;
import entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author martamiszczyk
 */
public class MPJReservation
{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public Flight checkReservation(String flightID, int numberOfSeats)
    {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            Flight f = (Flight) em.createQuery("Select f from Flight f where f.flightID=:flightID and f.numberOfSeats>=:numberOfSeats").setParameter("flightID", flightID).setParameter("numberOfSeats", numberOfSeats).getSingleResult();
            em.getTransaction().commit();
            System.out.println("flighter er her: " + f.toString());
            em.getTransaction().begin();
            String originCity = (String) em.createQuery("Select a.city from Airport a where a.IATAcode =:origin").setParameter("origin", f.getOrigin()).getSingleResult();
            String destinationCity = (String) em.createQuery("Select a.city from Airport a where a.IATAcode =:destination").setParameter("destination", f.getDestination()).getSingleResult();
            em.getTransaction().commit();
            f.setDestination(destinationCity + " (" + f.getDestination() + ")");
            f.setOrigin(originCity + " (" + f.getOrigin() + ")");
            return f;
        }
        finally
        {
            em.close();
        }
    }

}
