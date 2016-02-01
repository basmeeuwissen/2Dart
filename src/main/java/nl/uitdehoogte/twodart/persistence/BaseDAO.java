/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import nl.uitdehoogte.twodart.model.Model;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author RG79YA
 */
public class BaseDAO<T extends Model> extends AbstractDAO<T>
{
    public BaseDAO(SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }
    
    public T get(String id)
    {
        Criteria criteria = this.criteria()
                .add(Restrictions.eq("deleted", false))
                .add(Restrictions.eq("id", id));
       
        return this.uniqueResult(criteria);
    }
    
    public T getActual(String id)
    {
        return super.get(id);
    }
    
    public void create(T entity)
    {
        persist(entity);
    }
    
    public void update(T entity)
    {
        entity.setModificationDate();
        
        persist(entity);
    }
    
    public void delete(T entity)
    {
        entity.setDeletionDate();
        entity.setDeleted(true);
        
        persist(entity);
    }
    
    public void deleteHard(T entity)
    {
        super.currentSession().delete(entity);
    }
    
    public void undelete(T entity)
    {
        entity.setDeleted(false);
        
        persist(entity);
    }
}
