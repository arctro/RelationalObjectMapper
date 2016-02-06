package com.arctro.supporting;

/**
 * A class for holding column data
 * @author Ben McLean
 * @version 1.0
 */
public class Column {
	String table;
	String column;
	
	/**
	 * Create a foreign column object
	 * @param table The table name
	 * @param column The column name
	 */
	public Column(String table, String column) {
		this.table = table;
		this.column = column;
	}

	/**
	 * Create a non-foreign column object
	 * @param column The column name
	 */
	public Column(String column) {
		this.column = column;
	}

	/**
	 * Returns the column's tablename. Is null if the
	 * column is not foreign
	 * @return The column's tablename
	 */
	public String getTable() {
		return table;
	}

	/**
	 * Returns the column name.
	 * @return The column name
	 */
	public String getColumn() {
		return column;
	}
	
	/**
	 * Gets the full column table and name.
	 * @return The full column table and name.
	 */
	public String get(){
		if(table == null){
			return column;
		}
		return table + "." + column;
	}
	
	/**
	 * Returns if the column is foreign
	 * @return If the column is foreign
	 */
	public boolean isForeign(){
		return (table != null);
	}
}
