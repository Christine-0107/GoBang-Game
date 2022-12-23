package fiveinarow;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MyMenuBar extends JMenuBar{
	private GoBangFrame frame1;
	private GoBangFrame2 frame2;
	private JMenu[] menus={new JMenu("游戏"),new JMenu("功能")};
	private JMenuItem[] items1={new JMenuItem("开始"),
			new JMenuItem("帮助"),new JMenuItem("退出")};
	private JMenuItem[] items2={new JMenuItem("悔棋"),new JMenuItem("认输")};
	public MyMenuBar(GoBangFrame frame1){
		this.frame1=frame1;
		Font font=new Font("宋体",Font.PLAIN,40);
		for(int i=0;i<items1.length;i++){
			items1[i].setFont(font);
			menus[0].add(items1[i]);
		}
		for(int i=0;i<items2.length;i++){
			items2[i].setFont(font);
			menus[1].add(items2[i]);
		}
		addListener_1();
		addListener();
		for(JMenu m:menus){
			m.setFont(font);
			this.add(m);
		}
	}
	public MyMenuBar(GoBangFrame2 frame2){
		this.frame2=frame2;
		Font font=new Font("宋体",Font.PLAIN,40);
		for(int i=0;i<items1.length;i++){
			items1[i].setFont(font);
			menus[0].add(items1[i]);
		}
		for(int i=0;i<items2.length;i++){
			items2[i].setFont(font);
			menus[1].add(items2[i]);
		}
		addListener_2();
		addListener();
		for(JMenu m:menus){
			m.setFont(font);
			this.add(m);
		}
	}
	public void addListener_1(){
		items1[0].addActionListener(new ActionListener(){//开始
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font1=new Font("宋体",Font.PLAIN,50);
				Font font2=new Font("宋体",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				if(frame1.getGameFlag()==true){ //游戏还在进行中，不能开局
					JOptionPane.showMessageDialog(null, "游戏正在进行中，不能重新开局。",
							"提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(frame1.getGameMode()==1){ //单机模式
					frame1.restart();
				}
				else{ //联网模式
					frame1.out.println("start");
				}
				
			}
		});
		items2[0].addActionListener(new ActionListener(){ //悔棋
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame1.getGameMode()==2){
					frame1.out.println("regret");
				}
				
			}
			
		});
		items2[1].addActionListener(new ActionListener(){ //认输
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame1.getGameMode()==2){
					frame1.out.println("giveup");
				}
				
				
			}
			
		});
	}
	public void addListener_2(){
		items1[0].addActionListener(new ActionListener(){//开始
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font1=new Font("宋体",Font.PLAIN,50);
				Font font2=new Font("宋体",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				if(frame2.getGameFlag()==true){ //游戏还在进行中，不能开局
					JOptionPane.showMessageDialog(null, "游戏正在进行中，不能重新开局。",
							"提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(frame2.getGameMode()==1){ //单机模式
					frame2.restart();
				}
				else{
					frame2.out.println("start");
				}
			}
		});
		items2[0].addActionListener(new ActionListener(){ //悔棋
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame2.getGameMode()==2){
					frame2.out.println("regret");
				}
				
			}
			
		});
		items2[1].addActionListener(new ActionListener(){ //认输
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame2.getGameMode()==2){
					frame2.out.println("giveup");
				}
				
			}
			
		});
	}
	
	public void addListener(){ //帮助按钮和退出按钮
		
		items1[1].addActionListener(new ActionListener(){//帮助
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font font1=new Font("宋体",Font.PLAIN,45);
				Font font2=new Font("宋体",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				JOptionPane.showMessageDialog(null, "鼠标点击指示器所在位置时落子。\n"
						+ "黑棋先开始，双方交替落子。\n"
						+"当某一方五颗子连成一条线时，则胜利。","帮助",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		items1[2].addActionListener(new ActionListener(){//退出
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font font1=new Font("宋体",Font.PLAIN,50);
				Font font2=new Font("宋体",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				int result=JOptionPane.showConfirmDialog(null, "确定退出吗？","退出",
						JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION){
					System.exit(0);
				}
				
			}
			
		});
		
	}
	
	
}
