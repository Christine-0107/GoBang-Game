package fiveinarow;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*
 *客户端  下白棋
 */
public class GoBangFrame2 extends JFrame{
	public Socket s;
	public BufferedReader in;
	public PrintWriter out;
	public int gameMode;//1表示单机，2表示联网
	private South southpanel;
	private Center2 centerpanel;
	private North2 northpanel;
	private MyMenuBar menubar;
	private Date now;
	private ImageIcon img;
	private JLabel imglabel;
	public GoBangFrame2(){
		super("五子棋");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(2200,1450);
		setLocationRelativeTo(null);
		setResizable(false);
		img=new ImageIcon("back.jpg");
		Image image=img.getImage();
		image=image.getScaledInstance(2200, 1450, Image.SCALE_DEFAULT);
		img.setImage(image);
		imglabel=new JLabel(img);
		this.getLayeredPane().setLayout(null);
		this.imglabel.setSize(2200,1450);
		this.getLayeredPane().add(imglabel,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);
		initMenu();
		initTime();
		initCenter();
		initSouth();
		initNorth();
		centerpanel.setOpaque(false);
		northpanel.setOpaque(false);
		southpanel.setOpaque(false);
		menubar.setOpaque(false);
		setVisible(true);
	}
	public void initTime(){ //初始化倒计时
		now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		//calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,1);
		calendar.set(Calendar.SECOND, 0);
		now=calendar.getTime();
	}
	private void initCenter(){
		centerpanel=new Center2(this);
		add(centerpanel,BorderLayout.CENTER);
	}
	private void initSouth(){
		southpanel=new South(this);
		add(southpanel,BorderLayout.SOUTH);
	}
	private void initNorth(){
		northpanel=new North2(this);
		add(northpanel,BorderLayout.NORTH);
	}
	private void initMenu(){
		menubar=new MyMenuBar(this);
		setJMenuBar(menubar);
	}
	
	private String getText1(){
		return northpanel.getText1();
	}
	private int getNum(){
		return northpanel.getNum();
	}
	final Timer timer=new Timer(1000,new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Date now2=new Date(now.getTime()-1000);
			now=now2;
			SimpleDateFormat formatter=new SimpleDateFormat("mm:ss");
			southpanel.label2.setText("White Time:"+formatter.format(now));
		}
	});
	public void resetTime(){
		southpanel.resetTime2();
	}
	
	public void connectWithServer() throws Exception{
		s=new Socket(getText1(),getNum());
		in=new BufferedReader(
				new InputStreamReader(s.getInputStream()));
		out=new PrintWriter(s.getOutputStream(),true);
	}
	public void getInputFromServer() throws IOException{
//		in=new BufferedReader(
//				new InputStreamReader(s.getInputStream()));
		new Thread(){
			public void run(){
				while(true){
					try {
						String line;
						line = in.readLine();
						System.out.println(line);
						if(line.startsWith("Chess:")){//下棋
							addChess(line);
							centerpanel.setGameFlag(true);
							timer.start();
						}
						else if("blackWin".equals(line)){//对方胜利
							timer.stop();
							resetTime();
							centerpanel.chessWin();
						}
						else if("regret".equals(line)){
							centerpanel.regretMessage();
						}
						else if("regret:yes".equals(line)){
							centerpanel.regretYes();
							timer.start();
						}
						else if("regret:no".equals(line)){
							;
						}
						else if(line.startsWith("remove:")){
							removeChess(line);
							centerpanel.setGameFlag(false);
						}
						else if(line.startsWith("Server:")){
							centerpanel.addText(line);
						}
						else if("start".equals(line)){
							centerpanel.startMessage();
						}
						else if("start:yes".equals(line)){
							centerpanel.startGame();
							centerpanel.setGameFlag(false);
						}
						else if("start:no".equals(line)){
							;
						}
						else if("giveup".equals(line)){
							centerpanel.giveupMessage();
						}
						else if("giveup:yes".equals(line)){
							centerpanel.giveupGame();
						}
						else if("giveup:no".equals(line)){
							;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("here");
						e.printStackTrace();
					}
					
				}
			}
		}.start();
	}
	private void addChess(String line){
		String sx="";
		String sy="";
		char sc;
		int x,y,c;
		int indexOfy,indexOfc;
		indexOfy=line.indexOf("y");
		indexOfc=line.indexOf("c");
		for(int i=7;i<indexOfy;i++){
			sx+=line.charAt(i);
		}
		x=Integer.parseInt(sx);
		for(int i=indexOfy+1;i<indexOfc;i++){
			sy+=line.charAt(i);
		}
		y=Integer.parseInt(sy);
		sc=line.charAt(line.length()-1);
		c=(int)sc-48;
		centerpanel.addChess(x,y,c);
		
	}

	
	private void removeChess(String line){
		String si="";
		String sj="";
		int i,j;
		int indexOfj;
		indexOfj=line.indexOf("j");
		for(int k=8;k<indexOfj;k++){
			si+=line.charAt(k);
		}
		i=Integer.parseInt(si);
		for(int k=indexOfj+1;k<line.length();k++){
			sj+=line.charAt(k);
		}
		j=Integer.parseInt(sj);
		centerpanel.removeChess(i,j);
	}
	
	public void restart(){
		centerpanel.restart();
	}
	public void changeMode1To2(){
		centerpanel.changeMode1To2();
	}
	public void changeMode2To1(){
		centerpanel.changeMode2To1();
	}
	public void setCount(){
		centerpanel.setCount();
	}
	public boolean getGameFlag(){
		return centerpanel.getGameFlag();
	}
	public void setGameFlag(boolean flag){
		centerpanel.setGameFlag(flag);
	}
	public void setGameMode(int num){
		gameMode=num;
	}
	public int getGameMode(){
		return gameMode;
	}
	
	public static void main(String args[]){
		GoBangFrame2 frame2=new GoBangFrame2();
		new Thread(){
			public void run(){
				try {
					Clip bgm=AudioSystem.getClip();
					File musicPath=new File("resource/music/bgm.wav");
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					bgm.open(audioInput);
					bgm.start();
					while(true){
						
					}
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}
