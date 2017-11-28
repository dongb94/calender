package fileSystem;

import java.awt.*;
import javax.swing.*;

public class InnerPane extends JPanel{
	/*
	 * 0 = whole
	 * 1 = picture
	 * 2 = video
	 * 3 = music
	 * 4 = word
	 */
	private int type_flag;
	
	InnerPane(int flag){
		type_flag = flag;
	}
}
