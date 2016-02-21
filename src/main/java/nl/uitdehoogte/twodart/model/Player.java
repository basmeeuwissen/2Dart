/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.security.Principal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import nl.uitdehoogte.twodart.Views;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author RG79YA
 */
@Entity
@Table(name="players")
public class Player extends Model implements Principal
{

    @NotEmpty
    @Length(min = 4, max = 25)
    @JsonView(Views.Public.class)
    @Column
    private String name;

    @NotEmpty
    @Email
    @JsonView(Views.Protected.class)
    @Column
    private String email;

    @NotEmpty
    @Length(min = 8, max = 30)
    @JsonView(Views.Protected.class)
    @Column
    private String password;
    
    @NotNull
    @JsonView(Views.Public.class)
    @Column
    private boolean aiPlayer = false;

    @NotNull
    @JsonView(Views.Public.class)
    @Column
    private int skillLevel = 1;
    
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public boolean equals(Player player)
    {
        return email.equals(player.getEmail());
    }

    public boolean isAiPlayer()
    {
        return aiPlayer;
    }

    public void setAiPlayer(boolean aiPlayer)
    {
        this.aiPlayer = aiPlayer;
    }

    public int getSkillLevel()
    {
        return skillLevel;
    }

    public void setSkillLevel(int SkillLevel)
    {
        this.skillLevel = SkillLevel;
    }
}
