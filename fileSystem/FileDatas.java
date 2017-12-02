package fileSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**파일 정보를 담고있는 클래스
 * 생성자를 통해 초기화
 * file[]배열을 이용해서 정보 접근*/
public class FileDatas{
	
	public FileData file[];
	String path;
	Connection conn = DataBase.conn;
	
	/**공백으로 초기화*/
	FileDatas(){}
	
	/**파일 경로로 초기화*/
	FileDatas(String dir_path){
		this.path = DataBase.Directory_Path_Arrangment(dir_path);
		
		try {
			PreparedStatement pst = conn.prepareStatement("select count(*) from file where path='"+path+"'");
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				file = new FileData[count];
			}
			
			pst = conn.prepareStatement("select name,favor,type,album,date,thumnail from file where path='"+path+"' ORDER BY date DESC");
			rs = pst.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				String name = rs.getString(1);
				int fv = rs.getInt(2);
				String type = rs.getString(3);
				int img = rs.getInt(4);
				long date = rs.getTimestamp(5).getTime();
				ImageIcon thumnail=null;
				if(type.equals("img")) thumnail = new ImageIcon(rs.getBytes(6));
				file[i++]=new FileData(path, name, fv, type, img, date, thumnail);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**엘범명으로 초기화*/
	void getAlbumFiles(String album){
		try {
			PreparedStatement pst = conn.prepareStatement("select sid from album where name='"+album+"'");
			ResultSet rs = pst.executeQuery();
			int album_num = 0;
			if(rs.next()){
				album_num = rs.getInt(1);
			}
			
			pst = conn.prepareStatement("select count(*) from file where album='"+album_num+"'");
			rs = pst.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				file = new FileData[count];
			}
			
			pst = conn.prepareStatement("select name,favor,type,path,date,thumnail from file where album='"+album_num+"' ORDER BY date DESC");
			rs = pst.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				String name = rs.getString(1);
				int fv = rs.getInt(2);
				String type = rs.getString(3);
				String path = rs.getString(4);
				long date = rs.getTimestamp(5).getTime();
				ImageIcon thumnail=null;
				if(type.equals("img")) thumnail = new ImageIcon(rs.getBytes(6));
				file[i++]=new FileData(path, name, fv, type, album_num, date, thumnail);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**즐겨찾기로 초기화*/
	void getFavoriteFiles(){
		try {
			PreparedStatement pst = conn.prepareStatement("select count(*) from file where favor=1");
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				file = new FileData[count];
			}
			
			pst = conn.prepareStatement("select name,path,type,album,date,thumnail from file where favor=1 ORDER BY date DESC");
			rs = pst.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				String name = rs.getString(1);
				String path = rs.getString(2);
				String type = rs.getString(3);
				int img = rs.getInt(4);
				long date = rs.getTimestamp(5).getTime();
				ImageIcon thumnail=null;
				if(type.equals("img")) thumnail = new ImageIcon(rs.getBytes(6));
				file[i++]=new FileData(path, name, 1, type, img, date, thumnail);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	FileData[] getFileDatas(){
		return file;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//단위 테스트용 main클래스
		new DataBase();
		
//		FileData fd = new FileData("/test", "/20160722_053051.jpg");
//		System.out.println("date = " + fd.date);
//		System.out.println("favor = " + fd.favor);
//		System.out.println("img = " + fd.img);
//		System.out.println("dir = " + fd.dir);
//		System.out.println("vid = " + fd.vid);
		
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//		frame.setSize(200, 200);
//		JLabel l = new JLabel(fd.thumnail);
//		frame.add(l);
//		frame.setVisible(true);
		
		System.out.println("------------------------------");
		FileDatas fds = new FileDatas("");

		System.out.println("name = " + fds.file[0].name);
		System.out.println("path = " + fds.file[0].path);
		System.out.println("date = " + fds.file[0].date);
		System.out.println("favor = " + fds.file[0].favor);
		System.out.println("img = " + fds.file[0].img);
		System.out.println("dir = " + fds.file[0].dir);
		System.out.println("vid = " + fds.file[0].vid);
		System.out.println("------------------------------");

		FileDatas album = new FileDatas();
		album.getAlbumFiles("dog");
		
		System.out.println("name = " + album.file[1].name);
		System.out.println("path = " + album.file[1].path);
		System.out.println("date = " + album.file[0].date);
		System.out.println("favor = " + album.file[0].favor);
		System.out.println("img = " + album.file[0].img);
		System.out.println("dir = " + album.file[0].dir);
		System.out.println("vid = " + album.file[0].vid);
		System.out.println("------------------------------");
		
		FileDatas favor = new FileDatas();
		favor.getFavoriteFiles();
		
		System.out.println("name = " + favor.file[0].name);
		System.out.println("path = " + favor.file[0].path);
		System.out.println("date = " + favor.file[0].date);
		System.out.println("favor = " + favor.file[0].favor);
		System.out.println("img = " + favor.file[0].img);
		System.out.println("dir = " + favor.file[0].dir);
		System.out.println("vid = " + favor.file[0].vid);
		System.out.println("------------------------------");
	}
}

class FileData{
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
	FileData(String path,String name, int favor, String type, int album, long date, ImageIcon thumnail){
		this.path = path;
		this.name = name;
		img = album;
		this.date = date;
		
		if(favor==1) this.favor=true;
		switch(type){
		case "dir":
			dir=true;
			name = path.substring(path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/"));
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
			this.thumnail = thumnail;
		}
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
					name = path.substring(path.lastIndexOf("/"));
					path = path.substring(0, path.lastIndexOf("/"));
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
	
	
}
