package org.nanoj.util.beanwriter;

import java.io.Writer;
import java.util.Collection;

/**
 * @author laguerin
 *
 */
public interface BeanTranslator 
{

	void open(Writer writer);
	
	void close(Writer writer);


	/**
	 * Write a basic value 
	 * @param writer
	 * @param value the value to write ( String, Number, Boolean, etc )
	 * @param name  the name of the value
	 * @param parent the parent object 
	 */
	void writeValue(Writer writer, Object value, String name, Object parent );

	/**
	 * Write a null value 
	 * @param writer 
	 * @param name the name of the value
	 * @param parent the parent object 
	 */
	void writeNullValue(Writer writer, String name, Object parent );

	/**
	 * Write a basic value as a "sub-element"
	 * @param writer
	 * @param value the value to write ( String, Number, Boolean, etc )
	 * @param name the name of the value
	 * @param parent the parent object 
	 */
	void writeSubElementValue(Writer writer, Object value, String name, Object parent); 	
	
	/**
	 * Write a comment 
	 * @param writer
	 * @param comment
	 */
	void writeComment(Writer writer, String comment );
	

	void openObject(Writer writer, Object bean, String name);
	
	void closeObject(Writer writer, Object bean, String name, int subObjectsCount);


	void openSubObjects(Writer writer, Object bean, String name);

	void closeSubObjects(Writer writer, Object bean, String name);

	
	void openArray(Writer writer, Object[] array, String name, Object parent);
	
	void closeArray(Writer writer, Object[] array, String name, Object parent);

	
	void openCollection(Writer writer, Collection<?> array, String name, Object parent);
	
	void closeCollection(Writer writer, Collection<?> array, String name, Object parent);

}
