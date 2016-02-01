/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.resources;

import io.dropwizard.auth.Auth;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.uitdehoogte.twodart.Views;
import nl.uitdehoogte.twodart.model.Player;
import nl.uitdehoogte.twodart.services.PlayerService;

/**
 *
 * @author RG79YA
 */
@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class PlayerResource
{
    private final PlayerService playerService;

    @Inject
    public PlayerResource(PlayerService playerService)
    {
        this.playerService = playerService;
    }
    
    @POST
    @JsonView(Views.Protected.class)
    @UnitOfWork
    public void create(Player player)
    {
        playerService.create(player);
    }
    
    @Path("/me")
    @GET
    @JsonView(Views.Public.class)
    @UnitOfWork
    public Player getSelf(@Auth Player authenticator)
    {
        return authenticator;
    }
    
    /*
    @Path("/me")
    @PUT
    @JsonView(Views.Protected.class)
    public void update(@Auth Player authenticator, Player player)
    {
        playerService.update(authenticator, player);
    }
    */
}
