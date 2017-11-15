package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScheduleList extends JPanel{
	
	private Detail detail;
	private ArrayList<Schedule> list= new ArrayList<>();
	private ArrayList<Schedule> dayList= new ArrayList<>();
	private Schedule schedule;
	private DB db;
	private String start_time, end_time, date;
	private JLabel blank;
	private JScrollPane scroll;
	private JPanel btn_panel, list_panel;
	private JButton add, delete;
	private JCheckBox[] c_list;
	private JButton[] b_list;
	private JLabel[] st_list, et_list;
	private JPanel[] t_list, p_list;
	private JScrollPane scrollPane;
	private String arg_piece[]= new String[3];
	private String date_piece[] = new String[7];
	private int j;
	
	private double width, height;
	
	private Schedule throw_schedule;
	
	private Modify m;
	
	Font f = new Font("휴먼매직체", Font.BOLD, 20);
	
	ImageIcon plus_image = new ImageIcon("img/plus.png");
	ImageIcon delete_image = new ImageIcon("img/delete.png");
	Image image_plus = plus_image.getImage();
	Image image_delete = delete_image.getImage();
	
	ScheduleList() {
		this.setBackground(new Color(0,0,0,150));
		db = new DB();
		btn_panel = new JPanel();
		list_panel = new JPanel();
		
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8;
		height = res.height * 0.8;
		width = width * 0.3;
		height = height * 0.82;
		
		image_plus = image_plus.getScaledInstance((int) height / 13, (int) height / 13, java.awt.Image.SCALE_SMOOTH);
		image_delete = image_delete.getScaledInstance((int) height / 15, (int) height / 15, java.awt.Image.SCALE_SMOOTH);
		plus_image = new ImageIcon(image_plus);
		delete_image = new ImageIcon(image_delete);
		
		add = new JButton(plus_image);
		delete = new JButton(delete_image);
		
		delete.setBounds((int) width  / 30, (int) height / 80, 50, 50);
		add.setBounds((int) width  / 30+ 50 , (int) height / 80, 50, 50);
		
		add.setBackground(new Color(0,0,0,0));
		delete.setBackground(new Color(0,0,0,0));
		add.setBorder(null);
		delete.setBorder(null);
		add.setName("add");
		delete.setName("delete");
		
		add(add);
		add(delete);
		
		setLayout(null);
		list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
		list_panel.setBackground(Color.green);
		list_panel.setBounds((int) width * 10 / 300, (int) height / 80+60, (int) width * 280 / 300, (int)height*4/5);
		
		scrollPane = new JScrollPane(list_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		add(scrollPane);
		
		add(list_panel);
		
		add.addActionListener(new BtnActionListener());
		delete.addActionListener(new BtnActionListener());
		
	}
	ScheduleList(Detail detail) {
		this();
		this.detail = detail;
		
		SimpleDateFormat today = new SimpleDateFormat(); 
	    today.applyPattern("yyyy.MM.dd");
	    
		setList(today.format(new Date()));
	}
	ScheduleList(Detail detail, String date) {
		this();
		this.detail = detail;
		
		setList(date);
	}
	public void setList(String date) {
		try {
			// 0:년  1:월  2:일  3:시작시간  4:시작분  5:끝난시간  6:끝난분
			StringTokenizer argst = new StringTokenizer(date);
			StringTokenizer datest;
			int i, k;
			list = db.getDaySchedule();
			this.date = date;
			
			System.out.println(date);
			
			k=0;
			while (argst.hasMoreElements()) {
				arg_piece[k] = new String(argst.nextToken("."));
				k++;
			}

			for (i=0; i<list.size(); i++) {
				datest = new StringTokenizer(list.get(i).getDate());
				k=0;
				while (datest.hasMoreElements()) {
					date_piece[k] = new String(datest.nextToken("."));;
					k++;
				}
				if (arg_piece[0].equals(date_piece[0])) {
					if (arg_piece[1].equals(date_piece[1])) {
						if (arg_piece[2].equals(date_piece[2])) {
							dayList.add(list.get(i));
						}
					}
				}
			}
			
			if (dayList.size() == 0) {
				JLabel notice = new JLabel("일정이 없습니다. 추가해주세요.");
				list_panel.add(notice);
				add(list_panel);
				return;
			}
			
			b_list = new JButton[dayList.size()]; //dayList.size()
			c_list = new JCheckBox[dayList.size()];
			st_list = new JLabel[dayList.size()];
			et_list = new JLabel[dayList.size()];
			t_list = new JPanel[dayList.size()];
			p_list = new JPanel[dayList.size()];

			for (j=0; j<dayList.size(); j++) { //dayList.size()
				if (dayList.isEmpty() == false) {
					datest = new StringTokenizer(dayList.get(j).getDate());
					k=0;
					while (argst.hasMoreElements()) {
						date_piece[k] = new String(datest.nextToken("."));
						k++;
					}
					
					st_list[j] = new JLabel(date_piece[3] + " : " + date_piece[4]); //date_piece[3] + " : " + date_piece[4]
					et_list[j] = new JLabel(date_piece[5] + " : " + date_piece[6]); //date_piece[5] + " : " + date_piece[6]
					b_list[j] = new JButton(dayList.get(j).getTitle()); //dayList.get(j).getTitle()
					st_list[j].setFont(f);
					st_list[j].setForeground(Color.white);
					et_list[j].setFont(f);
					et_list[j].setForeground(Color.white);
					b_list[j].setFont(f);
					b_list[j].setForeground(Color.white);
					c_list[j] = new JCheckBox();
					t_list[j] = new JPanel();
					p_list[j] = new JPanel();
					
					b_list[j].addActionListener(new ModifyActionListener(dayList.get(j)));
					
					t_list[j].setLayout(new BoxLayout(t_list[j], BoxLayout.Y_AXIS));
					p_list[j].setLayout(new FlowLayout());
					
					st_list[j].setPreferredSize(new Dimension(80, 20));
					et_list[j].setPreferredSize(new Dimension(80, 20));
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
			}
			
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
			System.out.println(btn.getName());
			if (btn.getName().equals("add")) {
				detail.change("modify");
				detail.setCreate();
				System.out.println("this called 추가 : "+arg_piece[0]+arg_piece[1]+arg_piece[2]);
				detail.ifCre(arg_piece[0],arg_piece[1],arg_piece[2]);
			} else if (btn.getName().equals("delete")) {
				for (int i=0; i<list.size(); i++) { // list.size()만큼 해야함
					if (c_list[i].isSelected()) {
						try {
							db.deleteSchedule(list.get(i));
							detail.setScheduleList(date);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
	private class ModifyActionListener implements ActionListener {
		
		Schedule mod_schedule;
		
		ModifyActionListener(Schedule mod_schedule){
			this.mod_schedule = mod_schedule;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton btn = (JButton) e.getSource();
			detail.setModify();
			detail.set_schedule(mod_schedule);
			detail.ifMod();
			//detail.set_schedule(throw_schedule);
			detail.setScheduleList(date);
			detail.change("modify");
		}
		
	}
	public String get_date(){
		return this.date;
	}
	
}
