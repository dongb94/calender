package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScheduleList extends JPanel{
	
	private Detail d;
	private ArrayList<Schedule> list= new ArrayList<>();
	private ArrayList<Schedule> dayList= new ArrayList<>();
	private Schedule schedule;
	private DB db;
	private String start_time, end_time;
	private JLabel blank;
	private JScrollPane scroll;
	private JPanel btn_panel, list_panel;
	private JButton add, delete;
	private JCheckBox[] c_list;
	private JButton[] b_list;
	private JLabel[] st_list, et_list;
	private JPanel[] t_list, p_list;
	private JScrollPane scrollPane;
	
	ScheduleList(Detail D) {
		this.setBackground(new Color(0,0,0,150));

		this.d = D;
		db = new DB();
		btn_panel = new JPanel();
		list_panel = new JPanel();
		add = new JButton("추가");
		delete = new JButton("삭제");
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		btn_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
		
		add.addActionListener(new BtnActionListener());
		delete.addActionListener(new BtnActionListener());
		
		btn_panel.setBackground(new Color(0,0,0,150));
		btn_panel.add(add);
		btn_panel.add(delete);
		add(btn_panel, BorderLayout.NORTH);
		
		SimpleDateFormat today = new SimpleDateFormat(); 
	    today.applyPattern("yyyy.MM.dd"); 
		setList(today.format(new Date()));
	}
	
	void setList(String date) {
		try {
			// 0:년  1:월  2:일  3:시작시간  4:시작분  5:끝난시간  6:끝난분
			list = db.getDaySchedule();
			System.out.println(date);
			String[] arg_piece = date.split(".");
			System.out.println(arg_piece[0]);
			String date_piece[];
			int i, j;
			System.out.println(list.get(0).getDate());
			for (int j2 = 0; j2 < list.size(); j2++) {
				System.out.println(list.get(0).getDate().split(".")[j2]);
				 
			}
		
			for (i=0; i<list.size(); i++) {
				
				date_piece = list.get(i).getDate().split(".");
				if (arg_piece[0].equals(date_piece[0])) {
					if (arg_piece[1].equals(date_piece[1])) {
						if (arg_piece[2].equals(date_piece[2])) {
							dayList.add(list.get(i));
						}
					}
				}
			}
			
			b_list = new JButton[i];
			c_list = new JCheckBox[i];
			st_list = new JLabel[i];
			et_list = new JLabel[i];
			t_list = new JPanel[i];
			p_list = new JPanel[i];
			
			for (j=0; j<i; j++) {
				date_piece = dayList.get(j).getDate().split(".");
				
				st_list[j] = new JLabel(date_piece[3] + " : " + date_piece[4]);
				et_list[j] = new JLabel(date_piece[5] + " : " + date_piece[6]);
				b_list[j] = new JButton(dayList.get(j).getTitle());
				c_list[j] = new JCheckBox();
				t_list[j] = new JPanel();
				p_list[j] = new JPanel();
				
				b_list[j].addActionListener(new ModifyActionListener());
				
				t_list[j].setLayout(new BoxLayout(t_list[i], BoxLayout.Y_AXIS));
				p_list[j].setLayout(new FlowLayout());
				
				st_list[j].setPreferredSize(new Dimension(50, 20));
				et_list[j].setPreferredSize(new Dimension(50, 20));
				b_list[j].setPreferredSize(new Dimension(170, 40));
				
				b_list[j].setBackground(new Color(10,10,10));
				t_list[j].setBackground(new Color(10,10,10));
				p_list[j].setBackground(new Color(10,10,10));
			
				t_list[j].add(st_list[j]);
				t_list[j].add(et_list[j]);
				p_list[j].add(t_list[j]);
				p_list[j].add(b_list[j]);
				p_list[j].add(c_list[j]);

				list_panel.add(p_list[j]);
			}
			scrollPane = new JScrollPane(list_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBorder(null);
			add(scrollPane);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private class BtnActionListener implements ActionListener {


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
	
	private class ModifyActionListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton btn = (JButton) e.getSource();
			// Modify
		}
		
	}
	
}
