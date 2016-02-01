/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import nl.uitdehoogte.twodart.model.Player;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RG79YA
 */
@Singleton
public class PlayerDAO extends BaseDAO<Player>
{
    @Inject
    public PlayerDAO(@Named("sessionFactory") SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }
    
    public Player getByEmail(String email)
    {
        Criteria criteria = this.criteria()
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("deleted", false));
       
        return this.uniqueResult(criteria);
    }
}
