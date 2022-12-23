package fiveinarow;

import image.ImageValue;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 *棋子 
 */
public class Chess {
	private int x;
	private int y;//x,y坐标
	private int h=55;
	private int color=1;//1表示黑棋，2表示白棋
	private boolean last=false;//是否是最后一步棋子
	public Chess(int x,int y,int color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	//棋子绘制方法
	public void draw(Graphics g){
		if(color==1){
			g.drawImage(ImageValue.black, x-h/2, y-h/2, h, h, null);
		}
		else{
			g.drawImage(ImageValue.white, x-h/2-3, y-h/2-3, h, h, null);
			//图片的缘故，白棋稍微上移一点
		}
		if(last){//如果是最后一步棋子(给电脑最后一步做上标记)
			Graphics2D g2d=(Graphics2D)g;
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.drawRect(x-4,y-4,8,8);
			
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getColor(){
		return color;
	}
	public boolean getLast(){
		return last;
	}
	public void setLast(boolean last){
		this.last=last;
	}
}
