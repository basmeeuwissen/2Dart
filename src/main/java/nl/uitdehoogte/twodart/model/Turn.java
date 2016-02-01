/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import nl.uitdehoogte.twodart.Views;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author RG79YA
 */
@Entity
@Table(name="turns")
public class Turn extends Model
{
    @JsonView(Views.Public.class)
    @ManyToOne
    private Player player;
    
    @JsonIgnore
    @ManyToOne
    private Game game;
    
    @NotEmpty
    @JsonView(Views.Public.class)
    @OneToMany(mappedBy="turn", cascade={CascadeType.ALL})
    private List<Throw> playerThrows;
    
    @NotNull
    @Range(min=0, max=180)
    @JsonView(Views.Public.class)
    @Column
    private int score;

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public List<Throw> getPlayerThrows()
    {
        return playerThrows;
    }

    public void setPlayerThrows(List<Throw> playerThrows)
    {
        this.playerThrows = playerThrows;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
}
