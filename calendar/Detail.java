package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Detail extends JPanel implements ActionListener {

	public ScheduleList scheduleList = null;
	public Modify modify = null;
	private JButton add, delete;
	Dimension size;

	String name;
	
	private double width;
	private double height;

	Detail(String name) {
		this.name = name;
	}

	Detail() {
<<<<<<< .merge_file_a11724

=======
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		width = res.width*0.8;
		height = res.height*0.8;
		width = width*0.3;
		height = height*0.82;
		
>>>>>>> .merge_file_a07948
		setLayout(null);

		scheduleList = new ScheduleList(this);
		modify = new Modify(this);
		add(scheduleList);
<<<<<<< .merge_file_a11724
		
=======
		scheduleList.setBounds(0, 0, (int)width, (int)height);

		setBackground(Color.green);
>>>>>>> .merge_file_a07948
		setVisible(true);
	}

	public void setPanel(){
		scheduleList.setBounds(0, 0, getWidth(), getHeight());
	}
	
	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			this.removeAll();
			this.add(scheduleList);
<<<<<<< .merge_file_a11724
			scheduleList.setBounds(0, 0, getWidth(), getHeight());
=======
			scheduleList.setBounds(0, 0, (int)width, (int)height);
>>>>>>> .merge_file_a07948
			scheduleList.setVisible(true);
			this.updateUI();
		} else {
			this.removeAll();
			this.add(modify);
<<<<<<< .merge_file_a11724
			modify.setBounds(0, 0, getWidth(), getHeight());
=======
			modify.setBounds(0, 0, (int)width, (int)height);
>>>>>>> .merge_file_a07948
			modify.setVisible(true);
			this.updateUI();
		}
	}

	public void actionPerformed(ActionEvent e) {
		change("modify");
	}

}
