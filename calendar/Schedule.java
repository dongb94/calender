package calendar;
/* Version 1.0
 * ****************************
 * �����ۼ���	2017.11.09 11:30
 * �ۼ���		������
 * ****************************
 * ������		2017.11.10 11:30
 * ��������	���� �Ӽ� ����
 * �ۼ���		������
 * ****************************
 */
public class Schedule {
	String title;
	String date;
	String contents;
	
	Schedule(String title, String date, String contents){
		this.title = title;
		this.date = date;
		this.contents = contents;
	}
	String getTitle(){
		return title;
	}
	String getDate(){
		return date;
	}
	String getContents(){
		return contents;
	}
	void setTitle(String title){
		this.title = title;
	}
	void setDate(String date){
		this.date = date;
	}
	void setContents(String contents){
		this.contents = contents;
	}
}
