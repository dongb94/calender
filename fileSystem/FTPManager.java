package fileSystem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.*;
import javax.swing.JFrame;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPManager {
		
	FTPClient ftpClient = new FTPClient();
	//dir reference
	String workPath = "/";
	//connecting information
	String hostName = "168.131.153.176";
	String ID = "superuser";
	String Password = "team1";
	
	//loading image
//	JDialog dialog;
	FTPManager(JFrame frame){
		try {
			FTPClientConfig config = new FTPClientConfig();  
			config.setServerLanguageCode("ko");
			ftpClient.configure(config);
			
			ftpClient.connect(hostName);
			ftpClient.setControlEncoding("utf-8");

			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				System.out.println("FTP서버 연결 실패. 서버에서 연결을 거부함");
			} else {
				String replyString[] = ftpClient.getReplyString().split("\n");
				for(int i=0; i<replyString.length; i++)
					System.out.print(replyString[i].substring(4));
				System.out.println();
				
				ftpClient.setSoTimeout(5000);
				ftpClient.login(ID, Password);
		    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			}
			
//			dialog = new JDialog(frame,true);
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
	/**FTP서버의 파일 목록을 가져옴
	 * FTPGetFileList(가져올 파일 path)
	 * @return FTPFile[]*/
	public FileDatas[] FTPGetFileData(String path){
		
		return null;
	}
	/**FTP서버의 파일 목록을 가져옴
	 * FTPGetFileList(가져올 파일 path)
	 * @return FTPFile[]*/
	public FTPFile[] FTPGetFileList(String path){
		FTPFile[] ftpfiles = null;
		try {
			path = workPath+DataBase.Directory_Path_Arrangment(path);
			ftpfiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}  // public 폴더의 모든 파일을 list 합니다
        if (ftpfiles != null) {
            System.out.println("\tFile Size\tFile Name");
            for (int i = 0; i < ftpfiles.length; i++) {
                FTPFile file = ftpfiles[i];
                if(file.isDirectory())
                	System.out.print("dir");
                System.out.print("\t"+file.getSize());
                System.out.println("\t\t"+file.getName());
                
            }
        }
        return ftpfiles;
	}
	/**FTP 작업 디렉토리 변경
	 * FTPCd(작업 절대 경로)
	 * @return 작업path*/
	public String FTPCd(String path){
		try {
			path = DataBase.Directory_Path_Arrangment(path);
			if(path.equals("")) path = "/";
			workPath = path;
			ftpClient.changeWorkingDirectory(path);
	    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workPath;
	}
	/**FTP서버에 디렉토리 생성 
	 * FTPMkdir(생성할 디렉토리 경로) 
	 *  성공 1 실패 0*/
	public int FTPMkdir(String path){
		try {
			path = DataBase.Directory_Path_Arrangment(path);
			if(!workPath.equals("/"))
				path = workPath + path;
			
			ftpClient.makeDirectory(path);
			
			String name = "";
			if(!path.equals("")) {
				name = path.substring(path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/"));
			} else {
				return 1;
			}
			
			PreparedStatement pst = DataBase.conn.prepareStatement("insert into file values(?,?,?,?,?,?,?)");
		    pst.setString(1, name);
			pst.setString(2, path);
			pst.setInt(3, 0);
			pst.setInt(4, -1);
			pst.setTimestamp(5, new Timestamp(new Date().getTime()));
			pst.setString(6, "dir");
			pst.setString(7, null);
			pst.executeUpdate();
			
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		} catch (SQLException e) {
			System.err.println("이미 있는 디렉토리 "+path);
		}
		return 1;
	}
	
	/**FTP서버에 파일 업로드
	 * FTPUpload(업로드 상대경로, 업로드 파일 path1, path2, ...)
	 * 성공 1 실패 0*/
	public void FTPUpload(String upload_Folder, String... path){
		Upload pr=new Upload(upload_Folder, path);
		Thread th = new Thread(pr);
		th.start();
		
//		new Thread(new Loding()).start();

		synchronized (th) {
			try {
				th.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			dialog.dispose();
		}
	}
	/**FTP서버에서 파일 다운로드 
	 * FTPDownload(로컬 다운로드path, 받을파일1, 받을파일2, ...) 
	 * 다운로드 path가 null 일시 기본 디렉토리에 다운로드
	 *  성공 1 실패 0*/
	public void FTPDownload(String download_Folder, String... path){
		Download pr=new Download(download_Folder, path);
		Thread th = new Thread(pr);
		th.start();

//		new Thread(new Loding()).start();
		
		synchronized (th) {
			try {
				th.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			dialog.dispose();
		}
	}
	/**FTP서버에서 파일 삭제 
	 * FTPDelete(지울파일1, 지울파일2, ...) 
	 *  성공 1 실패 0*/
	public void FTPDelete(String... path){
		Delete pr=new Delete(path);
		Thread th = new Thread(pr);
		th.start();
		
//		new Thread(new Loding()).start();
		
		synchronized (th) {
			try {
				th.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			dialog.dispose();
		}
	}
	
	/**FTP서버 파일 폴더이동(경로 변경),이름 변경
	 * FTPChangePath(해당파일 포함 파일 path, 변경할 path)
	 * ex) FTPChangePath("/text.txt","/dir/change.txt") 
	 *  성공 1 실패 0*/
	public void FTPChangePath(String file_path, String change_path){
		Modify pr=new Modify(file_path, change_path);
		Thread th = new Thread(pr);
		th.start();
		
//		new Thread(new Loding()).start();
		
		synchronized (th) {
			try {
				th.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			dialog.dispose();
		}
	}
	
	class Upload extends Thread{
		String upload_Folder;
		String[] path;
		Upload(String upload_Folder, String... path){
			this.upload_Folder = upload_Folder;
			this.path = path;
		}
		public void run(){
			synchronized (this) {
				try {
			    	upload_Folder = DataBase.Directory_Path_Arrangment(upload_Folder);
			    	FTPMkdir(upload_Folder);
			    	
			    	BufferedInputStream bis = null;
			    	InputStream inputStream = null;
			    	for(int i=0; i<path.length; i++){
			    		path[i] = DataBase.Directory_Path_Arrangment(path[i]);
						File put_file = new File(path[i]);
						if(put_file.isDirectory()){
							String[] s =put_file.list();
							for(int j=0; j<s.length; j++)
								FTPUpload(upload_Folder+"/"+put_file.getName(),path[i]+"/"+s[j]);
						}else{
							String FileName = path[i].substring(path[i].lastIndexOf("/"));
							inputStream = new FileInputStream(put_file);
							bis = new BufferedInputStream(inputStream);
						    ftpClient.storeFile("./"+upload_Folder+FileName, bis);
						    inputStream.close();
						    bis.close();
						    
						    String extension = DataBase.ExtensionDetermination(FileName);//파일 분류
						    
						    PreparedStatement pst;
						    String FilePath = DataBase.Directory_Path_Arrangment(workPath+upload_Folder);
							try {
								pst = DataBase.conn.prepareStatement("insert into file values(?,?,?,?,?,?,?)");
								pst.setString(1, FileName);
								pst.setString(2, FilePath);
								pst.setInt(3, 0);
								if(extension.equals("img")) 
									pst.setInt(4, 0);
								else 
									pst.setInt(4, -1);
								pst.setTimestamp(5, new Timestamp(new Date().getTime()));
								pst.setString(6, extension);
								if(extension.equals("img")){
								    	
							            //썸네일 가로사이즈
							            int thumbnail_width = 100;
							            //썸네일 세로사이즈
							            int thumbnail_height = 100;
							            //원본이미지파일의 경로+파일명
							 
							            BufferedImage buffer_original_image = ImageIO.read(put_file);
							            BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width, thumbnail_height, BufferedImage.TYPE_3BYTE_BGR);
							            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
							            graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
							            ByteArrayOutputStream image_output = new ByteArrayOutputStream();
							            ImageIO.write(buffer_thumbnail_image, "jpg", image_output);
							            
							            byte[] buffer = image_output.toByteArray(); 
							            InputStream thumnail = new ByteArrayInputStream(buffer);
							            
							            System.out.println("썸네일 생성완료");
							            
							            pst.setBinaryStream(7, thumnail);
								}else
									pst.setString(7, null);
								
								pst.executeUpdate();
							} catch (SQLException e) {
								System.err.println("이미 있는 파일 "+FileName);
							}
						}
			    	}
				} catch (IOException e) {
					e.printStackTrace();
				}
				notify();
			}
		}
	}
	
	class Download extends Thread{
		String download_Folder;
		String[] path;
		Download(String download_Folder, String... path){
			this.download_Folder = download_Folder;
			this.path = path;
		}
		public void run(){
			synchronized (this) {
				try {
					if(download_Folder==null){
						download_Folder = System.getProperty("user.home")+"/AppData/Local/file_downloads";
					}
					
					File get_file = new File(download_Folder);
					
					BufferedOutputStream bos = null;
					OutputStream outputStream = null;
					for(int i=0; i<path.length; i++){
						path[i] = DataBase.Directory_Path_Arrangment(path[i]);
						
						FTPFile[] files = ftpClient.listFiles(path[i]);

						if(ftpClient.changeWorkingDirectory("."+path[i])){
							if(files.length==0){
								get_file = new File(download_Folder+path[i]);
								get_file.mkdir();
							}else{
								for(int j=0; j<files.length; j++){
									FTPFile f = files[j];
									FTPDownload(download_Folder+path[i],files[j].getName());
								}
							}
							ftpClient.changeWorkingDirectory("../");
						}else{
							String Filepath = path[i].substring(0,path[i].lastIndexOf("/"));
							get_file = new File(DataBase.Directory_Path_Arrangment(download_Folder)+Filepath);
							get_file.mkdirs();
							get_file = new File(DataBase.Directory_Path_Arrangment(download_Folder)+path[i]);
						    outputStream = new FileOutputStream(get_file);
						    bos = new BufferedOutputStream(outputStream);
						    ftpClient.retrieveFile(path[i], bos);
						    outputStream.flush();
						    bos.flush();
						    outputStream.close();
							bos.close();
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			notify();
			}
		}
	}
	class Delete extends Thread{
		String[] path;
		Delete(String... path){
			this.path = path;
		}
		public void run(){
			synchronized (this) {
				try {
					
					for(int i=0; i<path.length; i++){
						path[i] = DataBase.Directory_Path_Arrangment(path[i]);
						//FTP삭제
						FTPFile[] files = ftpClient.listFiles(path[i]);
						
						if(ftpClient.changeWorkingDirectory("."+path[i])){
							ftpClient.changeWorkingDirectory("../");
							if(files.length!=0){
								for(int j=0; j<files.length; j++){
									FTPFile f = files[j];
									FTPDelete(path[i]+"/"+files[j].getName());
								}
							}
							ftpClient.removeDirectory(path[i]);
							
							//dir DB삭제
							PreparedStatement pst;
							String FilePath = workPath + path[i].substring(0, path[i].lastIndexOf("/"));
							String FileName = path[i].substring(path[i].lastIndexOf("/"));
							FilePath = DataBase.Directory_Path_Arrangment(FilePath);
							String extension = "dir";//파일 분류
							try {					
								pst = DataBase.conn.prepareStatement("delete from file where name='"+FileName+"'&& path='"+FilePath+"'&& type='"+extension+"'");
								pst.executeUpdate();
							} catch (SQLException e) {
								System.err.println("삭제 오류 dir");
							}
						}else{
							ftpClient.deleteFile(workPath + path[i]);
							
							//file DB삭제
							PreparedStatement pst;
							String FilePath = workPath + path[i].substring(0, path[i].lastIndexOf("/"));
							String FileName = path[i].substring(path[i].lastIndexOf("/"));
							FilePath = DataBase.Directory_Path_Arrangment(FilePath);
							String extension = DataBase.ExtensionDetermination(FileName);//파일 분류
							try {					
								pst = DataBase.conn.prepareStatement("delete from file where name='"+FileName+"'&& path='"+FilePath+"'&& type='"+extension+"'");
								pst.executeUpdate();
							} catch (SQLException e) {
								System.err.println("삭제 오류 "+ FileName);
							}
						}

					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			notify();
			}
		}
	}
	class Modify extends Thread{
		String file_path;
		String change_path;
		Modify(String file_path, String change_path){
			this.file_path = file_path;
			this.change_path = change_path;
		}
		public void run(){
			synchronized (this) {
				try {
					file_path = DataBase.Directory_Path_Arrangment(file_path);
					change_path = DataBase.Directory_Path_Arrangment(change_path);
					
					String change_name = change_path.substring(change_path.lastIndexOf("/"));
					change_path = change_path.substring(0,change_path.lastIndexOf("/"));
					
					FTPMkdir(change_path);
					ftpClient.rename(file_path,change_path+change_name);
				
					String file_name = file_path.substring(file_path.lastIndexOf("/"));
					file_path = file_path.substring(0,file_path.lastIndexOf("/"));
				
					
					
					PreparedStatement pst = DataBase.conn.prepareStatement("update file set name='"+change_name+"',path='"+change_path+"' where name='"+file_name+"'&& path='"+file_path+"'");
					pst.executeUpdate();
				} catch (SQLException e) {
					System.err.println("실패 : .");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			notify();
			}
		}
	}
//	class Loding extends Thread{
//		public void run(){
//			ImageIcon icon = new ImageIcon("img/loading.gif");
//			JLabel loding = new JLabel(icon);
//			loding.setBackground(new Color(0,0,0));
//			loding.setVisible(true);
//			dialog.add(loding);
//			dialog.setUndecorated(true);
//			dialog.setBackground(new Color(0,0,0,0));
//			dialog.setAlwaysOnTop(true);
//			dialog.setSize(700, 700);
//			dialog.setLocationRelativeTo(null);
//			dialog.setVisible(true);
//		}
//	}
}


