package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * ��װӢ�ۻ����Ժ͹��ܵ���
 * @author Cassie_Lee
 */
public class Hero extends Flyer {
	private int doubleFire;//˫���������ӵ���
	private int life;//����ֵ
	private int score;//�÷�
	//�����ṩ�Ļ�ȡ����ֵ�ķ���
	public int getLife()
	{
		return life;
	}
	public int getScore()
	{
		return score;
	}
	public  Hero() {
		// TODO Auto-generated constructor stub
		image = PlaneWarsGames.hero0;
		width = image.getWidth();
		height = image.getHeight();
		doubleFire=0;
		life = 3;
		score =0;
		y=440;
		x=150;
	}
	
	@Override
	public void setp() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Ӣ�ۻ�������ƶ��ķ���
	 * Ҫ������굱ǰ����λ��
	 * @param x ���λ�õ�x����
	 * @param y ���λ�õ�y����
	 */
	public void move(int x,int y){
		//�����x,y������λ��
		//move���������þ�����Ӣ�ۻ������ĵ�����λ��һ��
		this.x = x-width/2;
		this.y = y-height/2;
	}
	
	/**
	 * Ӣ�ۻ���÷����ͽ����ķ���
	 * @param f ��һ�������͵ı���
	 */
	public void getScore_Award(Flyer f){
		//���жϵ��˶��������
		if (f instanceof Airplane) {
			
			score+=((Airplane) f).getScore();
			
		}else {//������۷�����
			//�����ж��۷�����б���Ľ�������
			if (((Bee)f).getAwardType()==Bee.DOUBLE_FIRE) {
				doubleFire+=40;
				
			}else {
				life+=1;
			}
		}
	}
	/**
	 * Ӣ�ۻ������ӵ��ķ���
	 * @return �´������ӵ�����
	 * 			������һ��,Ҳ����������
	 * 			���Բű������������ͱ��淵��ֵ
	 */
	public Bullet[] shoot(){
		Bullet[] bullets = null;
		//��ʱ����˫������
		if (doubleFire!= 0) {//����˫������
			bullets = new Bullet[2];
			Bullet b1=new Bullet(x+width/4, y-PlaneWarsGames.bullet.getHeight());
			Bullet b2=new Bullet(x+width*3/4, y-PlaneWarsGames.bullet.getHeight());
			bullets[0]=b1;
			bullets[1]=b2;
			//û����һ��˫������,�ӵ���-2
			doubleFire-=2;
		}else {
			
		
		//��������
		//�ӵ�λ�õ�x����: x+width/2
		//�ӵ�λ�õ�y����: y-�ӵ�ͼƬ�ĸ߶�
		bullets = new Bullet[1];
		bullets[0] = new Bullet(x+width/2,
		y-PlaneWarsGames.bullet.getHeight());
		}
		return bullets;
	}
	
	
	/**
	 * Ӣ�ۻ��Դ��ĺ͵�����ײ��ⷽ��
	 * @param f ������ײ�ĵ���
	 * @return  ����true˵����ײ.����false˵��δ��ײ
	 */
	public boolean hit(Flyer f){
		//������ײ��ⷽ��,����Ƿ���ײ
		boolean r = Flyer.bang(this, f);
		if (r) {
			life--;
			doubleFire = 0;
		}
		return r;
	}
}
