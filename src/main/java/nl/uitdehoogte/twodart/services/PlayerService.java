/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.services;

import javax.inject.Inject;
import javax.inject.Singleton;
import nl.uitdehoogte.twodart.model.Player;
import nl.uitdehoogte.twodart.persistence.PlayerDAO;

/**
 *
 * @author RG79YA
 */
@Singleton
public class PlayerService extends BaseService
{

    private final PlayerDAO playerDAO;

    @Inject
    public PlayerService(PlayerDAO playerDAO)
    {
        this.playerDAO = playerDAO;
    }

    public Player get(String id)
    {
        Player player = playerDAO.get(id);
        
        requireResult(player);
        
        return player;
    }
    
    public Player getByEmail(String email)
    {
        Player player = playerDAO.getByEmail(email);
        
        requireResult(player);
        
        return player;
    }
    
    public Player create(Player player)
    {
        player.setCreationDate();
        
        playerDAO.create(player);
        
        return player;
    }
    
    public void update(Player authenticator, Player player)
    {
        validateSelf(authenticator, player);
        
        authenticator.setEmail(player.getEmail());
        authenticator.setName(player.getName());
        authenticator.setPassword(player.getPassword());
        
        playerDAO.update(authenticator);
    }
}
