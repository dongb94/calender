package fileSystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FileManager {
		
	FTPClient ftpClient = new FTPClient();
	//dir reference
	String workPath = "/";
	//connecting information
	String hostName = "168.131.153.176";
	String ID = "superuser";
	String Password = "team1";
	
	FileManager(){
		try {
			
			FTPClientConfig config = new FTPClientConfig();  
			config.setServerLanguageCode("ko");
			config.setDefaultDateFormatStr("MM월 d일 HH:mm");
			config.setRecentDateFormatStr("MM월 d일 HH:mm");
			ftpClient.configure(config);
			
			ftpClient.connect(hostName);
			ftpClient.setControlEncoding("utf-8");

			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				System.out.println("FTP서버 연결 실패. 서버에서 연결을 거부함");
			} else {
				System.out.print(ftpClient.getReplyString());
				
				ftpClient.setSoTimeout(5000);
				ftpClient.login(ID, Password);
		    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			}
			
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**FTP서버와 연결을 끊는다. 프로그램 마지막에 실행*/
	public void FTPDisconnect(){
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**FTP서버와의 연결을 새로고침*/
	public void FTPUpdate(){
		FTPFile[] ftpfiles = null;
		try {
			ftpfiles = ftpClient.listFiles("/");
		} catch (IOException e) {
			e.printStackTrace();
		}  // public 폴더의 모든 파일을 list 합니다
        if (ftpfiles != null) {
            for (int i = 0; i < ftpfiles.length; i++) {
                FTPFile file = ftpfiles[i];
                if(file.isDirectory())
                	System.out.print("d");
                System.out.println("\t"+file.getName());
                //System.out.println(file.toString());
            }
        }
	}
	/**FTP 작업 디렉토리 변경
	 * FTPCd(작업 경로)
	 *  성공 1 실패 0*/
	public int FTPCd(String path){
		try {
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**FTP서버에 디렉토리 생성 
	 * FTPMkdir(생성할 디렉토리 경로) 
	 *  성공 1 실패 0*/
	public int FTPMkdir(String path){
		try {
			ftpClient.makeDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**FTP서버에 파일 업로드
	 * FTPUpload(업로드 상대경로, 업로드 파일 path1, path2, ...)
	 * 성공 1 실패 0*/
	public int FTPUpload(String upload_Folder, String... path){
	    try {
	    	FTPMkdir(upload_Folder);
	    	
	    	BufferedInputStream bis = null;
	    	InputStream inputStream = null;
	    	for(int i=0; i<path.length; i++){
	    		
				File put_file = new File(path[i]);
				if(put_file.isDirectory()){
					String[] s =put_file.list();
					for(int j=0; j<s.length; j++)
						FTPUpload(upload_Folder+"/"+put_file.getName(),path[i]+"/"+s[j]);
				}else{
					String FileName = path[i].substring(path[i].lastIndexOf("/"));
					inputStream = new FileInputStream(put_file);
					bis = new BufferedInputStream(inputStream);
				    boolean result = ftpClient.storeFile(upload_Folder+FileName, bis);

				    inputStream.close();
				    bis.close();
				}
	    	}
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	/**FTP서버에서 파일 다운로드 
	 * FTPDownload(다운로드path, 받을파일1, 받을파일2, ...) 
	 *  성공 1 실패 0*/
	public int FTPDownload(String download_Folder, String... path){
		try {
			File get_file = new File(download_Folder);
			get_file.mkdirs();
			
			BufferedOutputStream bos = null;
			OutputStream outputStream = null;
			for(int i=0; i<path.length; i++){
				
				FTPFile[] files = ftpClient.listFiles(path[i]);
				
				if(ftpClient.changeWorkingDirectory("./"+path[i])){
					if(files.length==0){
						get_file = new File(download_Folder+"/"+path[i]);
						get_file.mkdir();
					}else{
						for(int j=0; j<files.length; j++){
							FTPFile f = files[j];
							FTPDownload(download_Folder+"/"+path[i],files[j].getName());
						}
					}
					ftpClient.changeWorkingDirectory("../");
				}else{
					get_file = new File(download_Folder+"/"+path[i]);
				    outputStream = new FileOutputStream(get_file);
				    bos = new BufferedOutputStream(outputStream);
				    boolean result = ftpClient.retrieveFile(path[i], bos);
				    outputStream.flush();
				    bos.flush();
				    outputStream.close();
					bos.close();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return 0;		
		}
	   
		return 1;
	}
	public static void main(String[] args) {
		//단위 테스트용 main클래스
		FileManager fm = new FileManager();
		
		fm.FTPUpdate();
//		fm.FTPUpload("/a", "C:/Users/BDG/AppData/Local/file_client_download_root");
		System.out.println(fm.FTPDownload("C:/Users/BDG/Desktop/새폴더", "a"));
//		fm.FTPMkdir("/testDir2/subdir");
		fm.FTPDisconnect();
	}
}
