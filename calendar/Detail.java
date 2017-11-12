package calendar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class Detail extends JPanel{
	
	public ScheduleList scheduleList = null;
	public Modify modify = null;
	
	Detail() {
		setLayout(new BorderLayout());
		
		scheduleList = new ScheduleList(this);
		
		add(this.scheduleList, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			removeAll();
			add(scheduleList);
			revalidate();
			repaint();
		} else {
			removeAll();
			add(modify);
			revalidate();
			repaint();
		}
	}
	
}
