package fileSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.net.ftp.FTPFile;

public class FileData{
	FTPFile file;
	boolean favor;
	boolean dir;
	boolean vid;
	boolean msc;
	boolean dcm;
	int img;
	
	Connection conn = DataBase.conn;
	
	FileData(FTPFile file){
		this.file = file;
	}
	
	private void checkFile(){
		//check directory
		if(file.isDirectory()){
			dir = true;
			return;
		}else
			dir = false;
		
		try {
			PreparedStatement pst = conn.prepareStatement("select * from file");
			ResultSet rs = pst.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
