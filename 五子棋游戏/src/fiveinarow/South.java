package fiveinarow;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
/*
 *下方显示计时 
 */
public class South extends JPanel{
	private GoBangFrame frame1;
	private GoBangFrame2 frame2;
	public JLabel label1;
	public JLabel label2;
	public South(GoBangFrame frame1){
		this.frame1=frame1;
		label1=new JLabel("Black time:01:00");
		Font font1=new Font("Times New Roman",Font.BOLD,50);
		label1.setFont(font1);
		
		this.add(label1);
		
	}
	public South(GoBangFrame2 frame2){
		this.frame2=frame2;
		label2=new JLabel("White Time:01:00");
		Font font1=new Font("Times New Roman",Font.BOLD,50);
		label2.setFont(font1);
		this.add(label2);
	}
	public void resetTime(){
		label1.setText("Black time:01:00");
	}
	public void resetTime2(){
		label2.setText("White time:01:00");
	}
}
