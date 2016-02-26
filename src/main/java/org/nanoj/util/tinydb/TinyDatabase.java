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
package org.nanoj.util.tinydb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * This class represents a "tiny database" instance <br>
 * A "tiny database" is a set of "tables" ( based on Java Hashtable instances ) <br>
 * It works as an "in memory database", it can be useful for tests or very small databases <br>
 * The database dictionary and the database tables are stored in the filesystem by serialization <br>
 * 
 * @author Laurent Guerin
 * @since v 1.0.3
 * 
 */
public class TinyDatabase 
{

    //-----------------------------------------------------------------------------
    // STATIC ATTRIBUTES
    //-----------------------------------------------------------------------------
    private final static String DICTIONARY_FILE = "database.dict" ;

    private static TinyDatabase currentDatabase = null ;

    //-----------------------------------------------------------------------------
    // INSTANCE ATTRIBUTES
    //-----------------------------------------------------------------------------
	private String databaseFolder = null ;
	
	private String dictionaryFile = null ;
	
	//--- Collection of tables  : "table_name" --> DbTable instance
	private Hashtable<String,TinyTable> htDatabaseTables = null ;
	
    //-----------------------------------------------------------------------------
	/**
	 * Constructs a new TinyDatabse located in the given folder
	 * 
	 * @param sFolder
	 */
	public TinyDatabase(String sFolder) 
	{
		super();
		if ( sFolder == null ) throw new RuntimeException("Database constructor : folder is null ");
		if ( sFolder.endsWith("/") || sFolder.endsWith("\\") )
		{
			this.databaseFolder = sFolder;
		}
		else
		{
			this.databaseFolder = sFolder + "/";
		}
		dictionaryFile = databaseFolder + DICTIONARY_FILE ;
		
		// TODO : check db folder existence 
		// TODO : arg bCreateFolder
		
		//this.tableManager = new TableManager(databaseFolder);
		loadDictionary();
	}
	
    //-----------------------------------------------------------------------------
	/**
	 * Returns the database filesystem folder (where the tables are saved as files)
	 * @return
	 */
	public String getDatabaseFolder()
	{
		return databaseFolder ;
	}

    //-----------------------------------------------------------------------------
//	public void start() throws Exception
//	{
//		loadAllTables();
//		currentDatabase = this ;		
//	}

    //-----------------------------------------------------------------------------
	/**
	 * Returns the current database
	 * @return
	 */
	public static TinyDatabase getCurrentDatabase()
	{
		return currentDatabase ;
	}

//    //-----------------------------------------------------------------------------
//	public void stop() throws Exception
//	{
//		saveAllTables();
//	}

    //-----------------------------------------------------------------------------
	// DICTIONARY MANAGEMENT
    //-----------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private void loadDictionary() 
	{
		try {
			FileInputStream fis = new FileInputStream(dictionaryFile);
			
			ObjectInputStream ois = new ObjectInputStream(fis);
			htDatabaseTables = (Hashtable<String,TinyTable>) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
		    htDatabaseTables = new Hashtable<String,TinyTable>(); // Void Hashtable
		} catch (IOException e) {
			throw new RuntimeException("Cannot load dictionary : IOException",e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load dictionary : ClassNotFoundException",e);
		}
	}

	//-----------------------------------------------------------------------------
	private void saveDictionary() 
	{
		try {
			FileOutputStream fos = new FileOutputStream(dictionaryFile);
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(htDatabaseTables);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot save dictionary : FileNotFoundException",e);
		} catch (IOException e) {
			throw new RuntimeException("Cannot save dictionary : IOException",e);
		}
	}
	
	
    //-----------------------------------------------------------------------------
	private void checkDictionary(String method ) 
	{
	    if ( htDatabaseTables == null ) 
	    {
	        throw new RuntimeException( method + " : Database not initialized (dictionary is null)" );
	    }
	}
	
    //-----------------------------------------------------------------------------
	/**
	 * Loads all the tables in memory
	 */
	public void loadAllTables() 
	{
	    checkDictionary( "loadAllTables" );
	    
	    Enumeration<String> e = htDatabaseTables.keys();
	    while ( e.hasMoreElements() )
	    {
	        String tableName = (String) e.nextElement();
	        TinyTable table = (TinyTable) htDatabaseTables.get(tableName);
	        table.loadTable();
	    }
	}

    //-----------------------------------------------------------------------------
	/**
	 * Saves all the tables in the filesystem
	 */
	public void saveAllTables() 
	{
	    checkDictionary( "saveAllTables" );
	    
//	    Enumeration e = htDatabaseTables.keys();
//	    while ( e.hasMoreElements() )
//	    {
//	        String tableName = (String) e.nextElement();
//	        DbTable table = (DbTable) htDatabaseTables.get(tableName);
//	        table.saveTable();
//	    }
	    
	    Iterator<TinyTable> it = htDatabaseTables.values().iterator();
	    while ( it.hasNext() )
	    {
	        TinyTable table = (TinyTable) it.next();
	        table.saveTable();
	    }
	}
	
    //-----------------------------------------------------------------------------
//	public String getTableName(Class clazz)
//	{
//	    if ( clazz != null )
//	    {
//	        String sClassName = clazz.getName() ;
//	        // TODO : keep only the short name
//	        return sClassName + ".table" ;
//	    }
//	    throw new Exception("getTableName : class is null");
//	}

    //-----------------------------------------------------------------------------
	/**
	 * Returns all the table names registered in the database dictionary
	 * @return
	 */
	public String[] getTableNames() 
	{
	    checkDictionary( "getTableNames");
	    Set<String> keys = htDatabaseTables.keySet();
	    return (String[]) keys.toArray();
	}
	
    //-----------------------------------------------------------------------------
	/**
	 * Returns the table object for the given table name <br>
	 * If the table doesn't exist an exception is thrown<br>
	 * The table returned is ready to use (its records are loaded) 
	 * @param sTableName the table name
	 * @return
	 */
	public TinyTable getTable(String sTableName) 
	{
	    return getTable(sTableName, true ); 
	}
	
    //-----------------------------------------------------------------------------
	/**
	 * Returns the table object for the given table name (or null if the table is unknown and 'mustExist' flag = 'false')<br>
	 * If a table is returned, it is ready to use (its records are loaded) 
	 * @param sTableName the table name
	 * @param mustExist if true the table must exist (if it doesn't exist an exception is thrown), <br>
	 * if false a null value is returned if the table doesn't exist
	 * @return
	 */
	public TinyTable getTable(String sTableName, boolean mustExist ) 
	{
	    checkDictionary( "getTable(" + sTableName + ")");
	    if ( sTableName == null ) throw new RuntimeException("getTable : table name is null" ) ;
	    
		Object o = htDatabaseTables.get( sTableName ) ;
		if  ( o != null )
		{
		    if ( o instanceof TinyTable )
		    {
		        TinyTable table = (TinyTable)o ;
		        if ( table.isLoaded() != true ) // Records not yet loaded
		        {
			        table.loadTable();
		        }
		        return table ;
		    }
		    else
		    {
			    throw new RuntimeException("getTable : database corrupted : unexpected type for table '" + sTableName 
			            + "' ( " + o.getClass().getName() + " )" );
		    }
		}
	    else
	    {
	        if ( mustExist )
	        {
			    throw new RuntimeException("getTable : table '" + sTableName + "' unknown !" );
	        }
	        else
	        {
		        return null ;
	        }
	    }
	}

    //-----------------------------------------------------------------------------
	/**
	 * Creates a new table in the database
	 * @param sTableName the name of the table
	 * @param recordClass the class of the records stored in this table
	 * @return
	 */
	public TinyTable createTable(String sTableName, Class<?> keyClass, Class<?> recordClass ) 
	{
	    checkDictionary( "createTable(" + sTableName + ")");
	    if ( sTableName == null ) throw new RuntimeException("createTable : table name is null" ) ;
	    if ( recordClass == null ) throw new RuntimeException("createTable : record class is null" ) ;

	    //--- If the table exists in the dictionary and in the filesystem -> Error
	    TinyTable table = getTable( sTableName, false );
	    if ( table != null )
	    {
	        String fileName = table.getTableFile();
	        if ( fileName != null )
	        {
	            File file = new File(fileName);
	            if ( file.exists() )
	            {
	    		    throw new RuntimeException("createTable : table '" + sTableName + "' already exists" );
	            }
	        }
	    }
	    
        //--- Table file
        String sTableFile = buildTableFileName( sTableName )  ;

        
        //--- New table instance in the dictionary 
        table = new TinyTable( sTableName, keyClass, recordClass, sTableFile );
        htDatabaseTables.put(sTableName, table);
        
        saveDictionary();
        
        return table ;
	}
	
    //-----------------------------------------------------------------------------
	public boolean dropTable(String sTableName ) 
	{
	    checkDictionary( "dropTable(" + sTableName + ")");
	    if ( sTableName == null ) throw new RuntimeException("createTable : table name is null" ) ;

	    TinyTable table = getTable( sTableName, false);
	    if ( table != null )
	    {
		    //--- The table exists in the dictionary 
	        
	        //--- 1) Try to delete the file
	        String fileName = table.getTableFile();
	        deleteFile( fileName ) ;
	        
	        //--- 2) Remmove from the dictionary
	        htDatabaseTables.remove(sTableName);
	        saveDictionary();
	        
	        return true ;
	    }
	    else
	    {
		    //--- The table doesn't exist in the dictionary, but perhaps in the filesystem
	        
	        //--- Just try to delete the file
	        String fileName = buildTableFileName( sTableName )  ;
	        return deleteFile( fileName ) ;
	    }
	}
	
    //-----------------------------------------------------------------------------
	private String buildTableFileName( String sTableName ) 
	{
	    return databaseFolder + sTableName + ".table" ;
	}
	
    //-----------------------------------------------------------------------------
	private boolean deleteFile( String fileName ) 
	{
        if ( fileName != null )
        {
            File file = new File(fileName);
            if ( file.exists() )
            {
                return file.delete();
            }
        }
        return false ;
	}
}
