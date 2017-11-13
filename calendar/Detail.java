package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Detail extends JPanel{
	
	public ScheduleList scheduleList = null;
	public Modify modify = null;
	private JButton add, delete;
	Dimension size;
	
	Detail() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add = new JButton("추가");
		delete = new JButton("삭제");
		scheduleList = new ScheduleList(this);
		
		size = new Dimension();
		size.setSize(280, 500);
		scheduleList.setPreferredSize(size);
		
		JScrollPane scrollPane=new JScrollPane(scheduleList, 
				   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		add(add);
		add(delete);
		add(this.scheduleList);
		add(scrollPane);
		
		setBackground(Color.green);
		setVisible(true);
	}
	
	public void change(String panelName) {
		if (panelName.equals("scheduleList")) {
			removeAll();
			add(scheduleList);
			revalidate();
			repaint();
		} else {
			removeAll();
			add(modify);
			revalidate();
			repaint();
		}
	}
	
}
