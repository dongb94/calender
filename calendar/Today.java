package calendar;
/* Version 1.0
 * ****************************
 * 최초작성일	2017.11.09 11:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.13 02:31
 * 수정내용	내부 함수 및 쓰레드 구현
 * 작성자		변동건
 * ****************************
 * ****************************
 * 수정일		2017.11.13 16:59
 * 수정내용	쓰레드 수정 및 텍스트 중앙배치
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.14 01:04
 * 수정내용	setText 재정의
 * 작성자		변동건
 * ****************************
 */
/**화면 오른쪽 상단 현재시간을 보여주는 클래스*/


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;

public class Today extends JPanel implements Runnable{
	private Date d;
	private JLabel now;
	Today(){
		makeGUI();
		updateText();
		addMouseListener(new TodayListener());
	}
	private void setText(){
		
	}
	private void makeGUI(){
		setBackground(new Color(0,0,0,0));
		setLayout(new BorderLayout());
		
		now = new JLabel();
		now.setForeground(Color.white);
		
		JButton exitButton = new JButton("프로그램 종료");
		exitButton.setName("exit");
		exitButton.addMouseListener(new TodayListener());
		exitButton.setBorder(null);
		
		add(now,BorderLayout.CENTER);
		add(exitButton,BorderLayout.NORTH);
		setVisible(true);
	}
	private void updateText(){
		d =new Date();
		String time = null;
		if(d.getHours()<10)
			time = "0"+d.getHours(); 
		else
			time = ""+d.getHours();

		if(d.getMinutes()<10)
			time += ":0"+d.getMinutes();
		else
			time += ":"+d.getMinutes();
		
		if(d.getSeconds()<10)
			time += ":0"+d.getSeconds();
		else
			time += ":"+d.getSeconds();		
		now.setText((d.getYear()+1900)+"."+(d.getMonth()+1)+"."+d.getDate()+"("+d.toString().substring(0,3)+")"+" "+time);
	}
	public void setTimer(){
		Font f = now.getFont();
		f = new Font(f.getFamily(), f.getStyle(), (int)(getHeight()/4.5));
		now.setFont(f);
	}
	public void run() {
		while(true){
			updateText();
			repaint();
			try{
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}
	
	class TodayListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			JButton exit = (JButton)e.getSource();
			if(exit.getName().equals("exit")){
				System.exit(1);
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {	}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
