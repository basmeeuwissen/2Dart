/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import nl.uitdehoogte.twodart.Views;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author RG79YA
 */
@Entity
@Table(name="games")
public class Game extends Model
{
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Player creator;
    
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Player player1;
    
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Player player2;
    
    @NotNull
    @JsonIgnore
    @ManyToOne
    private Player activePlayer;
    
    @OneToMany(mappedBy="game", cascade={CascadeType.ALL})
    private List<Turn> turns;
    
    @Range(min=0)
    @JsonView(Views.Public.class)
    @Column
    private int turnCount;
    
    @Range(min=0, max=501)
    @JsonView(Views.Public.class)
    @Column
    private int remainingPlayer1;
    
    @Range(min=0, max=501)
    @JsonView(Views.Public.class)
    @Column
    private int remainingPlayer2;

    @JsonView(Views.Public.class)
    @Column
    private boolean finished = false;
    
    public Game()
    {
        turns = new ArrayList<>();
        turnCount = 0;
    }

    public Player getCreator()
    {
        return creator;
    }

    public void setCreator(Player creator)
    {
        this.creator = creator;
    }
    
    public Player getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }

    public Player getActivePlayer()
    {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer)
    {
        this.activePlayer = activePlayer;
    }

    public List<Turn> getTurns()
    {
        return turns;
    }

    public void setTurns(List<Turn> turns)
    {
        this.turns = turns;
    }

    public void addTurn(Turn turn)
    {
        this.turns.add(turn);
        increaseTurnCount();
    }
    
    public int getTurnCount()
    {
        return turnCount;
    }

    public void setTurnCount(int turnCount)
    {
        this.turnCount = turnCount;
    }

    public Turn getLastTurn()
    {
        return turns.get(turns.size() - 1);
    }
    
    public void increaseTurnCount()
    {
        this.turnCount++;
    }
    
    public int getRemaining(Player player)
    {
        return player.getId().equals(player1.getId()) 
            ? getRemainingPlayer1() 
            : getRemainingPlayer2();
    }
    
    public void setRemaining(Player player, int remaining)
    {
        if(player.getId().equals(player1.getId()))
        {
            setRemainingPlayer1(remaining);
        }
        else
        {
            setRemainingPlayer2(remaining);
        }
    }
    
    public int getRemainingPlayer1()
    {
        return remainingPlayer1;
    }

    public void setRemainingPlayer1(int remainingPlayer1)
    {
        this.remainingPlayer1 = remainingPlayer1;
    }
    
    public int getRemainingPlayer2()
    {
        return remainingPlayer2;
    }

    public void setRemainingPlayer2(int remainingPlayer2)
    {
        this.remainingPlayer2 = remainingPlayer2;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
}
