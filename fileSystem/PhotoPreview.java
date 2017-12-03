package fileSystem;
import javax.swing.*;
import java.awt.*;

public class PhotoPreview {
			ImageIcon originIcon = new ImageIcon(path);
		System.out.println(path);
		Image orign = originIcon.getImage();
		Image changedImage = orign.getScaledInstance(500,500, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImage);
		JLabel jl = new JLabel(Icon);
		add(jl);
		setSize(500,500);
		setTitle(path);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);
}
