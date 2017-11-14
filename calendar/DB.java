package calendar;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DB {
	Schedule sch;
<<<<<<< HEAD

=======
	
	public DB() {
		
	}
	
>>>>>>> 653a93ea0f68d918ed718bc723135880dac46cdd
	public DB(Schedule sch) {
		this.sch = sch;
	}

	public static void ADDWEBDB(Schedule sch) throws Exception {
		String url = String.format(
				"http://192.168.35.50:8083/DataBaseProject/WindowPro.jsp?date=%s&title=%s&contents=%s", sch.getDate(),
				sch.getTitle(), sch.getContents());
		AsyncDownThread thread = new AsyncDownThread(url);
		thread.start();
	}
	
	public static void getData() throws Exception{
		String url = "http://192.168.35.50:8083/DataBaseProject/WindowPro.jsp?";
		AsyncDownThread thread2 = new AsyncDownThread(url);
		thread2.start();

	}
}
