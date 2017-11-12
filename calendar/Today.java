package calendar;
/* Version 1.0
 * ****************************
 * 최초작성일	2017.11.09 11:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.13 02:31
 * 수정내용	클래스 함수 및 쓰레드 구현
 * 작성자		변동건
 * ****************************
 */


import java.awt.Color;
import java.awt.Font;
/**화면 오른쪽 상단 현재시간을 보여주는 클래스*/
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;

public class Today extends JPanel implements Runnable{
	private Date d;
	private JLabel now;
	Today(){
		d =new Date();
		makeGUI();
		updateText();
	}
	private void makeGUI(){
		now = new JLabel();
		Font f = now.getFont();
		f = new Font(f.getFamily(), f.getStyle(), 20);
		now.setFont(f);
		add(now);
		
		setVisible(true);
	}
	private void updateText(){
		now.setSize(getSize());
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
}
