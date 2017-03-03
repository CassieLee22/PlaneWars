package com.Cassie_Lee.planewars;

import java.awt.image.BufferedImage;

/**
 * ��װ���з����﹫�����Ժ͹��ܵĸ���
 * @author Cassie_Lee
 *
 */
public abstract class Flyer {
	protected int x;//���������Ͻǵ�x����
	protected int y;//���������Ͻǵ�y����
	protected int width;//������Ŀ��
	protected int height;//������ĸ߶�
	//java�б���ͼƬʹ��BufferedImage����
	protected BufferedImage image;//������ʹ�õ�ͼƬ\
	//Ҫ�����з�������붼���ƶ�,���ƶ��ķ�ʽ�������Լ�ʵ��
	public abstract void setp();
	public abstract boolean outOfBounds() ;
	/**
	 * ר�ż���������η������Ƿ���ײ�Ĺ��߷���
	 * �;�������޹�,���Զ���Ϊ��̬����
	 * @param f1   ���������1
	 * @param f2  ���������2
	 * @return ����true˵����ײ
	 */
	public static boolean bang(Flyer f1,Flyer f2){
		//step1:����������ε����ĵ�
		int f1x = f1.x+ f1.width/2;
		int f1y = f1.y+ f1.height/2;
		int f2x = f2.x+ f2.width/2;
		int f2y = f2.y+ f2.height/2;
		//step2:�����������ײ���
		boolean H= Math.abs(f1x-f2x)<(f1.width+f2.width)/2;
		boolean V= Math.abs(f1y-f2y)<(f1.height+f2.height)/2;
		//step3:��������������ײ,��˵��������ײ��	
		return H&V;
	}
}
