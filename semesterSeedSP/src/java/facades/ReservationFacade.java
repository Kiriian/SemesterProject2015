/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Passengers;
import entity.Reservation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pernille
 */
public class ReservationFacade
{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public String getAirlinesByAirlineName(String airlineName)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            String url = (String) em.createQuery("SELECT u.url from Airline u where u.airlineName=:airlineName").setParameter("airlineName", airlineName).getSingleResult();
            em.getTransaction().commit();
            return url;
        } finally
        {
            em.close();
        }
    }

    public Reservation saveReservation(Reservation r, List<Passengers> passengers)
    {
        EntityManager em = getEntityManager();
//        
        try
        {
            em.getTransaction().begin();
            em.persist(r);
            for (Passengers passenger : passengers)
            {
                em.persist(passenger);
            }
            em.getTransaction().commit();
            return (Reservation) em.createQuery("select a from Reservation a where a.user.userName=:userName order by a.id desc").setParameter("userName", r.getUser().getUserName()).setMaxResults(1).getSingleResult();

        } finally
        {
            em.close();
        }
    }

    public List<Reservation> getUsersReservations(String userName)
    {
       EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            List<Reservation> resList = em.createQuery("SELECT r from Reservation r where r.user.userName=:userName").setParameter("userName", userName).getResultList();
            em.getTransaction().commit();
            return resList;
        } finally
        {
            em.close();
        }
    }

    public List<Passengers> getPassengersByReservationID(long reservationID)
    {
       EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            List<Passengers> pasList = em.createQuery("SELECT p from Passengers p where p.reservation.id=:reservationID").setParameter("reservationID", reservationID).getResultList();
            em.getTransaction().commit();
            return pasList;
        } finally
        {
            em.close();
        }
    }

    public List<Reservation> getAllReservations()
    {
               EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            List<Reservation> resList = em.createQuery("SELECT r from Reservation r").getResultList();
            em.getTransaction().commit();
            return resList;
        } finally
        {
            em.close();
        }
    }
}
