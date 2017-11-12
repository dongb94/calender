package calendar;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class PanNorth extends JPanel{
    private CalendarPanel uP;
    private Calendar today;
    private JButton btnBefore, btnAfter;
    private JTextField textYear, textMonth;

    //Constructor #Entry Point
    public PanNorth(CalendarPanel upperPanel){
        this.uP = upperPanel;
        this.today = uP.getToday();
        Font font = uP.getDefaultFont();

        btnBefore = new JButton("◀");
        textYear = new JTextField(today.get(Calendar.YEAR)+"년");
        textYear.setEnabled(false);
        textMonth = new JTextField(today.get(Calendar.MONTH)+1+"월", 3);
        textMonth.setEnabled(false);
        btnAfter = new JButton("▶");

        textYear.setFont(font);
        textMonth.setFont(font);

        this.add(btnBefore);
        this.add(textYear);
        this.add(textMonth);
        this.add(btnAfter);


        btnBefore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                today.add(Calendar.MONTH, -1);
                uP.setToday(today.getTime());
                uP.reload();
            }
        });
        
        btnAfter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                today.add(Calendar.MONTH, 1);
                uP.setToday(today.getTime());
                uP.reload();
            }
        });


        textYear.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //regex + update
            }
        });
        textMonth.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //regex + update
            }
        });
    }
    public void reload(){
        this.today = uP.getToday();
        textYear.setText(today.get(Calendar.YEAR)+"년");
        textYear.setEnabled(false);
        textMonth.setText(today.get(Calendar.MONTH)+1+"월");
        textYear.setEnabled(false);
    }
}