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
package org.nanoj.util.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Memory serializer 
 * 
 * @author L. GUERIN
 *
 */
public class MemorySerializer
{    
    private byte[] _lastSerializedObject = null ;  
                 
    /**
     * Constructor 
     */
    public MemorySerializer()
    {
    }
    
    /**
     * Writes (serializes) the given object "in memory" and returns the result as an array of bytes.<br>
     * The result is kept, and can be retrieve with the 'read' method without parameter
     * @param obj
     * @return serialization result
     * @throws Exception
     */
    public byte[] write ( Object obj ) throws Exception
    {
        //byte[] serializedObject = null ;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = null ;
        try
        {
            oos = new ObjectOutputStream(baos);
        } catch (IOException e2)
        {
            throw new Exception("Cannot create ObjectOutputStream", e2);
        }

        try
        {
            //--- WRITE THE OBJECT
            oos.writeObject( obj );
            
            _lastSerializedObject = baos.toByteArray();
            
            //--- Close ALL
            oos.close();
            baos.close();
        } catch (IOException e3)
        {
            throw new Exception("ObjectOutputStream cannot write object ", e3);
        }
        
        return _lastSerializedObject ;
    }
    
    /**
     * Read the object from the bytes array produced by the last 'write' operation<br>
     * ( returns the last object serialized by this serializer) <br>
     * @return the last object serialized or null if none
     * @throws Exception
     */
    public Object read() throws Exception
    {
        if ( _lastSerializedObject != null )
        {
            return read ( _lastSerializedObject ) ;
        }
        return null ;
    }
    
    /**
     * Read the object from the given bytes array 
     * @param serializedObject
     * @return the object instance
     * @throws Exception
     */
    public Object read ( byte[] serializedObject ) throws Exception
    {
        ByteArrayInputStream bais = new ByteArrayInputStream( serializedObject );
        ObjectInputStream  ois = null ;
        Object obj = null ;

        try
        {
            ois = new ObjectInputStream(bais);
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
            bais.close();
            
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
