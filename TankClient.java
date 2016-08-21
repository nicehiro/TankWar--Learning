import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Blood b = new Blood();
	
	Wall w1 = new Wall(500, 0, 20, 400, this);
	Wall w2 = new Wall(0, 500, 400, 20, this);

	Tank myTank = new Tank(700, 500,true, Direction.STOP, this);

	List<Tank> tanks = new ArrayList<Tank>();
	
	List<Missile> missiles = new ArrayList<Missile>();
	
	List<Explosed> exploseds = new ArrayList<Explosed>();
	
	Image offScreenImage = null;
	
	public void update(Graphics g) {
		if (offScreenImage == null) offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		
		paint(gOffScreen);
		
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void paint(Graphics g) {
		g.drawString("Missiles Counts:"+missiles.size(), 10, 50);
		g.drawString("Explosed Counts:"+exploseds.size(), 10, 70);
		g.drawString("Tank Counts:"+tanks.size(), 10, 90);
		g.drawString("Tank Life:"+myTank.getLife(), 10, 110);
		
		if (tanks.size() <= 0) {
			this.addTank();
		}
		
		for (int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}

		for (int i=0; i<exploseds.size(); i++) {
			Explosed e = exploseds.get(i);
			e.draw(g);
		}
		
		for (int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.hitWall(w1);
			t.hitWall(w2);
			t.hitTanks(tanks);
			//t.collidesWithTanks(tanks);
		}
		
		myTank.draw(g);
		myTank.eat(b);
		
		w1.draw(g);
		w2.draw(g);
		
		b.draw(g);

	}

	public void launchFrame() {
		this.setBounds(50, 50, GAME_WIDTH, GAME_HEIGHT);
		this.setVisible(true);
		this.setTitle("TankWar");
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}		
		});
		
		
		int initTankCountX = Integer.parseInt(PropertyManage.getProperty("initTankCountX"));
		
		for (int i=0; i<initTankCountX; i++) {
			for (int j=0; j<5; j++) {
				Tank t = new Tank(50+70*(i+1), 50+70*(j+1), false, Direction.D, this);
				tanks.add(t);
			}		
		}
		
		this.addKeyListener(new KeyMonitor());
		
		PaintThread pt = new PaintThread();
		Thread t = new Thread(pt);
		t.start();
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
		}		
	}
	
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	
	}
	
	public void addTank() {
		for (int i=0; i<5; i++) {
			for (int j=0; j<2; j++) {
				Tank t = new Tank(50+70*(i+1), 50+70*(j+1), false, Direction.D, this);
				tanks.add(t);
			}		
		}
	}
	
}










