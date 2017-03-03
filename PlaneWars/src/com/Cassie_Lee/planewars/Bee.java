package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * ��װС�۷����Ժ͹��ܵ���
 * @author Cassie_Lee
 *
 */
public class Bee extends Flyer {

	//���影�����ͱ�ѡ����
	public static final int DOUBLE_FIRE = 0;//��������Ϊ0,˫������
	public static final int LIFE = 1;//����������1,˵������һ������
	protected int xspeed = 1;//ˮƽ�ٶ�
	protected int yspeed = 2;//��ֱ�ٶ�
	protected int awardType;//��������
	//�����ṩ�������͵Ļ�ȡ����
	public int getAwardType(){
		return awardType;
	}
	//�۷������޲ι��췽��
	 public Bee() {
		 //step1:
		 image = PlaneWarsGames.bee;
		 //setp2:ʹ��ͼƬ�Ŀ��,���ö���Ŀ��
		 width = image.getWidth();
		 height = image.getHeight();
		 //setp3:�����۷����ʼ����ĸ߶�Ϊ-height
		 y = height;
		 //setp4:�۷����ʼ�����x������0~(������-�۷���)֮�����
		 Random r = new Random();
		 x = r.nextInt(PlaneWarsGames.WIDTH-width);
		 //setp5:��0��1֮�����ѡȡһ�ֽ���
		 awardType = r.nextInt(2);
	}
	
	@Override
	public void setp() {
		// ÿ��x�ƶ�һ��xspeed,y�ƶ�һ��yspeed
		x += xspeed;
		y += yspeed;
		//�۷䲻�ܳ����߽�,һ�������߽�,xspeed*-1,�൱�ڷ����ƶ�
		if (x<0||x>(PlaneWarsGames.WIDTH-width)) {
			xspeed *= -1;
		}
		
	}
	@Override
	public boolean outOfBounds() {		
		return y>PlaneWarsGames.HEIGHT;
	}
}
