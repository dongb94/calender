package calendar;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    // Lists
    private ArrayList<Schedule> totalScheduleList;
    // Constructor #Entry Point
    public PanSouth(CalendarPanel upperPanel) {
        try {
            totalScheduleList = DB.getDaySchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setBackground(new Color(0,0,0,0));
        this.uP = upperPanel;
        this.setLayout(new GridLayout(7,7,3,3));
        gridInit();
        dayInit();
    }
    // Initializing dayNamePart(Sun~Sat) and dayBlocks, called only once
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

            day.setTitle("");

            day.setDetailPane(uP.getDetailPane());

        }
        Calendar firstDay = uP.getToday();
        firstDay.set(Calendar.DAY_OF_MONTH, firstDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        int blockCount = firstDay.get(Calendar.DAY_OF_WEEK)-firstDay.getFirstDayOfWeek();
        Date d = new Date();


        // 1.get Titles only for this month
        HashMap<String, Schedule> scheduleList = new HashMap<String, Schedule>();
        Iterator it = totalScheduleList.iterator();
        Schedule tempSchedule;
        Calendar calTemp = Calendar.getInstance();
        while(it.hasNext()){
            try {
                calTemp.setTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SS")
                        .parse((tempSchedule = (Schedule) it.next()).getDate()));
                if (calTemp.get(Calendar.YEAR) == firstDay.get(Calendar.YEAR)) {
                    if (calTemp.get(Calendar.MONTH) == firstDay.get(Calendar.MONTH)) {
                        scheduleList.put(
                                String.valueOf(calTemp.get(Calendar.YEAR)+"/"+calTemp.get(Calendar.MONTH)+"/"+calTemp.get(Calendar.DATE)),
                                        tempSchedule);
                    } else if (calTemp.get(Calendar.MONTH) >= firstDay.get(Calendar.MONTH))
                        break;
                }
            } catch(Exception e){}
        }
        // 2. set Titles and Dates for this month
        calTemp = (Calendar)firstDay.clone();
        for(int i = 0; i< firstDay.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
            dayBlock[i + blockCount].setDate(String.valueOf(calTemp.get(Calendar.YEAR)+"/"+calTemp.get(Calendar.MONTH)+"/"+calTemp.get(Calendar.DATE)));
            calTemp.add(Calendar.DATE, 1);
            //Sunday
            if((i+blockCount)%7 == 0) dayBlock[i + blockCount].setForeground(new Color(255,0,0));
                //Saturday
            else if((i+blockCount)%7 == 6) dayBlock[i + blockCount].setForeground(new Color(0,0,255));
                //other
            else dayBlock[i + blockCount].setForeground(new Color(255,255,255));
            dayBlock[i + blockCount].setText(String.valueOf(i + 1));
            if(scheduleList.get(dayBlock[i + blockCount].getDate()) != null)
                dayBlock[i + blockCount].setTitle(scheduleList.get(dayBlock[i + blockCount].getDate()).getTitle());
        }
        /*
        // 1.set Date(1~31) for each panel
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
        */
    }
    // Reloads GUI
    public void reload(){
    	
        dayInit();
    }
}
