package fileSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


class AddDialog extends JDialog {
	
	JLabel l = new JLabel("앨범명");
	JTextField tf = new JTextField(10);
	JButton okBtn = new JButton("확인");
	FileDatas fd = new FileDatas();
	
	
	public AddDialog(Album album, int flag) {
		super(album);
		if (flag == 0) { // 앨범 추가
			setTitle("앨범 추가");
			setLayout(new FlowLayout());
			setSize(200, 120);
			
			Dimension dlgSize = this.getSize();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			add(l);
			add(tf);
			add(okBtn);

			setLocation((int)(screenSize.width-dlgSize.width)/2, (int)(screenSize.height-dlgSize.height)/2);
			
			okBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					fd.addAlbum(tf.getText());
					album.makeBtnPanel();
					album.makeAlbumPanel();
					album.panelBtn.revalidate();
					album.panelAlbum.revalidate();
					album.panelBtn.repaint();
					album.panelAlbum.repaint();
					setVisible(false);
					dispose();
				}
				
			});
		} else { // 앨범에 파일 추가
			setTitle("앨범으로 이동");
			setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			setSize(300, 150);
			
			Dimension dlgSize = this.getSize();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			l.setText("이동시킬 앨범을 선택하세요.");
			String[] albumList = new String[album.albumFd.length];
			
			for (int i=0; i<album.albumFd.length; i++) {
				albumList[i] = album.albumFd[i].name;
			}
			
			JComboBox<String> combo = new JComboBox<String>(albumList);
			
			add(l);
			add(combo);
			add(okBtn);

			setLocation((int)(screenSize.width-dlgSize.width)/2, (int)(screenSize.height-dlgSize.height)/2);
			
			okBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String selectedAlbum = (String)combo.getSelectedItem();
					for (int i=0; i<album.albumFileFd.length; i++) {
						if (album.albumFile_list[i].isSelected()) {
							album.albumFileFd[i].changeAlbum(selectedAlbum);
						}
					}
					album.makeBtnPanel();
					album.makeAlbumPanel();
					album.panelBtn.revalidate();
					album.panelAlbum.revalidate();
					album.panelBtn.repaint();
					album.panelAlbum.repaint();
					setVisible(false);
					dispose();
				}
				
			});
		}
	}
}


class AlbumSelectedActionListener implements ActionListener {

	Album album;
	
	AlbumSelectedActionListener(Album album) {
		this.album = album;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i=0; i<album.album_list.length; i++) {
			if (album.album_list[i].isSelected()) {
				album.albumFds.deleteAlbum(album.albumFd[i].name);
			}
		}
		album.makeBtnPanel();
		album.makeAlbumPanel();
		album.panelBtn.revalidate();
		album.panelAlbum.revalidate();
		album.panelBtn.repaint();
		album.panelAlbum.repaint();			
	}
	
}


class SelectedActionListener implements ActionListener {

	Album album;
	int dataFlag; // 앨범 0, 사진 1
	int commandFlag; // 삭제 0, 추가 1
	ArrayList<Integer> selected = new ArrayList<Integer>();
	
	SelectedActionListener(Album album, int commandFlag) {
		this.album = album;
		this.commandFlag = commandFlag;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (commandFlag == 0) { // 삭제
			if (album.getAlbumFileFlag() == 1) { // 앨범에서 파일 삭제
				System.out.println(album.albumFile_list.length);
				for (int i=0; i<album.albumFile_list.length; i++) {
					if (album.albumFile_list[i].isSelected()) {
						selected.add(i);
					}
				}
				
				for (int i=0; i<selected.size(); i++) {
					album.albumFileFd[selected.get(i)].changeAlbum(null);					
				}
				
				album.makeBtnPanel();
				album.makeAlbumPanel();
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();
			} else {
				for (int i=0; i<album.imgfdLength; i++) {
					if (album.file_list[i].isSelected()) {
						JOptionPane.showMessageDialog(null, "앨범만 선택해주세요.");
						return;
					}
				}
				
				for (int i=0; i<album.album_list.length; i++) {
					if (album.album_list[i].isSelected()) {
						album.albumFds.deleteAlbum(album.albumFd[i].name);
					}
				}
				album.makeBtnPanel();
				album.makeAlbumPanel();
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();				
			}
		} else { // 추가
			for (int i=0; i<album.album_list.length; i++) {
				if (album.album_list[i].isSelected()) {
					JOptionPane.showMessageDialog(null, "파일만 선택해주세요.");
					return;
				}
			}
			
			for (int i=0; i<album.albumFileFd.length; i++) {
				if (album.albumFile_list[i].isSelected()) {
					AddDialog addDialog = new AddDialog(album, 1);
					addDialog.setVisible(true);
					return;
				}
			}
		}
	}
	
}


class MyMouseListener implements MouseListener {

	Album album;
	int flag; // 앨범 0, 앨범 없는 사진 1, 앨범 안의 사진 2
	JCheckBox cb;
	int index;
	
	MyMouseListener(Album album, int flag) {
		this.album = album;
		this.flag = flag;
	}
	
	MyMouseListener(Album album, int flag, int index) {
		this.album = album;
		this.flag = flag;
		this.index = index;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (flag == 0) { // 앨범
			album.setAlbumFileFlag(0);
			album.makeAlbumSelectedModeBtnPanel();
			album.panelBtn.revalidate();
			album.panelBtn.repaint();
			if (e.getClickCount() == 2) {
				cb = new JCheckBox();
				cb = (JCheckBox) e.getSource();
				
				album.setSelectedAlbum(cb.getText());
				album.makeAlbumFileBtnPanel();
				album.makeAlbumFilePanel(cb.getText());
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();
			}
		} else if (flag == 1){ // 앨범 없는 사진
			album.setAlbumFileFlag(0);
			album.makeSelectedModeBtnPanel();
			album.panelBtn.revalidate();
			album.panelBtn.repaint();
			if (e.getClickCount() == 2) {
				new AlbumPreview(album.imgfd, album.imgfdLength, index);
			}
		} else { // 앨범 안의 사진
			JCheckBox jc = (JCheckBox) e.getSource();
			album.setAlbumFileFlag(1);
			if (album.getSelectedAlbum().equals("default")) {
				album.makeDefaultAlbumFileSelectedBtnPanel();				
			} else {
				album.makeAlbumFileSelectedBtnPanel();				
			}
			album.panelBtn.revalidate();
			album.panelBtn.repaint();
			if (e.getClickCount() == 2) {
				new AlbumPreview(album.albumFileFd, album.albumFd.length, index);
			}
		}
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	
}


public class Album extends JFrame {
	
	private JScrollPane spAlbum;
	private Album album;
	public JPanel panelBtn = new JPanel();
	public JPanel panelAlbum = new JPanel();
	
	public FileDatas fds, albumFds, albumFileFds;
	public FileData[] fd, albumFd, albumFileFd;
	public FileData[] imgfd;
	public JCheckBox[] file_list, album_list, albumFile_list;
	public String selectedAlbum;
	public int albumFileFlag;
	
	private Dimension screenSize;
	private double width, height;
	public int imgfdLength;
	private int startFlag, albumFlag;
	
	
	public Album() {
		album = this;
		startFlag = 1;
		albumFlag = 0;
		
		setTitle("앨범");
		
		makeGUI();
		
		new FileDatas();
		
		setResizable(true);
		setVisible(true);
	}
	
	
	public void setSelectedAlbum(String selectedAlbum) {
		this.selectedAlbum = selectedAlbum;
	}
	
	public String getSelectedAlbum() {
		return selectedAlbum;
	}
	
	public void setAlbumFileFlag(int flag) {
		albumFileFlag = flag;
	}
	
	public int getAlbumFileFlag() {
		return albumFileFlag;
	}
	
	
	void makeGUI(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width*0.8;
		height = screenSize.height*0.8;
		
		setSize((int)(width), (int)(height));
		Dimension frameSize = this.getSize();
		setLocation((int)(screenSize.width-frameSize.width)/2, (int)(screenSize.height-frameSize.height)/2);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		spAlbum = new JScrollPane();
		spAlbum.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spAlbum.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		makeBtnPanel();
		makeAlbumPanel();
	}
	
	
	ImageIcon changeImageSize(ImageIcon originIcon, int width, int height) {
		Image originImg = originIcon.getImage();
		Image changedImg= originImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);		
		
		return Icon;
	}
	
	
	void makeBtnPanel() {
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnAdd = new JButton("");

		// 앨범 추가 버튼 생성
		btnAdd.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconAdd.png"), 50, 50));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorderPainted(false);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddDialog addDialog = new AddDialog(album, 0);
				addDialog.setVisible(true);
			}
			
		});
		
//		if (startFlag == 0) { // 처음 그리는 게 아님
//			for (int i=0; i<imgfdLength; i++) {
//				if (file_list[i].isSelected()) {
//					makeSelectedModeBtnPanel();
//					makeAlbumPanel();
//					panelBtn.revalidate();
//					panelAlbum.revalidate();
//					panelBtn.repaint();
//					panelAlbum.repaint();
//				}
//			}
//			
//			for (int i=0; i<album_list.length; i++) {
//				if (album_list[i].isSelected()) {
//					makeSelectedModeBtnPanel();
//					makeAlbumPanel();
//					panelBtn.revalidate();
//					panelAlbum.revalidate();
//					panelBtn.repaint();
//					panelAlbum.repaint();
//				}
//			}	
//		}
		
		panelBtn.add(btnAdd);
		startFlag = 0;
		getContentPane().add(panelBtn, BorderLayout.NORTH);
	}
	
	
	void makeAlbumSelectedModeBtnPanel() {
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// 삭제 버튼 생성
		JButton btnDelete = new JButton();
		
		btnDelete.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconDelete.jpg"), 50, 50));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBorderPainted(false);
		btnDelete.addActionListener(new SelectedActionListener(album, 0));
		panelBtn.add(btnDelete);
		
		getContentPane().add(panelBtn, BorderLayout.NORTH);		
	}
	
	
	void makeSelectedModeBtnPanel() {
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// 삭제 버튼 생성
		JButton btnDelete = new JButton();
		
		btnDelete.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconDelete.jpg"), 50, 50));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBorderPainted(false);
		btnDelete.addActionListener(new SelectedActionListener(album, 0));
		panelBtn.add(btnDelete);

		// 앨범에 파일 추가 버튼 생성
		JButton btnAdd = new JButton("");

		btnAdd.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconAdd.png"), 50, 50));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorderPainted(false);
		btnAdd.addActionListener(new SelectedActionListener(album, 1));
		panelBtn.add(btnAdd);
		
		getContentPane().add(panelBtn, BorderLayout.NORTH);
	}
	
	
	public void makeAlbumPanel() {
		panelAlbum.removeAll();
		spAlbum.remove(panelAlbum);
	
		spAlbum = new JScrollPane(panelAlbum);
		spAlbum.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spAlbum.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spAlbum.setPreferredSize(new Dimension((int) width, (int) height));
		
		panelAlbum = new JPanel();
		panelAlbum.setBackground(Color.WHITE);
		panelAlbum.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		spAlbum.setViewportView(panelAlbum);

		// 앨범 표시
		albumFds = new FileDatas();
		albumFds.getAlbums();
		albumFd = albumFds.getFileDatas();

		album_list = new JCheckBox[albumFd.length];

		for (int i=0; i<albumFd.length; i++) {
			if (albumFd[0] == null) { break; }
			
			ImageIcon file_icon = new ImageIcon("imgAlbum/iconAlbum.png");
			file_icon = changeImageSize(file_icon, (int)(width * 0.12), (int)(width * 0.12));
			
			album_list[i] = new JCheckBox(albumFd[i].name, file_icon);
			album_list[i].setText(albumFd[i].name);
			add_box(album_list[i]);
		}
		
		for (int i=0; i<albumFd.length; i++) {
			album_list[i].addMouseListener(new MyMouseListener(album, 0));
		}
			
		getContentPane().add(panelAlbum, BorderLayout.CENTER);
	}
	
	
	void makeAlbumFileSelectedBtnPanel() {		
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// 뒤로 가기 버튼 생성
		JButton btnBack = new JButton("");

		btnBack.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconBack.png"), 50, 50));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				album.makeBtnPanel();
				album.makeAlbumPanel();
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();			
			}
			
		});
		panelBtn.add(btnBack);		
		
		// 삭제 버튼 생성
		JButton btnDelete = new JButton();
		
		btnDelete.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconDelete.jpg"), 50, 50));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBorderPainted(false);
		btnDelete.addActionListener(new SelectedActionListener(album, 0));
		panelBtn.add(btnDelete);

		// 앨범 간 이동 버튼 생성
		JButton btnMove = new JButton("");

		btnMove.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconMove.png"), 50, 50));
		btnMove.setBackground(Color.WHITE);
		btnMove.setBorderPainted(false);
		btnMove.addActionListener(new SelectedActionListener(album, 1));
		panelBtn.add(btnMove);
		
		getContentPane().add(panelBtn, BorderLayout.NORTH);				
	}

	
	void makeDefaultAlbumFileSelectedBtnPanel() {
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// 뒤로 가기 버튼 생성
		JButton btnBack = new JButton("");

		btnBack.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconBack.png"), 50, 50));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				album.makeBtnPanel();
				album.makeAlbumPanel();
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();			
			}
			
		});
		panelBtn.add(btnBack);

		// 앨범 간 이동 버튼 생성
		JButton btnMove = new JButton("");

		btnMove.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconMove.png"), 50, 50));
		btnMove.setBackground(Color.WHITE);
		btnMove.setBorderPainted(false);
		btnMove.addActionListener(new SelectedActionListener(album, 1));
		panelBtn.add(btnMove);
		
		getContentPane().add(panelBtn, BorderLayout.NORTH);				
	}
	
	
	void makeAlbumFileBtnPanel() {
		panelBtn.removeAll();
		this.remove(panelBtn);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
//		// 삭제 버튼 생성
//		JButton btnDelete = new JButton();
//		
//		btnDelete.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconDelete.jpg"), 50, 50));
//		btnDelete.setBackground(Color.WHITE);
//		btnDelete.setBorderPainted(false);
//		btnDelete.addActionListener(new SelectedActionListener(album, 0));
//		panelBtn.add(btnDelete);
//
//		// 앨범 간 이동 버튼 생성
//		JButton btnMove = new JButton("");
//
//		btnMove.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconMove.png"), 50, 50));
//		btnMove.setBackground(Color.WHITE);
//		btnMove.setBorderPainted(false);
//		btnMove.addActionListener(new SelectedActionListener(album, 1));
//		panelBtn.add(btnMove);
		
		// 뒤로 가기 버튼 생성
		JButton btnBack = new JButton("");

		btnBack.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconBack.png"), 50, 50));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				album.makeBtnPanel();
				album.makeAlbumPanel();
				album.panelBtn.revalidate();
				album.panelAlbum.revalidate();
				album.panelBtn.repaint();
				album.panelAlbum.repaint();			
			}
			
		});
		panelBtn.add(btnBack);
		
		getContentPane().add(panelBtn, BorderLayout.NORTH);		
	}
	
	
	void makeAlbumFilePanel(String albumName) {
		album.makeAlbumFileBtnPanel();
		album.panelBtn.revalidate();
		album.panelBtn.repaint();
		
		panelAlbum.removeAll();
		spAlbum.remove(panelAlbum);

		spAlbum = new JScrollPane(panelAlbum);
		spAlbum.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spAlbum.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spAlbum.setPreferredSize(new Dimension((int) width, (int) height));
	
		panelAlbum = new JPanel();
		panelAlbum.setBackground(Color.WHITE);
		panelAlbum.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		spAlbum.setViewportView(panelAlbum);
		albumFileFds = new FileDatas();
		albumFileFds.getAlbumFiles(albumName);
		albumFileFd = albumFileFds.getFileDatas();

		albumFile_list = new JCheckBox[albumFileFd.length];
		for (int i=0; i<albumFileFd.length; i++) {
			ImageIcon file_icon;

			file_icon = albumFileFd[i].thumnail;
			file_icon = changeImageSize(file_icon, (int)(width * 0.12), (int)(width * 0.12));
			albumFile_list[i] = new JCheckBox(albumFileFd[i].name.substring(1), file_icon);

			albumFile_list[i].setText(albumFileFd[i].name);
			add_box(albumFile_list[i]);
		}
		
		for (int i=0; i<albumFileFd.length; i++) {
			albumFile_list[i].addMouseListener(new MyMouseListener(album, 2, i));
		}
		
		getContentPane().add(panelAlbum, BorderLayout.CENTER);
//		getContentPane().add(new JScrollPane(panelAlbum, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}
	
	
	public void add_box(JCheckBox box) {
		panelAlbum.add(box);
		box.setSize(new Dimension(230, 230));
		box.setHorizontalAlignment(SwingConstants.CENTER);
		box.setVerticalAlignment(SwingConstants.TOP);
		box.setHorizontalTextPosition(SwingConstants.CENTER);
		box.setVerticalTextPosition(SwingConstants.BOTTOM);
		box.setBorderPainted(true);
		box.setBackground(Color.WHITE);
	}
}
