/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.services;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import nl.uitdehoogte.twodart.model.Model;

/**
 *
 * @author RG79YA
 */
public class BaseService
{
    public void requireResult(Model model)
    {
        if (model == null)
        {
            throw new NotFoundException();
        }
    }
    
    public void validateSelf(Model model1, Model model2)
    {
        if (!model1.getId().equals(model2.getId()))
        {
            throw new BadRequestException();
        }
    }
}
