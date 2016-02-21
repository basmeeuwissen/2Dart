/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.round;
/**
 *
 * @author RG79YA
 */
public abstract class AI
{
    /*
      Level of the ai is the maximum deviation (12) minus the current level
      of the user. So a user who just starts (level 1) and plays against the
      ai will get an ai player with deviation 12 - 1 = 11. The best players 
      (level 10) will get an ai player with level 12 - 10 = 2;
     */
    int [] aiDifficultyLevels = new int[]
    {
        11, 10, 9, 8, 7, 6, 5, 4, 3, 2
    };
    
    private static final Dartboard dartboard = new Dartboard();
    
    private static final double SQRT_PI = Math.sqrt(Math.PI);

    private double erfInverse(double x)
    {
        x = (2 * x) / SQRT_PI;
        return x + (1.0 / 3 * pow(x, 3.0)) + (7.0 / 30 * pow(x, 5.0)) + (127.0 / 630 * pow(x, 7.0));
    }

    private double randn(int difficulty)
    {
        double x = (random() - 0.5) * 2;

        return round(aiDifficultyLevels[difficulty] * SQRT_PI * erfInverse(x));
    }
    
    protected abstract Score artificialThrow(int remaining);
    
    public Throw artificialThrow(int difficulty, int remaining)
    {
        Score score = artificialThrow(remaining);
        XYPosition xyPosition = dartboard.getXYPosition(score.getValue(), score.getMultiplier());

        xyPosition.incrementX(randn(aiDifficultyLevels[difficulty]));
        xyPosition.incrementY(randn(aiDifficultyLevels[difficulty]));

        BoxPosition boxPosition = dartboard.getBoxPosition(xyPosition);
        
        score = dartboard.getScore(boxPosition);
        
        Throw playerThrow = new Throw();
        playerThrow.setAngle(boxPosition.getAngle());
        playerThrow.setDistance(boxPosition.getDistance());
        playerThrow.setMultiplier(score.getMultiplier());
        playerThrow.setScore(score.getValue());
        
        return playerThrow;
    }

    public XYPosition getAdvise(int remaining)
    {
        Score finish = artificialThrow(remaining);

        return dartboard.getXYPosition(finish.getValue(), finish.getMultiplier());
    }
}
