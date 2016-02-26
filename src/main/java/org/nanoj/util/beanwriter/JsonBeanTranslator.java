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

import org.nanoj.util.beanwriter.util.JsonUtil;

/**
 * JSON implementation for BeanWriter 
 * 
 * @author Laurent Guerin
 *
 */
public class JsonBeanTranslator implements BeanTranslator
{
    private final static String END_OF_LINE = "\n" ;

    private int[] levelCount = new int[1024] ;
    private int currentLevel = 0 ;
    
    private boolean formattedOutput = false ;
    
	/**
	 * Creates a new JSON translator<br>
	 * By default the output will not be formated 
	 */
	public JsonBeanTranslator() {
		super();
		this.formattedOutput = false ;
	}
	
	/**
	 * Creates a new JSON translator
	 * @param formattedOutput indicates if the output must be formated or not
	 */
	public JsonBeanTranslator(boolean formattedOutput) {
		super();
		this.formattedOutput = formattedOutput ;
	}

	public void open(Writer output) 
	{
		currentLevel = 0 ;
        write(output, "{ ");
//        write(output, END_OF_LINE);
	}
	
	public void close(Writer output) 
	{
        write(output, END_OF_LINE);
        write(output, "}");
		currentLevel = 0 ;
	}
	
	public void openSubObjects(Writer output, Object bean, String name) 
	{
		// Nothing to do in JSON syntax
		//openLevel(output, "{ (sub-object)", name);
	}
	public void closeSubObjects(Writer output, Object bean, String name) 
	{
		// Nothing to do in JSON syntax
		//closeLevel(output, "} (sub-object)");
	}

	public void openObject(Writer output, Object bean, String name) 
	{
		openLevel(output, "{", name);
	}

	public void closeObject(Writer output, Object bean, String name, int subObjectsCount) 
	{
		closeLevel(output, "}");
	}

	public void writeComment(Writer output, String comment) 
	{
    	writeNewLine(output);
    	write(output, "/* " + comment + " */" );
	}

	public void writeNullValue(Writer output, String name, Object parent) 
	{
        //write(output, name + "=null " ) ;
        writeBasicValue(output, name, "null" );
	}
	
	public void writeValue(Writer output, Object obj, String name, Object parent) 
	{
		// obj is never null 
		
//		String sValue = null ;
//        if (obj instanceof String)
//        {
//        	//sValue = "\"" + ( (String) obj ) + "\"";
//        	sValue = JsonUtil.jsonString( (String) obj ) ;
//		}
//        else if (obj instanceof java.lang.Number) // Byte, Short, Integer, Long, Double, Float, BigDecimal, BigInterger
//        { 
//        	sValue = ((java.lang.Number)obj).toString() ;
//		}
//        else if (obj instanceof java.lang.Boolean)
//        {
//        	sValue = ((java.lang.Boolean)obj).toString() ;
//		}
//        else if (obj instanceof java.sql.Date) // Before java.util.Date ( because subclass )
//        {
//            sValue = "\"" + ((java.sql.Date)obj).toString() + "\"" ;
//		}
//        else if (obj instanceof java.sql.Time) // Before java.util.Date ( because subclass )
//        {
//            sValue = "\"" + ((java.sql.Time)obj).toString() + "\"" ;
//		}
//        else if (obj instanceof java.sql.Timestamp) // Before java.util.Date ( because subclass )
//        {
//            sValue = "\"" + ((java.sql.Timestamp)obj).toString() + "\"" ;
//		}
//        else if (obj instanceof java.util.Date)
//        {
//            java.util.Date date = (java.util.Date)obj;
//            sValue = "\"" + DateUtil.dateTimeISO(date) + "\"" ;
//		}
//        else if (obj instanceof java.lang.Character)
//        {
//        	java.lang.Character c = (java.lang.Character) obj;
//        	sValue = "\"" + c.toString() + "\"" ;
//        	//sValue = ((java.lang.Character)obj).toString() ;
//		}
        
		String sValue = JsonUtil.jsonObjectValue(obj);
        writeBasicValue(output, name, sValue );
	}
	
	public void writeSubElementValue(Writer output, Object value, String name, Object parent)
	{
		String sValue = JsonUtil.jsonObjectValue(value);
        writeBasicValue(output, name, sValue );
	}

	private void writeBasicValue(Writer output, String name, String value )
    {
    	if ( isNotFirstValueInCurrentLevel() ) write(output, ", ");
        //write(output, END_OF_LINE);
        writeNewLine(output);
		if ( name != null ) {
            write(output, "\"" + name + "\" : " + value + " " ) ;
		}
		else {
            write(output, value + " " ) ;
		}
        incrementValueInCurrentLevel();
    }

//    private String getTagName( Object obj, String name )
//    {
//    	if ( name != null ) return name ;
//    	return obj.getClass().getSimpleName() ;
//    }
    
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
    
    private void write(Writer writer, String s )
    {
        try {
	        writer.write(s);
	    } catch (IOException ex)
	    {
	        throw new RuntimeException("IOException", ex);
	    }        
    }
    
	public void openArray(Writer output, Object[] array, String name, Object parent) 
	{
		openLevel(output, "[", name);
	}
	
	public void closeArray(Writer output, Object[] array, String name, Object parent) 
	{
		closeLevel(output, "]");
	}
	
	public void openCollection(Writer output, Collection<?> collection, String name, Object parent) 
	{
		openLevel(output, "[", name);
	}
	
	public void closeCollection(Writer output, Collection<?> collection, String name, Object parent) 
	{
		closeLevel(output, "]");
	}
	
	//----------------------------------------------------------------------------------------------

	private void openLevel(Writer output, String sOpeningCharacter, String sPropertyName) 
	{
    	if ( isNotFirstValueInCurrentLevel() ) write(output, ", ");
        //write(output, END_OF_LINE);
        writeNewLine(output);
        if ( sPropertyName != null ) {
        	write(output, "\"" + sPropertyName + "\" : " ) ;
        }
        write(output, sOpeningCharacter); // Open the level : ie "{" or "["
		currentLevel++;
		levelCount[currentLevel] = 0 ;
	}
	private void closeLevel(Writer output, String s) 
	{
		currentLevel--;
        writeNewLine(output);		
        write(output, s); // Close the level : ie "}" or "]"
        incrementValueInCurrentLevel();
	}
	
	//----------------------------------------------------------------------------------------------
	private boolean isNotFirstValueInCurrentLevel() 
	{
		return levelCount[currentLevel] > 0 ;
	}
	private void incrementValueInCurrentLevel() 
	{
		levelCount[currentLevel]++ ;
	}
	
}
