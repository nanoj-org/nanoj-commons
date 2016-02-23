package org.nanoj.util.tinydb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * This class represents one "table" of a "tiny database" <br>
 * 
 * A "table" is based on a Hashtable instance, it contains 0..N "records" associated with a "key", <br>
 * It provides standard entity persistance operations : insert, find, update, delete, insert or update <br>
 * A table doesn't store the record object itself but a copy, in order to avoid unwanted changes  
 * 
 * @author Laurent Guerin
 * @since v 1.0.3
 *
 */
public class TinyTable implements Serializable
{
	private static final long serialVersionUID = 1L;

	//--- The records of this table (records persistance is managed by the DbTable itself)
    private transient Hashtable<Object,Object> htRecords = null ; 
    
    private String       tableName   = null ;
    
    private Class<?>     recordClass = null ;
    
    private Class<?>     keyClass    = null ;
    
    private String       tableFile   = null ;
    
    /**
     * Constructs a table 
     * 
     * @param tableName the logical name of the table
     * @param keyClass the class of the key used to identify a record
     * @param recordClass the class of the records stored in the table
     * @param tableFile the file where the table is physically stored 
     * 
	 * @throws RuntimeException if one of the parameters is null
     */
    protected TinyTable( String tableName, Class<?> keyClass, Class<?> recordClass, String tableFile ) 
    {
        super();
        
        if ( tableName == null ) throw new RuntimeException("Constructor : table name is null");
        if ( keyClass == null ) throw new RuntimeException("Constructor : key class is null");
        if ( recordClass == null ) throw new RuntimeException("Constructor : record class is null");
        if ( tableFile == null ) throw new RuntimeException("Constructor : table file is null");
        
        this.tableName = tableName;
        this.keyClass = keyClass;
        this.recordClass = recordClass;
        this.tableFile = tableFile ;
        
        htRecords = new Hashtable<Object,Object>(); // Void Hashtable
    }
    
	/**
	 * Returns the logical name of this table
	 * @return
	 */
	public String getTableName() 
	{
	    return tableName ;
	}
	
	/**
	 * Returns the filename of this table (filesystem file path)
	 * @return
	 */
	public String getTableFile() 
	{
	    return tableFile ;
	}
	
	/**
	 * Returns the class of the keys for this table
	 * @return
	 */
	public Class<?> getKeyClass() 
	{
	    return keyClass ;
	}

	/**
	 * Returns the class of the records stored in this table
	 * @return
	 */
	public Class<?> getRecordClass() 
	{
	    return recordClass ;
	}
	
	/**
	 * Return true if the table records have been loaded (at least on time) from the file
	 * @return
	 */
	public boolean isLoaded() 
	{
	    return htRecords != null  ;
	}
	
	/**
	 * Loads (or reloads) the table records from the file
	 * @return the Hashtable loaded (never null)
	 * @throws RuntimeException if FileSystem I/O error
	 */
	@SuppressWarnings("unchecked")
	public synchronized Hashtable<Object,Object> loadTable() 
	{
		Hashtable<Object,Object> ht = null ;
		try {
			FileInputStream fis = new FileInputStream(tableFile);
			
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Unchecked cast
			htRecords = (Hashtable<Object,Object>) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// throw new DatabaseException("Cannot load : FileNotFoundException",e);
		    htRecords = new Hashtable<Object,Object>(); // Void Hashtable
		} catch (IOException e) {
			throw new RuntimeException("Cannot load : IOException",e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load : ClassNotFoundException",e);
		}
		return ht ;
	}
	
	/**
	 * Saves the table records in the file
	 * 
	 * @throws RuntimeException if FileSystem I/O error
	 */
	public synchronized void saveTable() 
	{
		try {
			FileOutputStream fos = new FileOutputStream(tableFile);
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(htRecords);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot save : FileNotFoundException",e);
		} catch (IOException e) {
			throw new RuntimeException("Cannot save : IOException",e);
		}
	}
    
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Deep copy of a record
	 * @param record
	 * @return
	 */
	private Object copy ( Object record )
	{
	    try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ObjectOutputStream oos = new ObjectOutputStream (baos);
            oos.writeObject (record);
            
            ByteArrayInputStream bais = new ByteArrayInputStream ( baos.toByteArray () );
            ObjectInputStream ois = new ObjectInputStream (bais);
            return ois.readObject ();
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Cannot copy record " + record.getClass() + " via serialization " );
        }
	}
	
	//---------------------------------------------------------------------------------------------------------
	// RECORDS MANAGEMENT
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * Finds the record associated with the given key <br>
	 * @param key the key (must not be null )
	 * @return the record found, or null if there's no record for this key
	 * @throws RuntimeException if an error occurs
	 */
	public Object findRecord(Object key) 
	{
	    check("find", key);
        
	    Object record = htRecords.get(key);
	    if ( record != null )
	    {
		    //--- Return a copy of the record
	        return copy ( record );
	    }
	    else
	    {
	        return null ;
	    }
	}

	//---------------------------------------------------------------------------------------------------------
	/**
	 * Inserts or update the given record associated with the given key <br>
	 * If a record exists for the key it is updated, else it is inserted 
	 * @param key the key (must not be null )
	 * @param record
	 * @throws RuntimeException if an error occurs
	 */
	public void insertOrUpdateRecord(Object key, Object record ) 
	{
	    check("insert or update", key, record);
        
	    //--- Store a copy of the given key+record
	    Object key2    = copy ( key ); // Is it realy useful for the key ?
	    Object record2 = copy ( record );
	    
	    htRecords.put(key2, record2);
	}
	
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Inserts the given record associated with the given key <br>
	 * If a record already exists for this key, a "duplicate key" exception if throwned
	 * @param key the key (must not be null )
	 * @param record
	 * @throws RuntimeException if an error occurs or if duplicate key
	 */
	public void insertRecord(Object key, Object record ) 
	{        
	    check("insert", key, record);
	    if ( htRecords.get(key) == null )
	    {
		    //--- Store a copy of the given key+record
		    Object key2    = copy ( key ); // Is it realy useful for the key ?
		    Object record2 = copy ( record );
		    
		    htRecords.put(key2, record2);
	    }
	    else
	    {
	        throw new RuntimeException("Cannot insert record (" + recordClass + ") : duplicate key");
	    }
	}
	
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Updates the record associated with the given key <br>
	 * If there's no record for this key nothing is updated (it's not an error)
	 * @param key the key (must not be null )
	 * @param record
	 * @return true if an existing record was updated, false if there's no record for the given key
	 * @throws RuntimeException if an error occurs
	 */
	public boolean updateRecord(Object key, Object record ) 
	{        
	    check("update", key, record);
	    if ( htRecords.get(key) != null )
	    {
		    //--- Store a copy of the given key+record
		    Object key2    = copy ( key ); // Is it realy useful for the key ?
		    Object record2 = copy ( record );
		    
		    htRecords.put(key2, record2);
		    return true ;
	    }
	    else
	    {
	        return false ;
	    }
	}
	
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Deletes the record associated with the given key <br>
	 * If there's no record for this key nothing is deleted (it's not an error)
	 * @param key the key (must not be null )
	 * @return true if a record was found and deleted, false if there's no record for the given key
	 * @throws RuntimeException if an error occurs
	 */
	public boolean deleteRecord(Object key) 
	{
	    check("delete", key);
	    Object o = htRecords.remove(key);
	    return ( o != null ) ;
	}

	//---------------------------------------------------------------------------------------------------------
	private void check (String operation, Object key ) 
	{
	    if ( htRecords == null ) throw new RuntimeException("Cannot " + operation + " record : table not loaded");
	    if ( key == null ) throw new RuntimeException("Cannot " + operation + " record : key is null");
	    if ( ! keyClass.isInstance( key ) ) throw new RuntimeException("Cannot " + operation + " record : key is not an instance of " + keyClass );
	}

	//---------------------------------------------------------------------------------------------------------
	private void check (String operation, Object key, Object record ) 
	{
	    check ( operation, key );
	    if ( record == null )  throw new RuntimeException("Cannot " + operation + " record : record is null");
	    if ( ! recordClass.isInstance( record ) ) throw new RuntimeException("Cannot " + operation + " record : record is not an instance of " + recordClass );
	}
	//---------------------------------------------------------------------------------------------------------
}
