package fiveinarow;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

class Data{ //����Ȩ�ط�ʱ�����ص�����
	private int count=0;//Ȩ�ط���
	private int i=0;
	private int j=0; //�±�i��j
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

//�Զ���������
class DataCount implements Comparator<Data>{

	@Override
	public int compare(Data o1, Data o2) {
		if(o1.getCount()>o2.getCount()){
			return -1;
		}
		return 1;
	}
	
}



//�����������
public class AI {
	//������һ��
	public static boolean next(Center center){
		boolean flag=goWeight(center)||randomAI(center); //������Ȩ�طּ��㣬���ò�������ķ���ʱ��ִ���������
		return flag;
	}
	
	//����Ȩ�ط�����
	public static boolean goWeight(Center center){
		LinkedList<Data> datas=new LinkedList<Data>();//�������
		Pointer[][] pointers=center.pointers;
		Data data;
		Pointer pointer;
		for(int i=0;i<=center.row;i++){ //ѭ��ָʾ���õ�ÿһ��Ԫ��
			for(int j=0;j<=center.col;j++){
				pointer=pointers[i][j];
				if(pointer==null||pointer.getTag()==0){ //ֻȡ�����ӵ�
					continue;
				}
				//ѭ���ĸ�����4*2��
				int dir=0;
				for(int k=1;k<=4;k++){
					dir=k;
					//����1��ȡȨ�ط�
					data=getData(dir,1,center,pointer);//1��ʾ����
					if(data.getCount()!=0&&data.getCount()!=-1){
						//��ӵ�������
						datas.add(data);
					}
					//����2��ȡȨ�ط�
					data=getData(dir,2,center,pointer);//2��ʾ����
					if(data.getCount()!=0&&data.getCount()!=-1){
						//��ӵ�������
						datas.add(data);
					}
				}
			
			}
		}
		
		//�Ի�ȡ��Ȩ�طֽ��дӴ�С����
		Collections.sort(datas,new DataCount());
		for(int i=0;i<datas.size();i++){
			System.out.println("Ȩ�ط�:"+datas.get(i).getCount());
		}
		//ȡ������Ϊ���ӵĵط�
		if(datas.size()>0){
			Data data2=datas.get(0);//ȡ��һ��Ԫ����Ϊ��һ�����ӵ�λ��
			Pointer pointer2=pointers[data2.getI()][data2.getJ()];
			chessHere(pointer2,center.color,center);
			return true;
		}
		
		return false;
	}
	
	private static Data getData(int dir, int type, Center center,
			Pointer pointer) {
		Data resData=new Data();//���ؽ��
		int i=pointer.getI();
		int j=pointer.getJ();
		Pointer[][] pointers=center.pointers;
		Pointer tempPointer;
		int num=1;//��������õ�num����һ��������Ҳ�ۼӣ�
		int num2=1;//�ۼ���ͬ�ĸ����������ղ��ۼӣ�
		boolean breakFlag=false;//�Ƿ����ж�
		boolean lClosed=false;//��߹ر�
		boolean rClosed=false;//�ұ߹ر�
		//����
		if(dir==1){
			if(type==1){//������
				for(int k=j+1;k<=center.col;k++){
					tempPointer=pointers[i][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==center.col){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
			else{//���ҵ���
				for(int k=j-1;k>=0;k--){
					tempPointer=pointers[i][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
		//����
		else if(dir==2){
			if(type==1){//���ϵ���
				for(int k=i+1;k<=center.row;k++){
					tempPointer=pointers[k][j];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==center.row){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
			else{//���µ���
				for(int k=i-1;k>=0;k--){
					tempPointer=pointers[k][j];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
		//б����
		else if(dir==3){
			int tempi=i;
			if(type==1){//�����ϵ�����
				for(int k=j+1;k<=center.col;k++){
					tempi++;
					if(tempi>center.row){
						rClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==center.col){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
			else{//�����µ�����
				for(int k=j-1;k>=0;k--){
					tempi--;
					if(tempi<0){
						lClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==0){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
		//����б
		else if(dir==4){
			int tempi=i;
			if(type==1){//�����ϵ�����
				for(int k=j-1;k>=0;k--){
					tempi++;
					if(tempi>center.row){
						rClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==0){
							rClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
			else{//�����µ�����
				for(int k=j+1;k<=center.col;k++){
					tempi--;
					if(tempi<0){
						lClosed=true;
						break;
					}
					tempPointer=pointers[tempi][k];
					if(pointer.getTag()==tempPointer.getTag()){//��ʾ����ͬ��ɫ����
						num++;
						num2++;
						if(k==center.col){
							lClosed=true;
						}
					}
					else if(tempPointer.getTag()==0){//��
						if(breakFlag){
							//�ж�ǰһ�����Ƿ��ǿհ�
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
					else{//��ɫ��ͬ
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
		//�趨����
		setCount(resData,i,j,dir,type,num,num2,breakFlag,lClosed,rClosed,center);
		return resData;
	}

	private static void setCount(Data resData, int i, int j, int dir, int type, int num,
			int num2, boolean breakFlag, boolean lClosed, boolean rClosed,
			Center center) { //�趨����
		int count=0;//����ķ���
		if(num<=3)//ֻ��������������������
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
		//�ж��Ƿ��ж�
		if(breakFlag){
			
		}
		else{
			if(lClosed&&rClosed){
				count=-1;
			}
			else if(!lClosed){//��
				count+=2;
				//����
				if(dir==1){
					if(type==1){ //�󿪣������ң��������
						resData.setI(i);
						resData.setJ(j-1);
					}
					else{//�󿪣����������������
						resData.setI(i);
						resData.setJ(j-num+1);
					}
				}
				//����
				else if(dir==2){
					if(type==1){ //�󿪣������ң��������
						resData.setI(i-1);
						resData.setJ(j);
					}
					else{//�󿪣����������������
						resData.setI(i-num+1);
						resData.setJ(j);
					}
				}
				//����б
				else if(dir==3){
					if(type==1){ //�󿪣������ϵ����£���������
						resData.setI(i-1);
						resData.setJ(j-1);
					}
					else{//�󿪣������������ϣ��������ϱ�
						resData.setI(i-num+1);
						resData.setJ(j-num+1);
					}
				}
				//����б
				else if(dir==4){
					if(type==1){ //�󿪣������ϵ����£���������
						resData.setI(i-1);
						resData.setJ(j+1);
					}
					else{//�󿪣������������ϣ��������ϱ�
						resData.setI(i-num+1);
						resData.setJ(j+num-1);
					}
				}
			}
			else if(!rClosed){//�ҿ�
				count+=1;
				//����
				if(dir==1){
					if(type==1){ //�ҿ��������ң������ұ�
						resData.setI(i);
						resData.setJ(j+num-1);
					}
					else{//�ҿ����������������ұ�
						resData.setI(i);
						resData.setJ(j+1);
					}
				}
				else if(dir==2){
					if(type==1){ //�ҿ��������ң������ұ�
						resData.setI(i+num-1);
						resData.setJ(j);
					}
					else{//�ҿ����������������ұ�
						resData.setI(i+1);
						resData.setJ(j);
					}
				}
				else if(dir==3){
					if(type==1){ //�ҿ��������ϵ����£��������±�
						resData.setI(i+num-1);
						resData.setJ(j+num-1);
					}
					else{//�ҿ����������������ұ�
						resData.setI(i+1);
						resData.setJ(j+1);
					}
				}
				else if(dir==4){
					if(type==1){ //�ҿ��������ϵ����£��������±�
						resData.setI(i+num-1);
						resData.setJ(j-num+1);
					}
					else{//�ҿ��������������ϣ��������±�
						resData.setI(i+1);
						resData.setJ(j-1);
					}
				}
			}
		}
		
		
		
		resData.setCount(count);
	}

	//�������
	public static boolean randomAI(Center center){
		//�����ȡָʾ��λ��
		Pointer pointer=getRandomPointer(center);
		//��ָʾ��λ������
		chessHere(pointer,center.color,center);
		return false;
	}
	//������ָʾ����λ��
	private static Pointer getRandomPointer(Center center) {
		Random rand=new Random();
		int i=rand.nextInt(center.row);
		int j=rand.nextInt(center.col);
		Pointer pointer=center.pointers[i][j];
		if(pointer.getTag()!=0){ //�����λ���Ѿ����壬����һ��λ��
			getRandomPointer(center);
		}
		return pointer;
	}
	
	
	//����λ��ִ������
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
