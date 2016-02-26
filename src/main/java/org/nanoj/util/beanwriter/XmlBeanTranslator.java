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
import java.util.Collection;

import org.nanoj.util.DateUtil;
import org.nanoj.util.beanwriter.util.XmlUtil;

/**
 * XML implementation for BeanWriter 
 * 
 * @author Laurent Guerin
 *
 */
public class XmlBeanTranslator implements BeanTranslator
{
    private final static String END_OF_LINE = "\n" ;
    private final static String ROOT_TAG = "root" ;
    private final static String NULL_TAG = "null" ;
    
    private String rootTag = ROOT_TAG ;
    private String nullTag = NULL_TAG ;
    
    private int currentLevel = 0 ;
    
    private boolean formattedOutput = false ;

    private boolean writeNullAttribute = true ;
    
	/**
	 * Creates a new XML translator<br>
	 * By default the output will not be formated 
	 */
	public XmlBeanTranslator() {
		super();
		this.formattedOutput = false ;
		this.rootTag = ROOT_TAG ;
	}

	/**
	 * Creates a new XML translator
	 * @param formattedOutput indicates if the output must be formated or not
	 */
	public XmlBeanTranslator(boolean formattedOutput) {
		super();
		this.formattedOutput = formattedOutput ;
		this.rootTag = ROOT_TAG ;
	}

	/**
	 * Creates a new XML translator
	 * @param formattedOutput indicates if the output must be formated or not
	 * @param rootTag the name to be used as the XML root tag
	 */
	public XmlBeanTranslator(boolean formattedOutput, String rootTag) {
		super();
		this.formattedOutput = formattedOutput ;
		this.rootTag = rootTag ;
	}

	/**
	 * Set the name of the tag used for null objects ( the default is "null" and the output is "<null/>" <br>
	 * If the tag name is set to null then no null tag will be written 
	 * @param nullTag the name of the null tag ( ie "NullObject" for <NullObject/>, or null for none )
	 */
	public void setNullTag(String nullTag ) {
		this.nullTag = nullTag ;
	}
	
	/**
	 * Define if this writer must generate void attribute values for null values or not<br>
	 * If true : an attribute like  firstName="" will be generated if the firstName property is null <br>
	 * If false : no attribute will be generated if the property is null <br>
	 * The default value is true.
	 * @param flag 
	 */
	public void setWriteNullAttribute(boolean flag ) {
		this.writeNullAttribute = flag ;
	}
	
	
	public void open(Writer output) 
	{
		currentLevel = 0 ;
		write(output, "<" + rootTag + ">" );
	}
	
	public void close(Writer output) 
	{
		currentLevel = 0 ;
		write(output, END_OF_LINE);
		write(output, "</" + rootTag + ">" );
		write(output, END_OF_LINE);
	}
	
	public void openSubObjects(Writer output, Object bean, String name) 
	{
		write(output, " >" );
//        write(output, END_OF_LINE);
	}
	
	public void closeSubObjects(Writer output, Object bean, String name) 
	{
//		String sTagName = getTagName( bean, name );
//		write(output, "</" + sTagName + ">" );
//        write(output, END_OF_LINE);
	}

	public void openObject(Writer output, Object bean, String name) 
	{
		writeNewLine(output);
		String sTagName = getTagName( bean, name );		
		write(output, "<" + sTagName + " " );
		currentLevel++;
	}
	public void closeObject(Writer output, Object bean, String name, int subObjectsCount) 
	{
		currentLevel--;
		if ( subObjectsCount > 0 ) {
			writeNewLine(output);			
			String sTagName = getTagName( bean, name );
			write(output, "</" + sTagName + ">" );
		} 
		else {
			write(output, "/>" );
			// Stay at the same level 
		}
	}

	public void writeComment(Writer output, String comment) 
	{
    	writeNewLine(output);
    	write(output, "<!-- " + comment + " -->" );
	}
	
	public void writeNullValue(Writer output, String name, Object parent) 
	{
        if ( parent != null ) {
        	// Has a parent 
        	if ( isArrayOrCollection(parent) ) {
        		// The parent is a collection or an array => write a void "null" tag 
            	writeNullTag(output);
        	}
        	else {
        		// The parent is not a collection or an array => write a void attribute value
        		if ( this.writeNullAttribute ) {
                    write(output, name + "=\"\" " ) ;
        		}
        	}
        }
        else {
        	// No parent ( first level ) 
        	writeNullTag(output);
        }
	}
	
	private void writeNullTag(Writer output) 
	{
		if ( this.nullTag != null ) {
	    	writeNewLine(output);
	    	write(output, "<" + this.nullTag + "/>" );
		}
	}
	
	public void writeValue(Writer output, Object obj, String name, Object parent) 
	{
		//--- obj is never null here
		String sTagName = getTagName( obj, name );
		String sValue = null ;
//		if ( null == obj && ( ! isArrayOrCollection(parent) ) ) {
//	        if ( parent != null ) {
//	            write(output, name + "=\"\" " ) ;
//	        	return ;
//	        }
//	        else {
//	        	writeNewLine(output);
//	        	write(output, "<Null/>" );
//	        	return ;
//	        }
//		}
//		else {
			
	        if (obj instanceof String)
	        {
	        	sValue = XmlUtil.xmlString( (String) obj ) ;
			}
	        else if (obj instanceof java.lang.Number) // Byte, Short, Interger, Long, Double, Float, BigDecimal, BigInterger
	        { 
	        	sValue = ((java.lang.Number)obj).toString() ;
			}
	        else if (obj instanceof java.lang.Boolean)
	        {
	        	sValue = ((java.lang.Boolean)obj).toString() ;
			}
	        else if (obj instanceof java.sql.Date) // Before java.util.Date ( because subclass )
	        {
	            sValue = ((java.sql.Date)obj).toString();
			}
	        else if (obj instanceof java.sql.Time) // Before java.util.Date ( because subclass )
	        {
	            sValue = ((java.sql.Time)obj).toString();
			}
	        else if (obj instanceof java.sql.Timestamp) // Before java.util.Date ( because subclass )
	        {
	            sValue = ((java.sql.Timestamp)obj).toString();
			}
	        else if (obj instanceof java.util.Date)
	        {
	            java.util.Date date = (java.util.Date)obj;
	            sValue = DateUtil.dateTimeISO(date);
	            //sOther = " date=\"" + DateUtil.dateISO(date) + "\" time=\"" + DateUtil.timeISO(date) + "\" ";
			}
	        else if (obj instanceof java.lang.Character)
	        {
	        	java.lang.Character c = (java.lang.Character) obj;
	        	sValue = c.toString();
			}
	        
	        if ( parent != null && ( ! isArrayOrCollection(parent) ) ) {
	            write(output, name + "=\"" + sValue + "\" " ) ;
	        }
	        else {
	        	// No parent object => first level value 
	        	writeNewLine(output);
	            write(output, "<" + sTagName + " value=\"" + sValue + "\" />" );
	        }
//		}
	}

	/* (non-Javadoc)
	 * @see org.objectweb.telosys.util.beanwriter.BeanTranslator#writeSubElementValue(java.io.Writer, java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void writeSubElementValue(Writer output, Object value, String name, Object parent)
	{
		writeNewLine(output);		
		if ( value != null ) {
	        write(output, "<" + name + "><![CDATA[" ) ;
	        write(output, value.toString() ) ;
	        write(output, "]]></" + name + ">" ) ;
		}
		else {
	        write(output, "<" + name + "/>" ) ;
		}
	}

    private String getTagName( Object obj, String name )
    {
    	if ( name != null ) return name ;
    	if ( obj != null ) {
        	return obj.getClass().getSimpleName() ;
    	} 
    	else {
    		return "null" ;
    	}
    }
    
    private void write(Writer writer, String s )
    {
        try {
	        writer.write(s);
	    } catch (IOException ex)
	    {
	        throw new RuntimeException("IOException", ex);
	    }        
    }
    
	private boolean isArrayOrCollection(Object o) 
	{
		if ( null == o ) return false ;
		if ( o.getClass().isArray() ) return true ;
		if ( o instanceof Collection<?> ) return true ;
		return false ;
	}

	public void openArray(Writer output, Object[] array, String name, Object parent) 
	{
		writeNewLine(output);
		//write(output, "<!-- OPEN ARRAY  -->" );
		write(output, "<" + ( name != null ? name : "array" ) + ">" );
		currentLevel++;
	}
	
	public void closeArray(Writer output, Object[] array, String name, Object parent) 
	{
		currentLevel--;
		writeNewLine(output);
		write(output, "</" + ( name != null ? name : "array" ) + ">" );
		//write(output, "<!-- CLOSE ARRAY  -->" );
	}
	
	public void openCollection(Writer output, Collection<?> collection, String name, Object parent) 
	{
		writeNewLine(output);
		//write(output, "<!-- OPEN ARRAY  -->" );
		write(output, "<" + ( name != null ? name : "collection" ) + ">" );
		currentLevel++;
	}
	
	public void closeCollection(Writer output, Collection<?> collection, String name, Object parent) 
	{
		currentLevel--;
		writeNewLine(output);
		write(output, "</" + ( name != null ? name : "collection" ) + ">" );
		//write(output, "<!-- CLOSE ARRAY  -->" );
	}
	
    private void writeNewLine(Writer output)
    {
    	if ( formattedOutput ) {
            write(output, END_OF_LINE);
            //write(output, "("+currentLevel+")");
            for ( int i = 0 ; i <= currentLevel ; i++ ) {
                write(output, "  " );
            }
    	}
    }
	
}
