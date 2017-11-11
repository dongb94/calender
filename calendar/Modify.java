package calendar;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

public class Modify extends JPanel {

	Schedule modified_schedule = new Schedule();

	JTextField schedule_title = new JTextField(20);

	JComboBox duration_start_month;
	JComboBox duration_start_date;
	JComboBox duration_end_month;
	JComboBox duration_end_date;
	JComboBox duration_start_time;
	JComboBox duration_end_time;
	JComboBox duration_start_minute;
	JComboBox duration_end_minute;

	JLabel la_title = new JLabel("제목");
	JLabel la_duration = new JLabel("날짜");
	JLabel la_content = new JLabel("내용");
	JLabel la_start1 = new JLabel("시작 ");
	JLabel la_end1 = new JLabel("종료 ");
	JLabel la_time = new JLabel("시간");
	JLabel la_start2 = new JLabel("시작 ");
	JLabel la_end2 = new JLabel("종료 ");

	JTextArea schedule_content = new JTextArea();

	JButton confirm_button = new JButton("확인");

	String du_mon[] = new String[12];
	String du_day[] = new String[31];
	String du_time[] = new String[24];
	String du_minute[] = new String[60];

	String title;
	String duration;
	String content;
	String start_mon;
	String start_day;
	String end_mon;
	String end_day;
	String start_time;
	String start_minute;
	String end_time;
	String end_minute;

	Modify() {
		setLayout(null);

		for (int i = 1; i < 13; i++)
			du_mon[i - 1] = Integer.toString(i) + "월";
		for (int i = 1; i < 32; i++)
			du_day[i - 1] = Integer.toString(i) + "일";
		for (int i = 0; i < 24; i++)
			du_time[i] = Integer.toString(i) + "시";
		for (int i = 0; i < 60; i++)
			du_minute[i] = Integer.toString(i) + "분";

		duration_start_month = new JComboBox(du_mon);
		duration_start_date = new JComboBox(du_day);
		duration_end_month = new JComboBox(du_mon);
		duration_end_date = new JComboBox(du_day);
		duration_start_time = new JComboBox(du_time);
		duration_end_time = new JComboBox(du_time);
		duration_start_minute = new JComboBox(du_minute);
		duration_end_minute = new JComboBox(du_minute);

		confirm_button.setBounds(200, 10, 100, 50);
		la_title.setBounds(10, 50, 30, 30);
		schedule_title.setBounds(10, 80, 290, 30);
		la_duration.setBounds(10, 110, 30, 30);
		la_start1.setBounds(10, 140, 50, 30);
		duration_start_month.setBounds(40, 140, 60, 30);
		duration_start_date.setBounds(100, 140, 60, 30);
		la_end1.setBounds(160, 140, 50, 30);
		duration_end_month.setBounds(190, 140, 60, 30);
		duration_end_date.setBounds(250, 140, 60, 30);
		la_time.setBounds(10, 170, 50, 30);
		la_start2.setBounds(10, 200, 50, 30);
		duration_start_time.setBounds(40, 200, 60, 30);
		duration_start_minute.setBounds(100, 200, 60, 30);
		la_end2.setBounds(160, 200, 50, 30);
		duration_end_time.setBounds(190, 200, 60, 30);
		duration_end_minute.setBounds(250, 200, 60, 30);
		la_content.setBounds(10, 230, 50, 30);
		schedule_content.setBounds(10, 270, 290, 350);

		add(confirm_button);
		add(la_title);
		add(schedule_title);
		add(la_duration);
		add(la_start1);
		add(duration_start_month);
		add(duration_start_date);
		add(la_end1);
		add(duration_end_month);
		add(duration_end_date);
		add(la_time);
		add(la_start2);
		add(duration_start_time);
		add(duration_start_minute);
		add(la_end2);
		add(duration_end_time);
		add(duration_end_minute);
		add(la_content);
		add(schedule_content);

		confirm_button.addActionListener(new ConfirmAction());

		setVisible(true);
	}

	public Schedule modify_schedule() {

		return modified_schedule;
	}

	public Schedule create_schedule() {

		return modified_schedule;
	}

	class ConfirmAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			title = schedule_title.getText();
			content = schedule_content.getText();
			start_mon = (String) duration_start_month.getSelectedItem();
			start_mon = start_mon.replace("월", "");
			start_day = (String) duration_start_date.getSelectedItem();
			start_day = start_day.replace("일", "");
			end_mon = (String) duration_end_month.getSelectedItem();
			end_mon = end_mon.replace("월", "");
			end_day = (String) duration_end_date.getSelectedItem();
			end_day = end_day.replace("일", "");
			start_time = (String) duration_start_time.getSelectedItem();
			start_time = start_time.replace("시", "");
			start_minute = (String) duration_start_minute.getSelectedItem();
			start_minute = start_minute.replace("분", "");
			end_time = (String) duration_end_time.getSelectedItem();
			end_time = end_time.replace("시", "");
			end_minute = (String) duration_end_minute.getSelectedItem();
			end_minute = end_minute.replace("분", "");

			System.out.println(title);
			System.out.print(start_mon+"월");
			System.out.print(start_day+"일~");
			System.out.print(end_mon+"월");
			System.out.println(end_day+"일");
			System.out.print(start_time+"시");
			System.out.print(start_minute+"분~");
			System.out.print(end_time+"시");
			System.out.print(end_minute+"분");
			System.out.println();
			System.out.println(content);

		}

	}

}
