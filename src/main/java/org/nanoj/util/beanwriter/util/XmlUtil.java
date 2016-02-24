package org.nanoj.util.beanwriter.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.nanoj.util.DateUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utility class for XML operations ( set of static methods )
 * 
 * @author Laurent GUERIN
 *
 */
public final class XmlUtil
{
//    private static final TelosysClassLogger $log = new TelosysClassLogger(XmlUtil.class);

    
    /** 
     * Private constructor 
     */
    private XmlUtil()
    {
    }
    
    /**
     * Converts a "Standard String" to an "Xml String"
     * 
     * @param sStdString :
     * @return String :
     */ 
    public static String xmlString(final String sStdString)
    {
        if (sStdString == null)
        {
            return "";
        }
        
        char c;
        StringBuffer sb = new StringBuffer(sStdString.length() + 40);
        for (int i = 0; i < sStdString.length(); i++)
        {
            c = sStdString.charAt(i);
            switch (c)
            {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break; 
                default:
                	if ( c >= 128 ) {
                        sb.append("&#"+(int)c+";");
                	}
                	else {
                        sb.append(c);
                	}
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * Returns the given date as a Telosys XML date value ( in ISO format ) 
     * @param date
     * @return the ISO date or "" if the given date is null
     */
    public static String xmlDate(final Date date)
    {
        if (date == null)
        {
            return "";
        }
        // return $dateISOFormatter.format(date); // ver 1.0.3
        return DateUtil.dateISO(date); // ver 1.0.3
    }

    /**
     * Returns the given boolean as a Telosys XML boolean value 
     * @param b
     * @return "1" for TRUE, "0" for FALSE
     */
    public static String xmlBool(final boolean b)
    {
        if ( b )
        {
            return "1";
        }
        else
        {
            return "0";            
        }
    }
    
    //----------------------------------------------------------------------------------------------
    // XML DOM PARSER
    //----------------------------------------------------------------------------------------------
    
    private static DocumentBuilder getDocumentBuilder() throws Exception
    {
        DocumentBuilderFactory factory = null ;
        DocumentBuilder builder = null ;
        //--- Create a new factory instance via JAXP
        try
        {
            factory = DocumentBuilderFactory.newInstance();
        } catch (FactoryConfigurationError e)
        {
            throw new Exception("XmlUtil.parse() : Cannot create DocumentBuilderFactory " , e);
	    } catch (Throwable t)
	    {
            throw new Exception("XmlUtil.parse() : Cannot create DocumentBuilderFactory " , t);
	    }
	    if ( factory != null )
	    {
	        //--- Create a DOM parser ( instance of a builder )
		    try
	        {
	            builder = factory.newDocumentBuilder();
	        } catch (ParserConfigurationException e)
	        {
	            throw new Exception("XmlUtil.parse() : Cannot create DocumentBuilder " , e);
		    } catch (Throwable t)
		    {
	            throw new Exception("XmlUtil.parse() : Cannot create DocumentBuilder " , t);
		    }
	    }
	    else
	    {
            throw new Exception("XmlUtil.parse() : DocumentBuilderFactory instance is null" );
	    }
	    
	    if ( builder == null )
	    {
            throw new Exception("XmlUtil.parse() : DocumentBuilder instance is null" );
	    }
	    return builder ;
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * Parse the given File name
     * @param sFileName
     * @return DOM document object
     * @throws Exception
     */
    public static Document parse( String sFileName ) throws Exception
    {
        File file = new File(sFileName) ;
        if ( file.exists() != true )
        {
            throw new Exception("XmlUtil.parse() : file '" + sFileName + "' not found ! ");
        }
        return parse(file);
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * Parse the given File object
     * @param file
     * @return DOM document object
     * @throws Exception
     */
    public static Document parse( File file ) throws Exception
    {
        Document document = null;
        DocumentBuilder builder = getDocumentBuilder();
        if ( builder != null )
        {
            try
            {
                //--- Parse the file
                document = builder.parse(file);
            } catch (SAXException e)
            {
                throw new Exception("XmlUtil.parse() : SAXException " , e);
            } catch (IOException e)
            {
                throw new Exception("XmlUtil.parse() : IOException " , e);
		    } catch (Throwable t)
		    {
                throw new Exception("XmlUtil.parse() : Throwable " , t);
		    }
        }
        return document ;
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * Parse the given InputStream
     * @param is
     * @return DOM document object
     * @throws Exception
     */
    public static Document parse( InputStream is ) throws Exception
    {
        Document document = null;
        DocumentBuilder builder = getDocumentBuilder();
        if ( builder != null )
        {
            try
            {
                //--- Parse the file
                document = builder.parse(is);
            } catch (SAXException e)
            {
                throw new Exception("XmlUtil.parse() : SAXException " , e);
            } catch (IOException e)
            {
                throw new Exception("XmlUtil.parse() : IOException " , e);
		    } catch (Throwable t)
		    {
                throw new Exception("XmlUtil.parse() : Throwable " , t);
		    }
        }
        return document ;
    }
    
    //----------------------------------------------------------------------------------------------
    
}