package fileSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MenuItem extends JPanel {

	private double width;
	private double height;
	private JButton upload;
	private JButton download;
	private JButton delete;
	private JButton search;
	private JTextField search_name;
	private JButton plus;

	private String current_path = "root";
	private String file_name = "file";
	private String folder_name = "folder";

	FTPManager fm;

	MenuItem(FTPManager fm) {
		this.fm = fm;
		setLayout(null);

		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8;
		height = res.height * 0.8 * 0.1;

		this.setBackground(Color.yellow);

		search_name = new JTextField();
		search = new JButton("찾기");
		upload = new JButton("업");
		download = new JButton("다운");
		delete = new JButton("삭제");
		plus = new JButton("추가");
		

		add(search_name);
		add(search);
		add(upload);
		add(download);
		add(delete);
		add(plus);
		
		delete.setBounds((int) (width - height * 1.20), (int) (height * 0.05), (int) (height * 0.90),
				(int) (height * 0.90));
		download.setBounds((int) (width - height * 2.20), (int) (height * 0.05), (int) (height * 0.90),
				(int) (height * 0.90));
		upload.setBounds((int) (width - height * 3.20), (int) (height * 0.05), (int) (height * 0.90),
				(int) (height * 0.90));
		search.setBounds((int) (width - height * 4.20), (int) (height * 0.05), (int) (height * 0.90),
				(int) (height * 0.90));
		search_name.setBounds((int) (width - height * 8.40), (int) (height * 0.3), (int) (height * 4),
				(int) (height * 0.40));
		plus.setBounds((int) (width - height * 9.60), (int) (height * 0.05), (int) (height * 0.90),
				(int) (height * 0.90));

		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION)
					return;
				String filePath = chooser.getSelectedFile().getPath();
				fm.FTPUpload(current_path, filePath);
			}
		});
		
		download.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int ret = chooser.showSaveDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION)
					return;
				String filePath = chooser.getSelectedFile().getPath();
				fm.FTPDownload(filePath, current_path+"\\"+file_name);
				
			}
			
		});
		setVisible(true);
	}

}
