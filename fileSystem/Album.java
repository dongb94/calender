package fileSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	
	public AddDialog(Album album) {
		super(album);
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
				setVisible(false);
				dispose();
			}
			
		});
	}
}


public class Album extends JFrame {
	
	private JScrollPane paneAlbum;
	private Album album;
	
	private FileDatas fds;
	private FileData[] fd;
	private FileData[] imgfd;
	
	private Dimension screenSize;
	private double width, height;
	private int imgfdLength;
	
	
	public Album() {
		album = this;
		
		setTitle("앨범");
		
		makeGUI();
		
		new FileDatas();
		
		setResizable(true);
		setVisible(true);
	}
	
	
	void makeGUI(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width*0.8;
		height = screenSize.height*0.8;
		
		setSize((int)(width), (int)(height));
		Dimension frameSize = this.getSize();
		setLocation((int)(screenSize.width-frameSize.width)/2, (int)(screenSize.height-frameSize.height)/2);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		makeBtnPanel();
		makeAlbumPane();
	}
	
	
	ImageIcon changeImageSize(ImageIcon originIcon, int width, int height) {
		Image originImg = originIcon.getImage();
		Image changedImg= originImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);		
		
		return Icon;
	}
	
	
	void makeBtnPanel() {
		JPanel panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelBtn, BorderLayout.NORTH);
		panelBtn.setLayout(new BorderLayout(0, 0));
		
		JButton btnAdd = new JButton("");

		// 추가 버튼 생성
		btnAdd.setIcon(changeImageSize(new ImageIcon("imgAlbum/iconAdd.png"), 50, 50));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorderPainted(false);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddDialog addDialog = new AddDialog(album);
				addDialog.setVisible(true);
			}
			
		});
		panelBtn.add(btnAdd, BorderLayout.EAST);
	}
	
	
	void makeAlbumPane() {
		paneAlbum = new JScrollPane();
		paneAlbum.removeAll();
		this.remove(paneAlbum);
		
		paneAlbum.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		paneAlbum.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		paneAlbum.setBackground(Color.WHITE);
		
		fds = new FileDatas("/");
		fd = fds.getFileDatas();
		imgfd = new FileData[fd.length];
		
		JCheckBox[] file_list = new JCheckBox[fd.length];
		imgfdLength=0;
		for (int i=0; i<fd.length; i++) {
			ImageIcon file_icon;

			if (fds.file[i].img == 0) {
				file_icon = fd[i].thumnail;
				file_icon = changeImageSize(file_icon, (int)(width * 0.12), (int)(width * 0.12));
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);

				file_list[i].setText(fd[i].name);
				add_box(file_list[i]);
			
				imgfd[imgfdLength] = fd[i];
			}
		}
		
		// GUI 테스트
//		img = new ImageIcon[3];
//		img[0] = new ImageIcon("imgAlbum/iconAdd.png");
//		img[1] = new ImageIcon("imgAlbum/iconBack.png");
//		img[2] = new ImageIcon("imgAlbum/iconBefore.png");
//		JCheckBox[] file_list = new JCheckBox[3];
//		
//		for (int i=0; i<3; i++) {
//			file_list[i] = new JCheckBox(changeImageSize(img[i], (int)(width * 0.12), (int)(width * 0.12)));
//			add_box(file_list[i]);
//		}
	
		getContentPane().add(paneAlbum, BorderLayout.CENTER);
	}
	
	
	public void add_box(JCheckBox box) {
		paneAlbum.add(box);
		box.setSize(new Dimension(230, 230));
		box.setHorizontalAlignment(SwingConstants.CENTER);
		box.setVerticalAlignment(SwingConstants.TOP);
		box.setHorizontalTextPosition(SwingConstants.CENTER);
		box.setVerticalTextPosition(SwingConstants.BOTTOM);
		box.setBorderPainted(true);
		box.setBackground(Color.WHITE);
		box.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {
					new AlbumPreview(imgfd, imgfdLength);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
	public static void main(String[] args0){
		try {
			new DataBase();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Album();
	}

}
