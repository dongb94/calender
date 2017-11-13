package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

	Detail(String name) {
		this.name = name;
	}

	Detail() {
		setLayout(null);

		scheduleList = new ScheduleList(this);
		modify = new Modify(this);
		add(scheduleList);
		scheduleList.setBounds(0, 0, 350, 700);

		setBackground(Color.green);
		setVisible(true);
	}

	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			this.removeAll();
			this.add(scheduleList);
			scheduleList.setBounds(0, 0, 350, 700);
			scheduleList.setVisible(true);
			this.updateUI();
		} else {
			this.removeAll();
			this.add(modify);
			modify.setBounds(0, 0, 350, 700);
			modify.setVisible(true);
			this.updateUI();
		}
	}

	public void actionPerformed(ActionEvent e) {
		change("modify");
	}

}
