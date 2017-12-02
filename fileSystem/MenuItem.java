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
	private static JLabel show_path;

	private static String current_path = "/";
	private String file_name = "file";
	private String folder_name = "folder";

	private int btn_size;

	FTPManager fm;

	MenuItem(FTPManager fm) {
		this.fm = fm;
		setLayout(null);

		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8;
		height = res.height * 0.8 * 0.1;

		btn_size = (int) (height * 0.9);
		this.setBackground(Color.yellow);
		
		Font f = new Font("휴먼매직체", Font.BOLD, 20);

		search_name = new JTextField();
		search = new JButton("찾기");
		upload = new JButton("업");
		download = new JButton("다운");
		delete = new JButton("삭제");
		
		show_path = new JLabel("current path : " +current_path);
		show_path.setFont(f);

		ImageIcon plus_icon = new ImageIcon("img/nfolder.png");
		Image plus_img = plus_icon.getImage();
		plus_img = plus_img.getScaledInstance(btn_size, btn_size, java.awt.Image.SCALE_SMOOTH);
		plus_icon = new ImageIcon(plus_img);
		plus = new JButton(plus_icon);
		
		ImageIcon sch_icon = new ImageIcon("img/sch.png");
		Image sch_img = sch_icon.getImage();
		sch_img = sch_img.getScaledInstance(btn_size, btn_size, java.awt.Image.SCALE_SMOOTH);
		sch_icon = new ImageIcon(sch_img);
		search = new JButton(sch_icon);
		
		ImageIcon up_icon = new ImageIcon("img/up.png");
		Image up_img = up_icon.getImage();
		up_img = up_img.getScaledInstance(btn_size, btn_size, java.awt.Image.SCALE_SMOOTH);
		up_icon = new ImageIcon(up_img);
		upload = new JButton(up_icon);
		
		ImageIcon down_icon = new ImageIcon("img/down.png");
		Image down_img = down_icon.getImage();
		down_img = down_img.getScaledInstance(btn_size, btn_size, java.awt.Image.SCALE_SMOOTH);
		down_icon = new ImageIcon(down_img);
		download = new JButton(down_icon);
		
		ImageIcon delete_icon = new ImageIcon("img/delete.png");
		Image delete_img = delete_icon.getImage();
		delete_img = delete_img.getScaledInstance(btn_size, btn_size, java.awt.Image.SCALE_SMOOTH);
		delete_icon = new ImageIcon(delete_img);
		delete = new JButton(delete_icon);

		add(search_name);
		add(search);
		add(upload);
		add(download);
		add(delete);
		add(plus);
		add(show_path);

		search.setToolTipText("파일 이름을 검색합니다.");
		upload.setToolTipText("사용자의 컴퓨터에서 파일을 업로드 합니다.");
		download.setToolTipText("선택한 파일을 사용자의 컴퓨터 저장소로 다운로드합니다.");
		delete.setToolTipText("선택한 파일을 삭제합니다.");
		plus.setToolTipText("현재 위치에 폴더를 추가합니다.");

		show_path.setBounds((int)(width*0.01), (int)(height * 0.05), (int)(width*2/5), btn_size);
		delete.setBounds((int) (width - height * 1.20), (int) (height * 0.05), btn_size, btn_size);
		download.setBounds((int) (width - height * 2.20), (int) (height * 0.05), btn_size, btn_size);
		upload.setBounds((int) (width - height * 3.20), (int) (height * 0.05), btn_size, btn_size);
		search.setBounds((int) (width - height * 4.20), (int) (height * 0.05), btn_size, btn_size);
		search_name.setBounds((int) (width - height * 8.40), (int) (height * 0.3), (int) (height * 4),
				(int) (height * 0.40));
		plus.setBounds((int) (width - height * 9.60), (int) (height * 0.05), btn_size, btn_size);

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
				if (ret != JFileChooser.APPROVE_OPTION)
					return;
				String filePath = chooser.getSelectedFile().getPath();
				fm.FTPDownload(filePath, current_path + "/" + file_name);

			}

		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fm.FTPDelete(file_name);
			}
		});
		plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				folder_name = JOptionPane.showInputDialog("추가할 폴더 이름을 입력하세요");
				fm.FTPMkdir(folder_name);
			}

		});
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search_name.setText("");
			}
		});

		setVisible(true);
	}

	public static String get_path() {
		return current_path;
	}

	public static void set_path(String path) {
		current_path = path;
		show_path.setText("current path : "+path);
	}
}
