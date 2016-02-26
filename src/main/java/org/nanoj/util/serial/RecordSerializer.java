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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Serializer tool to build basic DAO with "save/load/delete" operations 
 * 
 * @author Laurent GUERIN
 *
 */
public class RecordSerializer
{
	private String _sDataDirectory = null;

	/**
	 * Constructor
	 * @param sDataDirectory : the full path of the directory where to store the instances files
	 */
	public RecordSerializer(String sDataDirectory)
	{
		super();
		//--- Store the data directory path (with a ending "/" )
		if (sDataDirectory.endsWith("/"))
		{
			_sDataDirectory = sDataDirectory;
		} else
		{
			_sDataDirectory = sDataDirectory + "/";
		}
		//--- Create the data directory if it doesnt exist
		String sDir = _sDataDirectory.substring(0, _sDataDirectory.length()-1 );
		File file = new File(sDir);
		if ( ! file.exists() )
		{
			file.mkdir();
		}
	}

	private String getFileName( Class<?> cl, String sKey )
    {
		return _sDataDirectory + cl.getName() + "-" + sKey ;
    }

	/**
	 * Save the given object in a instance file indentifying by the given key
	 * @param obj : the object to save
	 * @param sKey : the key to use to identify the instance
	 */
	public void save( Object obj, String sKey ) //throws TelosysException
	{
		// --- Check the object is not null
		if (obj == null)
		{
			throw new RuntimeException("save : Object to save is null");
		}
		// --- Check the key is not null
		if (sKey == null)
		{
			throw new RuntimeException("load : Key is null");
		}
		// --- Check the object is serializable
		if ((obj instanceof Serializable) != true)
		{
			throw new RuntimeException("save : Object to save (class '"
					+ obj.getClass().getName() + "') is not serializable");
		}
		// --- Get the file name
		String sFileName = getFileName(obj.getClass(), sKey);

		// --- Serialize the object
		try
		{
			FileOutputStream fos = new FileOutputStream(sFileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e)
		{
			throw new RuntimeException("save : FileNotFoundException - file ='"
					+ sFileName + "'", e);
		} catch (IOException e)
		{
			throw new RuntimeException("save : IOException - file ='"
					+ sFileName + "'", e);
		}
	}

	/**
	 * Load an object from the instance file corresponding to the given key 
	 * @param cl : the class of the object to load
	 * @param sKey : the key of the object to load
	 * @return : the instance loaded, or null if not found
	 */
	public Object load(Class<?> cl, String sKey) //throws TelosysException
	{
		// --- Check the class is not null
		if (cl == null)
		{
			throw new RuntimeException("load : Object class is null");
		}
		// --- Check the key is not null
		if (sKey == null)
		{
			throw new RuntimeException("load : Key is null");
		}
		// --- Get the file name
		String sFileName = getFileName(cl, sKey);
		
		// --- Try to load the object 
		FileInputStream fis;
		try
		{
			fis = new FileInputStream(sFileName);
		} catch (FileNotFoundException e1)
		{
			return null; // File not found => Object not found
		}

		Object obj = null;
		try
		{
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
		} catch (IOException e)
		{
			throw new RuntimeException("load : IOException - file ='"
					+ sFileName + "'", e);
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(
					"load : ClassNotFoundException - file ='" + sFileName + "'",
					e);
		}
		return obj;
	}

	/**
	 * Deletes the object instance file corresponding to the given key 
	 * @param cl : the class of the object to delete
	 * @param sKey : the key of the object to delete
	 * @return : 1 if the object file exists and has been deleted, 0 if the object file doesn't exist
	 */
	public int delete(Class<?> cl, String sKey) //throws TelosysException
	{
		// --- Check the class is not null
		if (cl == null)
		{
			throw new RuntimeException("load : Object class is null");
		}
		// --- Check the key is not null
		if (sKey == null)
		{
			throw new RuntimeException("load : Key is null");
		}
		// --- Get the file name
		String sFileName = getFileName(cl, sKey);
		
		// --- Delete the file 
		File file = new File(sFileName);
		if ( file.exists() )
		{
			file.delete();
			return 1;
		}
		else
		{
			return 0;			
		}
	}
}
