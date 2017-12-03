package fileSystem;


import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.event.*;

public class InnerPane extends JScrollPane {
	/*
	 * 0 = whole 1 = picture 2 = video 3 = music 4 = word 5 = bookmark
	 */

	protected InnerPane self;
	protected int type_flag;
	protected JButton to_higher = new JButton("이전");
	protected FlowLayout fl = new FlowLayout(FlowLayout.LEFT);

	protected Dimension res;
	protected double width;
	protected double height;

	protected int icon_size;
	protected String current_path;
	protected Viewer v;
	protected JPanel jp = new JPanel();

	protected FTPManager fm;
	protected FileDatas fds;
	protected FileData[] fd;
	protected Font f = new Font("휴먼매직체", Font.BOLD, 20);

	protected ArrayList<String> name_list = new ArrayList<String>();
	protected DropTarget dt;

	InnerPane(int flag, Viewer v, FTPManager fm) {
		this.fm = fm;
		self = this;
		res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8 * 0.99;
		height = res.height * 0.8 * 0.85;
		

		dt = new DropTarget(this,DnDConstants.ACTION_COPY_OR_MOVE, new DropListener(),true,null);

		
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
		
		fds = new FileDatas(current_path);
		fd = fds.getFileDatas();
		
		makeGUI();
	}

	public void makeGUI() {

		jp.removeAll();
		this.remove(jp);
		MenuItem.set_ip(self);

		jp = new JPanel();
		jp.setPreferredSize(new Dimension((int) (width * 0.8), (int) height));
		this.setViewportView(jp);
		jp.setLayout(fl);
		MenuItem.set_fd(fd);
		MenuItem.set_fds(fds);

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
				file_list[i].addItemListener(new BoxListener());

				if (type_flag == 0 || type_flag == 1)
					add_box(file_list[i], jp);

			} else if (fd[i].dir) {
				file_icon = new ImageIcon("img/folder.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				file_list[i].addMouseListener(new FdListener());

				if (type_flag != 5)
					add_box(file_list[i], jp);

			} else if (fd[i].dcm) {
				file_icon = new ImageIcon("img/doc.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());

				if (type_flag == 0 || type_flag == 4)
					add_box(file_list[i], jp);

			} else if (fd[i].msc) {
				file_icon = new ImageIcon("img/music.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());

				if (type_flag == 0 || type_flag == 3)
					add_box(file_list[i], jp);

			} else if (fd[i].vid) {
				file_icon = new ImageIcon("img/video.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());

				if (type_flag == 0 || type_flag == 2)
					add_box(file_list[i], jp);
			} else
				return;
			if (fd[i].favor) {
				if (type_flag == 5) {
					System.out.println(file_list[i].getName());
					add_box(file_list[i], jp);
				}
			}
		}

		setVisible(true);
	}

	protected void add_box(JCheckBox box, JPanel jp) {
		box.setFont(f);
		jp.add(box);
		box.setPreferredSize(new Dimension(icon_size, icon_size));
		box.setHorizontalAlignment(SwingConstants.CENTER);
		box.setVerticalAlignment(SwingConstants.TOP);
		box.setHorizontalTextPosition(SwingConstants.CENTER);
		box.setVerticalTextPosition(SwingConstants.BOTTOM);
		box.setBorderPainted(true);
	}

	protected ImageIcon set_icon(ImageIcon icon) {
		Image file_img = icon.getImage();
		file_img = file_img.getScaledInstance((int) (icon_size * 0.8), (int) (icon_size * 0.8),
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(file_img);
		return icon;
	}

	public void reload() {
		makeGUI();
		System.out.println("reload");
	}
	public void set_fd(FileData[] fd) {
		this.fd = fd;
		makeGUI();
	}

	protected class FdListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JCheckBox jb = (JCheckBox) e.getSource();
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (current_path != "/")
					current_path = current_path + "/" + jb.getText();
				else
					current_path = current_path + jb.getText();
				name_list.clear();
				fm.FTPCd(current_path);
				MenuItem.set_path(current_path);
				v.set_path();
				makeGUI();
				jb.removeMouseListener(this);
			}
		}
	}

	protected class BoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox box = (JCheckBox) e.getSource();
			if (box.isSelected()) 
				name_list.add(box.getText());
			else
				name_list.remove(box.getText());
			MenuItem.set_file_list(name_list);
			MenuItem.set_ip(self);
			System.out.println("list counts : "+name_list.size());
			MenuItem.set_fd(fd);
		}

	}

	protected class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current_path != "/") {
				String go_to = current_path.substring(0, current_path.lastIndexOf("/"));
				if (go_to.equals(""))
					go_to = "/";
				fm.FTPCd(go_to);
				current_path = go_to;
				MenuItem.set_path(go_to);
				v.set_path();
				name_list.clear();
				makeGUI();
			} else {
				JLabel la = new JLabel("여기가 최상위 디렉토리 이므로 더 이상 진행할 수 없습니다.");
				la.setFont(f);
				JOptionPane.showMessageDialog(v, la);
			}
		}
	}

	protected class DropListener extends DropTargetAdapter {

		@Override
		public void drop(DropTargetDropEvent dtde) {

			if ((dtde.getDropAction() &
					DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
					dtde.acceptDrop(dtde.getDropAction());
					Transferable tr = dtde.getTransferable();
				try {
					java.util.List list = (java.util.List)
					tr.getTransferData(DataFlavor.javaFileListFlavor);
					for(int i=0; i< list.size(); i++){
						File file = (File) list.get(i);
						String fpath = file.getAbsolutePath();
						fpath = fpath.replaceAll("\\\\", "/");
						fm.FTPUpload(current_path, file.getAbsolutePath());
						makeGUI();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}