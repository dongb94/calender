package fileSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PhotoPreview extends JFrame {
	public PhotoPreview(String path) {
		ImageIcon originIcon = new ImageIcon(path);
		System.out.println(path);
		Image orign = originIcon.getImage();
		Image changedImage = orign.getScaledInstance(700, 700, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImage);
		JLabel jl = new JLabel(Icon);
		JLabel lb_path = new JLabel("");
		lb_path.setForeground(Color.DARK_GRAY);
		lb_path.setFont(lb_path.getFont().deriveFont(lb_path.getFont().getStyle() | Font.BOLD));
		lb_path.setBackground(new Color(0,0,0,10));
		
		lb_path.setOpaque(true);
		
		add(lb_path, BorderLayout.NORTH);
		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}

			public void mouseEntered(MouseEvent e) {
				lb_path.setText(path);
			}
			
			public void mouseExited(MouseEvent e) {
				lb_path.setText("");
			}
		});
		
		add(jl);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setTitle(path);
		setUndecorated(true);
		setVisible(true);
		setResizable(true);
	}
}