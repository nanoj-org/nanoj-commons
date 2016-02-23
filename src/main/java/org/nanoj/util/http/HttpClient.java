package org.nanoj.util.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private Boolean followRedirects = null ;
        
	//---------------------------------------------------------------------
	// GET
	//---------------------------------------------------------------------
	public HttpClientResponse get(String url)  throws Exception  
	{
		return get(getURL(url));
	}
	
	public HttpClientResponse get(URL url) throws Exception 
	{
		return process(url, "GET", null);
	}

	//---------------------------------------------------------------------
	// HEAD
	//---------------------------------------------------------------------
	public HttpClientResponse head(String url)  throws Exception  
	{
		return head(getURL(url));
	}
	
	public HttpClientResponse head(URL url) throws Exception 
	{
		return process(url, "HEAD", null);
	}

	//---------------------------------------------------------------------
	// POST
	//---------------------------------------------------------------------
	public HttpClientResponse post(String url, String data) throws Exception 
	{
		byte[] dataBytes = null ;
		if ( data != null )
		{
			dataBytes = data.getBytes();
		}
		return post(url, dataBytes );
	}
	public HttpClientResponse post(String url, byte[] data) throws Exception 
	{
		return post(getURL(url), data );
	}
	public HttpClientResponse post(URL url, byte[] data) throws Exception 
	{
		return process(url, "POST", data);
	}

	//---------------------------------------------------------------------
	// PUT
	//---------------------------------------------------------------------
	public HttpClientResponse put(String url, String data) throws Exception 
	{
		byte[] dataBytes = null ;
		if ( data != null )
		{
			dataBytes = data.getBytes();
		}
		return put(url, dataBytes );
	}
	public HttpClientResponse put(String url, byte[] data) throws Exception 
	{
		return put(getURL(url), data );
	}
	public HttpClientResponse put(URL url, byte[] data) throws Exception 
	{
		return process(url, "PUT", data);
	}

	//---------------------------------------------------------------------
	// DELETE
	//---------------------------------------------------------------------
	public HttpClientResponse delete(String url) throws Exception 
	{
		return delete(getURL(url));
	}
	public HttpClientResponse delete(URL url) throws Exception 
	{
		return process(url, "DELETE", null);
	}

	//---------------------------------------------------------------------
	// Private methods
	//---------------------------------------------------------------------
	private HttpClientResponse process(URL url, String method, byte[] data) throws Exception 
	{
		HttpURLConnection connection = connect(url, method);
		if ( data != null )
		{
			writeRequestBody(connection, data);
		}
		HttpClientResponse response = new HttpClientResponse(connection);
		connection.disconnect();
		return response ;
	}
	
	//---------------------------------------------------------------------
	private URL getURL(String sUrl) throws Exception {
		URL url = null;
		try {
			url = new URL(sUrl);
		} catch (Exception e) {
			throw new Exception("Invalid URL");
		}
		return url;
	}

	//---------------------------------------------------------------------
	// GLOBAL CONFIGURATION  (STATIC)
	//---------------------------------------------------------------------
	/**
	 * Global "Follow Redirects" configuration 
	 * @param b
	 */
	public static void configFollowRedirects ( boolean b )  
	{
	    HttpURLConnection.setFollowRedirects(b);
	}
	/**
	 * Returns the global "Follow Redirects" configuration 
	 * @return
	 */
	public static boolean getConfigFollowRedirects()  
	{
	    return HttpURLConnection.getFollowRedirects();
	}
	
	//---------------------------------------------------------------------
	/**
	 * Global proxy configuration 
	 * @param proxyHost : example "myproxy"
	 * @param proxyPort : example ""
	 */
	public static void configProxy  (String proxyHost, String proxyPort )  
	{
		System.setProperty("http.proxyHost", proxyHost );
		System.setProperty("http.proxyPort", proxyPort );
		
		System.setProperty("https.proxyHost", proxyHost );
		System.setProperty("https.proxyPort", proxyPort );
	}

	//---------------------------------------------------------------------
	public static void configTrustStore (String trustStoreFile, String trustStorePassword )  
	{
		System.setProperty("javax.net.ssl.trustStore", trustStoreFile );
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword );
	}
	
	//---------------------------------------------------------------------
	// INSTANCE CONFIGURATION
	//---------------------------------------------------------------------
	/**
	 * Set the "Follow Redirects" flag for this instance
	 * @param b
	 */
	public void setFollowRedirects ( boolean b )  
	{
	    this.followRedirects = Boolean.valueOf(b);
	}
	public boolean getFollowRedirects()  
	{
	    if ( this.followRedirects != null )
	    {
	        return this.followRedirects.booleanValue() ;
	    }
	    return getConfigFollowRedirects();
	}
	
	
	//---------------------------------------------------------------------
	//---------------------------------------------------------------------
	private HttpURLConnection connect(URL url, String method) throws Exception {
		HttpURLConnection connection = null;
		try {
			
			connection = (HttpURLConnection) url.openConnection();
		    if ( this.followRedirects != null )
		    {
		        //--- This instance has a specific "Follow Redirects" flag => use it 
		        connection.setInstanceFollowRedirects( this.followRedirects.booleanValue() );
		    }
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			connection.connect();
			return connection;
		} catch (Exception e) {
			throw new Exception("Connection failed");
		}
	}

	//---------------------------------------------------------------------
//	public void disconnect() {
//		connection.disconnect();
//	}

	//---------------------------------------------------------------------
//	public void displayResponse() throws Exception {
//		String line;
//
//		try {
//			BufferedReader s = new BufferedReader(new InputStreamReader(
//					connection.getInputStream()));
//			line = s.readLine();
//			while (line != null) {
//				System.out.println(line);
//				line = s.readLine();
//			}
//			s.close();
//		} catch (Exception e) {
//			throw new Exception("Unable to read input stream");
//		}
//	}
//
	//---------------------------------------------------------------------
	private void writeRequestBody(HttpURLConnection connection, byte[] data) throws Exception 
	{
		try {
			OutputStream os = connection.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw new Exception("Cannot write data in the request body (unable to write in output stream)");
		}
	}
	//---------------------------------------------------------------------
//	private void postData(HttpURLConnection connection, String s) throws Exception 
//	{
//		try {
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
//					connection.getOutputStream()));
//			bw.write(s, 0, s.length());
//			bw.flush();
//			bw.close();
//		} catch (Exception e) {
//			throw new Exception("Cannot post date (unable to write in output stream)");
//		}
//	}
}
