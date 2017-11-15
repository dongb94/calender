package calendar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class Detail extends JPanel implements ActionListener {

	public ScheduleList scheduleList = null;
	public Modify modify = null;
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
		Thread t = new Thread(new reloadScheduleList(this, date));
		t.start();
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
	
	class reloadScheduleList extends Thread {
		Detail d;
		String date;
		reloadScheduleList(Detail d,String date){
			this.d = d;
			this.date = date;
		}
		public void run(){
			scheduleList = new ScheduleList(d , date);
			change("scheduleList");
		}
	}
}


