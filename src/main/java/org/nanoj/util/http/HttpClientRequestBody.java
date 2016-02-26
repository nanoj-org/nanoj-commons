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
