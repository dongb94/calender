package calendar;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ScheduleList extends JPanel{
	
	private Detail parent;
	private JButton add, delete;
	private ArrayList<Schedule> list;
	private Schedule schedule;
	
	ScheduleList(Detail parent) {
		this.parent = parent;
		setLayout(new FlowLayout(FlowLayout.RIGHT));
	
		add = new JButton("추가");
		delete = new JButton("삭제");
		add(add);
		add(delete);
	}
	
}
