package fileSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AlbumPreview extends JFrame {

	Dimension screenSize;
	double width, height;
	FileData[] fd;
	FTPManager fm = new FTPManager(this);
	private int fdlength, index;
	String localPath = (System.getProperty("user.home")+"/AppData/Local/file_downloads");
	String localFilePath, replacedString;
	
	public AlbumPreview(FileData[] fd, int fdlength, int index) {
		getContentPane().setBackground(Color.WHITE);
		setTitle("앨범 미리보기");
		
		this.fdlength = fdlength;
		this.fd = fd;
		this.index = index;
		
		makeGUI();
		
		setAlwaysOnTop(true);
		setResizable(true);
		setVisible(true);
		setFocusable(true);
	}
	
	
	void makeGUI(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width*0.4;
		height = screenSize.height*0.8;
		
		setSize((int)(width), (int)(height));
		Dimension frameSize = this.getSize();
		setLocation((int)(screenSize.width-frameSize.width)/2, (int)(screenSize.height-frameSize.height)/2);
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel labelImage = new JLabel("");
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setBackground(Color.BLACK);

		fm.FTPDownload(null, fd[index].path+fd[index].name);
		System.out.println(fd[index].path+fd[index].name);
		localFilePath = localPath + fd[index].path + fd[index].name;
		replacedString = localFilePath.replace('/', '\\');
		System.out.println(replacedString);
		
		labelImage.setIcon(changeImageSize(new ImageIcon(replacedString), (int)(width*0.7), (int)(width*0.7)));
		getContentPane().add(labelImage, BorderLayout.CENTER);
		
		JButton btnBefore = new JButton("");
		btnBefore.setBackground(Color.WHITE);
		getContentPane().add(btnBefore, BorderLayout.WEST);
		btnBefore.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconBefore.png"), 50, 50));
		btnBefore.setBorderPainted(false);
		btnBefore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (index != 0) {
					index--;
					
					fm.FTPDownload(null, fd[index].path+fd[index].name);
					System.out.println(fd[index].path+fd[index].name);
					localFilePath = localPath + fd[index].path + fd[index].name;
					replacedString = localFilePath.replace('/', '\\');
					System.out.println(replacedString);
					
					labelImage.setIcon(changeImageSize(new ImageIcon(replacedString), (int)(width*0.7), (int)(width*0.7)));
				}
			}
			
		});
		
		JButton btnNext = new JButton("");
		btnNext.setBackground(Color.WHITE);
		getContentPane().add(btnNext, BorderLayout.EAST);
		btnNext.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconNext.png"), 50, 50));
		btnNext.setBorderPainted(false);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (index != fd.length-1 || index < fd.length-1) {
					index++;
					
					fm.FTPDownload(null, fd[index].path+fd[index].name);
					System.out.println(fd[index].path+fd[index].name);
					localFilePath = localPath + fd[index].path + fd[index].name;
					replacedString = localFilePath.replace('/', '\\');
					System.out.println(replacedString);
					
					labelImage.setIcon(changeImageSize(new ImageIcon(replacedString), (int)(width*0.7), (int)(width*0.7)));
				}
			}
			
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnEdit = new JButton("");
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconEdit.png"), 30, 30));
		btnEdit.setBorderPainted(false);
		btnEdit.addActionListener(new MyActionListener(replacedString));
		panel.add(btnEdit);
	}
	
	
	ImageIcon changeImageSize(ImageIcon originIcon, int width, int height) {
		Image originImg = originIcon.getImage();
		Image changedImg= originImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		
		return Icon;
	}
	class MyActionListener implements ActionListener {

		String path;
		
		MyActionListener(String path) {
			this.path = path;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setAlwaysOnTop(false);
			PhotoEditor edit = new PhotoEditor(path);
			
		}
		
	}
}


