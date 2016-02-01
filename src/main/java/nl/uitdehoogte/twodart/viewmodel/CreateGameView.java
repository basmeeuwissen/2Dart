/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.viewmodel;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author RG79YA
 */
public class CreateGameView
{
    @NotEmpty
    public String player1Id;
    
    @NotEmpty
    public String player2Id;
}
