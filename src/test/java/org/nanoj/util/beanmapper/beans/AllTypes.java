package org.nanoj.util.beanmapper.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;




public class AllTypes implements Serializable
{
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY ATTRIBUTES 
    //----------------------------------------------------------------------
    private String    name ;

    private Integer   Int1 ;
    private int       Int2 ;

    private Short     Short1 ;
    private short     Short2 ;

    private Byte      Byte1 ;
    private byte      Byte2 ;

    private Long      Long1 ;
    private long      Long2 ;
    
    private Double    Double1 ;
    private double    Double2 ;
    
    private Float     Float1 ;
    private float     Float2 ;
    
    private Boolean   Bool1 ;
    private boolean   Bool2 ;
    
    private BigDecimal   bigDecimal1 ;
    private BigInteger   bigInteger1 ;

    private java.util.Date      utilDate ;

    private java.sql.Date       sqlDate ;
    private java.sql.Time       sqlTime ;
    private java.sql.Timestamp  sqlTimestamp ;

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public AllTypes()
    {
        super();
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getInt1() {
		return Int1;
	}

	public void setInt1(Integer int1) {
		Int1 = int1;
	}

	public int getInt2() {
		return Int2;
	}

	public void setInt2(int int2) {
		Int2 = int2;
	}

	public Short getShort1() {
		return Short1;
	}

	public void setShort1(Short short1) {
		Short1 = short1;
	}

	public short getShort2() {
		return Short2;
	}

	public void setShort2(short short2) {
		Short2 = short2;
	}

	public Byte getByte1() {
		return Byte1;
	}

	public void setByte1(Byte byte1) {
		Byte1 = byte1;
	}

	public byte getByte2() {
		return Byte2;
	}

	public void setByte2(byte byte2) {
		Byte2 = byte2;
	}

	public Long getLong1() {
		return Long1;
	}

	public void setLong1(Long long1) {
		Long1 = long1;
	}

	public long getLong2() {
		return Long2;
	}

	public void setLong2(long long2) {
		Long2 = long2;
	}

	public Double getDouble1() {
		return Double1;
	}

	public void setDouble1(Double double1) {
		Double1 = double1;
	}

	public double getDouble2() {
		return Double2;
	}

	public void setDouble2(double double2) {
		Double2 = double2;
	}

	public Float getFloat1() {
		return Float1;
	}

	public void setFloat1(Float float1) {
		Float1 = float1;
	}

	public float getFloat2() {
		return Float2;
	}

	public void setFloat2(float float2) {
		Float2 = float2;
	}

	public Boolean getBool1() {
		return Bool1;
	}

	public void setBool1(Boolean bool1) {
		Bool1 = bool1;
	}

	public boolean getBool2() {
		return Bool2;
	}

	public void setBool2(boolean bool2) {
		Bool2 = bool2;
	}

	public BigDecimal getBigDecimal1() {
		return bigDecimal1;
	}

	public void setBigDecimal1(BigDecimal bigDecimal1) {
		this.bigDecimal1 = bigDecimal1;
	}

	public BigInteger getBigInteger1() {
		return bigInteger1;
	}

	public void setBigInteger1(BigInteger bigInteger1) {
		this.bigInteger1 = bigInteger1;
	}

	public java.util.Date getUtilDate() {
		return utilDate;
	}

	public void setUtilDate(java.util.Date utilDate) {
		this.utilDate = utilDate;
	}

	public java.sql.Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(java.sql.Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	public java.sql.Time getSqlTime() {
		return sqlTime;
	}

	public void setSqlTime(java.sql.Time sqlTime) {
		this.sqlTime = sqlTime;
	}

	public java.sql.Timestamp getSqlTimestamp() {
		return sqlTimestamp;
	}

	public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
		this.sqlTimestamp = sqlTimestamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//----------------------------------------------------------------------
    // SPECIFIC toString METHOD
    //----------------------------------------------------------------------
    public String toString()
    {
        StringBuffer sb = new StringBuffer(); 
        sb.append(name);
        
        sb.append("|"); 
        sb.append(this.Int1); 
        sb.append("|"); 
        sb.append(this.Int2); 

        sb.append("|"); 
        sb.append(this.Byte1); 
        sb.append("|"); 
        sb.append(this.Byte2); 

        sb.append("|"); 
        sb.append(this.Short1); 
        sb.append("|"); 
        sb.append(this.Short2); 

        sb.append("|"); 
        sb.append(this.Long1); 
        sb.append("|"); 
        sb.append(this.Long2); 

        sb.append("|"); 
        sb.append(this.Float1); 
        sb.append("|"); 
        sb.append(this.Float2); 

        sb.append("|"); 
        sb.append(this.Double1); 
        sb.append("|"); 
        sb.append(this.Double2); 

        sb.append("|"); 
        sb.append(this.Bool1); 
        sb.append("|"); 
        sb.append(this.Bool2); 

        sb.append("|"); 
        sb.append(this.bigInteger1); 

        sb.append("|"); 
        sb.append(this.bigDecimal1); 

        sb.append("|"); 
        sb.append(this.utilDate); 

        sb.append("|"); 
        sb.append(this.sqlDate); 
        sb.append("|"); 
        sb.append(this.sqlTime); 
        sb.append("|"); 
        sb.append(this.sqlTimestamp); 

        
        
        
        return sb.toString();	
    }
}
