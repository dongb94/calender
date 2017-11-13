package calendar;


import javax.swing.*;
import java.awt.*;

public class DayPanel extends JPanel {
	JLabel centerLabel = new JLabel();
	
	public DayPanel(){
		this.add(centerLabel, BorderLayout.CENTER);
		Font font = centerLabel.getFont();
		font = new Font(font.getFamily(),font.getStyle(),30);
		centerLabel.setFont(font);
		//ImageIcon img = new ImageIcon("image/day.png");
		//Image image = img.getImage(); // transform it 
		//Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		//img = new ImageIcon(newimg);  // transform it back
		//centerLabel.setIcon(img);
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
	
}
