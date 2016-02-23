package org.nanoj.util.tson;

public class Attribute {

	public String name = null ;
	
	/**
	 * The attribute's value can be <br>
	 * . a basic value ( string, number, date, boolean, ... ) <br>
	 *     Java types : String, Boolean, BigDecimal, BigInteger <br>
	 * . an entity : in this case the value class can be any kind of object ) <br>
	 *     Java type : any kind of Java class <br>
	 * . a collection of entities : in this case the value class is a 'LinkedList' <br>
	 *     Java type : LinkedList ( to keep the order ) <br>
	 * . a null value <br>
	 */
	public Object value = null ;
}
