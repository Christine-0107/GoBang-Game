package fiveinarow;

import java.awt.*;

/*
 *ָʾ���� 
 */
public class Pointer {
	private int i=0; //��ά�����±�i
	private int j=0; //��ά�����±�j
	private int x; //x����
	private int y; //y����
	private int h=55;//ָʾ���ĳ��Ϳ�
	private boolean isShow=false;
	private int tag=0;//0��ʾ�õ�δ���壬1��ʾ�õ����˺��壬2��ʾ�õ����˰���
	public Pointer(int i,int j,int x,int y){
		this.i=i;
		this.j=j;
		this.x=x;
		this.y=y;
	}
	
	//����ָʾ��
	public void draw(Graphics g){
		g.setColor(new Color(255,0,0));
		if(isShow){
			drawPointer(g);
		}
		
	}
	private void drawPointer(Graphics g) {
		// TODO Auto-generated method stub
		//ת����2D����
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(3.0f));//���û��ʵĴ�ϸ
		int x1,y1,x2,y2;
		//���Ͻ�
		x1=this.x-h/2;
		y1=this.y-h/2; 
		x2=x1+h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //����
		x2=x1;
		y2=y1+h/4;
		g2d.drawLine(x1, y1, x2, y2); //����
		//���Ͻ�
		x1=this.x+h/2;
		y1=this.y-h/2;
		x2=x1-h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //����
		x2=x1;
		y2=y1+h/4;
		g2d.drawLine(x1, y1, x2, y2); //����
		//���½�
		x1=this.x-h/2;
		y1=this.y+h/2;
		x2=x1+h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //����
		x2=x1;
		y2=y1-h/4;
		g2d.drawLine(x1, y1, x2, y2); //����
		//���½�
		x1=this.x+h/2;
		y1=this.y+h/2;
		x2=x1-h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //����
		x2=x1;
		y2=y1-h/4;
		g2d.drawLine(x1, y1, x2, y2); //����
	}

	//�ж�ʲôʱ��չʾ
	public boolean isPoint(int x,int y){
		int x1=this.x-h/2;
		int y1=this.y-h/2;
		int x2=this.x+h/2;
		int y2=this.y+h/2;
		return x>x1&&x<x2&&y>y1&&y<y2;
	}
	public void setShow(boolean tag){
		isShow=tag;
	}
	
	public void setTag(int tag){
		this.tag=tag;
	}
	public int getTag(){
		return tag;
	}

	//���ָʾ����x�����y����
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	//���ָʾ���Ķ�ά�����±�i,j
	public int getI(){
		return i;
	}
	public int getJ(){
		return j;
	}
	public void setI(){
		this.i=i;
	}
	public void setJ(){
		this.j=j;
	}

}
