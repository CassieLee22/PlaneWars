package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * ��װ�л����Ժ͹��ܵ���
 * @author Cassie_Lee
 *
 */
public class Airplane extends Flyer {
	private int speed = 2;//�л�ÿ������������λ
	private int score = 5;//�л������Ľ�������
	//�����ṩ�Ķ�ȡ���������ķ���
	public int getScore()
	{
		return score;
	}

	public  Airplane() {
		// TODO Auto-generated constructor stub
		image = PlaneWarsGames.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y=-height;
		Random r = new Random();
		x= r.nextInt(PlaneWarsGames.WIDTH-width);
	}
	
	@Override
	public void setp() {
		//ÿ�������ƶ�һ��speed
		y += speed;
		
	}
	@Override
	public boolean outOfBounds() {
		//�л�y���������Ϸ����߶�,����Խ��
		return y>PlaneWarsGames.HEIGHT;
	}

	
}
