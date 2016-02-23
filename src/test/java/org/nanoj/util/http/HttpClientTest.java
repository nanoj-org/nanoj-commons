package org.nanoj.util.http;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HttpClientTest {

	@Test
	public void test() {
		
	    try {		
			System.out.println("Follow redirects ? " + HttpClient.getConfigFollowRedirects() ) ;
			HttpClient.configFollowRedirects(false);
			System.out.println("Follow redirects ? " + HttpClient.getConfigFollowRedirects() ) ;
			  
			HttpClient httpClient = new HttpClient();
			httpClient.setFollowRedirects(false);
			 
			System.out.println("Before http GET  " ) ;
			HttpClientResponse response = httpClient.get("http://www.telosys.org");
			System.out.println("After http GET  " ) ;
			  
			System.out.println("Status  : " + response.getStatusCode() );
			System.out.println("Message : " + response.getStatusMessage() );
			  
			System.out.println("Content : ");
			System.out.println(" . length   = " + response.getContentLength() );
			System.out.println(" . type     = " + response.getContentType());
			System.out.println(" . encoding = " + response.getContentEncoding() );
  
			System.out.println("--- RESPONSE HEADERS ---");
			Map<String,List<String>> m = response.getHeaderMap();
			Set<String> keys = m.keySet();
			for ( String k : keys )
			{
				System.out.println( " . " + k + " " + m.get(k) );
			}
			  
			Collection<String> values = response.getHeader("Server");
			System.out.println("Server : " + values );
			// System.out.println("Header class = " + values.getClass() );
			  
			System.out.println("--- RESPONSE BODY ---");
			System.out.write(response.getBodyContent());
			
			assertEquals(200, response.getStatusCode());
			assertTrue(response.getContentType().startsWith("text/html"));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
