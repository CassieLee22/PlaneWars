package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * 封装敌机属性和功能的类
 * @author Cassie_Lee
 *
 */
public class Airplane extends Flyer {
	private int speed = 2;//敌机每次下落两个单位
	private int score = 5;//敌机包含的奖励分数
	//对外提供的读取奖励分数的方法
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
		//每次向下移动一个speed
		y += speed;
		
	}
	@Override
	public boolean outOfBounds() {
		//敌机y坐标大于游戏界面高度,就算越界
		return y>PlaneWarsGames.HEIGHT;
	}

	
}
