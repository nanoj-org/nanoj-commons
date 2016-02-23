package org.nanoj.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpClientRequestBody 
{
	private final static String UTF8 = "UTF-8" ;

	private StringBuffer buffer = new StringBuffer();
	
	public void add(String name, String value)
	{
		String nameEncoded = encode( name ) ;
		String valueEncoded = encode( value ) ;
		if ( buffer.length() > 0 )
		{
			buffer.append("&") ;
		}
		buffer.append(nameEncoded) ;
		buffer.append("=") ;
		buffer.append(valueEncoded) ;
	}

	public String getAsString() 
	{
		return buffer.toString();
	}

	private String encode(String s)
	{
		String encoded = null ;
		try {
			encoded = URLEncoder.encode( s, UTF8 ) ;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Cannot encode : UnsupportedEncodingException",e);
			// never happends : UTF8 is always supported
		}
		return encoded ;
	}
}
