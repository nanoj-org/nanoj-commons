package org.nanoj.util.file;

public abstract class CSVFileParser extends GenericFileParser 
{
    private static final char NO_SEPARATOR     = '\0';

    private char              _separatorChar   = NO_SEPARATOR; // Fields separator character
    
    //-----------------------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //-----------------------------------------------------------------------------------
    /**
     * Constructor 
     * 
     * @param separatorChar  the field separator
     */
    public CSVFileParser(char separatorChar)
    {
    	super();
        _separatorChar = separatorChar;
    }

    //-----------------------------------------------------------------------------------
    /**
     * Constructor 
     * 
     * @param separatorChar  the field separator
     * @param commentString  the comment string
     */
    public CSVFileParser(char separatorChar, String commentString)
    {
    	super(commentString);
        _separatorChar = separatorChar ;
        //_commentString = commentString ;
    }

    //-----------------------------------------------------------------------------------
    /**
     * Constructor 
     * 
     * @param separatorChar  the field separator (use only the first character of the string)
     */
    public CSVFileParser(String separatorChar)
    {
    	super();
        _separatorChar = NO_SEPARATOR;
        if (separatorChar != null)
        {
            if (separatorChar.length() > 0)
            {
                _separatorChar = separatorChar.charAt(0);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    /**
     * Constructor
     * 
     * @param separatorChar  the field separator (use only the first character of the string)
     * @param commentString  the comment string
     */
    public CSVFileParser(String separatorChar, String commentString)
    {
    	super(commentString);
        _separatorChar = NO_SEPARATOR;
        if (separatorChar != null)
        {
            if (separatorChar.length() > 0)
            {
                _separatorChar = separatorChar.charAt(0);
            }
        }
        //_commentString = commentString ;
    }

    //-----------------------------------------------------------------------------------
    /**
     * Returns the separator character
     * @return
     */
    public char getSeparatorChar()
    {
    	return _separatorChar ;
    }

    
    //-----------------------------------------------------------------------------------
    /**
     * Called for each data line 
     * @param lineNumber the line number
     * @param line the line as it was read from the file
     * @param fields array of fields
     */
    protected abstract void dataLine(int lineNumber, String line, String fields[]);
    
    //-----------------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.objectweb.telosys.util.file.GenericFileParser#dataLine(int, java.lang.String)
     */
    protected void dataLine(int lineNumber, String line)
    {
    	//--- Split the line
    	String[] fields = split(line);
    	
    	//--- Call the subclass method ( with the line fields )
        dataLine(lineNumber, line, fields);
    }
    
    //-----------------------------------------------------------------------------------
    // PRIVATE
    //-----------------------------------------------------------------------------------
    private String[] split(String sLine)
    {
        int len = sLine.length();
        //--- All the characters of the line
        char chars[] = new char[len];
        sLine.getChars(0, len, chars, 0);

        int iFieldCount = getFieldCount(chars);
        String fields[] = new String[iFieldCount];

        // String sField = null;
        int iFieldIndex = 0;
        int iOffset = -1;
        int iCount = 0;
        for ( int i = 0 ; i < len ; i++ )
        {
            if (chars[i] == _separatorChar)
            {
                fields[iFieldIndex++] = getField(chars, iOffset, iCount);
                iOffset = -1;
                iCount = 0;
            }
            else
            {
                if (iOffset == -1)
                    iOffset = i;
                iCount++;
            }
        }
        fields[iFieldIndex++] = getField(chars, iOffset, iCount);
        return fields;
    }

    //-----------------------------------------------------------------------------------
    private int getFieldCount(char chars[])
    {
        //--- Nb separ + 1
        int len = chars.length;
        int iCount = 0;
        for ( int i = 0 ; i < len ; i++ )
        {
            if (chars[i] == _separatorChar)
            {
                iCount++;
            }
        }
        return iCount + 1;
    }

    //-----------------------------------------------------------------------------------
    private String getField(char chars[], int iOffset, int iCount)
    {
        if (iOffset < 0)
        {
            return "";
        }
        else
        {
            //            String sField = new String(chars,iOffset,iCount);
            //            //$log.trace(" . field [" + sField + "] offset = " +
            // iOffset + " count = " + iCount );
            //            return sField ;
            return new String(chars, iOffset, iCount);
        }
    }
    
}
