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
	//��Ϸ�����С�̶� :320*568  ����
	public static final int WIDTH = 320;
	public static final int HEIGHT = 568;
		
	/*
	 *��Ϸ�����ĵ�һ����,�Ǵ�Ӳ�̼�������Ҫ�õ���ͼƬ���ڴ���
	 *����,��������ʱ����һ�ξ�̬��
	 *�����ڳ����е�����ͼƬ,���ᷴ��ʹ��,������һ��--��̬����
	 *����,Ϊÿ��ͼƬ����һ����̬����,Ȼ���ھ�̬���м���ÿ��ͼƬ
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
	 * Ϊ��Ϸ�еĽ�ɫ���������ݽṹ
	 * 1��Ӣ�ۻ�����
	 * 1���洢���е��˶��������
	 * 1���洢�����ӵ����������
	 */
	public Hero hero = new Hero();
	/**
	 * ����:һ�������Ҫ����л����Ͷ���,��Ҫ�����۷����Ͷ���
	 * ���:�ø����Ͷ�������
	 */
	public Flyer[] flyers = {};//���е��˶��������
	public Bullet[] bullets = {};//�洢�����ӵ����������
	
	//������Ϸ״̬:��ǰ״̬����:Ĭ���ǿ�ʼ״̬
	private int state = START;
	//������Ϸ״̬��ѡ��ı���
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER=3;
	
	static{//��̬��,�������״μ��ص�������ʱִ��һ��,ר�ż��ؾ�̬��Դ
		/**
		 * JAVA�д�Ӳ�̼���ͼƬ���ڴ���
		 * ImageIO.read����,ר�Ŵ�Ӳ�̼���ͼƬ�ľ�̬����
		 * ����ʵ����,ֱ�ӵ���
		 * ShootGame.class:��õ�ǰ��ļ��������ڵ�·��
		 * ShootGame.class.getResource("�ļ���:"):�ӵ�ǰ�����ڵ�·���¼����ļ�
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
		 * java�л��ƴ���:JFrame����----����
		 * Ҫ���ڴ����л�������,����ҪǶ�뱱�����----JPanel
		 */
			JFrame frame = new JFrame("Fly");
			frame.setSize(WIDTH, HEIGHT);
			frame.setAlwaysOnTop(true);//���ô�������������
			//���ô���ر�ͬʱ,�˳�����
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);//���ô����ʼλ��
			//�ڴ�����Ƕ�뱳��������----jpnel
			PlaneWarsGames game = new PlaneWarsGames();
			frame.add(game);//������������Ƕ�뵽���������
			//����Ĭ�ϲ��ɼ�,������ʾ����setVisible����������ʾ������
			frame.setVisible(true);//�Զ����ô����paint����	
			game.action();
	}
	
	
	/**
	 * ��Ϸ����ʱҪ������
	 */
	public void action(){
		/**
		 * ��Ϸ��ʼʱ,Ҫ��������¼��ļ���
		 */
		//setp1:����Mouseadapter�����ڲ���----�¼�����Ӧ
		  MouseAdapter l =new MouseAdapter() {

			//setp2:��дϣ��������¼�����--����ƶ�	 
			  @Override
			public void mouseMoved(MouseEvent e) {
				//setp3:��������λ��x,y
				  if (state == RUNNING) {
					
				
				int x = e.getX();
				int y = e.getY();
				//setp4:�����λ�ô��ݸ�Ӣ�ۻ������move����
				hero.move(x, y);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (state == START) {//ֻ�д���start״̬��,��Ϊrunning
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
		//setp5:Ҫ��Ӧ����¼�,���뽫����¼���ӵ�����ļ�����
		this.addMouseMotionListener(l);//֧������ƶ��¼�
		/*
		 * mouseMoionListener��֧����굥���¼�
		 * Ҫ�������Ӧ��굥���¼�,������ע��һ���µļ�����
		 */
		this.addMouseListener(l);//֧����굥���¼�
		//step1:������ʱ��
		Timer timer = new Timer();
		//step2:���ö�ʱ������schedu����,���ƻ�
		//      ��һ������:TimerTask���͵������ڲ���
		//				��Ҫ��дrun����----����---Ҫ��ʲô��
		timer.schedule(new TimerTask() {
			//����:����һ����ʱ������index,��¼run����ִ�е�
		 private int runTimers = 0;
			@Override
			public void run() {
				//��repaint������,���๦��ֻ��running״ִ̬��
				if (state == RUNNING) {
					runTimers++;
					if(runTimers%50 == 0){// �Զ������������
						nextOne();
					}
					//����ÿ������,���ö����step����,�ƶ�һ�ζ����λ��
					for( int i = 0;i<flyers.length;i++){
						flyers[i].setp();
					}
					//ÿ300���봴��һ���ӵ�
					if (runTimers%30 == 0) {
						shoot();
					}
					//�����ӵ������е�ÿ���ӵ�����,�ƶ�λ��
					for (int i = 0; i < bullets.length; i++) {
						bullets[i].setp();
					}
					//�ӵ����е��˵���ײ���
					bang();
					//���˺�Ӣ�ۻ���ײ���
					hit();
					//Խ����ײ����
					outOfBounds();
				}
				/*
				 * ǿ��:ֻҪ���淢���κθı�,������repaint�����ػ�
				 */
				repaint();
			}
		}, 10,10);//����ÿ��10����仯һ��
	}

	@Override
	public void paint(Graphics g) {
		/*
		//��(10,10)��(100,100),��ֱ��
		g.drawLine(10, 10, 100, 100);
		//��(10,10)Ϊ���Ͻ�,��һ����100��100�ľ���
		g.drawRect(10, 10, 100, 100);
		//��(10,10)Ϊ���Ͻ�,��100*100��Χ�ڻ�һ��Բ
		g.drawOval(10, 10, 100, 100);
		*/
		//setp1:���Ʊ���ͼƬ,��(0,0)Ϊ���Ͻ�,����ͼƬbackground
		g.drawImage(background, 0, 0, null);	
		//setp2:����Ӣ�ۻ�
		paintHero(g);
		//setp3:�������Ƶ���
		painFlyers(g);
		//setp4:���������ӵ�
		paintBullets(g);
		//���Ʒ���������
		paintScore_Life(g);
		
		//������Ϸ״̬,���Ʋ�ͬͼƬ
		if (state == START) {
			g.drawImage(start, 0, 0, null);
		} else if (state == PAUSE) {
			g.drawImage(pause, 0, 0, null);
		}else if (state == GAME_OVER) {
			g.drawImage(gameover, 0, 0, null);
		}
			
		

	}
	
	//����Ӣ�ۻ�
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	//������������,�����������е��˶���ķ���
	public void painFlyers(Graphics g) {
		for(int i =0 ; i<flyers.length;i++){
			g.drawImage(flyers[i].image, flyers[i].x, flyers[i].y, null);
		}
	}
	//�����ӵ�����,�������������ӵ�����ķ���
	public void paintBullets(Graphics g){
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
		}
	}
	
	/**
	 *�������1�����˶���
	 *ÿ����һ���µ���,flyer���鶼Ҫ����1
	 *Ȼ���µ��˷����������һ��Ԫ��
	 */
	public void nextOne(){
		Random r = new Random();
		Flyer f = null;
		if (r.nextInt(20) == 0) {//ֻ�е������ȡ0ʱ,�Ŵ���һ���۷�
			f = new Bee();
		}else {//���඼�����л�
			f= new Airplane();
		}
		//�ȶ�flyer��������1
		flyers = Arrays.copyOf(flyers, flyers.length+1);
		//���µ��˷�������ĩβ
		flyers[flyers.length-1] = f;
		
	}
	/**
	 * ���Ӣ�ۻ���������ӵ�����
	 * ���µ��ӵ����󱣴浽�ӵ�������ͳһ����
	 */
	public void shoot(){
		Bullet[] newBullets = hero.shoot();//���Ӣ�ۻ����ص����ӵ���
		//���ݷ������ӵ������������ӵ�����
		bullets = Arrays.copyOf(bullets, bullets.length+newBullets.length);
		//��newBullets�����п���
		System.arraycopy(newBullets, 0, bullets,
				bullets.length-newBullets.length,
				newBullets.length);
	}
	/**
	 * �����ӵ�����͵�����,������ײ���
	 * һ��������ײ,���˺��ӵ�������һ��
	 */
	public void bang() {
		for (int i = 0; i < bullets.length; i++) {//ȡ��ÿ���ӵ�
			for (int j = 0; j < flyers.length; j++) {//��ÿ�����������
				if (Flyer.bang(bullets[i], flyers[j])) {//���������ײ
					//ΪӢ�ۻ���÷����ͽ���
					hero.getScore_Award(flyers[j]);
					
					//�ӵ���������ɾ�������еĵл�
					//step1:ʹ���������һ��Ԫ��,�滻��ǰ�����еĵ���
					flyers[j]= flyers[flyers.length-1];
					//setp2:ѹ������,Ԫ�ظ�����1
					flyers = Arrays.copyOf(flyers, flyers.length-1);
					//���ӵ�������ɾ�����е��˵��ӵ�
					bullets[i] = bullets[bullets.length-1];
					bullets= Arrays.copyOf(bullets, bullets.length-1);
					i--;//ÿ����һ����ײ,�ӵ���Ҫ��һ��Ԫ��,���¼�⵱ǰλ�����ӵ�					
					//ֻҪ�е��˱�����,���˳��������������ѭ��,�����������ж�		
					break;					
				}
			}
			
		}
	}
	/**
	 * ���Ʒ���������ֵ�ķ���
	 * @param g
	 */
	public void paintScore_Life(Graphics g){
		int x = 10;
		int y = 15;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		g.setFont(font);
		//���Ƶ�һ��,����
	     g.drawString("SCORE:"+hero.getScore(), x, y);
	     //���Ƶڶ���,y��������20����λ
	     y+=20;
	     g.drawString("LIFE:"+hero.getLife(), x, y);
	     
	}
	/**
	 * ������з������Ƿ�Խ��
	 */
	public void outOfBounds(){
		//������е����Ƿ�Խ��
		Flyer[] Flives = new Flyer[flyers.length];
		//������������,�����ĵ��˶���,���浽��������
		//���Flives����ļ�����index:  1.��ʾ��һ���������λ��
		//						   2.ͳ��lives�������ܹ��ж��ٴ�����
		int index = 0;
		for (int i = 0; i < Flives.length; i++) {
			if (!flyers[i].outOfBounds()) {
				Flives[index]=flyers[i];
				index++;
			} 
		}//����������:
		//index�б�����ǵ�ǰ������ĸ���
		//lives�����б�����ǵ�ǰ������
		//��lives����,����index�еĸ�������ѹ��
		//ѹ�����������,�滻��flyers������
		flyers = Arrays.copyOf(Flives, index);
		//��������ӵ�Խ��
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
	 * ������������,Ӣ�ۻ���ÿ�����˶����ж���ײ
	 */
	public void hit() {
		Flyer[] lives= new Flyer[flyers.length];
		//��¼���ĵ���
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
		//ѹ�����ĵ�������,�����滻��������
		flyers = Arrays.copyOf(lives, index);
	}
	
}
