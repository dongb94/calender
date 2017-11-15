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
		
		setVisible(true);
	}

	
	public void setPanel(){
		scheduleList.setBounds(0, 0, getWidth(), getHeight());
	}
	
	public void setScheduleList(String date) {
		scheduleList = new ScheduleList(this, date);
		change("scheduleList");
		setVisible(true);
	}
	
	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			this.removeAll();
			this.add(scheduleList);
			scheduleList.setBounds(0, 0, getWidth(), getHeight());
			scheduleList.setVisible(true);
			this.updateUI();
		} else {
			this.removeAll();
			this.add(modify);
			modify.setBounds(0, 0, getWidth(), getHeight());
			modify.setVisible(true);
			this.updateUI();
		}
	}

	public void actionPerformed(ActionEvent e) {
		change("modify");
	}

	public void setModify() {
		modify.set_modify();
	}
	public void setCreate() {
		modify.set_create();
	}
	public void ifMod() {
		modify.if_mod();
	}
	public void ifCre(String year, String mon, String day) {
		modify.if_cre(year, mon, day);
	}
	public void set_schedule(Schedule schedule){
		modify.set_schedule(schedule);
	}
	public void set_list(){
		setScheduleList(scheduleList.get_date());
	}

}
