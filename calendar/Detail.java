package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Detail extends JPanel implements ActionListener {

	public ScheduleList scheduleList = null;
	public Modify modify = null;
	private JButton add, delete;
	Dimension size;

	String name;
	
	private double width;
	private double height;

	Detail(String name) {
		this.name = name;
	}

	Detail() {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width*0.8;
		height = res.height*0.8;
		width = width*0.3;
		height = height*0.82;
		
		setLayout(null);

		scheduleList = new ScheduleList(this);
		modify = new Modify(this);
		add(scheduleList);
		scheduleList.setBounds(0, 0, (int)width, (int)height);

		setBackground(Color.green);
		setVisible(true);
	}

	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			this.removeAll();
			this.add(scheduleList);
			scheduleList.setBounds(0, 0, (int)width, (int)height);
			scheduleList.setVisible(true);
			this.updateUI();
		} else {
			this.removeAll();
			this.add(modify);
			modify.setBounds(0, 0, (int)width, (int)height);
			modify.setVisible(true);
			this.updateUI();
		}
	}

	public void actionPerformed(ActionEvent e) {
		change("modify");
	}

}
