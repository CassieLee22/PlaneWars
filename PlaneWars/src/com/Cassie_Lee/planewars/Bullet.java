package com.Cassie_Lee.planewars;
/**
 * 封装子弹属性和功能的类
 * @author Cassie_Lee
 *
 */
public class Bullet extends Flyer {
	private int speed = 3;
	/**
	 * 子弹类的带参构造方法
	 * 因为子弹对象创建的位置要根据英雄机的位置来决定
	 * 所以子弹对象的x和y坐标要从外界传入
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
		//子弹每次向上移动一个speed
		y -= speed;
	}
	
	@Override
	public boolean outOfBounds() {
		return y+height<0;
	}
}
