package com.Cassie_Lee.planewars;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.CORBA.PRIVATE_MEMBER;

public class PlaneWarsGames extends JPanel {
	//游戏界面大小固定 :320*568  常量
	public static final int WIDTH = 320;
	public static final int HEIGHT = 568;
		
	/*
	 *游戏启动的第一件事,是从硬盘加载所以要用到的图片到内存中
	 *而且,仅在启动时加载一次静态块
	 *缓存在程序中的所有图片,都会反复使用,仅保存一份--静态变量
	 *下面,为每张图片定义一个静态变量,然后在静态块中加载每张图片
	 */
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	
	/**
	 * 为游戏中的角色对象定义数据结构
	 * 1个英雄机对象
	 * 1个存储所有敌人对象的数组
	 * 1个存储所有子弹对象的数组
	 */
	public Hero hero = new Hero();
	/**
	 * 问题:一个数组既要保存敌机类型对象,又要保存蜜蜂类型对象
	 * 解决:用父类型定义数组
	 */
	public Flyer[] flyers = {};//所有敌人对象的数组
	public Bullet[] bullets = {};//存储所有子弹对象的数组
	
	//定义游戏状态:当前状态变量:默认是开始状态
	private int state = START;
	//定义游戏状态备选项的变量
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER=3;
	
	static{//静态块,仅在类首次加载到方法区时执行一次,专门加载静态资源
		/**
		 * JAVA中从硬盘加载图片到内存中
		 * ImageIO.read方法,专门从硬盘加载图片的静态方法
		 * 不用实例化,直接调用
		 * ShootGame.class:获得当前类的加载器所在的路径
		 * ShootGame.class.getResource("文件名:"):从当前类所在的路径下加载文件
		 */
		try {
			background = ImageIO.read(PlaneWarsGames.class.getResource("background.png"));
			start = ImageIO.read(PlaneWarsGames.class.getResource("start.png"));
			airplane = ImageIO.read(PlaneWarsGames.class.getResource("airplane.png"));
			bee = ImageIO.read(PlaneWarsGames.class.getResource("bee.png"));
			bullet = ImageIO.read(PlaneWarsGames.class.getResource("bullet.png"));
			hero0 = ImageIO.read(PlaneWarsGames.class.getResource("hero0.png"));
			hero1 = ImageIO.read(PlaneWarsGames.class.getResource("hero1.png"));
			pause  = ImageIO.read(PlaneWarsGames.class.getResource("pause.png"));
			gameover = ImageIO.read(PlaneWarsGames.class.getResource("gameover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		/**
		 * java中绘制窗体:JFrame对象----窗框
		 * 要想在窗体中绘制内容,还需要嵌入北京面板----JPanel
		 */
			JFrame frame = new JFrame("Fly");
			frame.setSize(WIDTH, HEIGHT);
			frame.setAlwaysOnTop(true);//设置窗体总在最上面
			//设置窗体关闭同时,退出程序
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);//设置窗体初始位置
			//在窗体中嵌入背景面板对象----jpnel
			PlaneWarsGames game = new PlaneWarsGames();
			frame.add(game);//将背景面板对象嵌入到窗体对象中
			//窗体默认不可见,必须显示调用setVisible方法才能显示出窗体
			frame.setVisible(true);//自动调用窗体的paint方法	
			game.action();
	}
	
	
	/**
	 * 游戏启动时要做的事
	 */
	public void action(){
		/**
		 * 游戏开始时,要定义鼠标事件的监听
		 */
		//setp1:创建Mouseadapter匿名内部类----事件的响应
		  MouseAdapter l =new MouseAdapter() {

			//setp2:重写希望的鼠标事件方法--鼠标移动	 
			  @Override
			public void mouseMoved(MouseEvent e) {
				//setp3:获得鼠标新位置x,y
				  if (state == RUNNING) {
					
				
				int x = e.getX();
				int y = e.getY();
				//setp4:将鼠标位置传递给英雄机对象的move方法
				hero.move(x, y);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (state == START) {//只有处于start状态下,改为running
					state = RUNNING;
				}else if (state == GAME_OVER) {
					state = START;
					flyers=new Flyer[0];
					bullets = new Bullet[0];
					hero = new Hero();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}	
			  
		};
		//setp5:要响应鼠标事件,必须将鼠标事件添加到程序的监听器
		this.addMouseMotionListener(l);//支持鼠标移动事件
		/*
		 * mouseMoionListener不支持鼠标单击事件
		 * 要想程序响应鼠标单击事件,必须再注册一个新的监听器
		 */
		this.addMouseListener(l);//支持鼠标单击事件
		//step1:创建定时器
		Timer timer = new Timer();
		//step2:调用定时器对象schedu方法,做计划
		//      第一个参数:TimerTask类型的匿名内部类
		//				必要重写run方法----核心---要做什么事
		timer.schedule(new TimerTask() {
			//首先:定义一个计时器变量index,记录run方法执行的
		 private int runTimers = 0;
			@Override
			public void run() {
				//除repaint方法外,其余功能只在running状态执行
				if (state == RUNNING) {
					runTimers++;
					if(runTimers%50 == 0){// 自动随机创建对象
						nextOne();
					}
					//遍历每个对象,调用对象的step方法,移动一次对象的位置
					for( int i = 0;i<flyers.length;i++){
						flyers[i].setp();
					}
					//每300毫秒创建一次子弹
					if (runTimers%30 == 0) {
						shoot();
					}
					//遍历子弹数组中的每个子弹对象,移动位置
					for (int i = 0; i < bullets.length; i++) {
						bullets[i].setp();
					}
					//子弹击中敌人的碰撞检测
					bang();
					//敌人和英雄机碰撞检测
					hit();
					//越界碰撞销毁
					outOfBounds();
				}
				/*
				 * 强调:只要界面发生任何改变,必须用repaint方法重绘
				 */
				repaint();
			}
		}, 10,10);//界面每隔10毫秒变化一次
	}

	@Override
	public void paint(Graphics g) {
		/*
		//从(10,10)到(100,100),画直线
		g.drawLine(10, 10, 100, 100);
		//以(10,10)为左上角,画一个宽100高100的矩形
		g.drawRect(10, 10, 100, 100);
		//以(10,10)为左上角,在100*100范围内画一个圆
		g.drawOval(10, 10, 100, 100);
		*/
		//setp1:绘制背景图片,以(0,0)为左上角,绘制图片background
		g.drawImage(background, 0, 0, null);	
		//setp2:绘制英雄机
		paintHero(g);
		//setp3:批量绘制敌人
		painFlyers(g);
		//setp4:批量绘制子弹
		paintBullets(g);
		//绘制分数和生命
		paintScore_Life(g);
		
		//根据游戏状态,绘制不同图片
		if (state == START) {
			g.drawImage(start, 0, 0, null);
		} else if (state == PAUSE) {
			g.drawImage(pause, 0, 0, null);
		}else if (state == GAME_OVER) {
			g.drawImage(gameover, 0, 0, null);
		}
			
		

	}
	
	//绘制英雄机
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	//遍历敌人数组,批量绘制所有敌人对象的方法
	public void painFlyers(Graphics g) {
		for(int i =0 ; i<flyers.length;i++){
			g.drawImage(flyers[i].image, flyers[i].x, flyers[i].y, null);
		}
	}
	//遍历子弹数组,批量绘制所有子弹对象的方法
	public void paintBullets(Graphics g){
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
		}
	}
	
	/**
	 *随机生成1个敌人对象
	 *每生成一个新敌人,flyer数组都要扩容1
	 *然后将新敌人放入数组最后一个元素
	 */
	public void nextOne(){
		Random r = new Random();
		Flyer f = null;
		if (r.nextInt(20) == 0) {//只有当随机数取0时,才创建一个蜜蜂
			f = new Bee();
		}else {//其余都创建敌机
			f= new Airplane();
		}
		//先对flyer数组扩容1
		flyers = Arrays.copyOf(flyers, flyers.length+1);
		//将新敌人放入数组末尾
		flyers[flyers.length-1] = f;
		
	}
	/**
	 * 获得英雄机对象发射的子弹对象
	 * 将新的子弹对象保存到子弹数组中统一管理
	 */
	public void shoot(){
		Bullet[] newBullets = hero.shoot();//获得英雄机返回的新子弹数
		//根据返回新子弹的数量扩容子弹数组
		bullets = Arrays.copyOf(bullets, bullets.length+newBullets.length);
		//从newBullets数组中拷贝
		System.arraycopy(newBullets, 0, bullets,
				bullets.length-newBullets.length,
				newBullets.length);
	}
	/**
	 * 遍历子弹数组和敌人数,进行碰撞检测
	 * 一旦发生碰撞,敌人和子弹都减少一个
	 */
	public void bang() {
		for (int i = 0; i < bullets.length; i++) {//取出每颗子弹
			for (int j = 0; j < flyers.length; j++) {//和每个敌人做检测
				if (Flyer.bang(bullets[i], flyers[j])) {//如果发生碰撞
					//为英雄机获得分数和奖励
					hero.getScore_Award(flyers[j]);
					
					//从敌人数组中删除被击中的敌机
					//step1:使用数组最后一个元素,替换当前被击中的敌人
					flyers[j]= flyers[flyers.length-1];
					//setp2:压缩数组,元素个数－1
					flyers = Arrays.copyOf(flyers, flyers.length-1);
					//从子弹数组中删除击中敌人的子弹
					bullets[i] = bullets[bullets.length-1];
					bullets= Arrays.copyOf(bullets, bullets.length-1);
					i--;//每发现一次碰撞,子弹就要退一个元素,重新检测当前位置新子弹					
					//只要有敌人被击中,就退出遍历敌人数组的循环,不再向后继续判断		
					break;					
				}
			}
			
		}
	}
	/**
	 * 绘制分数和生命值的方法
	 * @param g
	 */
	public void paintScore_Life(Graphics g){
		int x = 10;
		int y = 15;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		g.setFont(font);
		//绘制第一行,分数
	     g.drawString("SCORE:"+hero.getScore(), x, y);
	     //绘制第二行,y坐标下移20个单位
	     y+=20;
	     g.drawString("LIFE:"+hero.getLife(), x, y);
	     
	}
	/**
	 * 检查所有飞行物是否越界
	 */
	public void outOfBounds(){
		//检查所有敌人是否越界
		Flyer[] Flives = new Flyer[flyers.length];
		//遍历敌人数组,将存活的敌人对象,保存到新数组中
		//设计Flives数组的计数器index:  1.标示下一个存活对象的位置
		//						   2.统计lives数组中总共有多少存活对象
		int index = 0;
		for (int i = 0; i < Flives.length; i++) {
			if (!flyers[i].outOfBounds()) {
				Flives[index]=flyers[i];
				index++;
			} 
		}//遍历结束后:
		//index中保存的是当前存活对象的个数
		//lives数组中保存的是当前存活对象
		//对lives数组,按照index中的个数进行压缩
		//压缩后的新数组,替换回flyers变量中
		flyers = Arrays.copyOf(Flives, index);
		//检测所有子弹越界
		Bullet[] Blives = new Bullet[bullets.length];
		index = 0;
		for (int i = 0; i < bullets.length; i++) {
			if (!bullets[i].outOfBounds()) {
				Blives[index] = bullets[i];
				index++;
			}
		}
		bullets = Arrays.copyOf(Blives, index);
	}
	
	/**
	 * 遍历敌人数组,英雄机和每个敌人对象判断碰撞
	 */
	public void hit() {
		Flyer[] lives= new Flyer[flyers.length];
		//记录存活的敌人
		int index = 0;
		for (int i = 0; i < flyers.length; i++) {
			if(!hero.hit(flyers[i])){
				lives[index] = flyers[i];
				index++;
			}
		}
		if (hero.getLife()<=0) {
			state = GAME_OVER;
		}
		//压缩存活的敌人数组,并且替换敌人数组
		flyers = Arrays.copyOf(lives, index);
	}
	
}
