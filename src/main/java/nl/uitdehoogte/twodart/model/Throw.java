/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import nl.uitdehoogte.twodart.Views;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author RG79YA
 */
@Entity
@Table(name="throws")
public class Throw extends Model
{
    @JsonIgnore
    @ManyToOne
    private Turn turn;
    
    @NotNull
    @Range(min=0, max=25)
    @JsonView(Views.Public.class)
    @Column
    private int score;
    
    @NotNull
    @Range(min=1, max=3)
    @JsonView(Views.Public.class)
    @Column
    private int multiplier;
    
    @NotNull
    @Range(min=0, max=360)
    @JsonView(Views.Public.class)
    @Column
    private double angle;
    
    @NotNull
    @Range(min=0, max=100)
    @JsonView(Views.Public.class)
    @Column
    private double distance;

    public Turn getTurn()
    {
        return turn;
    }

    public void setTurn(Turn turn)
    {
        this.turn = turn;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(int multiplier)
    {
        this.multiplier = multiplier;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }
    
}
