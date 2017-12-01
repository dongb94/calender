package fileSystem;

import java.awt.*;
import javax.swing.*;
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
	private JPanel jp = new JPanel();
	private String current_path;
	private Viewer v;

	private FTPManager fm;

	InnerPane(int flag, Viewer v) {

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
		this.setViewportView(jp);
		jp.setLayout(fl);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

		this.v = v;
		current_path = MenuItem.get_path();
		fm = v.get_fm();

		makeGUI();
	}

	public void makeGUI() {
		jp.removeAll();

		if (type_flag == 0 || type_flag == 1 || type_flag == 2 || type_flag == 3 || type_flag == 4) {
			jp.add(to_higher);
			to_higher.setPreferredSize(new Dimension(icon_size, icon_size));
		}

		to_higher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String go_to = current_path.substring(0, current_path.lastIndexOf("/"));
				fm.FTPCd(go_to);
				current_path = go_to;
				MenuItem.set_path(go_to);
			}

		});

		setVisible(true);
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
	public class LabelListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			
		}
	}
}