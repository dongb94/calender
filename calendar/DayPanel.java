package calendar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class DayPanel extends JPanel {

	private JLabel centerLabel = new JLabel();
	private String date;
	private JLabel title = new JLabel();

	Detail D;


	public DayPanel(){
		this.add(centerLabel, BorderLayout.CENTER);
		this.add(title, BorderLayout.SOUTH);
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DayPanel DayPanel = (DayPanel)e.getSource();
				D.setScheduleList(DayPanel.date);
			}
		});
		Font font = centerLabel.getFont();
		font = new Font(font.getFamily(),font.getStyle(),30);
		centerLabel.setFont(new Font(font.getFamily(),font.getStyle(),30));
		title.setFont(new Font(font.getFamily(),font.getStyle(),24));
		//ImageIcon img = new ImageIcon("image/day.png");
		//Image image = img.getImage(); // transform it 
		//Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		//img = new ImageIcon(newimg);  // transform it back
		//centerLabel.setIcon(img);
	}
	// String longer than 15 char is goin' to be cut down
	public void setTitle(String title) {
	    if(title.length() > 15)
	        title = title.substring(0,15)+"...";
		this.title.setText(title);
	}
	public DayPanel(String text){
		this();
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(new Color(0,0,0,150));
	}
	public DayPanel(String text,Color color){
		this();
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(color);
	}
	public void setText(String text){
		centerLabel.setText(text);
		this.setBackground(new Color(0,0,0));
	}
	public void addText(String text){
		this.add(new JLabel("* "+text), BorderLayout.SOUTH);
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
	public void setDetailPane(Detail D){
		this.D = D;

	}
}
