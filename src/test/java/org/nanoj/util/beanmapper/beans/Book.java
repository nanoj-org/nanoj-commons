package org.nanoj.util.beanmapper.beans;

import java.io.Serializable;




public class Book implements Serializable
{
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY ATTRIBUTES 
    //----------------------------------------------------------------------
    private Integer    id           ;
    
    private Integer    publisherId  ;
    
    private Integer    authorId     ;
    
    private String     isbn         ;
    
    private String     title        ;
    
    private double     price        ;
    
    private Integer    quantity     ;
    
    private Integer    discount     ;
    
    private Short      availability ;
    
    private Short      bestSeller   ;
    

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Book()
    {
        super();
    }

    public void setA( Integer value )
    {
        
    }
    public Integer getA()
    {
        return 0;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR ENTITY FIELDS
    //----------------------------------------------------------------------
    //--- DB PRIMARY KEY : ID ( INTEGER ) 
    public void setId( Integer value )
    {
        this.id = value;
    }
    public Integer getId()
    {
        return this.id;
    }

    //--- DB COLUMN : PUBLISHER_ID ( INTEGER ) 
    public void setPublisherId( Integer value )
    {
        this.publisherId = value;
    }
    public Integer getPublisherId()
    {
        return this.publisherId;
    }

    //--- DB COLUMN : AUTHOR_ID ( INTEGER ) 
    public void setAuthorId( Integer value )
    {
        this.authorId = value;
    }
    public Integer getAuthorId()
    {
        return this.authorId;
    }

    //--- DB COLUMN : ISBN ( VARCHAR ) 
    public void setIsbn( String value )
    {
        this.isbn = value;
    }
    public String getIsbn()
    {
        return this.isbn;
    }

    //--- DB COLUMN : TITLE ( VARCHAR ) 
    public void setTitle( String value )
    {
        this.title = value;
    }
    public String getTitle()
    {
        return this.title;
    }

    //--- DB COLUMN : PRICE ( DECIMAL ) 
    public void setPrice( double value )
    {
        this.price = value;
    }
    public double getPrice()
    {
        return this.price;
    }

    //--- DB COLUMN : QUANTITY ( INTEGER ) 
    public void setQuantity( Integer value )
    {
        this.quantity = value;
    }
    public Integer getQuantity()
    {
        return this.quantity;
    }

    //--- DB COLUMN : DISCOUNT ( INTEGER ) 
    public void setDiscount( Integer value )
    {
        this.discount = value;
    }
    public Integer getDiscount()
    {
        return this.discount;
    }

    //--- DB COLUMN : AVAILABILITY ( SMALLINT ) 
    public void setAvailability( Short value )
    {
        this.availability = value;
    }
    public Short getAvailability()
    {
        return this.availability;
    }

    //--- DB COLUMN : BEST_SELLER ( SMALLINT ) 
    public void setBestSeller( Short value )
    {
        this.bestSeller = value;
    }
    public Short getBestSeller()
    {
        return this.bestSeller;
    }


    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    // SPECIFIC toString METHOD
    //----------------------------------------------------------------------
    public String toString()
    {
        StringBuffer sb = new StringBuffer(); 
        sb.append(id); 
        sb.append("|"); 
        sb.append(publisherId); 
        sb.append("|"); 
        sb.append(authorId); 
        sb.append("|"); 
        sb.append(isbn); 
        sb.append("|"); 
        sb.append(title); 
        sb.append("|"); 
        sb.append(price); 
        sb.append("|"); 
        sb.append(quantity); 
        sb.append("|"); 
        sb.append(discount); 
        sb.append("|"); 
        sb.append(availability); 
        sb.append("|"); 
        sb.append(bestSeller); 
        return sb.toString();	
    }
}
