package calendar;
/* Version 1.0
 * ****************************
 * 최초작성일	2017.11.09 23:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.10 23:30
 * 수정내용	Calendar 및 Detail 클래스  추가
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.12 23:30
 * 수정내용	Calendar 클래스 변경 -> CalendarPanel
 * 			해상도에 맞춰서 프레임 사이즈 조절, 프레임 사이즈 변경 불가능하도록 구현
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.13 21:30
 * 수정내용	프래임 배경 투명하게, 제목표시줄 제거
 * 작성자		변동건
 * ****************************
 */

/**메인 프레임*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class Main extends JFrame{
	private Main main;
	private Point initPoint;

	private double width;
	private double height;
	Main(){
		main = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		makeGUI();
		
		setResizable(false);
		setVisible(true);
	}
	
	void makeGUI(){
		//opaque
		setUndecorated(true);
		setBackground(new Color(0,0,0,122));

		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width*0.8;
		height = res.height*0.8;
		setSize((int)(width), (int)(height));
		setLocation((int)(width*0.1), (int)(height*0.1));
		setLayout(null);
		
		//set calendar
		CalendarPanel C = new CalendarPanel();
		C.setSize((int)(width*0.7), (int)(height));
		C.setLocation(0, 0);
		//set today
		Today T = new Today(C);
		T.setSize((int)(width*0.3), (int)(height*0.19));
		T.setLocation((int)(width*0.7),0);
		T.setTimer();
		Thread thread = new Thread(T);
		thread.start();
		//set detail
		Detail D = new Detail();
		D.setSize((int)(width*0.3), (int)(height*0.81));
		D.setLocation((int)(width*0.7), (int)(height*0.19));
		D.setPanel();
		
		add(C);
		add(T);
		add(D);

		// Panel Moving Methods
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				main.initPoint = e.getPoint();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				main.setLocation(
						main.getX()+(int)(e.getX()-initPoint.getX()),
						main.getY()+(int)(e.getY()-initPoint.getY()));
			}
		});
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
