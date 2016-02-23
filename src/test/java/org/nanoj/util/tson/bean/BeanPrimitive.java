package org.nanoj.util.tson.bean;

public class BeanPrimitive
{

    private char  ccc = 0 ;
    
    private byte  bbb = 0 ;
    private short sss = 0 ;
    private int   iii = 0 ;
    private long  lll = 0 ;
    
    private boolean bool = false ;
    
    private float  fff = 0 ;
    private double ddd = 0.0 ;
    
    private String str = null ;
    
    //----------------------------------------------------------------------
    //--- int
    public int getIii()
    {
        return iii;
    }
    public void setIii(int i)
    {
        this.iii = i;
    }
    
    //--- byte
    public byte getBbb()
    {
        return bbb;
    }
    public void setBbb(byte bbb)
    {
        this.bbb = bbb;
    }
    
    //--- long
    public long getLll()
    {
        return lll;
    }
    public void setLll(long lll)
    {
        this.lll = lll;
    }
    
    //--- short
    public void setSss(short sss)
    {
        this.sss = sss;
    }
    public short getSss()
    {
        return this.sss ;
    }
    

    //----------------------------------------------------------------------
    //--- float
    public float getFff()
    {
        return fff;
    }
    public void setFff(float fff)
    {
        this.fff = fff;
    }
    
    //--- double
    public double getDdd()
    {
        return ddd;
    }
    public void setDdd(double d)
    {
        this.ddd = d;
    }
    
    //----------------------------------------------------------------------
    //--- boolean
    public boolean isBool()
    {
        return bool;
    }
    public void setBool(boolean b)
    {
        this.bool = b;
    }
    
    //----------------------------------------------------------------------
    //--- char
    public char getCcc()
    {
        return ccc;
    }
    public void setCcc(char ccc)
    {
        this.ccc = ccc;
    }
    
    //----------------------------------------------------------------------
    //--- String
    public String getStr()
    {
        return str;
    }
    public void setStr(String s)
    {
        this.str = s;
    }
    
    //----------------------------------------------------------------------
	public String toString() {
		return "ccc=" + ccc 
			+ " bbb=" + bbb 
			+ " sss=" + sss 
			+ " iii=" + iii 
			+ " lll=" + lll 
			+ " fff=" + fff 
			+ " ddd=" + ddd  
			+ " bool=" + bool  
			+ " str='" + str +"'"
			;
	}
    
}
