package calendar;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class CalendarPanel extends JPanel {
    private Calendar today;
    private Font defaultFont;
    private PanNorth nPan;
    private PanSouth sPan;

    //Constructor #Entry Point
    public CalendarPanel(){
		setBackground(new Color(0,0,0,122));
        today = Calendar.getInstance();
        defaultFont = new Font("Sherif", Font.BOLD, 20);
        this.setLayout(new BorderLayout());
        nPan = new PanNorth(this);
        sPan = new PanSouth(this);
        this.add(nPan, "North");
        this.add(sPan, "Center");
    }
    //Reload Method
    public void reload(){
        nPan.reload();
        sPan.reload();
    }

    public Calendar getToday() {
        return (Calendar)today.clone();
    }
    public void setToday(Date date){
        this.today.setTime(date);
    }
    public Font getDefaultFont() {
        return defaultFont;
    }
	private void focusToday(){
		
	}
}