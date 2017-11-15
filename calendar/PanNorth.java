package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class PanNorth extends JPanel{
	private CalendarPanel uP;
	private Calendar today;
	private JButton btnMonthBefore, btnMonthAfter, btnYearBefore, btnYearAfter;
	private JTextField textYear, textMonth;
	
	ImageIcon left_image = new ImageIcon("img/left.png");
	ImageIcon right_image = new ImageIcon("img/right.png");
	ImageIcon left_image_s = new ImageIcon("img/left_s.png");
	ImageIcon right_image_s = new ImageIcon("img/right_s.png");
	Image image_left = left_image.getImage();
	Image image_right = right_image.getImage();
	Image image_left_s = left_image_s.getImage();
	Image image_right_s = right_image_s.getImage();
	
	double width;
	double height;
	
	//Constructor #Entry Point
	public PanNorth(CalendarPanel upperPanel){
		this.uP = upperPanel;
		this.today = uP.getToday();
		Font font = uP.getDefaultFont();
		setBackground(new Color(0,0,0));
		
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width * 0.8;
		height = res.height * 0.8;
		width = width * 0.3;
		height = height * 0.82;
		
		
		image_left = image_left.getScaledInstance((int) height / 13, (int) height / 26, java.awt.Image.SCALE_SMOOTH);
		image_right = image_right.getScaledInstance((int) height / 13, (int) height / 26, java.awt.Image.SCALE_SMOOTH);
		image_left_s = image_left_s.getScaledInstance((int) height / 20, (int) height / 26, java.awt.Image.SCALE_SMOOTH);
		image_right_s = image_right_s.getScaledInstance((int) height / 20, (int) height / 26, java.awt.Image.SCALE_SMOOTH);
		left_image = new ImageIcon(image_left);
		right_image = new ImageIcon(image_right);
		left_image_s = new ImageIcon(image_left_s);
		right_image_s = new ImageIcon(image_right_s);

		btnYearBefore = new JButton(left_image);
	
		btnMonthBefore = new JButton(left_image_s);
		textYear = new JTextField(today.get(Calendar.YEAR)+"년",5);
		textMonth = new JTextField(today.get(Calendar.MONTH)+1+"월", 3);
		btnMonthAfter = new JButton(right_image_s);
		btnYearAfter = new JButton(right_image);
		
		btnYearBefore.setBackground(new Color(0,0,0,0));
		btnMonthBefore.setBackground(new Color(0,0,0,0));
		btnYearAfter.setBackground(new Color(0,0,0,0));
		btnMonthAfter.setBackground(new Color(0,0,0,0));
		btnYearBefore.setBorder(null);
		btnMonthBefore.setBorder(null);
		btnYearAfter.setBorder(null);
		btnMonthAfter.setBorder(null);

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
				btnMonthBefore.setOpaque(false);
				today.add(Calendar.MONTH, -1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});

		btnMonthAfter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnMonthAfter.setOpaque(false);
				today.add(Calendar.MONTH, 1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});


		btnYearBefore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnYearBefore.setOpaque(false);
				today.add(Calendar.YEAR, -1);
				uP.setToday(today.getTime());
				uP.reload();
			}
		});

		btnYearAfter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnYearAfter.setOpaque(false);
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