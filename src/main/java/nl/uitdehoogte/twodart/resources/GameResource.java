/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.uitdehoogte.twodart.Views;
import nl.uitdehoogte.twodart.model.Game;
import nl.uitdehoogte.twodart.model.Turn;
import nl.uitdehoogte.twodart.model.Player;
import nl.uitdehoogte.twodart.model.Throw;
import nl.uitdehoogte.twodart.services.GameService;
import nl.uitdehoogte.twodart.services.PlayerService;
import nl.uitdehoogte.twodart.viewmodel.CreateGameView;
import nl.uitdehoogte.twodart.viewmodel.GameStatusView;

/**
 *
 * @author RG79YA
 */
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class GameResource
{
    private final GameService gameService;
    private final PlayerService playerService;
    
    @Inject
    public GameResource(GameService gameService, PlayerService playerService)
    {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GET
    @JsonView(Views.Public.class)
    @UnitOfWork
    @Path("/{id}")
    public Game get(@Auth Player authenticator, @PathParam("id") String id)
    {
        Game game = gameService.get(id);
        
        return game;
    }  
    
    @POST
    @JsonView(Views.Public.class)
    @UnitOfWork
    public Game create(@Auth Player authenticator, CreateGameView view)
    {
        Player player1 = playerService.get(view.player1Id);
        Player player2 = playerService.get(view.player2Id);
        
        return gameService.create(authenticator, player1, player2);
    }
    
    @DELETE
    @JsonView(Views.Public.class)
    @UnitOfWork
    @Path("/{id}")
    public void delete(@Auth Player authenticator, @PathParam("id") String id)
    {
        Game game = gameService.get(id);
        
        gameService.delete(game, authenticator);
    }
    
    @POST
    @JsonView(Views.Public.class)
    @UnitOfWork
    @Path("/{id}/turns")
    public List<Turn> addTurns(@Auth Player player, @PathParam("id") String id, List<Throw> playerThrows)
    {
        Game game = gameService.get(id);
        
        return gameService.addTurn(game, player, playerThrows);
    }
    
    @GET
    @JsonView(Views.Public.class)
    @UnitOfWork
    @Path("/{id}/status")
    public GameStatusView getStatus(@Auth Player authenticator, @PathParam("id") String id)
    {
        Game game = gameService.get(id);
        GameStatusView view = new GameStatusView();
     
        view.activePlayerId = game.getActivePlayer().getId();
        view.finished = game.isFinished();
        view.lastTurn = game.getLastTurn();
        
        return view;
    }  
}
