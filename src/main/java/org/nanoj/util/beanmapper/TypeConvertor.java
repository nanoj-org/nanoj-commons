package org.nanoj.util.beanmapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Types conversion utility class <br>
 * . String to specific type <br>
 * . Specific type to String <br>
 * 
 * @author Laurent GUERIN
 *  
 */
/* package */ class TypeConvertor
{
	private final static TypeConvertor instance = new TypeConvertor();
	
	public final static TypeConvertor getInstance() {
		return instance ;
	}
	
    //-----------------------------------------------------------------------------------------------
    // CONST
    //-----------------------------------------------------------------------------------------------
    public final static int DATE_ONLY = 1 ;
    public final static int TIME_ONLY = 2 ;
    public final static int DATE_AND_TIME = 3 ;
    
    private final static String DATE_ISO_FORMAT      = "yyyy-MM-dd" ;
    private final static String DATE_TIME_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
    private final static String TIME_ISO_FORMAT      = "HH:mm:ss" ;
    
    
    private final static String ERROR_VALUE_IS_NULL = "Value argument is null" ;
    private final static String ERROR_VALUE_IS_INVALID = "Value argument is invalid" ;
    
    //-----------------------------------------------------------------------------------------------
    // METHODS
    //-----------------------------------------------------------------------------------------------
    /**
     * Returns true if the given value is null or void or blank
     * @param s
     * @return
     */
    private boolean isNullOrVoidOrBlank(String s)
    {
        if ( s == null ) return true ;
        if ( s.trim().length() == 0 ) return true ;
        return false ;
    }
    
    private void checkParamNotNull(Object o)
    {
        if ( null == o ) throw new IllegalArgumentException( ERROR_VALUE_IS_NULL ) ;
    }
        
    //-----------------------------------------------------------------------------------
    /**
     * Throws a RuntimeException with the given message
     * 
     * @param sMessage
     *            the error message
     */
    private void throwException(String sMessage)
    {
        throw new RuntimeException("Type conversion error : " + sMessage);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Return a boolean value from the given string attribute value
     * 
     * @param sValue      the attribute value
     * @return true if the given value is "1" or "true" (ignore case), else false
     */
    public boolean toBooleanPrimitive(String sValue)
    {
    	checkParamNotNull( sValue ) ;
        
        if ( sValue.equals("1") ) return true ;
        if ( sValue.equalsIgnoreCase("true") ) return true ;
        return false;
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns a long value from the given string attribute value 
     * @param sValue      the value to convert ( cannot be null or void )
     * @return the long value
     */
    public long toLongPrimitive(String sValue)
    {
    	checkParamNotNull(sValue);
    	return Long.parseLong(sValue);
    }

    /**
     * Returns a long value from the given string attribute value 
     * @param sValue         the value to convert ( can be null or void )
     * @param defaultValue  the default value to use if the string is null or void 
     * @return the long value
     */
    public long toLongPrimitive( String sValue, long defaultValue )
    {
    	if ( isNullOrVoidOrBlank(sValue) ) return defaultValue ;
    	return Long.parseLong(sValue);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns an integer value from the given string attribute value 
     * @param sValue      the value to convert ( cannot be null or void )
     * @return the integer value
     */
    public int toIntPrimitive( String sValue)
    {
    	checkParamNotNull(sValue);
    	return Integer.parseInt(sValue);
    }

    /**
     * Returns an integer value from the given string attribute value 
     * @param sValue         the value to convert ( can be null or void )
     * @param defaultValue  the default value to use if the string is null or void 
     * @return the integer value
     */
    public int toIntPrimitive(String sValue, int defaultValue )
    {
    	if ( isNullOrVoidOrBlank(sValue) ) return defaultValue ;
    	return Integer.parseInt(sValue);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns a short value from the given string attribute value 
     * @param sValue     the value to convert ( cannot be null or void )
     * @return 
     */
    public short toShortPrimitive( String sValue)
    {
    	checkParamNotNull( sValue ) ;
    	return Short.parseShort(sValue);
    }

    /**
     * Returns a short value from the given string attribute value 
     * @param sValue     the value to convert ( can be null or void )
     * @param defaultValue  the default value to use if the string is null or void 
     * @return 
     */
    public short toShortPrimitive(String sValue, short defaultValue )
    {
    	if ( isNullOrVoidOrBlank(sValue) ) return defaultValue ;
    	return Short.parseShort(sValue);
    }

    //-----------------------------------------------------------------------------------
    
    
    //-----------------------------------------------------------------------------------
    /**
     * Returns a byte value from the given string attribute value 
     * @param sValue     the value to convert ( cannot be null or void )
     * @return the byte value
     */
    public byte toBytePrimitive(String sValue)
    {
    	checkParamNotNull( sValue ) ;
    	return Byte.parseByte(sValue);
    }

    /**
     * Returns a byte value from the given string attribute value 
     * @param sValue        the value to convert ( can be null or void )
     * @param defaultValue  the default value to use if the string is null or void 
     * @return the byte value
     */
    public byte toBytePrimitive(String sValue, byte defaultValue )
    {
    	if ( isNullOrVoidOrBlank( sValue ) ) return defaultValue ;
    	return Byte.parseByte(sValue);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns a double value from the given string attribute value
     * @param sValue      the value to convert ( cannot be null or void )
     * @return the double value
     */
    public double toDoublePrimitive( String sValue)
    {
    	checkParamNotNull(sValue);
    	return Double.parseDouble(sValue);
    }

    /**
     * Returns a double value from the given string attribute value
     * @param sValue       the value to convert ( can be null or void )
     * @param defaultValue the default value to use if the string is null or void 
     * @return the double value
     */
    public double toDoublePrimitive(String sValue, double defaultValue)
    {
        if ( isNullOrVoidOrBlank(sValue) ) return defaultValue ;
    	return Double.parseDouble(sValue);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns a float value from the given string attribute value
     * 
     * @param sValue     the value to convert ( cannot be null or void )
     * @return the float value
     */
    public float toFloatPrimitive(String sValue)
    {
    	checkParamNotNull(sValue);
    	return Float.parseFloat(sValue);
    }

    /**
     * Returns a float value from the given string attribute value
     * @param sValue        the value to convert ( can be null or void )
     * @param defaultValue  the default value to use if the string is null or void 
     * @return the float value
     */
    public float toFloatPrimitive(String sValue, float defaultValue)
    {
    	if ( isNullOrVoidOrBlank(sValue) ) return defaultValue ;
    	return Float.parseFloat(sValue);
    }

    //-----------------------------------------------------------------------------------
    /**
     * Parse the given date ( supposed to be not null and in ISO format : "YYYY-MM-DD" )
     * @param sDate
     * @return
     */
    //private java.util.Date parseDate( String sDate )
    private java.util.Date parseDateOrTime( String sDate, String format )
    {
//        java.util.Date ret = null ;
//        try
//        {
//            //--- Try to parse the input date ( with non lenient parsing => check validity )
//            ret = DateUtil.parseDate(sDate);
//        } catch (TelosysRuntimeException e)
//        {
//            throwException("Invalid date value '" + sDate + "' ");
//        }
//        return ret ;
        java.util.Date ret = null ;
        try
        {
            //--- Try to parse the input date ( with non lenient parsing => check validity )
//            DATE_FORMAT_ISO.setLenient(false); // non lenient parsing ( exception if invalid date )
//            ret = DATE_FORMAT_ISO.parse(sDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false); // non lenient parsing ( exception if invalid date )
            ret = dateFormat.parse(sDate);
            
        } catch (ParseException e)
        {
            throwException("Cannot parse date ( format '" + format + "' )" );
        }
        return ret ;
    	
    }

    private String formatDateOrTime(java.util.Date date, String format )
    {
        if (date != null)
        {
            //return DATE_ISO_FORMAT.format( date );
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format( date );    
        }
        else
        {
            return "";
        }
    }
    
//    /**
//     * Parse the given time ( supposed to be not null and in ISO format : "HH:MM:SS" )
//     * @param sTime
//     * @return
//     */
//    private java.util.Date parseTime( String sTime )
//    {
//        java.util.Date ret = null ;
//        try
//        {
//            //--- Try to parse the input date ( with non lenient parsing => check validity )
//            ret = DateUtil.parseTime(sTime);
//        } catch (TelosysRuntimeException e)
//        {
//            throwException("Invalid time value '" + sTime + "' ");
//        }
//        return ret ;
//    }

//    /**
//     * Parse the given date & time ( supposed to be not null and in ISO format : "YYYY-MM-DD HH:MM:SS" )
//     * @param sDateTime
//     * @return
//     */
//    private java.util.Date parseDateTime( String sDateTime )
//    {
//        java.util.Date ret = null ;
//        try
//        {
//            //--- Try to parse the input date ( with non lenient parsing => check validity )
//            ret = DateUtil.parseDateTime(sDateTime);
//        } catch (TelosysRuntimeException e)
//        {
//            throwException("Invalid datetime value '" + sDateTime + "' ");
//        }
//        return ret ;
//    }
    
    //-----------------------------------------------------------------------------------
    /**
     * Returns a java.util.Date instance for the given value, it can be <br>
     * . a date in ISO format "YYYY-MM-DD" <br>
     * . a time in ISO format "HH:MM:SS" <br>
     * . a date & time in ISO format "YYYY-MM-DD HH:MM:SS" <br>
     * 
     * @param sValue
     * @return the instance or null if the given value is void 
     */
    public java.util.Date toUtilDateObject(String sValue)
    {
        if (sValue != null)
        {
            String sTrim = sValue.trim();
            //--- Use the LENGTH to determine the type 
            int length = sTrim.length() ;
            if ( length == 0 ) // ""
            {
                return null ;
            }
            else if ( length == 10 ) // "YYYY-MM-DD"
            {
                return parseDateOrTime(sTrim, DATE_ISO_FORMAT);
            }
            else if ( length == 8 ) // "HH:MM:SS"
            {
                return parseDateOrTime(sTrim, TIME_ISO_FORMAT );
            }
            else if ( length == 19 ) // "YYYY-MM-DD HH:MM:SS"
            {
                return parseDateOrTime( sTrim, DATE_TIME_ISO_FORMAT );
            }
            else
            {
                throwException("Invalid value '" + sTrim + "' ");
            }
        }
        return null;
    }
    
    /**
     * Returns a java.sql.Date instance for the given date in ISO format "YYYY-MM-DD" 
     * @param sValue
     * @return the instance or null if the given value is void 
     */
    public java.sql.Date toSqlDateObject ( String sValue )
    {
        if ( sValue != null )
        {
            String sDateTrim = sValue.trim();
            //--- Use the LENGTH to determine the type 
            int length = sDateTrim.length() ;
            if ( length == 0 ) // ""
            {
                return null ;
            }
            else if ( length == 10 ) // "YYYY-MM-DD"
            {
                // NB : do not use 'java.sql.Date.valueOf(s)' because the validity is not checked !
                java.util.Date ud = parseDateOrTime( sDateTrim, DATE_ISO_FORMAT );
                return new java.sql.Date( ud.getTime() );
            }
            else
            {
                throwException(ERROR_VALUE_IS_INVALID + " '" + sDateTrim + "' ");
            }
        }
        return null;
    }
    
    /**
     * Returns a java.sql.Time instance for the given time in ISO format "HH:MM:SS" 
     * @param sValue
     * @return the instance or null if the given value is void 
     */
    public java.sql.Time toSqlTimeObject ( String sValue )
    {
        if ( sValue != null )
        {
            String sTrim = sValue.trim();
            //--- Use the LENGTH to determine the type 
            int length = sTrim.length() ;
            if ( length == 0 ) // ""
            {
                return null ;
            }
            else if ( length == 8 ) // "HH:MM:SS"
            {
                // NB : do not use 'java.sql.Time.valueOf(s)' because the validity is not checked !
                java.util.Date ud = parseDateOrTime( sTrim, TIME_ISO_FORMAT );
                return new java.sql.Time( ud.getTime() );
            }
            else
            {
                throwException("Invalid time value '" + sTrim + "' ");
            }
        }
        return null;
    }
    
    /**
     * Returns a java.sql.Timestamp instance for the given date-time in ISO format "YYYY-MM-DD HH:MM:SS" 
     * @param sValue
     * @return the instance or null if the given value is void 
     */
    public java.sql.Timestamp toSqlTimestampObject ( String sValue )
    {
        if ( sValue != null )
        {
            String sTrim = sValue.trim();
            //--- Use the LENGTH to determine the type 
            int length = sTrim.length() ;
            if ( length == 0 ) // ""
            {
                return null ;
            }
            else if ( length == 19 ) // "YYYY-MM-DD HH:MM:SS"
            {
                // NB : do not use 'java.sql.Timestamp.valueOf(s)' because the validity is not checked !
                java.util.Date ud = parseDateOrTime( sTrim, DATE_TIME_ISO_FORMAT );
                return new java.sql.Timestamp( ud.getTime() );
            }
            else
            {
                throwException("Invalid Timestamp value '" + sTrim + "' ");
            }
        }
        return null;
    }
    
    //-------------------------------------------------------------------------------- 
    /**
     * Returns a Boolean instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Boolean toBooleanObject( String sValue )
    {
        //return ( isNullOrVoid( sValue ) ? null : new Boolean(getBoolean(sFieldName, sValue) ) ) ;
        return ( isNullOrVoidOrBlank( sValue ) ? null : Boolean.valueOf(toBooleanPrimitive(sValue) ) ) ;
    }
    
    
    /**
     * Returns a Byte instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Byte toByteObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Byte.valueOf(sValue.trim()) ) ;
    }
    /**
     * Returns a Short instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Short toShortObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Short.valueOf(sValue.trim()) ) ;
    }
    
    /**
     * Returns an Integer instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Integer toIntegerObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Integer.valueOf( sValue.trim() ) ) ;
        
    }
    /**
     * Returns a Long instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Long toLongObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Long.valueOf( sValue.trim() ) ) ;
    }
    /**
     * Returns a Float instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Float toFloatObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Float.valueOf( sValue.trim() ) ) ;
    }
    
    /**
     * Returns a Double instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public Double toDoubleObject(String sValue)
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : Double.valueOf( sValue.trim() ) ) ;
    }
    
    /**
     * Returns a BigDecimal instance built from the given string value
     * @param sValue
     * @return the object or null if the value is null or void 
     */
    public BigDecimal toBigDecimalObject( String sValue )
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : new BigDecimal(sValue.trim()) ) ;
    }
    
    /**
     * Returns a BigInteger instance built from the given string value
     * @param sValue
     * @return
     */
    public BigInteger toBigIntegerObject( String sValue )
    {
        return ( isNullOrVoidOrBlank( sValue ) ? null : new BigInteger(sValue.trim()) ) ;
    }
    
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given date value
     * 
     * @param date
     * @return the date in ISO format ( YYYY-MM-DD ) or "" if date is null
     */
    public String toString(java.util.Date date)
    {
        return toString(date, DATE_ONLY);
    }
    
    /**
     * Returns a string for the given date value
     * @param date
     * @param iType : DATE_ONLY ( YYYY-MM-DD ), TIME_ONLY ( HH:MM:SS ) or DATE_AND_TIME ( YYYY-MM-DD HH:MM:SS )
     * @return
     */
    public String toString(java.util.Date date, int iType)
    {
        if (date == null)
        {
            return "";
        }
        if ( iType == DATE_AND_TIME )
        {
            //return DateUtil.dateTimeISO(date); // ver 1.0.3
            return formatDateOrTime(date, DATE_TIME_ISO_FORMAT);
        }
        else if ( iType == TIME_ONLY )
        {
            //return DateUtil.timeISO(date); // ver 1.0.3
            return formatDateOrTime(date, TIME_ISO_FORMAT);
        }
        else // default DATE_ONLY
        {
            //return DateUtil.dateISO(date); // ver 1.0.3
            return formatDateOrTime(date, DATE_ISO_FORMAT);
        }
    }
    
    /**
     * Returns a string for the given SQL date value : "YYYY-MM-DD"
     * @param d
     * @return
     */
    public String toString( java.sql.Date d )
    {
        //return d.toString(); // "YYYY-MM-DD"
        return ( d != null ? d.toString() : ""); // "YYYY-MM-DD" or void for NULL value // v 1.1.2
    }
    
    /**
     * Returns a string for the given SQL time value : "HH:MM:SS"
     * @param t
     * @return
     */
    public String toString( java.sql.Time t )
    {
        //return t.toString(); // "HH:MM:SS"
        return ( t != null ? t.toString() : ""); // "HH:MM:SS" or void for NULL value // v 1.1.2
    }
    
    /**
     * Returns a string for the given SQL time value : "YYYY-MM-DD HH:MM:SS"
     * @param ts
     * @return
     */
    public String toString( java.sql.Timestamp ts )
    {
        //return DateUtil.dateTimeISO(ts); // ver 1.0.3
        return formatDateOrTime(ts, DATE_TIME_ISO_FORMAT);
    }
    
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Boolean instance
     * @param v
     * @return boolean value ( "1" for true, "0" for false ) or "" if null
     */
    public String toString( Boolean v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Byte instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Byte v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Short instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Short v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Integer instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Integer v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Long instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Long v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Float instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Float v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Double instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Double v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given BigDecimal instance
     * @param v
     * @return value or "" if null
     */
    public String toString( BigDecimal v)
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given BigInteger instance
     * @param v
     * @return value or "" if null
     */
    public String toString( BigInteger v) 
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns a string for the given Character instance
     * @param v
     * @return value or "" if null
     */
    public String toString( Character v) 
    {
        return ( v != null ? v.toString() : ""); // void for NULL value
    }

}