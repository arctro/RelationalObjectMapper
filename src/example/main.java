package example;

import java.util.HashMap;

import com.arctro.supporting.Column;
import com.arctro.supporting.ColumnGroup;
import com.arctro.supporting.Structure;

public class main {

	public static void main(String[] args) {
		ColumnGroup columnGroup = new ColumnGroup(new Column[]{EStructure.id, EStructure.user, EStructure.username, EStructure.threadTitle});
		System.out.println(generateSelect(columnGroup));
	}
	
	/**
	 * Generates a select statement. Do not allow user inputed
	 * columns, as column tables or names are not sanitized and
	 * result in a huge security hole when improperly handled. 
	 * @param columns The columns to select
	 * @return A select statement
	 */
	public static String generateSelect(ColumnGroup columns){
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
		
		return "SELECT " + cts + " FROM `test` " + ((dj) ? j : "") + " ";
	}

	public static Structure getStructure(){
		return new EStructure();
	}
	
	public static class EStructure implements Structure{
		
		public static Column id = new Column("id");
		public static Column user = new Column("user_id");
		public static Column thread = new Column("thread_id");
		
		public static Column username = new Column("users", "id");
		public static Column threadTitle = new Column("threads", "title");
		
		HashMap<String, Column> columns;
		HashMap<String, Column> foreign;
		
		HashMap<String, Column> linking;
		
		public EStructure(){
			columns = new HashMap<String, Column>();
			foreign = new HashMap<String, Column>();
			linking = new HashMap<String, Column>();
			
			columns.put(id.getColumn(), id);
			columns.put(user.getColumn(), user);
			columns.put(thread.getColumn(), thread);
			
			foreign.put(username.getColumn(), username);
			foreign.put(threadTitle.getColumn(), threadTitle);
			
			linking.put("users", user);
			linking.put("threads", threadTitle);
		}

		public int count() {
			return columns.size();
		}

		public int foreignCount() {
			return foreign.size();
		}

		public Column getColumn(String name) {
			return columns.get(name);
		}

		public Column getForignColumn(String name) {
			return foreign.get(name);
		}

		public Column getJoiningColumn(String table) {
			return linking.get(table);
		}
	}
}
