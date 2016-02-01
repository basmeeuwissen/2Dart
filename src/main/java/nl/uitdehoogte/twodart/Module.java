/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import javax.inject.Named;
import org.hibernate.SessionFactory;

/**
 *
 * @author RG79YA
 */
public class Module extends AbstractModule
{
    private final HibernateBundle<Config> hibernateBundle;
    
    public Module(HibernateBundle<Config> hibernateBundle)
    {
        this.hibernateBundle = hibernateBundle;
    }
    
    @Override
    protected void configure()
    {
        
    }
    
    @Provides
    @Named("sessionFactory")
    public SessionFactory provideSessionFactory()
    {
        return hibernateBundle.getSessionFactory();
    }
}
