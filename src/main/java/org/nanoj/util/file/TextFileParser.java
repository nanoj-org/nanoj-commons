package org.nanoj.util.file;

public abstract class TextFileParser extends GenericFileParser 
{
    //-----------------------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //-----------------------------------------------------------------------------------
    /**
     * Constructor
     */
    public TextFileParser()
    {
    	super();
    }

    //-----------------------------------------------------------------------------------
    /**
     * Constructor
     * 
     * @param commentString
     */
    public TextFileParser(String commentString)
    {
    	super(commentString);
    }

//    //-----------------------------------------------------------------------------------
//    /**
//     * Called for each data line 
//     * @param lineNumber
//     * @param line
//     */
//    protected abstract void dataLine(int lineNumber, String line);
    
}
