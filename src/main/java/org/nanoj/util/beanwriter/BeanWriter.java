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
package org.nanoj.util.beanwriter;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Collection;

import org.nanoj.util.introspection.BeanGetter;
import org.nanoj.util.introspection.BeanIntrospector;

public class BeanWriter {

	private Writer  output = null ;
	private BeanTranslator writer = null ;
	
	
	/**
	 * Constructor
	 * @param outputWriter the output writer
	 * @param beanWriter the bean writer implementation to use ( XML, JSON, etc... )
	 */
	public BeanWriter(Writer outputWriter, BeanTranslator beanWriter) {
		super();
		this.output = outputWriter;
		this.writer = beanWriter;
	}

	/**
	 * Invokes the open method of the writer ( ie : with an XML writer it opens the root tag )
	 */
	public void open()
	{
		writer.open(output);
	}
	
	/**
	 * Invokes the close method of the writer ( ie : with an XML writer it closes the root tag )
	 * then flushes and closes the output flow.
	 */
	public void close()
	{
		writer.close(output); 
		try {
			output.flush();
			output.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException : cannot close output.", e);
		}

	}
	
	/**
	 * Writes the given object in the output flow ( in XML, JSON, etc... ) without name
	 * @param bean the bean to write
	 */
	public void writeObject(Object bean)
	{
		// Can be null
		//if ( null == bean ) throw new IllegalArgumentException("Bean is null");
		// Call with no parent 
		writeLevel( bean, null, null ); 
	}
	
	/**
	 * Writes the given object in the output flow ( in XML, JSON, etc... )
	 * @param bean the bean to write
	 * @param name the name of the bean ( used as tag name in XML, or object name in JSON )
	 */
	public void writeObject(Object bean, String name)
	{
		// Can be null
		//if ( null == bean ) throw new IllegalArgumentException("Bean is null");
		// Call with no parent 
		writeLevel( bean, name, null ); 
	}
	
	/**
	 * Converts the given string into a comment and writes it <br>
	 * ( the string is writen between XML comments ) 
	 * @param comment
	 */
	public void writeComment( String comment ) 
	{
		writer.writeComment(output, comment);
	}
	
	private void writeLevel(Object bean, String name, Object parent )
	{
		if ( bean != null ) {
			
			if ( isBasicType(bean)) {
				writer.writeValue(output, bean, name, parent); 
			}
			else if ( isArray(bean)) {
				
				Object[] array = getArrayOfObjects(bean) ;
				writer.openArray(output, array, name, parent); 
				for ( Object o : array ) {
					//if ( o != null ) {
						writeLevel(o, null, bean ); // write even if null
					//}
				}
				writer.closeArray(output, array, name, parent); 
			}
			else if ( isCollection(bean)) {
				Collection<?> collection = (Collection<?>) bean ;
				writer.openCollection(output, collection, name, parent); 
				for ( Object o : collection ) {
					//if ( o != null ) {
						writeLevel(o, null, bean ); // write even if null
					//}
				}
				writer.closeCollection(output, collection, name, parent); 
			}
			else {
				writer.openObject(output, bean, name);
				
				//--- Process all the basic elements, skip and count the sub-objects elements
				int subObjectsCount = 0 ;
				BeanGetter[] getters = BeanIntrospector.getGetters(bean);
				for ( BeanGetter getter : getters ) 
				{
					if ( getter.isSubElement() ) 
					{
						subObjectsCount++ ; // XML-ELEMENT
					}
					else {
						
						Class<?> returnType = getter.getReturnType();
						if ( isBasicType(returnType) ) 
						{
							//--- WRITE BASIC VALUE
							Object childObject = getter.invoke(bean);
							if ( childObject != null ) {
								writeLevel(childObject, getter.getPropertyName(), bean );
							}
							else {
								// Call the writer even for a null object (the writer decides what to do )
								writer.writeNullValue(output, getter.getPropertyName(), bean ); 							
							}
						}
						else 
						{
							subObjectsCount++ ;
						}
					}
				}
				
				//--- Process all the "sub-objects" elements if any
				if ( subObjectsCount > 0 ) {
					writer.openSubObjects(output, bean, name);
					for ( BeanGetter getter : getters ) 
					{
						Class<?> returnType = getter.getReturnType();
						if ( isBasicType(returnType) == false ) 
						{
							//--- SUB-OBJECT 
							Object childObject = getter.invoke(bean);
							if ( childObject != null ) {
								writeLevel(childObject, getter.getPropertyName(), bean );
							}
							else {
								// Call the writer even for a null object (the writer decides what to do )
								writer.writeNullValue(output, getter.getPropertyName(), bean ); 							
							}
						}
						else 
						{
							//--- BASIC VALUE / SUB-ELEMENT 
							if ( getter.isSubElement() ) 
							{
								Object value = getter.invoke(bean);
								writer.writeSubElementValue(output, value, getter.getPropertyName(), bean); 
							}
							//subObjectsCount++ ;
						}
					}
					writer.closeSubObjects(output, bean, name);
				}
				
				writer.closeObject(output, bean, name, subObjectsCount);
			}
		}
		else {
			// Call the writer even for a null object (the writer decides what to do )
			writer.writeNullValue(output, name, parent ); 
		}
	}

	private boolean isBasicType(Object bean) 
	{
        if (bean instanceof String) return true ;
        if (bean instanceof java.lang.Number) return true ; // Byte, Short, Interger, Long, Double, Float, BigDecimal, BigInterger
        if (bean instanceof java.lang.Boolean) return true ; 
        if (bean instanceof java.util.Date) return true ; // util.Date, sql.Date, sql.Time, sql.Timestamp
        if (bean instanceof java.lang.Character) return true ;
        return false ;
	}

	private boolean isCollection(Object bean) 
	{
		//return Collection.class.isAssignableFrom( bean.getClass() ) ;
		return bean instanceof Collection<?> ;
	}
	
//	private boolean isMap(Object bean) 
//	{
//		//return Map.class.isAssignableFrom( bean.getClass() ) ;
//		return bean instanceof Map<?,?> ;
//	}
	
	private boolean isArray(Object bean) 
	{
		return  bean.getClass().isArray() ;
	}
	
	/**
	 * Returns an array of objects (even for an array of primitives)
	 * @param array
	 * @return
	 */
	private Object[] getArrayOfObjects(Object array) 
	{
		Class<?> itemType = array.getClass().getComponentType() ;
		if ( itemType.isPrimitive() ) {
		    int arraylength = Array.getLength(array);
		    Object[] arrayOfObjects = new Object[arraylength];
		    for(int i = 0; i < arraylength; ++i){
		       arrayOfObjects[i] = Array.get(array, i); // primitive type to object (wrapped)
		    }
		    return arrayOfObjects;			
		}
		else {
			return (Object[]) array ;
		}
	}
	
	private boolean isBasicType(Class<?> clazz) 
	{
		//String s = clazz.getCanonicalName();
		if ( clazz.isPrimitive() ) return true ;
        if ( java.lang.String.class.isAssignableFrom(clazz) ) return true ;
        if ( java.lang.Number.class.isAssignableFrom(clazz) ) return true ; // Byte, Short, Interger, Long, Double, Float, BigDecimal, BigInterger
        if ( java.lang.Boolean.class.isAssignableFrom(clazz) ) return true ; 
        if ( java.util.Date.class.isAssignableFrom(clazz) ) return true ; // util.Date, sql.Date, sql.Time, sql.Timestamp
        return false ;
	}
	
}
