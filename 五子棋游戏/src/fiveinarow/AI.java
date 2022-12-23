package fiveinarow;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

class Data{ //计算权重分时，返回的数据
	private int count=0;//权重分数
	private int i=0;
	private int j=0; //下标i，j
	public int getCount(){
		return count;
	}
	public void setCount(int count){
		this.count=count;
	}
	public int getI(){
		return i;
	}
	public void setI(int i){
		this.i=i;
	}
	public int getJ(){
		return j;
	}
	public void setJ(int j){
		this.j=j;
	}
}

//自定义排序类
class DataCount implements Comparator<Data>{

	@Override
	public int compare(Data o1, Data o2) {
		if(o1.getCount()>o2.getCount()){
			return -1;
		}
		return 1;
	}
	
}



//电脑随机落子
public class AI {
	//电脑走一步
	public static boolean next(Center center){
		boolean flag=goWeight(center)||randomAI(center); //先利用权重分计算，当得不到满意的分数时，执行随机落子
		return flag;
	}
	
	//根据权重分落子
	public static boolean goWeight(Center center){
		LinkedList<Data> datas=new LinkedList<Data>();//存放数据
		Pointer[][] pointers=center.pointers;
		Data data;
		Pointer pointer;
		for(int i=0;i<=center.row;i++){ //循环指示器拿到每一个元素
			for(int j=0;j<=center.col;j++){
				pointer=pointers[i][j];
				if(pointer==null||pointer.getTag()==0){ //只取有棋子的
					continue;
				}
				//循环四个方向（4*2）
				int dir=0;
				for(int k=1;k<=4;k++){
					dir=k;
					//方向1获取权重分
					data=getData(dir,1,center,pointer);//1表示方向
					if(data.getCount()!=0&&data.getCount()!=-1){
						//添加到集合中
						datas.add(data);
					}
					//方向2获取权重分
					data=getData(dir,2,center,pointer);//2表示方向
					if(data.getCount()!=0&&data.getCount()!=-1){
						//添加到集合中
						datas.add(data);
					}
				}
			
			}
		}
		
		//对获取的权重分进行从大到小排序
		Collections.sort(datas,new DataCount());
		for(int i=0;i<datas.size();i++){
			System.out.println("权重分:"+datas.get(i).getCount());
		}
		//取最大的作为落子的地方
		if(datas.size()>0){
			Data data2=datas.get(0);//取第一个元素作为下一次落子的位置
			Pointer pointer2=pointers[data2.getI()][data2.getJ()];
			chessHere(pointer2,center.color,center);
			return true;
		}
		
		return false;
	}
	
	private static Data getData(int dir, int type, Center center,
			Pointer pointer) {
		Data resData=new Data();//返回结果
		int i=pointer.getI();
		int j=pointer.getJ();
		Pointer[][] pointers=center.pointers;
		Pointer tempPointer;
		int num=1;//计算分数用的num（第一次遇到空也累加）
		int num2=1;//累计相同的个数（遇到空不累加）
		boolean breakFlag=false;//是否有中断
		boolean lClosed=false;//左边关闭
		boolean rClosed=false;//右边关闭
		//横向
		if(dir==1){
			if(type==1){//从左到右
				for(int k=j+1;k<=center.col;k++){
					tempPointer=pointers[i][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==center.col){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[i][k-1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}
					else{//颜色不同
						rClosed=true;
						break;
					}
					if(j==0){
						lClosed=true;
					}
					else{
						if(pointers[i][j-1].getTag()!=0){
							lClosed=true;
						}
					}
				}
			}
			else{//从右到左
				for(int k=j-1;k>=0;k--){
					tempPointer=pointers[i][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[i][k+1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}
					else{//颜色不同
						lClosed=true;
						break;
					}
					if(j==center.col){
						rClosed=true;
					}
					else{
						if(pointers[i][j+1].getTag()!=0){
							rClosed=true;
						}
					}
				}
			}
		}
		//竖向
		else if(dir==2){
			if(type==1){//从上到下
				for(int k=i+1;k<=center.row;k++){
					tempPointer=pointers[k][j];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==center.row){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[k-1][j].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(k);
						resData.setJ(j);
					}
					else{//颜色不同
						rClosed=true;
						break;
					}
					if(i==0){
						lClosed=true;
					}
					else{
						if(pointers[i-1][j].getTag()!=0){
							lClosed=true;
						}
					}
				}
			}
			else{//从下到上
				for(int k=i-1;k>=0;k--){
					tempPointer=pointers[k][j];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[k+1][j].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(k);
						resData.setJ(j);
					}
					else{//颜色不同
						lClosed=true;
						break;
					}
					if(i==center.row){
						rClosed=true;
					}
					else{
						if(pointers[i+1][j].getTag()!=0){
							rClosed=true;
						}
					}
				}
			}
		}
		//斜右下
		else if(dir==3){
			int tempi=i;
			if(type==1){//从左上到右下
				for(int k=j+1;k<=center.col;k++){
					tempi++;
					if(tempi>center.row){
						rClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==center.col){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[tempi-1][k-1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}
					else{//颜色不同
						rClosed=true;
						break;
					}
					if(i==0||j==0){
						lClosed=true;
					}
					else{
						if(pointers[i-1][j-1].getTag()!=0){
							lClosed=true;
						}
					}
				}
			}
			else{//从右下到左上
				for(int k=j-1;k>=0;k--){
					tempi--;
					if(tempi<0){
						lClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[tempi+1][k+1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}
					else{//颜色不同
						lClosed=true;
						break;
					}
					if(j==center.col||i==center.row){
						rClosed=true;
					}
					else{
						if(pointers[i+1][j+1].getTag()!=0){
							rClosed=true;
						}
					}
				}
			}
		}
		//左下斜
		else if(dir==4){
			int tempi=i;
			if(type==1){//从右上到左下
				for(int k=j-1;k>=0;k--){
					tempi++;
					if(tempi>center.row){
						rClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==0){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[tempi-1][k+1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}
					else{//颜色不同
						rClosed=true;
						break;
					}
					if(i==0||j==center.col){
						lClosed=true;
					}
					else{
						if(pointers[i-1][j+1].getTag()!=0){
							lClosed=true;
						}
					}
				}
			}
			else{//从左下到右上
				for(int k=j+1;k<=center.col;k++){
					tempi--;
					if(tempi<0){
						lClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//表示是相同颜色连续
						num++;
						num2++;
						if(k==center.col){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//空
						if(breakFlag){
							//判断前一个子是否是空白
							if(pointers[tempi+1][k-1].getTag()==0){
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}
					else{//颜色不同
						lClosed=true;
						break;
					}
					if(j==0||i==center.row){
						rClosed=true;
					}
					else{
						if(pointers[i+1][j-1].getTag()!=0){
							rClosed=true;
						}
					}
				}
			}
		}
		//设定分数
		setCount(resData,i,j,dir,type,num,num2,breakFlag,lClosed,rClosed,center);
		return resData;
	}

	private static void setCount(Data resData, int i, int j, int dir, int type, int num,
			int num2, boolean breakFlag, boolean lClosed, boolean rClosed,
			Center center) { //设定分数
		int count=0;//计算的分数
		if(num<=3)//只对连三个及以上做处理
			return;
		if(num==4){ 
			count=30;
		}
		else if(num==5){
			count=40;
		}
		else if(num==6){
			count=50;
		}
		//判断是否中断
		if(breakFlag){
			
		}
		else{
			if(lClosed&&rClosed){
				count=-1;
			}
			else if(!lClosed){//左开
				count+=2;
				//横向
				if(dir==1){
					if(type==1){ //左开，从左到右，设在左边
						resData.setI(i);
						resData.setJ(j-1);
					}
					else{//左开，从右往左，设在左边
						resData.setI(i);
						resData.setJ(j-num+1);
					}
				}
				//竖向
				else if(dir==2){
					if(type==1){ //左开，从左到右，设在左边
						resData.setI(i-1);
						resData.setJ(j);
					}
					else{//左开，从右往左，设在左边
						resData.setI(i-num+1);
						resData.setJ(j);
					}
				}
				//右下斜
				else if(dir==3){
					if(type==1){ //左开，从左上到右下，设在左上
						resData.setI(i-1);
						resData.setJ(j-1);
					}
					else{//左开，从右下往左上，设在左上边
						resData.setI(i-num+1);
						resData.setJ(j-num+1);
					}
				}
				//左下斜
				else if(dir==4){
					if(type==1){ //左开，从右上到左下，设在右上
						resData.setI(i-1);
						resData.setJ(j+1);
					}
					else{//左开，从左下往右上，设在右上边
						resData.setI(i-num+1);
						resData.setJ(j+num-1);
					}
				}
			}
			else if(!rClosed){//右开
				count+=1;
				//横向
				if(dir==1){
					if(type==1){ //右开，从左到右，设在右边
						resData.setI(i);
						resData.setJ(j+num-1);
					}
					else{//右开，从右往左，设在右边
						resData.setI(i);
						resData.setJ(j+1);
					}
				}
				else if(dir==2){
					if(type==1){ //右开，从左到右，设在右边
						resData.setI(i+num-1);
						resData.setJ(j);
					}
					else{//右开，从右往左，设在右边
						resData.setI(i+1);
						resData.setJ(j);
					}
				}
				else if(dir==3){
					if(type==1){ //右开，从左上到右下，设在右下边
						resData.setI(i+num-1);
						resData.setJ(j+num-1);
					}
					else{//右开，从右往左，设在右边
						resData.setI(i+1);
						resData.setJ(j+1);
					}
				}
				else if(dir==4){
					if(type==1){ //右开，从右上到左下，设在左下边
						resData.setI(i+num-1);
						resData.setJ(j-num+1);
					}
					else{//右开，从左下往右上，设在左下边
						resData.setI(i+1);
						resData.setJ(j-1);
					}
				}
			}
		}
		
		
		
		resData.setCount(count);
	}

	//随机落子
	public static boolean randomAI(Center center){
		//随机获取指示器位置
		Pointer pointer=getRandomPointer(center);
		//在指示器位置下棋
		chessHere(pointer,center.color,center);
		return false;
	}
	//随机获得指示器的位置
	private static Pointer getRandomPointer(Center center) {
		Random rand=new Random();
		int i=rand.nextInt(center.row);
		int j=rand.nextInt(center.col);
		Pointer pointer=center.pointers[i][j];
		if(pointer.getTag()!=0){ //如果此位置已经有棋，再找一个位置
			getRandomPointer(center);
		}
		return pointer;
	}
	
	
	//根据位置执行落子
	private static void chessHere(Pointer pointer, int color, Center center) {
		// TODO Auto-generated method stub
		Chess chess=new Chess(pointer.getX(),pointer.getY(),color);
		chess.setLast(true);
		center.chesses.add(chess);
		center.win(pointer.getI(), pointer.getJ(), color);
		pointer.setTag(color);
		center.repaint();
		center.gameFlag=true;
		if(color==1)
			center.color=2;
		else
			center.color=1;
	}
}
