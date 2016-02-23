package org.nanoj.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HttpClientResponse {

	private static final byte[] VOID_BYTE_ARRAY = new byte[0] ;
	
	protected static final boolean STORE_RESPONSE_BODY = true ;
	
	protected static final boolean DO_NOT_STORE_RESPONSE_BODY = true ;
	
	private int    statusCode    = 0 ;
	private String statusMessage = "" ;
	
	private int    contentLength   = 0 ;
	private String contentType     = "" ;
	private String contentEncoding = "" ;

	private Map<String,List<String>> headerFields = null ;
	
	private byte[] bodyContent = new byte[0];
	
	protected HttpClientResponse(HttpURLConnection connection) throws Exception 
	{
		try {
			statusCode      = connection.getResponseCode();
			
			statusMessage   = connection.getResponseMessage();

			contentType     = connection.getContentType();

			contentLength   = connection.getContentLength();
			
			contentEncoding = connection.getContentEncoding();
			
			bodyContent = readResponseBody(connection);
			
		} catch (IOException e) {
			throw new Exception("Cannot create HttpResponse (IOException)", e); 
		}
		headerFields = connection.getHeaderFields();
	}
	
	private byte[] readResponseBody( HttpURLConnection connection ) // throws IOException
	{
		byte[] buffer = new byte[1024] ; 
		int totalLength = 0 ;

		byte[] responseBody = VOID_BYTE_ARRAY ;
		
		try {
			int len = 0 ;
			InputStream is = connection.getInputStream(); // Throws IOException if server error ( ie http Status Code = 500 ) 
			ByteArrayOutputStream baos = new ByteArrayOutputStream (1024);
			while ( ( len = is.read(buffer) ) > 0 )
			{
				baos.write(buffer, 0, len);
				totalLength = totalLength + len ;
			}
			baos.close();
			responseBody = baos.toByteArray();
		} catch (IOException e) {
			// e.printStackTrace();
			responseBody = VOID_BYTE_ARRAY ;
		}
		
		return responseBody ;
	}
	
	//--------------------------------------------------------------------------------------------
	/**
	 * Returns the status code from an HTTP response message : 200 ( OK ), 404 ( Not Found ), etc 
	 * @return
	 */
	public int getStatusCode()
	{
		return statusCode ;
	}
	
	/**
	 * Returns the HTTP response message if any : "OK", "Not Found", "Unauthorized", etc
	 * @return
	 */
	public String getStatusMessage()
	{
		return statusMessage ;
	}
	
	/**
	 * Returns the value of the "content-length" header field. 
	 * @return
	 */
	public int getContentLength()
	{
		return contentLength ;
	}
	
	/**
	 * Returns the value of the "content-type" header field. 
	 * @return
	 */
	public String getContentType()
	{
		return contentType ;
	}
	
	/**
	 * Returns the value of the "content-encoding" header field. 
	 * @return
	 */
	public String getContentEncoding()
	{
		return contentEncoding ;
	}
	
	/**
	 * Returns the content of the response body
	 * @return
	 */
	public byte[] getBodyContent()
	{
		return bodyContent ;
	}
	
	/**
	 * Returns an unmodifiable List of Strings that represents the corresponding header field values.
	 * @param name the header field name ( "Server", "Content-Type", ... )
	 * @return the list of values for the given header field name ( usually only one value )
	 */
	public Collection<String> getHeader(String name)
	{
		//return (Collection) headerFields.get(name);
		List<String> fields = headerFields.get(name);
		return fields ;
	}
	
	/**
	 * Returns all the header fields ( including the http status ) stored in an unmodifiable Map<br> 
	 * The Map keys are Strings that represent the response-header field names. <br>
	 * Each Map value is an unmodifiable List of Strings that represents the corresponding field values. 
	 * 
	 * @return
	 */
	public Map<String,List<String>> getHeaderMap()
	{
		return  headerFields;
	}
}
