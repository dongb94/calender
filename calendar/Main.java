package calendar;
/* Version 1.0
 * ****************************
 * 최초작성일	2017.11.09 11:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.10 11:30
 * 수정내용	Calendar 및 Detail 클래스  추가
 * 작성자		변동건
 * ****************************
 */

/**메인 프레임*/
import javax.swing.JFrame;

public class Main extends JFrame{

	Main(){
		setSize(1500,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		makeGUI();
		
		setVisible(true);
	}
	
	void makeGUI(){
		Calendar C = new Calendar();
		Detail D = new Detail();
		add(C);
		add(D);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
