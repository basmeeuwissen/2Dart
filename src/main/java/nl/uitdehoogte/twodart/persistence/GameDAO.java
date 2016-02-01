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
import nl.uitdehoogte.twodart.model.Game;
import org.hibernate.SessionFactory;

/**
 *
 * @author RG79YA
 */
@Singleton
public class GameDAO extends BaseDAO<Game>
{
    @Inject
    public GameDAO(@Named("sessionFactory") SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }
    
    @Override
    public void update(Game game)
    {
        throw new UnsupportedOperationException();
    }
}
