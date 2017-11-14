package calendar;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DB {
	Schedule sch;
	static Connection conn;
	public static String result;

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

	// 2017.11.14:8.28
	public static ArrayList<Schedule> getDaySchedule() throws Exception {
		getInfo();

		ArrayList<Schedule> sch_list = new ArrayList<>();
		PreparedStatement pst = conn.prepareStatement("select * from Calendar");
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String date = rs.getString(1);
			String title = rs.getString(2);
			String contents = rs.getString(3);
			sch_list.add(new Schedule(title, date, contents));
		}
		return sch_list;

	}

	public static void updateSchedule(Schedule sch) throws Exception {
		getInfo();
		ArrayList<Schedule> schedule = new ArrayList<>();
		schedule = getDaySchedule();
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getTitle().equals(sch.getTitle())) {
				i = schedule.size() - 1;
				// update SMART_11 set id ='sunshine' where id ='sun';
				PreparedStatement pst = conn.prepareStatement("update calendar set day =? where day =? ");
				pst.setString(1, sch.getDate());
				pst.setString(2, schedule.get(i).getDate());
				PreparedStatement pst1 = conn.prepareStatement("update calendar set title =? where title =? ");
				pst.setString(1, sch.getTitle());
				pst.setString(2, schedule.get(i).getTitle());
				PreparedStatement pst2 = conn
						.prepareStatement("update calendar set contents =? where contents =?");
				pst.setString(1, sch.getContents());
				pst.setString(2, schedule.get(i).getContents());

				int cnt = pst.executeUpdate();
				pst1.executeUpdate();
				pst2.executeUpdate();
			}
		}
	}

	public static void deleteSchedule(Schedule sch) throws Exception {
		getInfo();
		ArrayList<Schedule> schedule = new ArrayList<>();
		schedule = getDaySchedule();
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getTitle().equals(sch.getTitle())) {
				i = schedule.size() - 1;
				PreparedStatement pst = conn.prepareStatement("delete calendar where title=?");
				pst.setString(1, sch.getTitle());

			}
		}
	}

}
