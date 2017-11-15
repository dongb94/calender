package calendar;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PanSouth extends JPanel{
    private CalendarPanel uP;
    //Sunday ~ Saturday
    private DayPanel[] dayNames = {
            new DayPanel(this,"일",new Color(255,0,0,150)),
            new DayPanel(this,"월"),
            new DayPanel(this,"화"),
            new DayPanel(this,"수"),
            new DayPanel(this,"목"),
            new DayPanel(this,"금"),
            new DayPanel(this,"토",new Color(0,0,255,150))
    };
    // day 1 ~ 28/29(Feb) 30/31 + blanks
    private DayPanel[] dayBlock = new DayPanel[42];
    // Constructor #Entry Point
    public PanSouth(CalendarPanel upperPanel) {
        this.setBackground(new Color(0,0,0,0));
        this.uP = upperPanel;
        this.setLayout(new GridLayout(7,7,3,3));
        gridInit();
        dayInit();
        reload r = new reload();
        Thread t = new Thread(r);
        t.setName("init");
        t.start();
    }
    // Initializing dayNamePart(Sun~Sat) and dayBlocks, called only once
    public void gridInit(){
        for(DayPanel day: dayNames)
            this.add(day);
        for(int i=0; i<dayBlock.length; ++i) {
            dayBlock[i] = new DayPanel(this);
            this.add(dayBlock[i]);
        }
    }
    //Initializing dayBlock with Numbers, can be called many times
    public void dayInit(){
        for(DayPanel day: dayBlock){
            day.setText("");

            day.resetTitle();

            day.setDetailPane(uP.getDetailPane());
        }
        Calendar firstDay = uP.getToday();
        firstDay.set(Calendar.DAY_OF_MONTH, firstDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        int blockCount = firstDay.get(Calendar.DAY_OF_WEEK)-firstDay.getFirstDayOfWeek();
        
       

        // 1. set Titles and Dates for this month
        Date d = new Date();
        String selectedDate=ScheduleList.get_date().substring(8,10);
        for(int i = 0; i< firstDay.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
        	dayBlock[i + blockCount].setDate((d.getYear()+1900)+"."+(d.getMonth()+1)+"."+(i+1));
        	dayBlock[i + blockCount].setDay(String.valueOf(firstDay.get(Calendar.YEAR)+"/"+(firstDay.get(Calendar.MONTH)+1)+"/"+firstDay.get(Calendar.DATE)));        	
        	firstDay.add(Calendar.DATE, 1);
        	
            //Sunday
            if((i+blockCount)%7 == 0) dayBlock[i + blockCount].setForeground(new Color(255,0,0));
                //Saturday
            else if((i+blockCount)%7 == 6) dayBlock[i + blockCount].setForeground(new Color(0,0,255));
                //other
            else dayBlock[i + blockCount].setForeground(new Color(255,255,255));
            if(Integer.parseInt(selectedDate)==(i+1)){
            	dayBlock[i+blockCount].setBackground(Color.gray);
            }else{
            	dayBlock[i+blockCount].setBackground(Color.black);
            }
            dayBlock[i + blockCount].setText(String.valueOf(i + 1));
            dayBlock[i + blockCount].setTitles();
        }
    }
    // Callback Function for DayPanel
    public ArrayList<String> getTitlesFromList(String date){
        Calendar receivedDay =Calendar.getInstance();
        try {
            receivedDay.setTime((new SimpleDateFormat("yyyy/MM/dd")).parse(date));
        }catch(Exception e){}

        ArrayList<String> strList = new ArrayList<String>();
        Iterator it = ScheduleList.get_list().iterator();
        Schedule tempSchedule;
        Calendar calTemp = Calendar.getInstance();
        while(it.hasNext()){
            try {
                calTemp.setTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SS")
                        .parse((tempSchedule = (Schedule) it.next()).getDate()));
                if (calTemp.get(Calendar.YEAR) == receivedDay.get(Calendar.YEAR) &&
                        calTemp.get(Calendar.DAY_OF_YEAR) == receivedDay.get(Calendar.DAY_OF_YEAR))
                            strList.add(tempSchedule.title);
            } catch(Exception e){}
        }
        return strList;
    }
    // Reloads GUI
    public void reload(){
    	dayInit();
    }
    
    class reload extends Thread{
		public void run(){
			while(true){
			    dayInit();
			    try{
			    	Thread.sleep(1000);
			    }catch(InterruptedException e){
			    	
			    }
			}
		}
    }
    
   
}