package calendar;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.regex.*;

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
		this.setLayout(new FlowLayout());
		

		btnYearBefore = new JButton("◀◀");
		btnMonthBefore = new JButton("◀");
		textYear = new JTextField(today.get(Calendar.YEAR)+"년");
		textMonth = new JTextField(today.get(Calendar.MONTH)+1+"월", 3);
		btnMonthAfter = new JButton("▶");
		btnYearAfter = new JButton("▶▶");

		textYear.setFont(font);
		textMonth.setFont(font);

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


//		textYear.addCaretListener(new CaretListener() {
//			@Override
//			public void caretUpdate(CaretEvent e) {
//				//regex + update
//				textYear = (JTextField) e.getSource();
//				String textYear = "^[0-9]+$";
//				
//			}
//		});
//		textMonth.addCaretListener(new CaretListener() {
//			@Override
//			public void caretUpdate(CaretEvent e) {
//				//regex + update
//			}
//		});
	}
	public void reload(){
		this.today = uP.getToday();
		textYear.setText(today.get(Calendar.YEAR)+"년");
		textMonth.setText(today.get(Calendar.MONTH)+1+"월");
	}
}