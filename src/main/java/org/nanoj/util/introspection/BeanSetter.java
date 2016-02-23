package org.nanoj.util.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanSetter 
{
	private final static String ERROR_INVOKE_SETTER = "Cannot invoke setter " ;

	String    propertyName  = "" ;
	
	Class<?>  propertyType  = null ;
	
	Method    method        = null ;

	public BeanSetter(Method method, String propertyName) {
		super();
		this.method = method;
		this.propertyName = propertyName;
		this.propertyType = method.getParameterTypes()[0]; // first parameter type
	}
	
	public Method getMethod() {
		return method ;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	public Class<?> getPropertyType() {
		return propertyType;
	}
	
	public void invoke(Object bean, Object value) 
	{
		String sMethodName = method.getName() ;
		
		Object params[] = new Object[1];
		params[0] = value ;
		
		try {
			this.method.invoke(bean, params);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
		}
	}
	
}
