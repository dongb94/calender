package fileSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Viewer extends JTabbedPane {
	private String current_path="/";

	InnerPane home_p;
	InnerPane picture_p;
	InnerPane video_p;
	InnerPane music_p;
	InnerPane word_p;
	InnerPane book_p;
	
	private Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
	private double width;
	private double height;
	
	FTPManager fm;
	
	Viewer(FTPManager fm){
		this.fm = fm;
		setTabPlacement(LEFT);
		width = res.width*0.8*0.99;
		height = res.height*0.8*0.85;
		
		home_p = new InnerPane(0, this, fm);
		picture_p = new InnerPane(1, this, fm);
		video_p = new InnerPane(2, this, fm);
		music_p = new InnerPane(3, this, fm);
		word_p = new InnerPane(4, this, fm);
		book_p = new InnerPane(5, this, fm);
		
		ImageIcon home_icon = new ImageIcon("img/home.png");
		ImageIcon picture_icon = new ImageIcon("img/picture.png");
		ImageIcon video_icon = new ImageIcon("img/video.png");
		ImageIcon music_icon = new ImageIcon("img/music.png");
		ImageIcon doc_icon = new ImageIcon("img/doc.png");
		ImageIcon star_icon = new ImageIcon("img/star.png");
		ImageIcon album_icon = new ImageIcon("img/album.png");
		
		Image picture_img = picture_icon.getImage();
		Image home_img = home_icon.getImage();
		Image video_img = video_icon.getImage();
		Image music_img = music_icon.getImage();
		Image doc_img = doc_icon.getImage();
		Image star_img = star_icon.getImage();
		Image album_img = album_icon.getImage();
		
		picture_img = picture_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		home_img = home_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		video_img = video_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		music_img = music_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		doc_img = doc_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		star_img = star_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		album_img = album_img.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		
		picture_icon = new ImageIcon(picture_img);
		home_icon = new ImageIcon(home_img);
		video_icon = new ImageIcon(video_img);
		music_icon = new ImageIcon(music_img);
		doc_icon = new ImageIcon(doc_img);
		star_icon = new ImageIcon(star_img);
		album_icon = new ImageIcon(album_img);
		
		Font f = new Font("휴먼매직체", Font.BOLD, 20);
		this.setFont(f);
		//this.addTab(title, imageicon, component);
		addTab("HOME",home_icon, home_p );
		addTab("사진",picture_icon, picture_p);
		addTab("동영상",video_icon, video_p);
		addTab("음악",music_icon, music_p);
		addTab("문서",doc_icon, word_p);
		addTab("즐겨찾기",star_icon, book_p);
		addTab("앨범",album_icon, new JPanel());
		
	}
	public FTPManager get_fm() {
		return fm;
	}
	public void set_path() {
		current_path = MenuItem.get_path();
	}
}
