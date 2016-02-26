/**
 *  Copyright (C) 2013-2016 Laurent GUERIN - NanoJ project org. ( http://www.nanoj.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.nanoj.util.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class BeanIntrospector {

	private final static BeanGetter[]  VOID_GETTERS_ARRAY = new BeanGetter[0];
	private final static BeanSetter[]  VOID_SETTERS_ARRAY = new BeanSetter[0];
	
	/**
	 * Returns all the methods of the given bean class
	 * @param bean
	 * @return
	 */
	public final static Method[] getMethods(Object bean) 
	{
		Class<?> beanClass = bean.getClass();		
        Method[] methods = null;
        try
        {
            methods = beanClass.getMethods();
            
        } catch (SecurityException e)
        {
            throw new RuntimeException("mapToBean : security violation, class = " + beanClass.getName(), e);
        }
        return methods ;
	}

	private final static String getPropertyName(String sMethodName) 
	{
    	char c = sMethodName.charAt(3); // setFirstName, getFirstName --> 'F'
    	if ( c >= 'A' && c <= 'Z' ) {
    		c = Character.toLowerCase(c) ; 
    	}
    	String propertyName = c + sMethodName.substring(4); // 'f' + "irstName"
		return propertyName ;
	}

	/**
	 * Returns true if the given method is a "getter" : <br>
	 *  starts with "get", no parameter, not "getClass", name length > 3
	 * @param m
	 * @return
	 */
	public final static boolean isGetter(Method m) 
	{
        String sMethodName = m.getName();
        Class<?> paramTypes[] =  m.getParameterTypes() ;
        if ( sMethodName.length() > 3 && sMethodName.startsWith("get") 
        		&& paramTypes.length == 0 && ( ! "getClass".equals(sMethodName) ) )
        {
        	return true ;
        }
        return false ;
	}
	
	/**
	 * Returns true if the given method is a "setter" : <br>
	 *  starts with "set", one parameter, name length > 3
	 * @param m
	 * @return
	 */
	public final static boolean isSetter(Method m) 
	{
        String sMethodName = m.getName();
        Class<?> paramTypes[] =  m.getParameterTypes() ;
        if ( sMethodName.length() > 3 && sMethodName.startsWith("set") && paramTypes.length == 1 )
        {
        	//--- This is SETTER 
        	return true ;
        }
        return false ;
	}
	
	/**
	 * Returns all the getters for the given bean class ( methods starting with "get" except "getClass" ) 
	 * @param bean
	 * @return
	 */
	public final static BeanGetter[] getGetters(Object bean) 
	{
//		Class<?> beanClass = bean.getClass();
//		
//        Method[] methods = null;
//        try
//        {
//            methods = beanClass.getMethods();
//            
//        } catch (SecurityException e)
//        {
//            throw new RuntimeException("mapToBean : security violation, class = " + beanClass.getName(), e);
//        }

        Method[] methods = getMethods(bean) ;
        if ( methods != null )
        {
            Method m = null;
        	LinkedList<BeanGetter> getters = new LinkedList<BeanGetter>();
            for ( int i = 0 ; i < methods.length ; i++ )
            {
                m = methods[i] ;
                
//                String sMethodName = m.getName();
//                Class<?> paramTypes[] =  m.getParameterTypes() ;
//                if ( sMethodName.length() > 3 && sMethodName.startsWith("get") 
//                		&& paramTypes.length == 0 && ( ! "getClass".equals(sMethodName) ) )
                	
                if ( isGetter(m) )
                {
                	//--- This is a GETTER : get the property name
//                	String propertyName = getPropertyName(sMethodName) ;
                	String propertyName = getPropertyName(m.getName()) ;
                	getters.add( new BeanGetter(m,propertyName) );
                }
            }
            return getters.toArray(VOID_GETTERS_ARRAY);
        }
		
		return VOID_GETTERS_ARRAY ;
	}
	
	/**
	 * Returns all the setters for the given bean class ( methods starting with "set" ) 
	 * @param bean
	 * @return 
	 */
	public final static BeanSetter[] getSetters(Object bean) 
	{
        Method[] methods = getMethods(bean) ;
        if ( methods != null )
        {
            Method m = null;
        	LinkedList<BeanSetter> setters = new LinkedList<BeanSetter>();
            for ( int i = 0 ; i < methods.length ; i++ )
            {
                m = methods[i] ;
//                String sMethodName = m.getName();
//                Class<?> paramTypes[] =  m.getParameterTypes() ;
//                if ( sMethodName.length() > 3 && sMethodName.startsWith("set") && paramTypes.length == 1 )
                if ( isSetter(m) )
                {
                	//--- This is SETTER : get the property name
//                	String propertyName = getPropertyName(sMethodName) ;
                	String propertyName = getPropertyName(m.getName()) ;
                	setters.add( new BeanSetter(m,propertyName) );
                }
            }
            return setters.toArray(VOID_SETTERS_ARRAY);
        }
		return VOID_SETTERS_ARRAY ;
	}
	
	
	
	/**
	 * Returns the field identified by the given name
	 * @param c
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Field getFieldByName(Class<?> c, String fieldName) throws SecurityException, NoSuchFieldException
	{
		//System.out.println(". getFieldByName + '" + fieldName + "'" );
		Field f = null ;
		try 
		{
			// f = c.getField(fieldName); // NoSuchFieldException
			f = c.getDeclaredField(fieldName); // even if "private"
		} 
		catch (NoSuchFieldException e) 
		{
			//--- Not (yet) found => try in super class if any
			Class<?> ancestor = c.getSuperclass() ;
			if ( ancestor != null ) 
			{
				if ( ancestor.getSuperclass() != null ) // Not yet "Object" class
				{					
					f = getFieldByName(ancestor, fieldName); 
				}
				else {
					throw e ; // Really unknown in this class
				}
			}
			else {
				throw e ; // Really unknown in this class
			}
		}
		return f ;
	}
	
	private final static String ERROR_GET_GETTER_METHOD    = "Cannot get method for getter " ;
	private final static String ERROR_INVOKE_GETTER_METHOD = "Cannot invoke method for getter " ;

	public static Object invokeGetter(Object instance, String getterName ) 
	{
		Object o = invokeGetter(instance, getterName, Object.class);
		return o ;
	}
	
	public static <T> T invokeGetter(Object instance, String getterName, Class<T> expectedClass) 
	{
		if ( null == instance ) {
			throw new IllegalArgumentException("Object instance argument is null");
		}
		if ( null == getterName ) {
			throw new IllegalArgumentException("Getter name argument is null");
		}
		if ( null == expectedClass ) {
			throw new IllegalArgumentException("Expected class argument is null");
		}

		Class<?> cl = instance.getClass();
		
		Object result = null ;
		Method m ;
		try {
			m = cl.getMethod(getterName, (Class<?>[])null); 
		} catch (SecurityException e) {
			throw new RuntimeException(ERROR_GET_GETTER_METHOD + getterName + "(SecurityException)", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(ERROR_GET_GETTER_METHOD + getterName + "(NoSuchMethodException)", e);
		}

		try {
			result = m.invoke(instance, (Object[])null);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER_METHOD + getterName + "(IllegalArgumentException)", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER_METHOD + getterName + "(IllegalAccessException)", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(ERROR_INVOKE_GETTER_METHOD + getterName + "(InvocationTargetException)", e);
		}
		
		if ( expectedClass.isInstance(result) ) {
			return expectedClass.cast(result) ;
			//return (T) result ;
		}
		else {
			return null ;
		}
	}
	
}
