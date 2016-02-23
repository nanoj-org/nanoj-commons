package org.nanoj.util.serial;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * File serializer 
 * 
 * @author L. GUERIN
 *
 */
public class FileSerializer
{

    private String _sFilePath = null ;
    
    /**
     * Constructor 
     * @param sFileName the file where to serialize the given object
     */
    public FileSerializer( String sFileName )
    {
    	if ( null == sFileName ) {
    		throw new IllegalArgumentException("File name is null");
    	}
        _sFilePath = sFileName ;
    }
    
    /**
     * Serializes the given object in the file defined by the constructor
     * @param obj
     * @throws Exception
     */
    public void write ( Object obj ) throws Exception
    {
        FileOutputStream  fos = null ;
        ObjectOutputStream oos = null ;
        if ( _sFilePath == null )
        {
            throw new Exception("File path is null");
        }
        
        try
        {
            fos = new FileOutputStream ( _sFilePath );
        } catch (FileNotFoundException e1)
        {
            throw new Exception("Cannot create FileOutputStream", e1);
        }
        
        try
        {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e2)
        {
            throw new Exception("Cannot create ObjectOutputStream", e2);
        }

        try
        {
            //--- WRITE THE OBJECT
            oos.writeObject( obj );
            //--- Close ALL
            oos.close();
            fos.close();
        } catch (IOException e3)
        {
            throw new Exception("ObjectOutputStream cannot write object ", e3);
        }
    }
    
    /**
     * Unserializes an object by reading the file defined by the constructor  
     * @return the object instance
     * @throws Exception
     */
    public Object read () throws Exception
    {
        FileInputStream   fis = null ;
        ObjectInputStream  ois = null ;
        Object obj = null ;
        if ( _sFilePath == null )
        {
            throw new Exception("File path is null");
        }
        
        try
        {
            fis = new FileInputStream ( _sFilePath );
        } catch (FileNotFoundException e1)
        {
            throw new Exception("Cannot create FileInputStream", e1);
        }
        
        try
        {
            ois = new ObjectInputStream(fis);
        } catch (IOException e2)
        {
            throw new Exception("Cannot create ObjectInputStream", e2);
        }

        try
        {
            //--- READ THE OBJECT
            obj = ois.readObject();
            //--- Close ALL
            ois.close();
            fis.close();
            
        } catch (IOException e3)
        {
            throw new Exception("ObjectOutputStream cannot read object (IOException)", e3);
        } catch (ClassNotFoundException e4)
        {
            throw new Exception("ObjectOutputStream cannot read object (ClassNotFoundException)", e4);
        }
        
        return obj;
    }
    
}
