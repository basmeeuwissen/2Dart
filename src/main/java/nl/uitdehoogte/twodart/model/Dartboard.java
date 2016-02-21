/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author RG79YA
 */
public class Dartboard
{
    //Board definition
    private final int minX = -100,
                      maxX = 100,
                      minY = -100,
                      maxY = 100,
                      center = Math.round((maxY - minY) / 2);

    
    private final int [] slices = new int []{20, 1, 18, 4, 13, 6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5, 20};
    
    private final double [][] rings = new double [][]
    {
        // (distance from center (based on circle with radius of 100), calculated value, multiplier)
        {3.46, 0, 50}, // Bulseye
        {9.09, 0, 25}, // Single bull
        {56.36, 1, 1}, // Single value
        {63.18, 1, 3}, // Triple value
        {93.64, 1, 1}, // Single value
        {100.00, 1, 2} // Double value
    };
    
    public Score getScore(BoxPosition boxPosition)
    {
        // hoek + 9 voor de verschuiving van de hoek
        // Hoek 0 begint namelijk in het midden van de 20
        int slice = slices[(int)floor((boxPosition.getAngle() + 9) / 18)];
        double percentage = (100 / center) * boxPosition.getDistance();
        Score score = new Score();
        
        if (percentage < 100)
        {
            double [] ring;

            for (int index = 0; index < rings.length; index++)
            {
                if (rings[index][0] > percentage)
                {
                    ring = rings[index];
                    
                    score.setMultiplier(ring[1] == 1 ? (int)ring[2] : 1);
                    score.setValue(ring[1] == 1 ? slice : (int)ring[2]);
                    
                    break;
                }
            }
        }
        
        return score;
    }
    
    public BoxPosition getBoxPosition(XYPosition xyPosition)
    {
        double width = xyPosition.getX() - (center + minX),
               height = xyPosition.getY() - (center + minY),
               distance = sqrt(pow(width, 2) + pow(height, 2)),
               angle = atan(width / height) * 180 / PI;

        if (width < 0 && height < 0)
        {
            angle = 360 - angle;
        }
        else if (width < 0)
        {
            angle = -angle + 180;
        }
        else if (height < 0)
        {
            angle = -angle;
        }
        else
        {
            angle = 180 - angle;
        }

        BoxPosition boxPosition = new BoxPosition();
        boxPosition.setDistance(distance);
        boxPosition.setAngle(angle);
        
        return boxPosition;
    }
    
    public BoxPosition getBoxPosition(int value, int ring)
    {
        double angle = getAngle(value);
        double distance = getDistance(value, ring);
        
        BoxPosition boxPosition = new BoxPosition();
        boxPosition.setAngle(angle);
        boxPosition.setDistance(distance);
        
        return boxPosition;
    }
    
    public XYPosition getXYPosition(int value, int ring)
    {
        BoxPosition boxPosition = getBoxPosition(value, ring);
        
        XYPosition xyPosition = new XYPosition();
        xyPosition.setX(boxPosition.getDistance() * sin(boxPosition.getAngle() * PI / 180));
        xyPosition.setY(boxPosition.getDistance() * cos(boxPosition.getAngle() * PI / 180) * -1);
        
        return xyPosition;
    }
    
    private double getAngle(int value)
    {
        for (int i = 0; i < 20; i++)
        {
            if (slices[i] == value)
            {
                //center of the box, 18 degrees per slice
                return i * 18;
            }
        }

        return 0;
    }
    
    private double getBoxCenter(int ring)
    {
        double min = ring > 0 ? rings[ring - 1][0] : -rings[0][0];
        double max = rings[ring][0];
        
        return (min + max) / 2;
    }

    private double getDistance(int value, int ring)
    {
        if (value == 50)
        {
            return 0;
        }

        if (value == 25)
        {
            return getBoxCenter(1);
        }

        switch (ring)
        {
            case 2:
                return getBoxCenter(5);
            case 3:
                return getBoxCenter(3);
            default:
                return getBoxCenter(4);
        }
    }
}
