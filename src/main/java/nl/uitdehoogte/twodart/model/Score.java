/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

/**
 *
 * @author RG79YA
 */
public class Score
{
    private int value;
    private int multiplier;

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(int multiplier)
    {
        this.multiplier = multiplier;
    }
}
