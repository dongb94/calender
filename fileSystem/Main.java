package fileSystem;

import java.sql.SQLException;

public class Main {
	
	public static void main(String args){
		try {
			new DataBase();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
