package fiveinarow;

import java.awt.*;

/*
 *指示器类 
 */
public class Pointer {
	private int i=0; //二维数组下标i
	private int j=0; //二维数组下标j
	private int x; //x坐标
	private int y; //y坐标
	private int h=55;//指示器的长和宽
	private boolean isShow=false;
	private int tag=0;//0表示该点未下棋，1表示该点下了黑棋，2表示该点下了白棋
	public Pointer(int i,int j,int x,int y){
		this.i=i;
		this.j=j;
		this.x=x;
		this.y=y;
	}
	
	//绘制指示器
	public void draw(Graphics g){
		g.setColor(new Color(255,0,0));
		if(isShow){
			drawPointer(g);
		}
		
	}
	private void drawPointer(Graphics g) {
		// TODO Auto-generated method stub
		//转换成2D画笔
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(3.0f));//设置画笔的粗细
		int x1,y1,x2,y2;
		//左上角
		x1=this.x-h/2;
		y1=this.y-h/2; 
		x2=x1+h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //横向
		x2=x1;
		y2=y1+h/4;
		g2d.drawLine(x1, y1, x2, y2); //竖向
		//右上角
		x1=this.x+h/2;
		y1=this.y-h/2;
		x2=x1-h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //横向
		x2=x1;
		y2=y1+h/4;
		g2d.drawLine(x1, y1, x2, y2); //竖向
		//左下角
		x1=this.x-h/2;
		y1=this.y+h/2;
		x2=x1+h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //横向
		x2=x1;
		y2=y1-h/4;
		g2d.drawLine(x1, y1, x2, y2); //竖向
		//右下角
		x1=this.x+h/2;
		y1=this.y+h/2;
		x2=x1-h/4;
		y2=y1;
		g2d.drawLine(x1, y1, x2, y2); //横向
		x2=x1;
		y2=y1-h/4;
		g2d.drawLine(x1, y1, x2, y2); //竖向
	}

	//判断什么时候展示
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

	//获得指示器的x坐标和y坐标
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	//获得指示器的二维数组下标i,j
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
