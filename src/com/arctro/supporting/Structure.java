package com.arctro.supporting;

/**
 * An object designed to describe the structure of a database.
 * @author Ben McLean
 * @version 1.0
 */
public interface Structure {
	
	/**
	 * Returns the number of non-foreign columns.
	 * @return The number of non-foreign columns.
	 */
	public int count();
	
	/**
	 * Returns the number of foreign columns.
	 * @return The number of foreign columns.
	 */
	public int foreignCount();
	
	/**
	 * Get a column by index
	 * @param name The column name  
	 * @return The column
	 */
	public Column getColumn(String name);
	
	/**
	 * Get a foreign column by index
	 * @param name The column name 
	 * @return The column
	 */
	public Column getForignColumn(String name);
	
	/**
	 * Returns the joining column for a foreign table
	 * @param table The table to get the joining row of
	 * @return The column that joins the tables
	 */
	public Column getJoiningColumn(String table);
}
