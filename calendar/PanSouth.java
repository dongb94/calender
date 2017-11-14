package calendar;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class PanSouth extends JPanel{
    private CalendarPanel uP;
    //Sunday ~ Saturday
    private DayPanel[] dayNames = {
            new DayPanel("일",new Color(255,0,0,150)),
            new DayPanel("월"),
            new DayPanel("화"),
            new DayPanel("수"),
            new DayPanel("목"),
            new DayPanel("금"),
            new DayPanel("토",new Color(0,0,255,150))
    };
    // day 1 ~ 28/29(Feb) 30/31 + blanks
    private DayPanel[] dayBlock = new DayPanel[42];

    //Constructor #Entry Point
    public PanSouth(CalendarPanel upperPanel){
		setBackground(new Color(0,0,0,0));
        this.uP = upperPanel;
        this.setLayout(new GridLayout(7,7,3,3));
        gridInit();
    }
    //Initializing dayNamePart(Sun~Sat) and dayBlocks, called only once
    public void gridInit(){
        for(DayPanel day: dayNames)
            this.add(day);
        for(int i=0; i<dayBlock.length; ++i) {
            dayBlock[i] = new DayPanel();
            this.add(dayBlock[i]);
        }
    }
    //Initializing dayBlock with Numbers, can be called many times
    public void dayInit(){
        for(DayPanel day: dayBlock){
            day.setText("");
            day.setDate("");
        }
        Calendar firstDay = uP.getToday();
        firstDay.set(Calendar.DAY_OF_MONTH, firstDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        int blockCount = firstDay.get(Calendar.DAY_OF_WEEK)-firstDay.getFirstDayOfWeek();
        Date d = new Date();
        
        for(int i = 0; i< firstDay.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
        	dayBlock[i + blockCount].setDate(Calendar.YEAR+"."+Calendar.MONTH+"."+(i+1));
            //Sunday
            if((i+blockCount)%7 == 0) dayBlock[i + blockCount].setForeground(new Color(255,0,0));
            //Saturday
            else if((i+blockCount)%7 == 6) dayBlock[i + blockCount].setForeground(new Color(0,0,255));
            //other
            else dayBlock[i + blockCount].setForeground(new Color(255,255,255));
            dayBlock[i + blockCount].setText(String.valueOf(i + 1));
        }
    }
    public void reload(){
    	
        dayInit();
    }
}
