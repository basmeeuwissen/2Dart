/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.services;

import nl.uitdehoogte.twodart.model.Player;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import nl.uitdehoogte.twodart.persistence.PlayerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 *
 * @author RG79YA
 */
@Singleton
public class AuthenticationService implements Authenticator<BasicCredentials, Player>, Authorizer<Player>
{
    private final SessionFactory sessionFactory;
    private final PlayerDAO playerDAO;

    @Inject
    public AuthenticationService(@Named("sessionFactory") SessionFactory sessionFactory, PlayerDAO playerDAO)
    {
        this.sessionFactory = sessionFactory;
        this.playerDAO = playerDAO;
    }

    @Override
    public Optional<Player> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        Session session = sessionFactory.openSession();
        ManagedSessionContext.bind(session);
                
        Player player = playerDAO.getByEmail(credentials.getUsername());
        
        session.close();
        ManagedSessionContext.unbind(sessionFactory);
        
        if (player != null && player.getPassword().equals(credentials.getPassword()))
        {
            return Optional.of(player);
        }
        
        return Optional.absent();
    }

    @Override
    public boolean authorize(Player player, String roleName)
    {
        return true;
    }

}
