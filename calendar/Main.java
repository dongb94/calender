package calendar;
/* Version 1.0
 * ****************************
 * �����ۼ���	2017.11.09 11:30
 * �ۼ���		������
 * ****************************
 * ������		2017.11.10 11:30
 * ��������	Calendar �� Detail Ŭ����  �߰�
 * �ۼ���		������
 * ****************************
 */

/**���� ������*/
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
