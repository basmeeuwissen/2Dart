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
public class AIX01 extends AI
{
    private static final int [][] throwOutTable = 
    {
	{ 0,0},{ 0,0},
	{ 1,2},{ 1,1},{ 2,2},{ 1,1},{ 3,2},{ 3,1},{ 4,2},{ 1,1},{ 5,2},{ 3,1},
	{ 6,2},{ 5,1},{ 7,2},{ 7,1},{ 8,2},{ 9,1},{ 9,2},{ 3,1},{10,2},{ 5,1},
	{11,2},{ 7,1},{12,2},{17,1},{13,2},{19,1},{14,2},{13,1},{15,2},{15,1},
	{16,2},{ 1,1},{17,2},{ 3,1},{18,2},{ 5,1},{19,2},{ 7,1},{20,2},{ 9,1},
	{10,1},{11,1},{12,1},{13,1},{ 6,1},{15,1},{16,1},{17,1},{50,1},{19,1},
	{12,3},{17,1},{14,1},{15,1},{16,3},{17,1},{18,1},{19,1},{20,1},{15,3},
	{10,3},{17,3},{16,3},{25,1},{10,3},{17,3},{20,3},{19,3},{18,3},{13,3},
	{12,3},{19,3},{14,3},{17,3},{20,3},{19,3},{18,3},{13,3},{20,3},{19,3},
	{14,3},{17,3},{20,3},{19,3},{18,3},{17,3},{20,3},{19,3},{20,3},{17,3},
	{20,3},{20,3},{20,3},{20,3},{19,3},{18,3},{20,3},{20,3},{19,3},{20,3},
	{20,3},{20,3},{20,3},{20,3},{19,3},{20,3},{19,3},{20,3},{20,3},{20,3},
	{19,3},{20,3},{20,3},{18,3},{19,3},{20,3},{18,3},{19,3},{20,3},{18,3},
	{19,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},
	{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},
	{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},{20,3},
	{13,3},{20,3},{20,3},{14,3},{19,3},{20,3},{20,3},{20,3},{20,3},{20,3},
	{20,3},{20,3}
    };
    
    @Override
    protected Score artificialThrow(int remaining)
    {
        Score score = new Score();
        
	if(remaining > 170)
        {
            score.setValue(20);
            score.setMultiplier(3);
        }
	else
        {
            score.setValue(throwOutTable[remaining][0]);
            score.setMultiplier(throwOutTable[remaining][1]);
        }
	
        return score;
    }
}
