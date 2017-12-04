package fileSystem;

import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.io.*;

public class Main extends JFrame{
	
	private double width;
	private double height;
	
	FTPManager fm;
	MenuItem mi;
	Viewer v;
	
	Main(){
		try {
			new DataBase();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("클라우드");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		fm = new FTPManager(this);
		mi = new MenuItem(fm);
		v = new Viewer(fm);
		
		makeGUI();
		
		setResizable(true);
		setVisible(true);
		
	}
	
	void makeGUI(){
		
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width*0.8;
		height = res.height*0.8;
		
		setSize((int)(width), (int)(height));
		setLocation((int)(width*0.1), (int)(height*0.1));
		setLayout(null);
		
		add(v);
		add(mi);
		mi.setBounds(0, 0, (int)(width*0.99), (int)(height*0.1));
		v.setBounds(0,(int)(height*0.1),(int)(width*0.99) ,(int)(height*0.85));
	}
	
	public static void main(String[] args0){
		Main m = new Main();
	}
}
