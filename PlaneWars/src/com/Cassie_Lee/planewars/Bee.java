package com.Cassie_Lee.planewars;

import java.util.Random;

/**
 * 封装小蜜蜂属性和功能的类
 * @author Cassie_Lee
 *
 */
public class Bee extends Flyer {

	//定义奖励类型备选常量
	public static final int DOUBLE_FIRE = 0;//奖励类型为0,双倍火力
	public static final int LIFE = 1;//奖励类型是1,说明奖励一次生命
	protected int xspeed = 1;//水平速度
	protected int yspeed = 2;//垂直速度
	protected int awardType;//奖励类型
	//对外提供奖励类型的获取方法
	public int getAwardType(){
		return awardType;
	}
	//蜜蜂对象的无参构造方法
	 public Bee() {
		 //step1:
		 image = PlaneWarsGames.bee;
		 //setp2:使用图片的宽高,设置对象的宽高
		 width = image.getWidth();
		 height = image.getHeight();
		 //setp3:设置蜜蜂对象开始下落的高度为-height
		 y = height;
		 //setp4:蜜蜂对象开始下落的x坐标在0~(界面宽度-蜜蜂宽度)之间随机
		 Random r = new Random();
		 x = r.nextInt(PlaneWarsGames.WIDTH-width);
		 //setp5:在0和1之间随机选取一种奖励
		 awardType = r.nextInt(2);
	}
	
	@Override
	public void setp() {
		// 每次x移动一个xspeed,y移动一个yspeed
		x += xspeed;
		y += yspeed;
		//蜜蜂不能超出边界,一旦碰到边界,xspeed*-1,相当于反向移动
		if (x<0||x>(PlaneWarsGames.WIDTH-width)) {
			xspeed *= -1;
		}
		
	}
	@Override
	public boolean outOfBounds() {		
		return y>PlaneWarsGames.HEIGHT;
	}
}
