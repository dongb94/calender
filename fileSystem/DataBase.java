package fileSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class DataBase {
	public static Connection conn;
	DataBase() throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://168.131.153.176:3306/filesystem";
		String dbid = "superuser";
		String dbpw = "1234";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);
	}
	/**파일의 종류를 판별해주는 함수
	 * ExtensionDetermination(파일명)
	 * @return String filetype*/
	public static String ExtensionDetermination (String fileName){

		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		extension = extension.toUpperCase();
		switch(extension){
		case "BMP":
		case "RLE":
		case "JPG":
		case "GIF":
		case "PNG":
			// 그 외  PSD,PDD,TIF,PDF,PAW,AI,EPS,SVG,SVGZ 등
			return "img";
		case "MP4":
		case "AVI":
		case "MPG":
		case "MPEG":
		case "MPE":
		case "WMV":
		case "ASF":
		case "ASX"://목록파일
		case "MKV":	
			// 그 외 RM,MOV,DAT 등
			return "vid";
		case "WAV":
		case "MP3":
		case "M4A":
		case "WMA":
		case "AU":
		case "AAC":
			// 그 외 등등
			return "msc";
		default ://문서 파일
			return "dcm";
		}
	}
	
	/**경로명을 정리해주는 함수 (/a/b/c.jpg)
	 * Directory_Path_Arrangment(path)
	 * @return String 정리된 경로명*/
	public static String Directory_Path_Arrangment (String path){
		if(path.startsWith("/")){
			while(path.startsWith("/")) path = path.substring(1);
		}
		path = "/"+path;
		
		if(path.endsWith("/"))
			path = path.substring(0, path.length()-1);
		
		return path;
		
	}

}
