/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Reservation;
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
  
    public Reservation saveReservation(Reservation r)
    {
        EntityManager em = getEntityManager();
//        
        try
        {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            return (Reservation)em.createQuery("select a from Reservation a where a.user.userName=:userName order by a.id desc").setParameter("userName", r.getUser().getUserName()).setMaxResults(1).getSingleResult();
                   
        } finally
        {
            em.close();
        }
    }
    
    
}
