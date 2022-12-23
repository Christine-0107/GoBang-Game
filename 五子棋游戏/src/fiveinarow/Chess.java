package fiveinarow;

import image.ImageValue;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 *���� 
 */
public class Chess {
	private int x;
	private int y;//x,y����
	private int h=55;
	private int color=1;//1��ʾ���壬2��ʾ����
	private boolean last=false;//�Ƿ������һ������
	public Chess(int x,int y,int color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	//���ӻ��Ʒ���
	public void draw(Graphics g){
		if(color==1){
			g.drawImage(ImageValue.black, x-h/2, y-h/2, h, h, null);
		}
		else{
			g.drawImage(ImageValue.white, x-h/2-3, y-h/2-3, h, h, null);
			//ͼƬ��Ե�ʣ�������΢����һ��
		}
		if(last){//��������һ������(���������һ�����ϱ��)
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
