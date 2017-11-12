package calendar;
/* Version 1.0
 * ****************************
 * 최초작성일	2017.11.09 23:30
 * 작성자		변동건
 * ****************************
 * 수정일		2017.11.10 23:30
 * 수정내용	내부 속성 구현
 * 작성자		변동건
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
