package fiveinarow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import javax.swing.*;
public class North extends JPanel{
	private GoBangFrame frame;
	private JComboBox combo;
	private JLabel label1;
	private JButton button1;
	private JTextField text1;
	public North(GoBangFrame frame){
		this.frame=frame;
		FlowLayout flow = new FlowLayout();
		this.setLayout(flow);
		Font font1=new Font("宋体",Font.PLAIN,35);
		Font font2=new Font("Times New Roman",Font.PLAIN,35);
		String[] modes={"单机模式","联网模式"};
		combo=new JComboBox(modes);
		combo.setFont(font1);
		label1=new JLabel("服务端口号");
		text1 = new JTextField("9090");
		button1=new JButton();
		button1.setText("连接");
		label1.setFont(font1);
		text1.setFont(font2);
		button1.setFont(font1);
		add(combo);
		add(label1);
		add(text1);
		add(button1);
		addListener();
	}
	public int getNum() {
		String s1=text1.getText();
		int num=Integer.parseInt(s1);
		return num;
	}
	
	
	public void addListener(){
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
						frame.connectWithClient();
						Font font1=new Font("宋体",Font.PLAIN,55);
						Font font2=new Font("宋体",Font.PLAIN,40);
						UIManager.put("Label.font", font1);
						UIManager.put("ComboBox.font", font1);
						UIManager.put("Button.font", font2);
						UIManager.put("TabbedPane.font", font1);
						UIManager.put("RadioButton.font", font2);
						UIManager.put("TitledBorder.font", font1);
						JOptionPane.showMessageDialog(null, "成功连接!");
						frame.getInputFromClient();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if("断开".equals(button1.getText())){
					button1.setText("连接");
					text1.setText("Type Here");
					frame.setGameFlag(false);
					try {
						frame.s.close();
						frame.ss.close();
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
