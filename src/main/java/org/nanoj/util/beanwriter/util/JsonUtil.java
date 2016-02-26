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
package org.nanoj.util.beanwriter.util;

import java.util.Date;

import org.nanoj.util.DateUtil;

/**
 * 
 * Utility class for JSON operations ( set of static methods )
 * 
 * @author Laurent GUERIN
 *
 */
public final class JsonUtil
{
    // private static final TelosysClassLogger $log = new TelosysClassLogger(JsonUtil.class);

    private static final String  NULL = "null";
    
    //----------------------------------------------------------------------------
    /** 
     * Private constrictor 
     */
    private JsonUtil()
    {
    }
    
    //----------------------------------------------------------------------------
    /**
     * Convert "Standard Java String" to "JSON String value" <br>
     * A String value in JSON is just the string itself between quotes, <br>
     * with special characters protection ( \x ) 
     * 
     * @param sStdString :
     * @return the JSON value
     */ 
    public static String jsonString(final String sStdString)
    {
        if (sStdString == null)
        {
            return NULL ;
        }
        
        char c;
        StringBuffer sb = new StringBuffer(sStdString.length() + 40);
        sb.append('\"');
        for (int i = 0; i < sStdString.length(); i++)
        {
            c = sStdString.charAt(i);
            switch (c)
            {
                case '\"':
                    sb.append("\\\"");
                    break;
//                case '\\':
//                    sb.append("\\");
//                    break;
                case '/':
                    sb.append("\\/");
                    break;
//                case '\'':
//                    sb.append("\\\'");
//                    break; 
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    //----------------------------------------------------------------------------
    /**
     * There's no predefined format for Date objects in JSON <br>
     * Here, just use a string in ISO format
     * @param date
     * @return the JSON value
     */
    public static String jsonDate(final Date date)
    {
        if (date == null)
        {
            return NULL;
        }
        return "\"" + DateUtil.dateISO(date) + "\"";
    }

    //----------------------------------------------------------------------------------------------
    // BOOLEANS : return ( without quotes ) or "null" if null
    //----------------------------------------------------------------------------------------------
    /**
     * Returns JSON literal value the given boolean value
     * @param b
     * @return "true" or "false" ( without quotes )
     */
    public static String jsonBool(final boolean b)
    {
        if ( b )
        {
            return "true";
        }
        else
        {
            return "false";            
        }
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Returns JSON literal value the given boolean object
     * @param v
     * @return "true" or "false" ( without quotes ) or "null" if null
     */
    public static String jsonBool( Boolean v )
    {
        return ( v != null ? v.toString() : NULL );
    }
    
    //----------------------------------------------------------------------------------------------
    // NUMBERS : return value as is ( without quotes ) or "null" if null
    //----------------------------------------------------------------------------------------------
    /**
     * Returns JSON literal value for 'Number' instances (Byte, Short, Integer, Long, Float, Double, BigInteger, BigDecimal)
     * @param v
     * @return the value as is ( without quotes ) or "null" if null
     */
    public static String jsonNumber( Number v)
    {
        return ( v != null ? v.toString() : NULL ); 
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * Returns the JSON value of the given object <br>
     * The supported objects are : String, Number, Boolean, Date (util/sql), Time, Timestamp, Character, StringBuffer
     * @param obj
     * @return the JSON value ( ie : "abc", true, 123, "2012-01-24", null, etc... )
     * @throws RuntimeException if the given object is an instance of an unsupported class
     */
    public static String jsonObjectValue( Object obj )
    {
    	if ( null == obj ) {
    		return NULL ;
    	}
    	
        if (obj instanceof String)
        {
        	return jsonString( (String) obj ) ;
		}
        else if (obj instanceof java.lang.Number) // Byte, Short, Integer, Long, Double, Float, BigDecimal, BigInterger
        { 
        	return ((java.lang.Number)obj).toString() ;
		}
        else if (obj instanceof java.lang.Boolean)
        {
        	return ((java.lang.Boolean)obj).toString() ;
		}
        else if (obj instanceof java.sql.Date) // Before java.util.Date ( because subclass )
        {
        	return "\"" + ((java.sql.Date)obj).toString() + "\"" ;
		}
        else if (obj instanceof java.sql.Time) // Before java.util.Date ( because subclass )
        {
        	return "\"" + ((java.sql.Time)obj).toString() + "\"" ;
		}
        else if (obj instanceof java.sql.Timestamp) // Before java.util.Date ( because subclass )
        {
        	return "\"" + ((java.sql.Timestamp)obj).toString() + "\"" ;
		}
        else if (obj instanceof java.util.Date)
        {
            java.util.Date date = (java.util.Date)obj;
            return "\"" + DateUtil.dateTimeISO(date) + "\"" ;
		}
        else if (obj instanceof java.lang.Character)
        {
        	java.lang.Character c = (java.lang.Character) obj;
        	return "\"" + c.toString() + "\"" ;
        	//sValue = ((java.lang.Character)obj).toString() ;
		}
        else if (obj instanceof StringBuffer )
        {
        	return jsonString( ((StringBuffer) obj).toString() ) ;
		}
        throw new RuntimeException("Cannot convert '" + obj.getClass().getName() + "' to JSON value");
    }
    //----------------------------------------------------------------------------------------------
}