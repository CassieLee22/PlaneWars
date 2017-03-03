package com.Cassie_Lee.planewars;

import java.awt.image.BufferedImage;

/**
 * 封装所有飞行物公共属性和功能的父类
 * @author Cassie_Lee
 *
 */
public abstract class Flyer {
	protected int x;//飞行物左上角的x坐标
	protected int y;//飞行物左上角的y坐标
	protected int width;//飞行物的宽度
	protected int height;//飞行物的高度
	//java中保存图片使用BufferedImage类型
	protected BufferedImage image;//飞行物使用的图片\
	//要求所有飞行物必须都能移动,但移动的方式由子类自己实现
	public abstract void setp();
	public abstract boolean outOfBounds() ;
	/**
	 * 专门检测两个矩形飞行物是否碰撞的工具方法
	 * 和具体对象无关,所以定义为静态方法
	 * @param f1   飞行物对象1
	 * @param f2  飞行物对象2
	 * @return 返回true说明碰撞
	 */
	public static boolean bang(Flyer f1,Flyer f2){
		//step1:求出两个矩形的中心点
		int f1x = f1.x+ f1.width/2;
		int f1y = f1.y+ f1.height/2;
		int f2x = f2.x+ f2.width/2;
		int f2y = f2.y+ f2.height/2;
		//step2:横向和纵向碰撞检测
		boolean H= Math.abs(f1x-f2x)<(f1.width+f2.width)/2;
		boolean V= Math.abs(f1y-f2y)<(f1.height+f2.height)/2;
		//step3:必须两个方向都碰撞,才说明真正碰撞了	
		return H&V;
	}
}
