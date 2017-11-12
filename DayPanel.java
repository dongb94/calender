package calendar;

import javax.swing.*;
import java.awt.*;

public class DayPanel extends JPanel {
	JLabel centerLabel = new JLabel();
	
	public DayPanel(){
		this.add(centerLabel, BorderLayout.CENTER);
	}
	public DayPanel(String text){
		this();
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(new Color(150,150,150));
	}
	public DayPanel(String text,Color color){
		this();
		centerLabel.setText(text);
		this.setForeground(new Color(255,255,255));
		this.setBackground(color);
	}
	public void setText(String text){
		centerLabel.setText(text);
		this.setBackground(new Color(255,255,255));
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
	
}
