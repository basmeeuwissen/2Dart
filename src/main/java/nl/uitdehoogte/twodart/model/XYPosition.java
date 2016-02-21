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
public class XYPosition
{
    private double x;
    private double y;

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }
   
    public void incrementX(double increment)
    {
        this.x += increment;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
    
    public void incrementY(double increment)
    {
        this.y += increment;
    }
}
