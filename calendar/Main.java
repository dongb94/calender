package calendar;
/* 코드 수정시 밑에 추가로 기입할 것
 * ****************************
 * 최초작성일	2017.11.09 11:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.09 11:30
 * 수정내용	없음
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
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
