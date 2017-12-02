package fileSystem;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class InnerPane extends JScrollPane {
	/*
	 * 0 = whole 1 = picture 2 = video 3 = music 4 = word 5 = bookmark
	 */
	private int type_flag;
	private JButton to_higher = new JButton("이전");
	private FlowLayout fl = new FlowLayout(FlowLayout.LEFT);

	private Dimension res;
	private double width;
	private double height;

	private int icon_size;
	private String current_path;
	private Viewer v;

	FTPManager fm;
	private FileDatas fds;
	private FileData[] fd;
	private Font f = new Font("휴먼매직체", Font.BOLD, 20);
	
	public static ArrayList<String> name_list = new ArrayList<String>();

	InnerPane(int flag, Viewer v, FTPManager fm) {
		this.fm = fm;
		res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8 * 0.99;
		height = res.height * 0.8 * 0.85;

		fl.setVgap((int) (width * 0.02));
		fl.setHgap((int) (width * 0.02));

		icon_size = (int) (width * 0.12);

		ImageIcon pr_icon = new ImageIcon("img/pr.png");
		Image pr_img = pr_icon.getImage();
		pr_img = pr_img.getScaledInstance((int) (icon_size * 0.95), (int) (icon_size * 0.95),
				java.awt.Image.SCALE_SMOOTH);
		pr_icon = new ImageIcon(pr_img);

		to_higher = new JButton(pr_icon);
		to_higher.setBackground(new Color(255, 255, 255));

		this.setPreferredSize(new Dimension((int) width, (int) height));

		type_flag = flag;
		
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

		this.v = v;
		current_path = MenuItem.get_path();
		fm = v.get_fm();
		BackListener bl = new BackListener();
		to_higher.addActionListener(bl);
		
		makeGUI();
	}

	public void makeGUI() {
		
		JPanel jp = new JPanel();
		this.setViewportView(jp);
		jp.setLayout(fl);
		fds = new FileDatas(current_path);
		fd = fds.getFileDatas();
		
		if (type_flag == 0 || type_flag == 1 || type_flag == 2 || type_flag == 3 || type_flag == 4) {
			jp.add(to_higher);
			to_higher.setPreferredSize(new Dimension(icon_size, icon_size));
			to_higher.setToolTipText("이전 위치로");
		}
		JCheckBox[] file_list = new JCheckBox[fd.length];
		for (int i = 0; i < fd.length; i++) {
			ImageIcon file_icon;

			if (fd[i].img != -1) {
				file_icon = fd[i].thumnail;
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);

				if (type_flag == 0 || type_flag == 1)
					add_box(file_list[i], jp);

			} else if (fd[i].dcm) {
				file_icon = new ImageIcon("img/doc.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);

				if (type_flag == 0 || type_flag == 4)
					add_box(file_list[i], jp);

			} else if (fd[i].dir) {
				file_icon = new ImageIcon("img/folder.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new FolderListener());

				if (type_flag != 5)
					add_box(file_list[i], jp);

			} else if (fd[i].msc) {
				file_icon = new ImageIcon("img/music.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);

				if (type_flag == 0 || type_flag == 3)
					add_box(file_list[i], jp);

			} else if (fd[i].vid) {
				file_icon = new ImageIcon("img/video.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);

				if (type_flag == 0 || type_flag == 2)
					add_box(file_list[i], jp);
			} else
				return;
			if (fd[i].favor) {
				if (type_flag == 5)
					add_box(file_list[i], jp);
			}
		}

		setVisible(true);
	}

	public void add_box(JCheckBox box, JPanel jp) {
		box.setFont(f);
		jp.add(box);
		box.setPreferredSize(new Dimension(icon_size, icon_size));
		box.setHorizontalAlignment(SwingConstants.CENTER);
		box.setVerticalAlignment(SwingConstants.TOP);
		box.setHorizontalTextPosition(SwingConstants.CENTER);
		box.setVerticalTextPosition(SwingConstants.BOTTOM);
		box.setBorderPainted(true);
	}

	public ImageIcon set_icon(ImageIcon icon) {
		Image file_img = icon.getImage();
		file_img = file_img.getScaledInstance((int) (icon_size * 0.8), (int) (icon_size * 0.8),
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(file_img);
		return icon;
	}

	public class FolderListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox box = (JCheckBox) e.getSource();
			if(box.isSelected()) {
				if(current_path != "/")
					current_path = current_path +"/"+ box.getText() ;
				else
					current_path = current_path + box.getText();
					fm.FTPCd(current_path);
					MenuItem.set_path(current_path);
					v.set_path();
					makeGUI();
			}
			System.out.println(box.getItemListeners().length);
			box.removeItemListener(this);
		}
	}
	
	public class BoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox box = (JCheckBox) e.getSource();
			if(box.isSelected())
				name_list.add(box.getText());
			else
				name_list.remove(box.getText());
		}
		
	}
	public class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current_path != "/") {
				String go_to = current_path.substring(0, current_path.lastIndexOf("/"));
				if(go_to.equals(""))
					go_to="/";
				fm.FTPCd(go_to);
				current_path = go_to;
				MenuItem.set_path(go_to);
				v.set_path();
				makeGUI();
			} else {
				JLabel la = new JLabel("여기가 최상위 디렉토리 이므로 더 이상 진행할 수 없습니다.");
				la.setFont(f);
				JOptionPane.showMessageDialog(v, la);
			}
		}
	}
	public static ArrayList get_list() {
		return name_list;
	}

}