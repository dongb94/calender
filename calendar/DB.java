package calendar;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DB {
	Schedule sch;
	static Connection conn;
	
	public DB() {
		
	}
	
	public DB(Schedule sch) {
		this.sch = sch;
	}
	public static void getInfo() throws Exception {
		String url = "jdbc:mysql://168.131.153.176:3306/calendar";
		String dbid = "superuser";
		String dbpw = "1234";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);
	}
	
	 public static int addDaySchedule(Schedule sch) throws Exception {
			getInfo();
			PreparedStatement pst = conn.prepareStatement("insert into Calendar values(?,?,?)");
			pst.setString(1, sch.getTitle());
			pst.setString(2, sch.getDate());
			pst.setString(3, sch.getContents());
			int cnt = pst.executeUpdate();
			return cnt;// 리턴값 int인 이유는 추가 되었을떄 cnt>0 이상일경우에 디비에 추가가 성공됨을 알 수 있다.
		}
}
