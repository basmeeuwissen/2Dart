/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.services;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import nl.uitdehoogte.twodart.model.AI;
import nl.uitdehoogte.twodart.model.AIX01;
import nl.uitdehoogte.twodart.model.Game;
import nl.uitdehoogte.twodart.model.Player;
import nl.uitdehoogte.twodart.model.Throw;
import nl.uitdehoogte.twodart.model.Turn;
import nl.uitdehoogte.twodart.persistence.GameDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author RG79YA
 */
public class GameService extends BaseService
{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    private final GameDAO gameDAO;
    private final AI ai;
    
    @Inject
    public GameService(GameDAO gameDAO, AIX01 ai)
    {
        this.gameDAO = gameDAO;
        this.ai = ai;
    }
    
    public Game get(String id)
    {
        Game game = gameDAO.get(id);
        
        requireResult(game);
        
        return game;
    }
    
    public Game create(Player authenticator, Player player1, Player player2)
    {
        Game game = new Game();
        
        game.setCreationDate();
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setRemainingPlayer1(501);
        game.setRemainingPlayer2(501);
        game.setCreator(authenticator);
        game.setActivePlayer(player1);
        
        validatePlayer(game, authenticator);
        
        gameDAO.create(game);
        
        return game;
    }
    
    public Turn addTurn(Game game, Player player, List<Throw> playerThrows)
    {
        validateNotFinished(game);
        validateSelf(game.getActivePlayer(), player);
        
        int score = updateScore(game, player, playerThrows);
        
        Turn turn = new Turn();
                
        turn.setCreationDate();
        turn.setPlayer(player);
        turn.setPlayerThrows(playerThrows);
        turn.setScore(score);
        turn.setGame(game);
        
        game.addTurn(turn);
        game.setModificationDate();
        
        for(Throw playerThrow : playerThrows)
        {
            playerThrow.setCreationDate();
            playerThrow.setTurn(turn);
        }
        
        if(game.getRemaining(game.getActivePlayer()) == 0)
        {
            game.setFinished(true);
        }
        
        switchActivePlayer(game);
        
        if(!game.isFinished() && game.getActivePlayer().isAiPlayer())
        {
            Turn aiTurn = getAiTurn(game);
            
            game.addTurn(aiTurn);

            if(game.getRemaining(game.getActivePlayer()) == 0)
            {
                game.setFinished(true);
            }            
            
            switchActivePlayer(game);
            
            return aiTurn;
        }
        
        return null;
    }
    
    private Turn getAiTurn(Game game)
    {
        List<Throw> playerThrows = new ArrayList<>();
        Player player = game.getActivePlayer();
        int remaining = game.getRemaining(player);
        
        Turn turn = new Turn();
        
        turn.setCreationDate();
        turn.setPlayer(player);
        turn.setGame(game);        
        
        for(int i = 0; i < 3; i++)
        {
            Throw playerThrow = ai.artificialThrow(player.getSkillLevel(), remaining);
            
            playerThrow.setCreationDate();
            playerThrow.setTurn(turn);
            
            remaining -= playerThrow.getScore() * playerThrow.getMultiplier();
            
            playerThrows.add(playerThrow);    
            
            if(remaining <= 0)
            {
                break;
            }
        }
        
        int score = updateScore(game, player, playerThrows);
        
        turn.setPlayerThrows(playerThrows);
        turn.setScore(score);;
        
        turn.setPlayerThrows(playerThrows);
        
        return turn;
    }
    
    private void validateNotFinished(Game game)
    {
        if(game.isFinished())
        {
            throw new BadRequestException();
        }
    }
    
    private void switchActivePlayer(Game game)
    {
        if(game.getActivePlayer().getId().equals(game.getPlayer1().getId()))
        {
            game.setActivePlayer(game.getPlayer2());
        }
        else
        {
            game.setActivePlayer(game.getPlayer1());
        }
    }
    
    private int updateScore(Game game, Player player, List<Throw> playerThrows)
    {
        validateThrows(playerThrows);
        
        int score = calculateScore(playerThrows);
        int remaining = game.getRemaining(player);
        
        score = validateScore(score, remaining);
        remaining = remaining - score;

        if(validScore(remaining, playerThrows))
        {
            game.setRemaining(player, remaining);
        }
        else
        {
            score = 0;
        }
        
        return score;
    }
    
    private void validateThrows(List<Throw> playerThrows)
    {        
        for(Throw playerThrow : playerThrows)
        {
            int score = playerThrow.getScore();
            int multiplier = playerThrow.getMultiplier();
            
            if(score > 20 && score != 25)
            {
                throw new BadRequestException();
            }
            
            if(score == 25 && multiplier > 2)
            {
                throw new BadRequestException();
            }
        }
    }
    
    private boolean validScore(int remaining, List<Throw> playerThrows)
    {
        if (remaining == 0)
        {
            return validateLastPlayerThrow(playerThrows.get(playerThrows.size() - 1));
        }
        
        return true;
    }
    
    private int calculateScore(List<Throw> playerThrows)
    {
        int score = 0;
        
        for(Throw playerThrow : playerThrows)
        {
            score += playerThrow.getScore() * playerThrow.getMultiplier();
        }
        
        return score;
    }
    
    private int validateScore(int score, int remaining)
    {
        return score <= remaining ? score : 0;
    }
    
    private boolean validateLastPlayerThrow(Throw lastPlayerThrow)
    {
        return lastPlayerThrow.getMultiplier() == 2;
    }
    
    public void delete(Game game, Player authenticator)
    {
        validatePlayer(game, authenticator);
        
        gameDAO.delete(game);
    }
    
    private void validatePlayer(Game game, Player player)
    {
        if (!game.getPlayer1().getId().equals(player.getId())
         && !game.getPlayer2().getId().equals(player.getId()))
        {
            throw new NotAuthorizedException("Not a game player");
        }
    }
}
