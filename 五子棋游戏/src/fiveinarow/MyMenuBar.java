package fiveinarow;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MyMenuBar extends JMenuBar{
	private GoBangFrame frame1;
	private GoBangFrame2 frame2;
	private JMenu[] menus={new JMenu("��Ϸ"),new JMenu("����")};
	private JMenuItem[] items1={new JMenuItem("��ʼ"),
			new JMenuItem("����"),new JMenuItem("�˳�")};
	private JMenuItem[] items2={new JMenuItem("����"),new JMenuItem("����")};
	public MyMenuBar(GoBangFrame frame1){
		this.frame1=frame1;
		Font font=new Font("����",Font.PLAIN,40);
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
		Font font=new Font("����",Font.PLAIN,40);
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
		items1[0].addActionListener(new ActionListener(){//��ʼ
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font1=new Font("����",Font.PLAIN,50);
				Font font2=new Font("����",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				if(frame1.getGameFlag()==true){ //��Ϸ���ڽ����У����ܿ���
					JOptionPane.showMessageDialog(null, "��Ϸ���ڽ����У��������¿��֡�",
							"��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(frame1.getGameMode()==1){ //����ģʽ
					frame1.restart();
				}
				else{ //����ģʽ
					frame1.out.println("start");
				}
				
			}
		});
		items2[0].addActionListener(new ActionListener(){ //����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame1.getGameMode()==2){
					frame1.out.println("regret");
				}
				
			}
			
		});
		items2[1].addActionListener(new ActionListener(){ //����
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
		items1[0].addActionListener(new ActionListener(){//��ʼ
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font1=new Font("����",Font.PLAIN,50);
				Font font2=new Font("����",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				if(frame2.getGameFlag()==true){ //��Ϸ���ڽ����У����ܿ���
					JOptionPane.showMessageDialog(null, "��Ϸ���ڽ����У��������¿��֡�",
							"��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(frame2.getGameMode()==1){ //����ģʽ
					frame2.restart();
				}
				else{
					frame2.out.println("start");
				}
			}
		});
		items2[0].addActionListener(new ActionListener(){ //����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame2.getGameMode()==2){
					frame2.out.println("regret");
				}
				
			}
			
		});
		items2[1].addActionListener(new ActionListener(){ //����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame2.getGameMode()==2){
					frame2.out.println("giveup");
				}
				
			}
			
		});
	}
	
	public void addListener(){ //������ť���˳���ť
		
		items1[1].addActionListener(new ActionListener(){//����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font font1=new Font("����",Font.PLAIN,45);
				Font font2=new Font("����",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				JOptionPane.showMessageDialog(null, "�����ָʾ������λ��ʱ���ӡ�\n"
						+ "�����ȿ�ʼ��˫���������ӡ�\n"
						+"��ĳһ�����������һ����ʱ����ʤ����","����",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		items1[2].addActionListener(new ActionListener(){//�˳�
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font font1=new Font("����",Font.PLAIN,50);
				Font font2=new Font("����",Font.PLAIN,35);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				int result=JOptionPane.showConfirmDialog(null, "ȷ���˳���","�˳�",
						JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION){
					System.exit(0);
				}
				
			}
			
		});
		
	}
	
	
}
