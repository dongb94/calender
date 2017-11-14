package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScheduleList extends JPanel{
	
	private Detail d;
	private ArrayList<Schedule> list;
	private Schedule schedule;
	private DB db;
	private String start_time, end_time;
	private JLabel blank;
	private JScrollPane scroll;
	private JPanel btn_panel;
	private JButton add, delete;
	private JCheckBox[] c_list;
	private JLabel[] st_list, et_list;
	private JPanel[] t_list, p_list;
	
	ScheduleList(Detail D) {
		this.setBackground(new Color(0,0,0,150));

		this.d = D;
		db = new DB();
		btn_panel = new JPanel();
		add = new JButton("추가");
		delete = new JButton("삭제");
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		btn_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add.addActionListener(new MyActionListener());
		delete.addActionListener(new MyActionListener());
		
		btn_panel.setBackground(new Color(0,0,0,150));
		btn_panel.add(add);
		btn_panel.add(delete);
		add(btn_panel, BorderLayout.NORTH);
		
		try {
			//list = db.getDaySchedule(선택된 날짜);
			
			c_list = new JCheckBox[30]; // list.size()만큼 생성
			st_list = new JLabel[30];
			et_list = new JLabel[30];
			t_list = new JPanel[30];
			p_list = new JPanel[30];
			
			for (int i=0; i<30; i++) {
				
				st_list[i] = new JLabel("start time"); // list.
				et_list[i] = new JLabel("end time");
				c_list[i] = new JCheckBox("schedule name");
				t_list[i] = new JPanel();
				p_list[i] = new JPanel();
				
				t_list[i].setLayout(new BoxLayout(t_list[i], BoxLayout.Y_AXIS));
				p_list[i].setLayout(new FlowLayout());
				
				st_list[i].setPreferredSize(new Dimension(50, 20));
				et_list[i].setPreferredSize(new Dimension(50, 20));
				c_list[i].setPreferredSize(new Dimension(170, 40));
				
				c_list[i].setBackground(new Color(10,10,10));
				t_list[i].setBackground(new Color(10,10,10));
				p_list[i].setBackground(new Color(10,10,10));
			
				t_list[i].add(st_list[i]);
				t_list[i].add(et_list[i]);
				p_list[i].add(t_list[i]);
				p_list[i].add(c_list[i]);

				add(p_list[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class MyActionListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton btn = (JButton) e.getSource();
			if (btn.getText().equals("추가")) {
				d.change("modify");
			} else if (btn.getText().equals("삭제")) {
				for (int i=0; i<30; i++) { // list.size()만큼 해야함
					if (c_list[i].isSelected()) {
						// db.deleteSchedule(list[i])
					}
				}
			}
		}
		
	}
	
}
