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
package org.nanoj.util.tson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * @author Laurent GUERIN
 *
 */
public class ParserUtil
{
    //----------------------------------------------------------------------------------------------
    // CONSTANTS
    //----------------------------------------------------------------------------------------------
	private static void log(String msg) {
		// System.out.println(msg); System.out.flush();
	}
    
    //----------------------------------------------------------------------------------------------
    private static Method findMethodByName(Class<?> beanClass, String sMethodName )
    {
    	Method[] methods = beanClass.getMethods();

		for ( int i = 0 ; i < methods.length ; i++ ) 
		{
			Method m = methods[i] ;
			if ( sMethodName.equals ( m.getName() ) )
			{
				return m ;
			}
		}
		return null ;
    }

 
    //----------------------------------------------------------------------------------------------
    private static void invokeSetterMethod(Method method, Object oBean, Object oValue)
    {
    	Object[] args = new Object[1] ;
    	args[0] = oValue ;
        try
        {
            method.invoke(oBean, args);
        } catch (IllegalArgumentException e)
        {
            throw new RuntimeException("invokeSetterMethod : IllegalArgumentException ", e);
            //return null;
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException("invokeSetterMethod : IllegalAccessException ", e);
            //return null;
        } catch (InvocationTargetException e)
        {
            throw new RuntimeException("invokeSetterMethod : InvocationTargetException ", e);
            //return null;
        }
    }
    //----------------------------------------------------------------------------------------------
 
    //----------------------------------------------------------------------------------------------
    private static Object convertParam( Object oValue, Class<?> cType, String sFieldName )
    {
        String sTypeName = cType.getName();
        log("convertParam : type name = '" + sTypeName + "'");
        
//		if ( cType.isPrimitive() ) 
//		{
//	    	if ( oValue == null )
//	    	{
//		        //--- A primitive type cannot be null
//	    		throw new RuntimeException("Cannot set null value for primitive type '" + sTypeName + "'" ) ;
//	    	}
//	    	
//	        //--- The 8 Java primitive types :
//
//		    if ( "boolean".equals(sTypeName) ) 
//		    {
//		    	if ( oValue instanceof Boolean ) return oValue ; // Return the value as is
//		    	else throw new RuntimeException("Cannot set field '" + sFieldName + "' : boolean expected" ); 
//		    }
//
//		    if ( "char".equals(sTypeName) )
//		    {
//		    	if ( oValue instanceof Character ) return oValue ; // Return the value as is
//		    	else throw new RuntimeException("Cannot set field '" + sFieldName + "' : char expected" ); 
//		    }
//
//		    //--- Other cases : must be a number
//	    	if ( ! ( oValue instanceof Number ) ) 
//	    	{
//	    		throw new RuntimeException("Cannot set field '" + sFieldName + "' : number expected" ); 
//	    	}
//	    	Number number = (Number) oValue ;
//            if ( "int".equals(sTypeName) ) 
//        	{
//		    	return new Integer ( number.intValue() ) ;
//        	}
//            if ( "long".equals(sTypeName) ) 
//        	{
//		    	return new Long ( number.longValue() ) ;
//        	}
//            if ( "short".equals(sTypeName) ) 
//        	{
//		    	return new Short ( number.shortValue() ) ;
//        	}
//            if ( "byte".equals(sTypeName) ) 
//        	{
//		    	return new Byte ( number.byteValue() ) ;
//        	}
//            if ( "float".equals(sTypeName) )
//            {
//	    		return new Float ( number.floatValue() ) ;
//            }
//            if ( "double".equals(sTypeName) )
//            {
//	    		return new Double ( number.doubleValue() ) ;
//            }
//            throw new RuntimeException("Cannot set field '" + sFieldName + "' : unknown primitive type '" + sTypeName + "'" );
//		}
//		else
//		{
//	        System.out.println("convertParam : NOT primitive type ");
	        if ( oValue == null ) 
	        {
	        	if ( cType.isPrimitive() ) 
	        	{
			        //--- A primitive type cannot be null
		    		throw new RuntimeException("Cannot set null value : primitive type '" + sTypeName + "'" ) ;	        		
	        	}
	        	else
	        	{
		        	return null ; // The 'null' value can be set via the setter method
	        	}
	        }
	        
	        if ( oValue instanceof String )
	        {
		        if ( "java.lang.String".equals ( sTypeName ) )
		        {
			    	 return oValue ; // Return the value as is
		        }
		    	throw new RuntimeException("Cannot set string in field '" + sFieldName + "' " ); 	        	
	        }
	        
	        if ( oValue instanceof Boolean )
	        {
		        if ( "java.lang.Boolean".equals ( sTypeName ) || "boolean".equals(sTypeName))
		        {
			    	return oValue ; // Return the value as is
		        }
		    	throw new RuntimeException("Cannot set boolean in field '" + sFieldName + "' " ); 	        	
	        }
	        
	        if ( oValue instanceof java.util.Date )
	        {
	        	java.util.Date date = (java.util.Date) oValue ;
	        	
		        if ( "java.util.Date".equals ( sTypeName ) )
		        {
			    	return date ; // Return the value as is
		        }
		        if ( "java.sql.Date".equals ( sTypeName ) )
		        {
		    		return new java.sql.Date( date.getTime() );
		        }
		        if ( "java.sql.Time".equals ( sTypeName ) )
		        {
		    		return new java.sql.Time( date.getTime() );
		        }
		        if ( "java.sql.Timestamp".equals ( sTypeName ) )
		        {
			    	return new java.sql.Timestamp( date.getTime() );
		        }
		        throw new RuntimeException("Cannot set date in field '" + sFieldName + "' " ); 
	        }
	        
	        if ( oValue instanceof Number ) // for BigInteger and BigDecimal
	        {
	        	Number number = (Number) oValue ;
	            if ( "int".equals(sTypeName)   || "java.lang.Integer".equals(sTypeName)  ) 
	        	{
			    	return new Integer ( number.intValue() ) ;
	        	}
	            if ( "long".equals(sTypeName)  || "java.lang.Long".equals(sTypeName) ) 
	        	{
			    	return new Long ( number.longValue() ) ;
	        	}
	            if ( "short".equals(sTypeName) || "java.lang.Short".equals(sTypeName) ) 
	        	{
			    	return new Short ( number.shortValue() ) ;
	        	}
	            if ( "byte".equals(sTypeName)  || "java.lang.Byte".equals(sTypeName) ) 
	        	{
			    	return new Byte ( number.byteValue() ) ;
	        	}
	            if ( "float".equals(sTypeName) || "java.lang.Float".equals(sTypeName) )
	            {
		    		return new Float ( number.floatValue() ) ;
	            }
	            if ( "double".equals(sTypeName) || "java.lang.Double".equals(sTypeName) )
	            {
		    		return new Double ( number.doubleValue() ) ;
	            }
		        throw new RuntimeException("Cannot set number in field '" + sFieldName + "' " ); 
	        }
	        
	        if ( oValue instanceof LinkedList ) // for List/Collection
	        {
	        	LinkedList<?> list = (LinkedList<?>) oValue ;
	            if ( "java.util.List".equals(sTypeName) )
	            {
	            	return list ; // Return the list as is
	            }
	            if ( "java.util.Collection".equals(sTypeName) )
	            {
	            	return list ; // Return the list as is
	            }
	            if ( "java.util.LinkedList".equals(sTypeName) )
	            {
	            	return list ; // Return the list as is
	            }
	            
	            if ( cType.isArray() ) 
	            {
	            	throw new RuntimeException("Array type " + sTypeName + " not yet supported" );
//	            	String[] a = (String[]) list.toArray();
//	            	return list.toArray();
	            }
	        }
	        
	        //--- Other types ( can be any kind of Entity )
	        return oValue ; // Keep the type as is ...
//		}
    }

    //----------------------------------------------------------------------------------------------
 
    public static void setFieldValue(Object oBean, String sFieldName, Object oValue ) 
    {
        if (oBean == null)
        {
            throw new RuntimeException("setFieldValue : bean instance is null ");
        }
        if (sFieldName == null)
        {
            throw new RuntimeException("setFieldValue : field name is null ");
        }

        //--- Build the "method field name" (1rst char Upper Case)
        String sFieldName2 = sFieldName.trim();
        char cFirstChar = sFieldName2.charAt(0);
        if (Character.isLowerCase(cFirstChar))
        {
            String s1 = sFieldName2.substring(0, 1);
            String s2 = sFieldName2.substring(1);
            sFieldName2 = s1.toUpperCase() + s2;
        }
        
        
        Class<?> beanClass = oBean.getClass();
        String sSetterMethodName = "set" + sFieldName2 ;
        Method setterMethod = null;
        //--- Try to find a "getXxxx" getter method
        setterMethod = findMethodByName(beanClass, sSetterMethodName);
        if (setterMethod != null)
        {
			Class<?>[] paramTypes = setterMethod.getParameterTypes();
			if ( paramTypes.length == 1 )
			{
			    Object oNewValue = convertParam( oValue, paramTypes[0], sFieldName );
			    invokeSetterMethod(setterMethod, oBean, oNewValue);
			    return ;
//				if ( oValue != null )
//				{
//				    Object oNewValue = convertParam( oValue, paramTypes[0] );
//				    invokeSetterMethod(setterMethod, oBean, oNewValue);
//				    return ;
//				}
//				else
//				{
//				    invokeSetterMethod(setterMethod, oBean, null);
//				    return ;
//				}
			}
        }
        //--- Unknown field
        throw new RuntimeException("Unknown field '" + sFieldName 
        		+ "' : no "+sSetterMethodName+"(arg) in class = " + beanClass.getName() );
    }
 
    
    //----------------------------------------------------------------------------------------------
 
}
