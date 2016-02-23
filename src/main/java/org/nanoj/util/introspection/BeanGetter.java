package org.nanoj.util.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanGetter 
{
	private final static String ERROR_INVOKE_GETTER = "Cannot invoke getter " ;

	String propertyName = "" ;
	
	Method method = null ;

	boolean xmlElement = false ; // XML Attribute by default

	public BeanGetter(Method method, String propertyName) {
		super();
		this.propertyName = propertyName;
		this.method = method;
		
		Annotation[] annotations = method.getAnnotations();
		for ( Annotation a : annotations ) {
			Class<?> c = a.annotationType();
			if ( c.getName().endsWith("XmlElement") ) {
				this.xmlElement = true ;
			}
		}
	}
	
	public Method getMethod() {
		return method ;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	public Class<?> getReturnType() {
		return method.getReturnType();
	}
	
	public boolean isSubElement() {
		return this.xmlElement;
	}
	
	public Object invoke(Object bean) 
	{
		String sMethodName = method.getName() ;
		Object retValue = null ;
		try {
			retValue = method.invoke(bean, (Object []) null);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
		}
		return retValue ;
	}
	
}
