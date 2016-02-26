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
package org.nanoj.util.beanmapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

import org.nanoj.util.introspection.BeanGetter;
import org.nanoj.util.introspection.BeanIntrospector;
import org.nanoj.util.introspection.BeanSetter;

public class BeanMapper {
	
//	private final static String ERROR_INVOKE_SETTER = "Cannot invoke setter " ;
//
//	private final static String ERROR_INVOKE_GETTER = "Cannot invoke getter " ;

	private TypeConvertor typeConvertor = TypeConvertor.getInstance();
	
	private PropertyErrorCollector errorCollector = null ;
	
	private void log( String msg )
	{
//		System.out.println(msg);
//		System.out.flush();
	}

	public BeanMapper() {
		super();
		this.errorCollector = new PropertyErrorCollector() ;
	}
	
	public BeanMapper(PropertyErrorCollector errorCollector) {
		super();
		this.errorCollector = errorCollector;
	}

	public PropertyErrorCollector getErrors()
	{
		return this.errorCollector ;
	}
	
	/**
	 * Returns the typed value instance after conversion from the given string value
	 * @param expectedType
	 * @param stringValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getTypedValue( Class<T> expectedType, String stringValue )
	{
		return (T) convertStringToObject( expectedType, stringValue );		
	}
	
	/**
	 * Converts the given string value to an instance of the given class
	 * @param expectedType
	 * @param stringValue
	 * @return
	 */
	private Object convertStringToObject( Class<?> expectedType, String stringValue )
	{
		String typeName =  expectedType.getSimpleName() ;
		String longTypeName =  expectedType.getCanonicalName();
		
		log("convertValue(" + typeName +", '" + stringValue + "')");
		
		if ( "String".equals(typeName) ) {
			return stringValue;
		}
		
		if ( "int".equals(typeName) || "Integer".equals(typeName) ) {
			return typeConvertor.toIntegerObject(stringValue);
		}
		if ( "double".equals(typeName) || "Double".equals(typeName) ) {
			return typeConvertor.toDoubleObject(stringValue);
		}
		if ( "float".equals(typeName) || "Float".equals(typeName) ) {
			return typeConvertor.toFloatObject(stringValue);
		}
		if ( "long".equals(typeName) || "Long".equals(typeName) ) {
			return typeConvertor.toLongObject(stringValue);
		}
		if ( "short".equals(typeName) || "Short".equals(typeName) ) {
			return typeConvertor.toShortObject(stringValue);
		}
		if ( "byte".equals(typeName) || "Byte".equals(typeName) ) {
			return typeConvertor.toByteObject(stringValue);
		}
		
		if ( "boolean".equals(typeName) || "Boolean".equals(typeName) ) {
			return typeConvertor.toBooleanObject(stringValue);
		}
		
		if ( "BigDecimal".equals(typeName) ) {
			return typeConvertor.toBigDecimalObject(stringValue);
		}
		if ( "BigInteger".equals(typeName) ) {
			return typeConvertor.toBigIntegerObject(stringValue);
		}
		
		if ( "java.util.Date".equals(longTypeName) ) {
			return typeConvertor.toUtilDateObject(stringValue);
		}
		if ( "java.sql.Date".equals(longTypeName) ) {
			return typeConvertor.toSqlDateObject(stringValue);
		}
		if ( "java.sql.Time".equals(longTypeName) ) {
			return typeConvertor.toSqlTimeObject(stringValue);
		}
		if ( "java.sql.Timestamp".equals(longTypeName) ) {
			return typeConvertor.toSqlTimestampObject(stringValue);
		}
		//--- Unknown type !
		throw new RuntimeException("Unknown type '" + longTypeName + "'", null);
	}

	/**
	 * Converts the given object value to a String
	 * @param value
	 * @return
	 */
	private String convertObjectToString( Object value )
	{
		String typeName =  value.getClass().getSimpleName() ;
		String longTypeName =  value.getClass().getCanonicalName();
		
		log("convertValue(" + value + ")  type = " + typeName);
		
		if ( value instanceof String ) {
			return (String) value ;
		}
		if ( value instanceof Integer ) {
			return typeConvertor.toString( (Integer)value );
		}
		if ( value instanceof Double ) {
			return typeConvertor.toString( (Double)value );
		}
		if ( value instanceof Float ) {
			return typeConvertor.toString( (Float)value );
		}
		if ( value instanceof Long ) {
			return typeConvertor.toString( (Long)value );
		}
		if ( value instanceof Short ) {
			return typeConvertor.toString( (Short)value );
		}
		if ( value instanceof Byte ) {
			return typeConvertor.toString( (Byte)value );
		}
		if ( value instanceof Boolean ) {
			return typeConvertor.toString( (Boolean)value );
		}
		if ( value instanceof BigDecimal ) {
			return typeConvertor.toString( (BigDecimal)value );
		}
		if ( value instanceof BigInteger ) {
			return typeConvertor.toString( (BigInteger)value );
		}
		if ( value instanceof java.util.Date ) {
			return typeConvertor.toString( (java.util.Date)value );
		}
		if ( value instanceof java.sql.Date ) {
			return typeConvertor.toString( (java.sql.Date)value );
		}
		if ( value instanceof java.sql.Time ) {
			return typeConvertor.toString( (java.sql.Time)value );
		}
		if ( value instanceof java.sql.Timestamp ) {
			return typeConvertor.toString( (java.sql.Timestamp)value );
		}
		
		//--- Unknown type !
		throw new RuntimeException("Unknown type '" + longTypeName + "'", null);
	}
	
//	private void invokeSetter( Method m, Object bean, Object value )
//	{
//		String sMethodName = m.getName() ;
//
//		log("--> invokeSetter : " + sMethodName + "(" + value + ")");
//
//		Object params[] = new Object[1];
//		params[0] = value ;
//		
//		try {
//			m.invoke(bean, params);
//		} catch (IllegalArgumentException e) {
//			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(ERROR_INVOKE_SETTER + sMethodName, e);
//		}
//	}
	
//	private Object invokeGetter( Method m, Object bean )
//	{
//		String sMethodName = m.getName() ;
//
//		log("--> invokeGetter : " + sMethodName + "()");
//
//		Object retValue = null ;
//		try {
//			retValue = m.invoke(bean, (Object []) null);
//		} catch (IllegalArgumentException e) {
//			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(ERROR_INVOKE_GETTER + sMethodName, e);
//		}
//		return retValue ;
//	}
	
	/**
	 * Set each map entry to the corresponding bean property, if it exists.
	 * @param map
	 * @param bean
	 */
	public final void mapToBean(Map<String,String> map, Object bean )
	{
		if ( null == map ) throw new IllegalArgumentException("map is null");
		mapToBean(new BeanProperties(map), bean);
	}
	
	/**
	 * Set each properties entry to the corresponding bean property, if it exists.
	 * @param properties
	 * @param bean
	 */
	public final void mapToBean(Properties properties, Object bean )
	{
		if ( null == properties ) throw new IllegalArgumentException("properties is null");
		mapToBean(new BeanProperties(properties), bean );
	}
	
	private final void mapToBean(BeanProperties beanProperties, Object bean )
	{
		if ( null == bean ) throw new IllegalArgumentException("bean is null");
		
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
//        
//        Method m = null;
//        if ( methods != null )
//        {
//            for ( int i = 0 ; i < methods.length ; i++ )
//            {
//                m = methods[i] ;
//                String sMethodName = m.getName();
//                Class<?> paramTypes[] =  m.getParameterTypes() ;
//                if ( sMethodName.length() > 3 && sMethodName.startsWith("set") && paramTypes.length == 1 )
//                {
//                	//--- This is setter : get the property name
//                	String propertyName = "";
//                	char c = sMethodName.charAt(3);
//                	if ( c >= 'A' && c <= 'Z' ) {
//                		c = Character.toLowerCase(c) ; 
//                		propertyName = c + sMethodName.substring(4);
//                	}
//                	log("SETTER : method = '" + sMethodName + "' property = '" + propertyName + "'");
//                	
//                	Class<?> setterParamType = paramTypes[0];
//                	String propertyValue = beanProperties.get(propertyName);
//                	
//                	try {
//						setPropertyInBean(bean, m, propertyValue, setterParamType  );
//					} catch (Throwable e) {
//						log("ERROR : " + e.getMessage() );
//						errorCollector.addError(propertyName, PropertyErrorCollector.NULL_VALUE_FOR_PRIMITIVE_TYPE);
//						//throw new RuntimeException("Cannot set property in bean", e);
//					}
//                	
////                	if ( propertyValue != null ) {
////                		Class<?> valueType = propertyValue.getClass(); 
////                		if ( paramType.isAssignableFrom(valueType) ) {
////                			invokeSetter( m, bean, propertyValue );
////                		} 
////                		else {
////                			log("Not assignable : param type = " + paramType.getSimpleName() );
////                			
////                			Object convertedValue = convertValue( paramType, propertyValue );
////                			if ( paramType.isPrimitive() && ( null == convertedValue ) )
////                			{
////                				throw new RuntimeException 
////                			}
////                			else
////                			{
////                    			invokeSetter( m, bean, convertedValue );
////                			}
////
////                		}
////                	}
//                }
//                
//            }
//        }
        
        BeanSetter[] setters = BeanIntrospector.getSetters(bean);
        for ( BeanSetter setter : setters ) 
        {
        	//--- Try to get the property value as a String
        	String propertyName  = setter.getPropertyName() ;
        	String propertyValue = beanProperties.get(propertyName);
        	if ( propertyValue != null ) 
        	{
        		Class<?> setterParamType = setter.getPropertyType();
        		if ( setterParamType.isAssignableFrom(String.class) ) 
        		{
        			//--- The String type can be set directly ( no conversion needed )
        			//log("Assignable directly : param type = " + setterParamType.getSimpleName() );
        			//invokeSetter( m, bean, propertyValue );
    	        	//--- Put the property value in the bean
    	        	setter.invoke(bean, propertyValue);
        		} 
        		else 
        		{
        			//--- Conversion needed => convert the property value 
        			//log("Not assignable : param type = " + setterParamType.getSimpleName() );
        			
        			Object convertedValue = convertStringToObject( setterParamType, propertyValue );
        			if ( setterParamType.isPrimitive() && ( null == convertedValue ) )
        			{
        				//throw new RuntimeException("Cannot set null value for primitive type " + setterParamType.getSimpleName() );
        				errorCollector.addError(propertyName, PropertyErrorCollector.NULL_VALUE_FOR_PRIMITIVE_TYPE);        				
        			}
        			else
        			{
        	        	//--- Put the property value in the bean
            			//invokeSetter( m, bean, convertedValue );
        	        	setter.invoke(bean, convertedValue);
        			}
        		}
        	}
        }
	}
	
//	private void setPropertyInBean(Object bean, Method m, String propertyValue, Class<?> setterParamType  )
//	{
//		log("-----> setPropertyInBean(" + propertyValue + ")" );
//    	if ( propertyValue != null ) 
//    	{
//    		Class<?> valueType = propertyValue.getClass(); 
//    		if ( setterParamType.isAssignableFrom(valueType) ) { // for String type
//    			log("Assignable directly : param type = " + setterParamType.getSimpleName() );
//    			invokeSetter( m, bean, propertyValue );
//    		} 
//    		else {
//    			log("Not assignable : param type = " + setterParamType.getSimpleName() );
//    			
//    			Object convertedValue = convertValue( setterParamType, propertyValue );
//    			if ( setterParamType.isPrimitive() && ( null == convertedValue ) )
//    			{
//    				throw new RuntimeException("Cannot set null value for primitive type " + setterParamType.getSimpleName() );
//    			}
//    			else
//    			{
//        			invokeSetter( m, bean, convertedValue );
//    			}
//    		}
//    	}
//// Not in map => not set in bean
////    	else
////    	{
////    		//  If not primitive type => set to null 
////    		if ( ! setterParamType.isPrimitive() ) {
////    			invokeSetter( m, bean, null );
////    		}
////    	}
//	}
	public final void beanToMap(Object bean, Properties properties )
	{
		// Properties extends Hashtable<Object,Object> and implements Map<Object,Object>
		// properties.put(Object, Object)
		//beanToGenericMap(bean, properties );
		
		if ( null == properties  ) throw new IllegalArgumentException("properties is null");
		BeanProperties beanProperties = new BeanProperties(properties);
		beanToMap(bean, beanProperties );
	}
	
	public final void beanToMap(Object bean, Map<String,String> map )
	{
//		@SuppressWarnings(("unchecked"))
//		Map<Object,Object> genericMap = (Map<Object,Object>) map ;
//		beanToGenericMap(bean, genericMap );
		if ( null == map  ) throw new  IllegalArgumentException("map is null");
		BeanProperties beanProperties = new BeanProperties(map);
		beanToMap(bean, beanProperties );
	}
	
	//public final void beanToGenericMap(Object bean, Map<Object,Object> map )
	private final void beanToMap(Object bean, BeanProperties beanProperties )
	{
		if ( null == bean ) throw new IllegalArgumentException("bean is null");
		
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
//        
//        Method m = null;
//        if ( methods != null )
//        {
//            for ( int i = 0 ; i < methods.length ; i++ )
//            {
//                m = methods[i] ;
//                String sMethodName = m.getName();
//                Class<?> paramTypes[] =  m.getParameterTypes() ;
//                if ( sMethodName.length() > 3 && sMethodName.startsWith("get") 
//                		&& paramTypes.length == 0 && ( ! "getClass".equals(sMethodName) ) )
//                {
//                	//--- This is getter : get the property name
//                	String propertyName = "";
//                	char c = sMethodName.charAt(3);
//                	if ( c >= 'A' && c <= 'Z' ) {
//                		c = Character.toLowerCase(c) ; 
//                		propertyName = c + sMethodName.substring(4);
//                	}
//                	log("GETTER : method = '" + sMethodName + "' property = '" + propertyName + "'");
//                	
//                	//--- Call the getter 
//                	Object ret = invokeGetter(m, bean);
//
//                	//--- Put the result in the map 
//                	if ( ret != null ) {
//                		String sValue = convertToString(ret);
//                		beanProperties.set(propertyName, sValue );
//                	}
//                	else {
//                		beanProperties.set(propertyName, ""); // void for null 
//                	}
//                }
//            }
//        }
        
        BeanGetter[] getters = BeanIntrospector.getGetters(bean);
        for ( BeanGetter getter : getters ) {
        	Object ret = getter.invoke(bean);
        	String propertyName = getter.getPropertyName() ;
        	//--- Put the result in the map 
        	if ( ret != null ) {
        		String sValue = convertObjectToString(ret);
        		beanProperties.set(propertyName, sValue );
        	}
        	else {
        		beanProperties.set(propertyName, ""); // void for null 
        	}
        }
	}

}
