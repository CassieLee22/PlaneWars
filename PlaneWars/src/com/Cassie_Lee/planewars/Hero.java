package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * 封装英雄机属性和功能的类
 * @author Cassie_Lee
 */
public class Hero extends Flyer {
	private int doubleFire;//双倍火力的子弹数
	private int life;//生命值
	private int score;//得分
	//对外提供的获取生命值的方法
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
	 * 英雄机随鼠标移动的方法
	 * 要求传入鼠标当前所在位置
	 * @param x 鼠标位置的x坐标
	 * @param y 鼠标位置的y坐标
	 */
	public void move(int x,int y){
		//传入的x,y是鼠标的位置
		//move方法的作用就是让英雄机的中心点和鼠标位置一致
		this.x = x-width/2;
		this.y = y-height/2;
	}
	
	/**
	 * 英雄机获得分数和奖励的方法
	 * @param f 是一个父类型的变量
	 */
	public void getScore_Award(Flyer f){
		//先判断敌人对象的类型
		if (f instanceof Airplane) {
			
			score+=((Airplane) f).getScore();
			
		}else {//如果是蜜蜂类型
			//继续判断蜜蜂对象中保存的奖励类型
			if (((Bee)f).getAwardType()==Bee.DOUBLE_FIRE) {
				doubleFire+=40;
				
			}else {
				life+=1;
			}
		}
	}
	/**
	 * 英雄机发射子弹的方法
	 * @return 新创建的子弹对象
	 * 			可能是一发,也可能是两发
	 * 			所以才必须用数组类型保存返回值
	 */
	public Bullet[] shoot(){
		Bullet[] bullets = null;
		//何时创建双倍火力
		if (doubleFire!= 0) {//创建双倍火力
			bullets = new Bullet[2];
			Bullet b1=new Bullet(x+width/4, y-PlaneWarsGames.bullet.getHeight());
			Bullet b2=new Bullet(x+width*3/4, y-PlaneWarsGames.bullet.getHeight());
			bullets[0]=b1;
			bullets[1]=b2;
			//没创建一次双倍火力,子弹数-2
			doubleFire-=2;
		}else {
			
		
		//单倍火力
		//子弹位置的x坐标: x+width/2
		//子弹位置的y坐标: y-子弹图片的高度
		bullets = new Bullet[1];
		bullets[0] = new Bullet(x+width/2,
		y-PlaneWarsGames.bullet.getHeight());
		}
		return bullets;
	}
	
	
	/**
	 * 英雄机自带的和敌人碰撞检测方法
	 * @param f 可能碰撞的敌人
	 * @return  返回true说明碰撞.返回false说明未碰撞
	 */
	public boolean hit(Flyer f){
		//调用碰撞检测方法,检测是否碰撞
		boolean r = Flyer.bang(this, f);
		if (r) {
			life--;
			doubleFire = 0;
		}
		return r;
	}
}
