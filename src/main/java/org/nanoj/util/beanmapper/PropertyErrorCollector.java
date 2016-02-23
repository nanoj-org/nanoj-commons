package org.nanoj.util.beanmapper;

import java.util.LinkedList;

public class PropertyErrorCollector {

	public final static int NULL_VALUE_FOR_PRIMITIVE_TYPE = 1;
	
	private LinkedList<String> errors = null ;
	
	private void checkErrorsListReady() 
	{
		if ( null == errors ) {
			errors = new LinkedList<String> () ;
		}
	}
	
	public void addError(String propertyName, String errorMessage) 
	{
		checkErrorsListReady() ;
		errors.add(errorMessage + " (property '" + propertyName + "')" );
	}

	public void addError(String propertyName, int errorType) 
	{
		checkErrorsListReady() ;
		String sMessage = "Error";
		switch (errorType) 
		{
		case NULL_VALUE_FOR_PRIMITIVE_TYPE :
			sMessage = "Cannot set a null value for a primitive type";
			break;
		}
		errors.add(sMessage + " (property '" + propertyName + "')" );
	}
	
	/**
	 * Returns the number of errors
	 * @return
	 */
	public int errorsCount() 
	{
		return ( errors != null ? errors.size() : 0 ) ;
	}
	
	/**
	 * Returns the list of errors (or null if no error )
	 * @return
	 */
	public LinkedList<String> getErrors() 
	{
		return errors ;
	}
}
