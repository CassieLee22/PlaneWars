package com.Cassie_Lee.planewars;
/**
 * ��װ�ӵ����Ժ͹��ܵ���
 * @author Cassie_Lee
 *
 */
public class Bullet extends Flyer {
	private int speed = 3;
	/**
	 * �ӵ���Ĵ��ι��췽��
	 * ��Ϊ�ӵ����󴴽���λ��Ҫ����Ӣ�ۻ���λ��������
	 * �����ӵ������x��y����Ҫ����紫��
	 */
	public  Bullet(int x,int y) {
		// TODO Auto-generated constructor stub
		image = PlaneWarsGames.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void setp() {
		//�ӵ�ÿ�������ƶ�һ��speed
		y -= speed;
	}
	
	@Override
	public boolean outOfBounds() {
		return y+height<0;
	}
}
