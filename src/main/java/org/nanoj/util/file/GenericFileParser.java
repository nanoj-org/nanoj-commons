package org.nanoj.util.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public abstract class GenericFileParser 
{
    //-----------------------------------------------------------------------------------
	// ABSTRACT METHODS
    //-----------------------------------------------------------------------------------
	
    /**
     * Called when the parsing starts
     */
	protected abstract void start();

    /**
     * Called for each comment line
     * @param lineNumber
     * @param line
     */
    protected abstract void commentLine(int lineNumber, String line);

    /**
     * Called for each void line
     * @param lineNumber
     */
    protected abstract void voidLine(int lineNumber);
    
    /**
     * Specific processing for each data line
     * @param lineNumber 
     * @param line the line to process
     */
    protected abstract void dataLine(int lineNumber, String line) ;
    
    /**
     * Called when the parsing ends
     */
    protected abstract void end();
    
    /**
     * Returns the result of the file parsing
     * @return
     */
    public abstract List<Object> getResult() ;

    //--------------------------------------------------------------------------------
	
    private static final int     BUFFER_SIZE     = 2048;

    private String               _commentString  = null;        // String for comments

    //--------------------------------------------------------------------------------

    /**
     * Constructor
     */
    protected GenericFileParser()
    {
        _commentString = null;
    }
    
	//-----------------------------------------------------------------------------------
    /**
     * Constructor
     * @param commentString
     */
    protected GenericFileParser(String commentString)
    {
        _commentString = commentString;
    }
    
	//-----------------------------------------------------------------------------------
    /**
     * Returns the comment string
     * @return
     */
    protected String getCommentString()
	{
		return _commentString ;
	}

    //-----------------------------------------------------------------------------------
    /**
     * Parse the given file 
     * @param sFileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parse(String sFileName) throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader(sFileName);
        BufferedReader br = new BufferedReader(fr, BUFFER_SIZE);
        try
        {
            parse(br);
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            br.close();
            fr.close();
        }
    }

    //-----------------------------------------------------------------------------------
    /**
     * Parse the given input
     * @param is
     * @throws IOException
     */
    public void parse(InputStream is) throws IOException
    {
    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader br = new BufferedReader(isr, BUFFER_SIZE);
        try
        {
            parse(br);
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            br.close();
            isr.close();
        }
    }
    
    //-----------------------------------------------------------------------------------
    /**
     * Parse the given input
     * @param br
     * @throws IOException
     */
    public void parse(BufferedReader br) throws IOException
    {
        int iRowNum = 0;
        String sLine;
        //_handler.start();
        start();
        while ((sLine = br.readLine()) != null)
        {
            iRowNum++;
            if (sLine.length() == 0)
            {
                //_handler.voidLine(iRowNum);
                voidLine(iRowNum);
            }
            else
            {
                if (isComment(sLine))
                {
                    //_handler.commentLine(iRowNum, sLine);
                    commentLine(iRowNum, sLine);
                }
                else
                {
                	//--- Specific processing for each data line
                    dataLine(iRowNum, sLine);
                }
            }
        }
        //_handler.end();
        end();
        br.close();
    }
    
    //-----------------------------------------------------------------------------------
    // PRIVATE
    //-----------------------------------------------------------------------------------
    /**
     * @param sLine
     *            the line to check
     * @return true if the line is a comment, false else
     */
    private boolean isComment(String sLine)
    {
        if (_commentString != null)
        {
            if (sLine.startsWith(_commentString))
            {
                return true;
            }
        }
        return false;
    }

    
}
