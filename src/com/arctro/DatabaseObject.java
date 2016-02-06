package com.arctro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.arctro.supporting.Column;
import com.arctro.supporting.ColumnGroup;
import com.arctro.supporting.Structure;

/**
 * An abstract class designed for mapping database values to an object
 * @author Ben McLean
 * @version 1.0
 */
public abstract class DatabaseObject {
	
	/**
	 * Holds object values
	 */
	protected HashMap<String, Object> data;
	
	/**
	 * An empty constructor, intended for use with a constructor that uses direct variable input
	 */
	public DatabaseObject(){data = new HashMap<String, Object>();}
	
	/**
	 * Generates a select statement. Do not allow user inputed
	 * columns, as column tables or names are not sanitized and
	 * result in a huge security hole when improperly handled. 
	 * @param columns The columns to select
	 * @return A select statement
	 */
	protected String generateSelect(ColumnGroup columns){
		String cts = " * ", j = " JOIN ";
		boolean dj = false;
		
		if(!columns.isAll()){
			cts="";
			
			Column[] c = columns.getColumns();
			int s = c.length;
			
			for(int i=0; i<s; i++){
				cts+=" `"+c[i].get()+"` ,";
				
				if(c[i].isForeign()){
					dj = true;
					
					String t = c[i].getTable();
					j+=" `" + t + "` USING(`" + getStructure().getJoiningColumn(t).get() + "`) ,";
				}
			}
			
			cts = cts.substring(0, cts.length() - 1);
			j = j.substring(0, j.length() - 1);
		}
		
		return "SELECT " + cts + " FROM `" + getTablename() + "` " + ((dj) ? j : "") + " ";
	}
	
	/**
	 * Returns the structure of the table
	 * @return The structure of the table
	 */
	protected abstract Structure getStructure();
	/**
	 * Returns the tablename
	 * @return The tablename
	 */
	protected abstract String getTablename();
	
	/**
	 * Maps values from a result set to the data <tt>HashMap</tt>.<br/>
	 * It is suggested that the function be written similarly to this:
	 * <pre><code>
	 * 	if(columns.isAll()){
	 * 		&lt;Set Data&gt;
	 * 		return;
	 * 	}
	 * 
	 * 	Column[] c = columns.getColumns();
	 * 	int s = c.length;
	 * 
	 * 	for(int i = 0; i&lt;s; i++;){
	 * 		switch(c.getColumn()){
	 * 		case("&lt;Example&gt;"){
	 * 			data.set("&lt;Example&gt;", &lt;Get Variable&gt;);
	 * 			break;
	 * 		}
	 * 		etc...
	 * 		}
	 * 	}
	 * </code></pre>
	 * @param rs The result set to get values from
	 * @param columns The columns that have been selected
	 */
	protected abstract void mapValues(ResultSet rs, ColumnGroup columns) throws SQLException;
	
	/**
	 * Gets a value. It is recommended that this generic function is
	 * replaced with a getter for every possible variable, for
	 * robustness and ease of use.<br/>
	 * A typical function would look similar to this:<br/>
	 * <pre><code>
	 * 	public String getUsername(){
	 * 		return (String) data.get("username");
	 * 	}
	 * </code></pre>
	 * @param key The key to get
	 * @return A value
	 */
	public Object get(String key){
		return data.get(key);
	}
	
	/**
	 * Sets a value. It is recommended that this generic function is
	 * replaced with a setter for every possible variable, for
	 * robustness and ease of use.<br/>
	 * A typical function would look similar to this:<br/>
	 * <pre><code>
	 * 	public void setUsername(String value){
	 * 		data.set("username", value);
	 * 	}
	 * </code></pre>
	 * @param key The key to set
	 * @param value The value to set
	 */
	public void put(String key, Object value){
		data.put(key, value);
	}
	
	/**
	 * Returns if a key/value is null or not
	 * @param key The key to check
	 * @return If a key/value is null or not
	 */
	public boolean set(String key){
		return (data.get(key) != null);
	}
}
