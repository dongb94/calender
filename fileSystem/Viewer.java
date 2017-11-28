package fileSystem;

import java.awt.*;
import javax.swing.*;

public class Viewer extends JTabbedPane {
	private String current_path;

	InnerPane home_p = new InnerPane(0);
	InnerPane picture_p = new InnerPane(1);
	InnerPane video_p = new InnerPane(2);
	InnerPane music_p = new InnerPane(3);
	InnerPane word_p = new InnerPane(4);

	Viewer(){
		setTabPlacement(LEFT);
		
		addTab("HOME", home_p );
		addTab("사진", picture_p);
		addTab("동영상", video_p);
		addTab("음악", music_p);
		addTab("문서", word_p);
	}

}
