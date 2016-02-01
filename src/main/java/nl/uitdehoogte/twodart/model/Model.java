/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author RG79YA
 */
@MappedSuperclass
public class Model implements Serializable
{
    @Id
    @GeneratedValue(generator="cat-uuid") 
    @GenericGenerator(name="cat-uuid", strategy="uuid")
    private String id;
    
    @JsonIgnore
    @Column
    private boolean deleted = false;
    
    @JsonIgnore
    @Column
    private Date creationDate;
    
    @JsonIgnore
    @Column
    private Date modificationDate;
    
    @JsonIgnore
    @Column
    private Date deletionDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate()
    {
        this.creationDate = new Date();
    }

    public Date getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate()
    {
        this.modificationDate = new Date();
    }

    public Date getDeletionDate()
    {
        return deletionDate;
    }

    public void setDeletionDate()
    {
        this.deletionDate = new Date();
    }
}
