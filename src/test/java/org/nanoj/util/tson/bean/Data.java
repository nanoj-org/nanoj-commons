package org.nanoj.util.tson.bean;

public class Data {

	private BeanDate      beanDate = null ;
	
	private BeanPrimitive beanPrimitive = null ;

	//---------------------------------------------------------------
	public BeanDate getBeanDate() {
		return beanDate;
	}

	public void setBeanDate(BeanDate v) {
		beanDate = v;
	}

	//---------------------------------------------------------------
	public BeanPrimitive getBeanPrimitive() {
		return beanPrimitive;
	}

	public void setBeanPrimitive(BeanPrimitive v) {
		beanPrimitive = v;
	}
	
	//---------------------------------------------------------------
    public String toString()
    {
		return 
		  " beanPrimitive=" + beanPrimitive 
		+ "\n"
		+ " beanDate=" + beanDate 
		;
    }
	
}
