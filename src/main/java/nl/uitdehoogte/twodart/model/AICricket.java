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
public class AICricket extends AI
{
    // to be implemented
    @Override
    protected Score artificialThrow(int remaining)
    {
        Score score = new Score();
        
        score.setValue(20);
        score.setMultiplier(3);
        
        return score;
    }
}
