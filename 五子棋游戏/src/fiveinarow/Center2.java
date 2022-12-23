package fiveinarow;
import image.ImageValue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
/*
 * 棋盘部分 客户端
 */
public class Center2 extends JPanel{
		private GoBangFrame2 frame=null;
		private JTextArea textarea1;//发送的文字框
		private JButton button1;//发送按键
		private JTextArea textarea2;//接受的文字框
		private JButton button2; //复盘按钮
		private JButton button3; //上一步
		private JButton button4; //下一步
		private int count=-1;//保存复盘步数
		private LinkedList<Chess> chessestemp=new LinkedList<Chess>();
		public boolean gameFlag=false;//设置游戏状态
		public final int row=15;
		public final int col=15; //行数和列数
		public Pointer[][] pointers=new Pointer[row+1][col+1]; //指示器数组
		public int color=2;//当前应该下的颜色
		public LinkedList<Chess> chesses=new LinkedList<Chess>();//保存棋子
		public Center2(GoBangFrame2 frame){
			this.frame=frame;
			setLayout(null);
			setBackground(new Color(232,244,255));
			initSend();
			initReceive();
			initReplay();
			ImageValue.initImage();
			//鼠标移动监听
			createMouseMoveListener();
			//鼠标点击监听
			createMouseClickListener();
			//创建指示器数组内容
			createPointers();
		}
		private void initSend() {
			// TODO Auto-generated method stub
			textarea1=new JTextArea(10,50);
			textarea1.setBounds(1300,50,730,500);
			Font font1=new Font("宋体",Font.PLAIN,35);
			textarea1.setFont(font1);
			textarea1.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.BLACK,2),
					"需要发送的内容",TitledBorder.LEFT,TitledBorder.TOP,
					new java.awt.Font("宋体",0,35)));
			add(textarea1);
			button1=new JButton();
			button1.setText("发送");
			button1.setBounds(1300,570,150,60);
			Font font3=new Font("宋体",Font.PLAIN,35);
			button1.setFont(font3);
			add(button1);
			addListener();
		}
		
		private void initReceive() {
			// TODO Auto-generated method stub
			textarea2=new JTextArea(10,50);
			textarea2.setBounds(1300,650,730,520);
			Font font1=new Font("宋体",Font.PLAIN,35);
			textarea2.setFont(font1);
			textarea2.setForeground(new Color(35,156,255));
			textarea2.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.BLACK,2),
					"日志",TitledBorder.LEFT,TitledBorder.TOP,
					new java.awt.Font("宋体",0,35)));
			add(textarea2);
		}
		private void initReplay() {
			// TODO Auto-generated method stub
			button2=new JButton();
			button3=new JButton();
			button4=new JButton();
			button3.setBounds(170,1100,150,60);
			button2.setBounds(340,1100,150,60);
			button4.setBounds(510,1100,150,60);
			button2.setText("复盘");
			button3.setText("上一步");
			button4.setText("下一步");
			Font font1=new Font("宋体",Font.PLAIN,35);
			button2.setFont(font1);
			button3.setFont(font1);
			button4.setFont(font1);
			button3.setEnabled(false);
			button4.setEnabled(false);
			this.add(button2);
			this.add(button3);
			this.add(button4);
			addListener2();
		}
		
		private void addListener(){
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == null){
						return;
					}
					try {
						frame.out=new PrintWriter(frame.s.getOutputStream(),true);
						frame.out.println("Client:"+textarea1.getText());
						textarea2.append("Client:"+textarea1.getText());
						textarea2.append("\n");
						textarea1.setText(null);
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
		}
		private void addListener2(){
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == null){
						return;
					}
					Font font1=new Font("宋体",Font.PLAIN,50);
					Font font2=new Font("宋体",Font.PLAIN,35);
					UIManager.put("OptionPane.buttonFont", font2);
					UIManager.put("OptionPane.messageFont", font1);
					if(frame.getGameFlag()==true){ //游戏还在进行中，不能复盘
						JOptionPane.showMessageDialog(null, "游戏正在进行中，不能复盘。",
								"提示",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						for(int i=0;i<chesses.size();i++){
							chessestemp.add(chesses.get(i));
						}
						chesses.clear();
						replay();
						button3.setEnabled(true);
						button4.setEnabled(true);
						
					}
					
				}

				
			});
			button3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == null){
						return;
					}
					Font font1=new Font("宋体",Font.PLAIN,50);
					Font font2=new Font("宋体",Font.PLAIN,35);
					UIManager.put("OptionPane.buttonFont", font2);
					UIManager.put("OptionPane.messageFont", font1);
					if(frame.getGameFlag()==true){ //游戏还在进行中，不能复盘
						JOptionPane.showMessageDialog(null, "游戏正在进行中，不能复盘。",
								"提示",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						replaypre();
						
					}
					
				}

				
			});
			button4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == null){
						return;
					}
					Font font1=new Font("宋体",Font.PLAIN,50);
					Font font2=new Font("宋体",Font.PLAIN,35);
					UIManager.put("OptionPane.buttonFont", font2);
					UIManager.put("OptionPane.messageFont", font1);
					if(frame.getGameFlag()==true){ //游戏还在进行中，不能复盘
						JOptionPane.showMessageDialog(null, "游戏正在进行中，不能复盘。",
								"提示",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						
						replay();
						
					}
					
				}

				
			});
		}
		private void replay() {
			// TODO Auto-generated method stub
			if(count<chessestemp.size()-1){
				count++;
				chesses.add(chessestemp.get(count));
				
				repaint();
			}
			else{
				button4.setEnabled(false);
			}
			
		}
		private void replaypre(){
			if(count>=0){
				count--;
				chesses.removeLast();
				repaint();
				
			}
			else{
				button3.setEnabled(false);
			}
			
		}
		private void createPointers() {
			// TODO Auto-generated method stub
			int x,y;
			int start=45;
			int start2=170;
			Pointer pointer;
			for(int i=0;i<=row;i++){
				for(int j=0;j<=col;j++){
					x=j*64+start2;
					y=i*64+start;
					pointer=new Pointer(i,j,x,y);
					pointers[i][j]=pointer;
				}
			}
		}

		private void createMouseMoveListener() {
			// TODO Auto-generated method stub
			MouseAdapter mouseAdapter=new MouseAdapter(){
				public void mouseMoved(MouseEvent e){
					if(gameFlag==false)
						return;
					Pointer pointer;
					int x=e.getX();
					int y=e.getY();//鼠标的x,y坐标
					for(int i=0;i<=row;i++){
						for(int j=0;j<=col;j++){
							pointer=pointers[i][j];
							if(pointer.isPoint(x, y)&&pointer.getTag()==0){
								pointer.setShow(true);
							}
							else{
								pointer.setShow(false);
							}
						}
					}
					repaint();
				}
			};
			addMouseMotionListener(mouseAdapter);
			addMouseListener(mouseAdapter);
		}
		private Center2 getCenter() {
			// TODO Auto-generated method stub
			return this;
		}
		private void clearAILast() { //清理给电脑标记的之前的最后一步
			// TODO Auto-generated method stub
			Chess chess1;
			for(int i=0;i<chesses.size();i++){
				chess1=chesses.get(i);
				if(chess1!=null){
					chess1.setLast(false);
				}
			}
		}
		
		private void createMouseClickListener() {
			// TODO Auto-generated method stub
			MouseAdapter mouseAdapter=new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(gameFlag==false)
						return;
					Pointer pointer;
					int x=e.getX();
					int y=e.getY();//鼠标的x,y坐标
					for(int i=0;i<=row;i++){
						for(int j=0;j<=col;j++){
							pointer=pointers[i][j];
							if(pointer.isPoint(x, y)&&pointer.getTag()==0){
								Chess chess=new Chess(pointer.getX(),
										pointer.getY(),color);
								chesses.add(chess);
								pointer.setTag(color);
								new Thread(){  //下棋的音效
									public void run(){
										try {
											Clip bgm=AudioSystem.getClip();
											File musicPath=new File("resource/music/button.wav");
											AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
											bgm.open(audioInput);
											bgm.start();
										} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}.start();
								if(frame.getGameMode()==1){
									clearAILast();
									repaint();
									win(i,j,color); //判断是否赢了
									if(gameFlag==true){
										if(color==1)
											color=2;
										else
											color=1;
										gameFlag=false;
										AI2.next(getCenter());
										//win(i,j,color);//判断电脑是否赢了
									}
								}
								else{
									repaint();
									frame.out.println("Chess:x"+pointer.getX()+
											"y"+pointer.getY()+"c"+color);
									frame.timer.stop();
									frame.initTime();
									frame.resetTime();
									win(i,j,2); //判断是否赢了
									gameFlag=false;
								}
								
								return;
								
							}
						}
					}
				}
			};
			addMouseMotionListener(mouseAdapter);
			addMouseListener(mouseAdapter);
		}
		
		public void startMessage(){
			Font font1=new Font("宋体",Font.PLAIN,55);
			Font font2=new Font("宋体",Font.PLAIN,40);
			UIManager.put("OptionPane.buttonFont", font2);
			UIManager.put("OptionPane.messageFont", font1);
			int result=JOptionPane.showConfirmDialog(null, "对方准备开始游戏，您同意吗？","开局",
					JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION){
				startGame();
				frame.out.println("start:yes");
			}
			else{
				frame.out.println("start:no");
			}
		}
		public void startGame(){
			frame.restart();
			frame.setCount();
		}
		
		public void addText(String line){
			
			textarea2.append(line);
			textarea2.append("\n");
		}
		
		
		
		public void addChess(int x,int y,int color){
			Chess chess1=new Chess(x,y,color);
			chesses.add(chess1);
			int i=(x-170)/64;
			int j=(y-45)/64;
			pointers[j][i].setTag(1);
			repaint();
		}
		public void chessWin(){
			gameFlag=false;
			repaint();
		}
		
		public void regretMessage(){
			Font font1=new Font("宋体",Font.PLAIN,55);
			Font font2=new Font("宋体",Font.PLAIN,40);
			UIManager.put("OptionPane.buttonFont", font2);
			UIManager.put("OptionPane.messageFont", font1);
			int result=JOptionPane.showConfirmDialog(null, "对方想悔棋，您同意吗？","悔棋",
					JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION){
				frame.out.println("regret:yes");
				frame.timer.stop();
				frame.initTime();
				frame.resetTime();
			}
			else{
				frame.out.println("regret:no");
			}
			
		}
		public void regretYes(){
			Chess lastChess;
			lastChess=chesses.getLast();//获得最后一步棋子
			chesses.removeLast();//删除最后一步棋子
			int i,j;//棋子坐标对应的二维数组下标
			i=(lastChess.getX()-170)/64;
			j=(lastChess.getY()-45)/64;
			pointers[j][i].setTag(0);
			gameFlag=true;
			frame.out.println("remove:i"+i+"j"+j);
			repaint();
		}
		
		
		public void removeChess(int i,int j){
			chesses.removeLast();
			pointers[j][i].setTag(0);
			repaint();
		}
		
		public void giveupMessage(){
			Font font1=new Font("宋体",Font.PLAIN,55);
			Font font2=new Font("宋体",Font.PLAIN,40);
			UIManager.put("OptionPane.buttonFont", font2);
			UIManager.put("OptionPane.messageFont", font1);
			int result=JOptionPane.showConfirmDialog(null, "对方想认输，您同意吗？","认输",
					JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION){
				frame.out.println("giveup:yes");
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				JOptionPane.showMessageDialog(null, "白棋胜利！");
				gameFlag=false;
				repaint();
			}
			else{
				frame.out.println("giveup:no");
			}
		}
		public void giveupGame(){
			Font font1=new Font("宋体",Font.PLAIN,55);
			Font font2=new Font("宋体",Font.PLAIN,40);
			UIManager.put("OptionPane.buttonFont", font2);
			UIManager.put("OptionPane.messageFont", font1);
			JOptionPane.showMessageDialog(null, "黑棋胜利！");
			gameFlag=false;
			repaint();
		}
		
		
		public void win(int lasti,int lastj,int color){
			boolean flag=false;
			flag=judgeWin(lasti,lastj,color);
			if(flag==true){
				new Thread(){
					public void run(){
						try {
							Clip bgm=AudioSystem.getClip();
							File musicPath=new File("resource/music/win.wav");
							AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
							bgm.open(audioInput);
							bgm.start();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				Font font1=new Font("宋体",Font.PLAIN,55);
				Font font2=new Font("宋体",Font.PLAIN,40);
				UIManager.put("OptionPane.buttonFont", font2);
				UIManager.put("OptionPane.messageFont", font1);
				if(frame.getGameMode()==1){
					if(color==1){
						JOptionPane.showMessageDialog(null, "您胜利了！");
					}
					else{
						JOptionPane.showMessageDialog(null, "您失败了！");
					}
					gameFlag=false;
					repaint();
				}
				else{
					if(color==1){
						JOptionPane.showMessageDialog(null, "黑棋胜利！");
					}
					else{
						JOptionPane.showMessageDialog(null, "白棋胜利！");
					}
					gameFlag=false;
					repaint();
					frame.out.println("whiteWin");
				}
				
			}
		}
		
		public boolean judgeWin(int lasti,int lastj,int color){
			//水平方向
			int count=1;
			for(int i=lasti-1;i>=0;i--){
				if(pointers[i][lastj].getTag()==color){ 
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			for(int i=lasti+1;i<=row;i++){
				if(pointers[i][lastj].getTag()==color){ 
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			//竖直方向
			count=1;
			for(int j=lastj-1;j>=0;j--){
				if(pointers[lasti][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			for(int j=lastj+1;j<=col;j++){
				if(pointers[lasti][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			//左上右下
			count=1;
			for(int i=lasti-1,j=lastj-1;i>=0&&j>=0;i--,j--){
				if(pointers[i][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			for(int i=lasti+1,j=lastj+1;i<=row&&j<=col;i++,j++){
				if(pointers[i][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			//右上左下
			count=1;
			for(int i=lasti+1,j=lastj-1;i<=row&&j>=0;i++,j--){
				if(pointers[i][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			for(int i=lasti-1,j=lastj+1;i>=0&&j<=col;i--,j++){
				if(pointers[i][j].getTag()==color){
					count++;
					if(count>=5){
						return true;
					}
				}
				else
					break;
			}
			//没赢
			return false;
		}

		@Override
		public void paint(Graphics g){
			super.paint(g); //清空
			drawBoardBack(g);//画棋盘背景
			drawBoard(g); //画棋盘
			drawPointer(g); //绘制指示器
			drawChess(g);//画棋子
		}
		
		private void drawChess(Graphics g) {
			// TODO Auto-generated method stub
			Chess ch;
			for(int i=0;i<chesses.size();i++){
				ch=chesses.get(i);
				
				ch.draw(g);
			}
		}

		private void drawPointer(Graphics g) {
			// TODO Auto-generated method stub
			Pointer pointer;
			for(int i=0;i<=row;i++){
				for(int j=0;j<=col;j++){
					pointer=pointers[i][j];
					if(pointer!=null){
						pointer.draw(g);
					}
				}
			}
		}
		private void drawBoardBack(Graphics g){
			g.drawImage(ImageValue.background, 170, 45, 960, 960, null);
		}
		private void drawBoard(Graphics g) {
			// TODO Auto-generated method stub
			//转换成2D画笔
			Graphics2D g2d=(Graphics2D)g;
			g2d.setStroke(new BasicStroke(3.0f));//设置画笔的粗细
			//15*15的棋盘，每个小方格64*64
			int x1=170,x2=1130,y1=45,y2=45;
			int start=45;
			int start2=170;
			for(int i=0;i<=row;i++){
				y1=start+i*64;
				y2=y1;
				g.drawLine(x1, y1, x2, y2);
			}
			x1=170;x2=170;y1=45;y2=1005;
			for(int i=0;i<=col;i++){
				x1=start2+i*64;
				x2=x1;
				g.drawLine(x1, y1, x2, y2);
			}
			
		}
		
		public void restart(){
			/*重置参数
			 * 1.游戏状态
			 * 2.指示器状态
			 * 3.棋子显示
			 * */
			gameFlag=true; //游戏状态设为开始
			if(frame.getGameMode()==1){
				color=1;//单机模式下黑棋先下
			}
			Pointer pointer;
			for(int i=0;i<=row;i++){
				for(int j=0;j<=col;j++){
					pointer=pointers[i][j];
					pointer.setTag(0); //指示器状态设为未下棋子
					pointer.setShow(false);
				}
			}
			chesses.clear();//存放棋子的链表清空
			repaint();
		}
		public void changeMode1To2(){
			chesses.clear();
			repaint();
			textarea1.setEditable(true);
			textarea2.setEditable(true);
			button1.setEnabled(true);
		}
		public void changeMode2To1(){
			chesses.clear();
			repaint();
			textarea1.setEditable(false);
			textarea2.setText(null);
			textarea2.setEditable(false);
			button1.setEnabled(false);
			
		}
		public void setCount(){
			count=-1;
		}
		public boolean getGameFlag(){
			return gameFlag;
		}
		public void setGameFlag(boolean flag){
			gameFlag=flag;
		}
}
