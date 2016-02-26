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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import org.nanoj.util.DateUtil;


public class Parser {

	private final static String INVALID_SYNTAX = "Invalid syntax" ;
	private final static String INVALID_VALUE  = "Invalid value" ;
	private final static String UNEXPECTED_END = "Unexpected end of input" ;
	
	private byte[] input = null ;
	
	private int pos = 0 ; 
	
	private HashMap<String,Class<?>> map = null ;
	
	private void log(String msg) {
		// System.out.println(msg); System.out.flush();
	}
	
	public Parser(byte[] input, HashMap<String,Class<?>> map) {
		super();
		this.input = input;
		this.map = map;
	}

	public Object parse () {
		log("parse()...");
		byte b = 0 ;
		while ( ( b = nextByte() ) > 0 )
		{
			if ( b == '{' ) // Entity
			{
				log("parse() : { found => parse ENTITY ");
				Object oEntity = parseEntity();
				return oEntity ;
			}
			else if ( b == '[' ) // List ( collection ) 
			{
				log("parse() : [ found => parse LIST ");
				LinkedList<Object> oList = parseList();
				return oList ;
			}
		}
		return null ;
	}
	
	private byte nextByte()
	{
		//log("nextByte()...");

		byte b = 0 ;
		do {
			if ( pos >= input.length ) return 0 ; // End of input
			b = input[pos++] ;
			//log("nextByte() : b = " + (char) b ) ;
		}
		while ( b == ' ' || b == '\b' || b == '\f' || b == '\n' || b == '\r' || b == '\t' ) ;
		return b ;
	}
	
	private void rewind () {
		if ( pos > 0 ) pos-- ; // rewind 1 char 
	}
//	private void rewind ( int n ) {
//		if ( pos - n >= 0 )
//		{
//			pos = pos - n ; // rewind N char 
//		}
//	}
	
	private boolean isValidNameChar ( byte b ) {
		return ( b >= 'a' && b <= 'z' ) 
			|| ( b >= 'A' && b <= 'Z' ) 
			|| ( b >= '0' && b <= '9' ) 
			|| ( b == '_' ) ;
	}
	
	/**
	 * Read the name until the given separator
	 * i.e. : returns "my_name" for " my_name   :", "  my_name  =", or "my_name="
	 * After this method the current position points the first byte after the separator
	 * 
	 * @param separator the separator following the name : usually '=' or ':'
	 * @return 
	 */
	private String nextName ( byte separator ) {
		log("nextName(" + (char)separator + ")...");
		byte b = nextByte();
		if ( b == '}' && separator == '=' ) 
		{
			// No more attributes
			return null ;
		}

		log("nextName : first char = " + (char)b);
		
		if ( ! isValidNameChar(b) ) throw new RuntimeException(INVALID_SYNTAX);
		
		rewind(); // rewind 1 char 
		int start = pos ; 
		int length = 0 ;
		do {
			if ( pos >= input.length ) throw new RuntimeException(INVALID_SYNTAX);
			b = input[pos++] ;
			//log("nextName : char = " + (char)b);
			length++ ;
		}
		while ( isValidNameChar(b) ) ;
		
		rewind(); // rewind 1 char 
		length-- ;
		if ( length <= 0 )throw new RuntimeException(INVALID_SYNTAX);
		
		String name = new String(input, start, length);
		log("nextName : name = '" + name + "'" ) ;
		
		b = nextByte();
		if ( b == separator )
		{
			return name ;
		}
		else
		{
			throw new RuntimeException(INVALID_SYNTAX + " : separator expected (not '" + (char) b + "' )");
		}
	}
	
	private Object nextValue( String name ) {
		log("nextValue( '" + name + "') ");
		byte b = nextByte();
		if ( b == 0 )
		{
			throw new RuntimeException(INVALID_SYNTAX + " : value expected ");
		}
		if ( b == '{' ) 
		{
			//--- ENTITY ( Object )
			Object oEntity = parseEntity();
			return oEntity ;
		}
		else if ( b == '[' )
		{
			//--- COLLECTION ( List )
			LinkedList<Object> oList = parseList();
			return oList ;
		}
		else if ( b == '"' )
		{
			//--- STRING VALUE 
			return readStringValue( name );
		}
		else if ( b == '\'' ) 
		{
			//--- DATE, TIME, DATE AND TIME 
			return readDateTimeValue(name);
		}
		else
		{
			//--- LITERAL VALUE 
////			//--- There's no enclosing character : { [ "  so it can be a boolean, a number or null
////			if ( pos < input.length )
////			{
//				// What is the next byte ? Lets have a look ...
//				byte b1 = input[pos] ; // Do not increment pos here
			// One position too far => rewind 
			rewind();
			return readLiteralValue( name, b );
			/***
			byte b1 = b ;
				if ( b1 == 't' )
				{
					// "true" expected
					readTrueValue( name );
					return Boolean.valueOf(true);
				}
				if ( b1 == 'f' )
				{
					// "false" expected
					readFalseValue( name );
					return Boolean.valueOf(false);
				}
				if ( b1 == 'n' )
				{
					// "null" expected
					readNullValue( name );
					return null ;
				}
				// Other cases : number value expected
				String s = readNumberValue( name );
				if ( s.indexOf('.') >= 0 )
				{
					return new BigDecimal(s);
				}
				else
				{
					return new BigInteger(s);
				}
			***/
//			}
//			else
//			{
//				throw new RuntimeException(UNEXPECTED_END);
//			}
		}
	}
	
	private String readStringValue( String name ) {
		log("readStringValue( '" + name + "') ");

		StringBuffer sb = new StringBuffer();
		while ( pos < input.length )
		{
			byte b = input[pos++] ;
			char c = '?';

			// log("readStringValue() : b = " + (char) b ) ;
			
			if ( b == '\"' )
			{
				//--- Normal end of String
				String s = sb.toString();
				log("readStringValue() : return '" + s + "'" ) ;
				return s;
			}
			else if ( b == '\\' )
			{
				b = input[pos++] ; // Can throw out of bound exception 
				switch (b)
				{
				case 'b' : 
					c = '\b' ;
					break ;
				case 'f' : 
					c = '\f' ;
					break ;
				case 'n' : 
					c = '\n' ;
					break ;
				case 'r' : 
					c = '\r' ;
					break ;
				case 't' : 
					c = '\t' ;
					break ;
				case 'u' :
					//--- Unicode character : ex "\u0123" 
					// TODO
					throw new RuntimeException("Unicode character not yet supported");
					//break ;
				default : 
					c = (char) b;  //   \"  \\  \'  etc...
					break ;
				}
			}
			else
			{
				c = (char) b; 
			}
			sb.append(c); // 
		}
		throw new RuntimeException(INVALID_SYNTAX + " : unterminated String value for '" + name + "'");
	}
	
	private java.util.Date readDateTimeValue( String name )
	{
		log("readDateTimeValue( '" + name + "') ");

		StringBuffer sb = new StringBuffer();
		while ( pos < input.length )
		{
			byte b = input[pos++] ;
			if ( b == '\'' )
			{
				//--- Normal end of Date/Time Value
				String s = sb.toString();
				log("readStringValue() : return '" + s + "'" ) ;
				//return StrUtil.getDate(s); // "YYYY-MM-DD", "HH:MM:SS" or "YYYY-MM-DD HH:MM:SS"
				return DateUtil.parse(s); // "YYYY-MM-DD", "HH:MM:SS" or "YYYY-MM-DD HH:MM:SS"
			}
			sb.append((char) b); 
		}
		throw new RuntimeException(INVALID_SYNTAX + " : unterminated Date/Time value for '" + name + "'");
	}
	
	final static int TRUE_SIZE = 4 ;
	private byte[] readBytes( int n)
	{
		if ( pos + n <= input.length )
		{
			//--- Read the n bytes
			byte[] bytes = new byte[n];
			for (int i = 0 ; i < n ; i++ )
			{
				bytes[i] = input[pos++] ;
			}
			return bytes ;
		}
		throw new RuntimeException(UNEXPECTED_END);
	}
	
	private Object readLiteralValue( String name, byte first )
	{
		if ( first == 't' )  // "true" expected
		{
			readTrueValue( name );
			return Boolean.valueOf(true);
		}
		if ( first == 'f' ) // "false" expected
		{
			readFalseValue( name );
			return Boolean.valueOf(false);
		}
		if ( first == 'n' ) // "null" expected
		{
			readNullValue( name );
			return null ;
		}
		
		// Other cases : number value expected
		String s = readNumberValue( name );
		if ( s.indexOf('.') >= 0 )
		{
			return new BigDecimal(s);
		}
		else
		{
			return new BigInteger(s);
		}
	}
	
	private void readTrueValue( String name )
	{
		byte[] bytes = readBytes(4);
		String s = new String(bytes);
		if ( "true".equals(s) ) return ; // OK it's true
		throw new RuntimeException(INVALID_VALUE + " '" + s + "' for attribute '" + name + "'" );
	}
	
	private void readFalseValue( String name )
	{
		byte[] bytes = readBytes(5);
		String s = new String(bytes);
		if ( "false".equals(s) ) return ; // OK it's false
		throw new RuntimeException(INVALID_VALUE + " '" + s + "' for attribute '" + name + "'" );
	}
	
	private void readNullValue( String name )
	{
		byte[] bytes = readBytes(4);
		String s = new String(bytes);
		if ( "null".equals(s) ) return ; // OK it's null
		throw new RuntimeException(INVALID_VALUE + " '" + s + "' for attribute '" + name + "'" );
	}
	
	private String readNumberValue( String name )
	{
		log("readNumberValue( '" + name + "') ");

		StringBuffer sb = new StringBuffer();
		while ( pos < input.length )
		{
			byte b = input[pos++] ;
			if ( ( b >= '0' && b <= '9' ) || ( b == '.' ) || ( b == '+' ) || ( b == '-' ) )
			{
				sb.append((char)b); 
				log("readNumberValue( '" + name + "') : append '" + (char)b + "'");
			}
			else 
			{
				// One char too far => rewind
				rewind();
				// Convert to String 
				String s = sb.toString();
				log("readNumberValue( '" + name + "') : return '" + s + "'");
				if ( s.length() == 0 )
				{
					throw new RuntimeException(INVALID_VALUE + " for attribute '" + name + "'" );
				}
				return s;
			}
		}
		throw new RuntimeException(UNEXPECTED_END);
	}
	
	private Attribute nextAttribute ()
	{
		//--- Name
		String name = nextName( (byte)'=' );
		log("attribute name = '" + name + "'");
		
		if ( name == null )
		{
			// No more attributes
			return null ;
		}
		
		//--- The "=" separator is already read
//		byte b = nextByte();
//		if ( b != (byte)'=' ) throw new RuntimeException("Invalid syntaxe '=' expected after '" + name + "'");
		
		//--- Value
		Object value = nextValue(name);
		log("attribute value = '" + value + "'");
		
		Attribute attr = new Attribute() ;
		attr.name = name ;
		attr.value = value ;
		return attr ;
	}
	
	
	//private Object parseEntity ( byte[] input, int index, HashMap<String,Class<?>> map )
	private Object parseEntity ()
	{
		log("parseEntity()...");
		// Here the current position is just after the '{' char
		
		String name = nextName( (byte)':' );
		log("name = '" + name + "'");
		
		//--- Create the Entity instance 
		Object oEntity = null ;
		//Class<?> c = (Class<?>) map.get(name);
		Class<?> c = map.get(name);
		if ( c != null )
		{
			try {
				oEntity = c.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException("Cannot create instance for class '" + c.getName() + "'", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Cannot create instance for class '" + c.getName() + "'", e);
			}
		}
		else
		{
			throw new RuntimeException("Unknown entity '" + name + "' : cannot create object instance");
		}
		
		Attribute attr = null ;
		while ( ( attr = nextAttribute() ) != null )
		{
			//System.out.println("=== Attribute : " + attr.name + " = " + attr.value.getClass().getCanonicalName() );
			// Set attribute by reflection
			ParserUtil.setFieldValue(oEntity, attr.name, attr.value ) ;
		}
		
		//--- Return the ENTITY
		return oEntity ;
	}

	//private LinkedList parseList ( byte[] input, int index, HashMap<String,Class<?>> map )
	private LinkedList<Object> parseList ()
	{
		log("parseList()...");
		// Here the current position is just after the '[' char
		
		//--- Create the LIST instance 
		LinkedList<Object> list = new LinkedList<Object>();
		
		byte b = 0 ;
		while ( ( b = nextByte() ) > 0 )
		{
			if ( b == '{' ) // Entity
			{
				log("parseList() : '{' found => parse ENTITY ");
				Object oEntity = parseEntity();
				log("parseList() : entity added " + oEntity.getClass().getName() );
				list.add(oEntity);
			}
			else if ( b == ']' ) // End of list 
			{
				log("parseList() : ']' found => END OF LIST ");
				return list ;
			}
			else 
			{
				throw new RuntimeException("Syntax error in list : '" + (char)b + " found, '{' expected ");
			}
		}
		
		// Abnormal end of list : exception or not ?
		return list ;
	}
}
