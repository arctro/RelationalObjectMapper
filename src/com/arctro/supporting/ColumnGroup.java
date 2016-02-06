package com.arctro.supporting;

/**
 * Holds columns and information about them
 * @author Ben McLean
 * @version 1.0
 */
public class ColumnGroup {
	public Column[] columns = null;
	
	public boolean all = false;
	
	/**
	 * Construct a column group selecting specific columns
	 * @param columns The columns to select
	 */
	public ColumnGroup(Column[] columns){
		this.columns = columns;
	}
	
	/**
	 * Construct a column group selecting all columns
	 */
	public ColumnGroup(){
		all = true;
	}
	
	/**
	 * Returns if all columns are to be selected
	 * @return If all columns are to be selected
	 */
	public boolean isAll(){
		return all;
	}
	
	/**
	 * Returns specific columns to select. returns null if all columns are to be selected.
	 * @return Specific columns to select.
	 */
	public Column[] getColumns(){
		return columns;
	}
}
