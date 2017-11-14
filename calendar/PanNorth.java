package calendar;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class PanNorth extends JPanel{
	private CalendarPanel uP;
	private Calendar today;
	private JButton btnMonthBefore, btnMonthAfter, btnYearBefore, btnYearAfter;
	private JTextField textYear, textMonth;

	//Constructor #Entry Point
	public PanNorth(CalendarPanel upperPanel){
		this.uP = upperPanel;
		this.today = uP.getToday();
		Font font = uP.getDefaultFont();
		setBackground(new Color(0,0,0,0));


		btnYearBefore = new JButton("◀◀");
		btnMonthBefore = new JButton("◀");
		textYear = new JTextField(today.get(Calendar.YEAR)+"년");
		textMonth = new JTextField(today.get(Calendar.MONTH)+1+"월", 3);
		btnMonthAfter = new JButton("▶");
		btnYearAfter = new JButton("▶▶");

		textYear.setFont(font);
		textMonth.setFont(font);

		textYear.setBorder(null);
		textMonth.setBorder(null);

		this.add(btnYearBefore);
		this.add(btnMonthBefore);
		this.add(textYear);
		this.add(textMonth);
		this.add(btnMonthAfter);
		this.add(btnYearAfter);


		btnMonthBefore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today.add(Calendar.MONTH, -1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});

		btnMonthAfter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today.add(Calendar.MONTH, 1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});


		btnYearBefore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today.add(Calendar.YEAR, -1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});

		btnYearAfter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today.add(Calendar.YEAR, 1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});


		textYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField textField = (JTextField) e.getSource();
				String textStr = textField.getText();
				String textNum = textStr.replaceAll("[^0-9]", "");

				today.set(Calendar.YEAR, Integer.parseInt(textNum));
				uP.setToday(today.getTime());
				uP.reload();
			}
		});
		textMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField textField = (JTextField) e.getSource();
				String textStr = textField.getText();
				String textNum = textStr.replaceAll("[^0-9]", "");

				today.set(Calendar.MONTH, Integer.parseInt(textNum)-1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});
	}


	public void reload(){
		this.today = uP.getToday();
		textYear.setText(today.get(Calendar.YEAR)+"년");
		textMonth.setText(today.get(Calendar.MONTH)+1+"월");
	}
}