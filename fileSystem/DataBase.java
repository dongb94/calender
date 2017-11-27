package fileSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	public static Connection conn;
	DataBase() throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://168.131.153.176:3306/filesystem";
		String dbid = "superuser";
		String dbpw = "1234";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);
	}
}
