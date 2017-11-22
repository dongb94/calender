package calendar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class DayPanel extends JPanel {

	private JLabel centerLabel = new JLabel();
	private String date;
	private String day;
	private JLabel[] titles = {
			new JLabel(),
			new JLabel(),
			new JLabel()
	};
	private int titleCount = 0;
	private PanSouth uP;

	Detail D;

	public DayPanel(PanSouth uP){
		setLayout(new GridLayout(4,1));
		setBackground(Color.black);
		this.uP = uP;
		Font font = centerLabel.getFont();
		font = new Font(font.getFamily(),font.getStyle(),30);
		centerLabel.setFont(new Font(font.getFamily(),font.getStyle(),30));
		this.add(centerLabel);
		for(JLabel label: titles) {
			label.setFont(new Font(font.getFamily(), font.getStyle(), 14));
			label.setForeground(Color.white);
			this.add(label);
		}
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DayPanel DayPanel = (DayPanel)e.getSource();
				D.setScheduleList(DayPanel.date);
			}
		});
		
	}
	public DayPanel(PanSouth uP, String text){
		this(uP);
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(new Color(0,0,0,150));

	}
	public DayPanel(PanSouth uP, String text,Color color){
		this(uP);
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(color);
	}
	//get Title data from PanSouth
	public void setTitles(){
		 Iterator it = uP.getTitlesFromList(day).iterator();
		 String tempStr;
		 while(it.hasNext()){
			 tempStr = (String)it.next();
			 if(titleCount < 3) {
				 if (tempStr.length() > 10)
					 tempStr = tempStr.substring(0, 10) + "...";
				 titles[titleCount].setText(" * " + tempStr);
				 titleCount++;
			 }
		 }
	}
	public void resetTitle(){
		for(JLabel labels: titles)
			labels.setText("");
		titleCount = 0;
	}
	public void setText(String text){
		centerLabel.setText(text);
	}
	// Allow only 3 texts
	public void addText(String text){
		this.add(new JLabel("* "+text));
	}
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(centerLabel!=null)
			centerLabel.setForeground(fg);

	}
	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return this.date;
	}
	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return this.day;
	}
	public void setDetailPane(Detail D){
		this.D = D;

	}
}
