package fileSystem;

import java.awt.*;
import javax.swing.*;

import fileSystem.MenuItem;
import fileSystem.InnerPane.BoxListener;
import fileSystem.InnerPane.FdListener;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Bookmark extends InnerPane {

	Bookmark(int flag, Viewer v, FTPManager fm) {
		super(flag, v, fm);
		
		
	}

	public void makeGUI() {
		jp.removeAll();
		this.remove(jp);
		MenuItem.set_ip(self);

		jp = new JPanel();
		jp.setPreferredSize(new Dimension((int) (width * 0.8), (int) height));
		this.setViewportView(jp);
		jp.setLayout(fl);
		fds = new FileDatas();
		fds.getFavoriteFiles();
		fd = fds.getFileDatas();

		JCheckBox[] file_list = new JCheckBox[fd.length];
		for (int i = 0; i < fd.length; i++) {
			ImageIcon file_icon;

			if (fd[i].img != -1) {
				file_icon = fd[i].thumnail;
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				add_box(file_list[i], jp);
			} else if (fd[i].dir) {
				file_icon = new ImageIcon("img/folder.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				file_list[i].addMouseListener(new FdListener());
				add_box(file_list[i], jp);
			} else if (fd[i].dcm) {
				file_icon = new ImageIcon("img/doc.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				add_box(file_list[i], jp);
			} else if (fd[i].msc) {
				file_icon = new ImageIcon("img/music.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				add_box(file_list[i], jp);
			} else if (fd[i].vid) {
				file_icon = new ImageIcon("img/video.png");
				file_icon = set_icon(file_icon);
				file_list[i] = new JCheckBox(fd[i].name.substring(1), file_icon);
				file_list[i].addItemListener(new BoxListener());
				add_box(file_list[i], jp);
			} else
				return;
		}
		MenuItem.set_bmk(this);
		setVisible(true);
		
		this.setEnabled(false);

	}
	public void reload() {
		makeGUI();
	}
}
