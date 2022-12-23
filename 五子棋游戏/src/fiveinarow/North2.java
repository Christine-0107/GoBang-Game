package fiveinarow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;
public class North2 extends JPanel{
	private GoBangFrame2 frame;
	private JComboBox combo;
	private JLabel label1;
	private JButton button1;
	private JTextField text1;
	private JLabel label2;
	private JTextField text2;
	public North2(GoBangFrame2 frame){
		this.frame=frame;
		FlowLayout flow = new FlowLayout();
		this.setLayout(flow);
		Font font1=new Font("宋体",Font.PLAIN,35);
		Font font2=new Font("Times New Roman",Font.PLAIN,35);
		String[] modes={"单机模式","联网模式"};
		combo=new JComboBox(modes);
		combo.setFont(font1);
		label1=new JLabel("服务器地址");
		text1 = new JTextField("localhost");
		button1=new JButton();
		label2=new JLabel("服务端口号");
		text2 = new JTextField("9090");
		button1.setText("连接");
		label1.setFont(font1);
		text1.setFont(font2);
		button1.setFont(font1);
		label2.setFont(font1);
		text2.setFont(font2);
		add(combo);
		add(label1);
		add(text1);
		add(label2);
		add(text2);
		add(button1);
		addListener();
	}
	
	public String getText1(){
		return text1.getText();
	}
	public int getNum(){
		String s1=text2.getText();
		int num=Integer.parseInt(s1);
		return num;
	}
	
	private void addListener() {
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				cb.setEditable(true);
				String ss=(String) cb.getSelectedItem();
				if("单机模式".equals(ss)){
					frame.changeMode2To1();
					frame.setGameMode(1);
					button1.setEnabled(false);
					text1.setEnabled(false);
					text2.setEnabled(false);
				}
				else{
					frame.changeMode1To2();
					frame.setGameMode(2);
					button1.setEnabled(true);
					text1.setEnabled(true);
				}
			}
		});
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == null){
					return;
				}
				if("连接".equals(button1.getText())){
					System.out.println("yes");
					button1.setText("断开");
					frame.setGameFlag(false);
					try {
						frame.connectWithServer();
						frame.getInputFromServer();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if("断开".equals(button1.getText())){
					button1.setText("连接");
					text2.setText("Type Here");
					frame.setGameFlag(false);
					try {
						frame.s.close();
						frame.in.close();
						frame.out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		
	}
	

}
