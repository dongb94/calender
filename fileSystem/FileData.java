package fileSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FileData{
	String name;
	String path;
	long date;
	boolean favor=false;
	boolean dir=false;
	boolean vid=false;
	boolean msc=false;
	boolean dcm=false;
	int img;	//-1 = not img, 0 = img without album, 1~ = img in 1~th album
	ImageIcon thumnail;
	
	Connection conn = DataBase.conn;
	
	FileData(String path,String name){
		this.path = path;
		this.name = name;
		checkFile();
	}
	
	private void checkFile(){
		try {
			PreparedStatement pst = conn.prepareStatement("select favor,type,album,date,thumnail from file where name='"+name+"'&&path='"+path+"'");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int fv = rs.getInt(1);
				String type = rs.getString(2);
				img = rs.getInt(3);
				date = rs.getTimestamp(4).getTime();
				
				if(fv==1) favor=true;
				
				switch(type){
				case "dir":
					dir=true;
					break;
				case "vid":
					vid=true;
					break;
				case "msc":
					msc=true;
					break;
				case "dcm":
					dcm=true;
					break;
				case "img":
					thumnail = new ImageIcon(rs.getBytes(5));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//단위 테스트용 main클래스
		new DataBase();
		
		FileData fd = new FileData("", "/Angdorotty.png");
		System.out.println("date = " + fd.date);
		System.out.println("favor = " + fd.favor);
		System.out.println("img = " + fd.img);
		System.out.println("dir = " + fd.dir);
		System.out.println("vid = " + fd.vid);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		JLabel l = new JLabel(fd.thumnail);
		frame.add(l);
		frame.setVisible(true);
		
		FileData fd2 = new FileData("/main", "");
		System.out.println("date = " + fd2.date);
		System.out.println("favor = " + fd2.favor);
		System.out.println("img = " + fd2.img);
		System.out.println("dir = " + fd2.dir);
		System.out.println("vid = " + fd2.vid);
	}
}
