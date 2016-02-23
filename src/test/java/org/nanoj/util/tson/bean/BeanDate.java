package org.nanoj.util.tson.bean;

public class BeanDate
{

    private java.util.Date      utilDate ;
    
    private java.sql.Date       sqlDate ;
    private java.sql.Time       sqlTime ;
    private java.sql.Timestamp  sqlTimestamp ;
    
    private String              str ;
    
    
    public java.util.Date getUtilDate()
    {
        return utilDate;
    }
    public void setUtilDate(java.util.Date utilDate)
    {
        this.utilDate = utilDate;
    }

    public java.sql.Date getSqlDate()
    {
        return sqlDate;
    }
    public void setSqlDate(java.sql.Date sqlDate)
    {
        this.sqlDate = sqlDate;
    }
    
    public java.sql.Time getSqlTime()
    {
        return sqlTime;
    }
    public void setSqlTime(java.sql.Time sqlTime)
    {
        this.sqlTime = sqlTime;
    }
    
    public java.sql.Timestamp getSqlTimestamp()
    {
        return sqlTimestamp;
    }
    public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp)
    {
        this.sqlTimestamp = sqlTimestamp;
    }
    
    public String getStr()
    {
        return str;
    }
    public void setStr(String str)
    {
        this.str = str;
    }
    
    
    
    public String toString()
    {
		return "utilDate=" + utilDate 
		+ " sqlDate=" + sqlDate 
		+ " sqlTime=" + sqlTime 
		+ " sqlTimestamp=" + sqlTimestamp 
		+ " str='" + str +"'"
		;
    }
}
