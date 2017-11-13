package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScheduleList extends JPanel{
	
	private Detail parent;
	private DB db = new DB();
	private ArrayList<Schedule> list;
	private Schedule schedule;
	private String start_time, end_time;
	private JLabel blank;
	private JScrollPane scroll;
	
	ScheduleList(Detail parent) {
		this.parent = parent;
		
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		setBackground(Color.BLUE);
		
		try {
			//list = db.getDaySchedule(); // 날짜 인자 넘겨야함
			//JButton[] b_list = new JButton[list.size()];
			//JLabel[] l_list = new JLabel[list.size() * 3];
			JCheckBox[] c_list = new JCheckBox[30];
			JLabel[] t_list = new JLabel[30];
			
			for (int i=0; i<10; i++) {
				t_list[i] = new JLabel("time");
				c_list[i] = new JCheckBox("schedule name");
				t_list[i].setPreferredSize(new Dimension(50, 40));
				c_list[i].setPreferredSize(new Dimension(170, 40));
			
				add(t_list[i]);
				add(c_list[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
