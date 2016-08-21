import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	
	private int oldX;
	private int oldY;
	
	private static Random r = new Random();
	
	private TankClient tc;
	
	private int step = r.nextInt(12)+3;
	
	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	
	
	private Direction dir = Direction.STOP;
	private Direction ptdir = Direction.D;
	
	private int fullBlood = 100;
	
	private boolean bL = false, bU = false, bR = false, bD = false;

	private int x = 0, y = 0;
	
	private boolean good;
	
	private int life = fullBlood;
	
	private BloodBar bb = new BloodBar();
	
	private static Map<String, Image> images = new HashMap<String, Image>();
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImage;
	
	static {
		tankImage = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/TankLD.gif")),
		};
		
		images.put("L", tankImage[0]);
		images.put("LU", tankImage[1]);
		images.put("U", tankImage[2]);
		images.put("RU", tankImage[3]);
		images.put("R", tankImage[4]);
		images.put("RD", tankImage[5]);
		images.put("D", tankImage[6]);
		images.put("LD", tankImage[7]);
	}
	
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir,TankClient tc) {
		this(x, y, good);
		this.tc = tc;
		this.dir = dir;
	}
	
	void draw(Graphics g) {
		
		if (!good) {
			if (!live) {
				tc.tanks.remove(this);
				return;
			}
		}
		
		if (good) {
			if (!live) {
				return;
			}
			
			bb.draw(g);
		}
		
		switch (ptdir) {
		case L:
			g.drawImage(images.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(images.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(images.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(images.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(images.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(images.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(images.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(images.get("LD"), x, y, null);
			break;
		}
		
		move();
	}
	
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_W:
			bU = true;
			break;
		case KeyEvent.VK_D:
			bR = true;
			break;
		case KeyEvent.VK_S:
			bD = true;
			break;
		case KeyEvent.VK_A:
			bL = true;
			break;
		case KeyEvent.VK_P:
			surpeFire();
		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F3:
			tc.addTank();
			break;
		case KeyEvent.VK_F2:
			if(!this.live) {
				this.live = true;
				this.life = fullBlood;
			}
			break;
		case KeyEvent.VK_J:
			fire();
			break;
		case KeyEvent.VK_W:
			bU = false;
			break;
		case KeyEvent.VK_D:
			bR = false;
			break;
		case KeyEvent.VK_S:
			bD = false;
			break;
		case KeyEvent.VK_A:
			bL = false;
			break;
		}
		locateDirection();
	}
	
	void locateDirection() {
		if (bL && !bU && !bR && !bD) dir = Direction.L;
		else if (bL && bU && !bR && !bD) dir = Direction.LU;
		else if (!bL && bU && !bR && !bD) dir = Direction.U;
		else if (!bL && bU && bR && !bD) dir = Direction.RU;
		else if (!bL && !bU && bR && !bD) dir = Direction.R;
		else if (!bL && !bU && bR && bD) dir = Direction.RD;
		else if (!bL && !bU && !bR && bD) dir = Direction.D;
		else if (bL && !bU && !bR && bD) dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
	
	private void stay() { 
		x = this.oldX;
		y = this.oldY;
	}
	
	void move() {
		
		this.oldX = x;
		this.oldY = y;
		
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		if (dir != Direction.STOP) {
			ptdir = dir;
		}
		
		if (x<0) x=0;
		if (y<30) y = 30;
		if (x+Tank.WIDTH > TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if (y+Tank.HEIGHT > TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		if (!good) {
			Direction[] dirc = dir.values();
			
			if (step == 0) {
				int rn = r.nextInt(dirc.length);
				dir = dirc[rn];
				step = r.nextInt(12) + 3;
			}
			
			step --;
			
			if (r.nextInt(40) > 37) {
				this.fire();
			}
		}
		
	}
	
	private Missile fire(Direction dir) {
		if (this.live) {
			Missile m = new Missile(this.x + Tank.WIDTH/2 - Missile.WIDTH/2, this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2, dir, good, tc);
			tc.missiles.add(m);
			return m;
		}
		
		return null;
	}
	
	private Missile fire() {
		if (this.live) {
			Missile m = new Missile(this.x + Tank.WIDTH/2 - Missile.WIDTH/2, this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2, ptdir, good, tc);
			tc.missiles.add(m);
			return m;
		}
		
		return null;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitWall(Wall w) {
		if (this.live && this.getRectangle().intersects(w.getRanctangle())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean hitTank(Tank t) {
		if (this.live && t.isLive() && this.getRectangle().intersects(t.getRectangle())) {
			this.stay();
			t.stay();
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(java.util.List<Tank> tanks) {
		for (int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive() && this.getRectangle().intersects(t.getRectangle())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean eat(Blood b) {
		if (this.isLive() && b.isLive() && this.getRectangle().intersects(b.getRectangle())) {
			b.setLive(false);
			this.life = fullBlood;
			return true;
		}
		return false;
	}
	
	public void surpeFire() {
		Direction[] dirc = dir.values();
		for (int i=0; i<8; i++) {
			fire(dirc[i]);
		}
	}
	
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int width = WIDTH * life / 100;
			g.fillRect(x, y-10, width, 10);
			g.setColor(c);
		}
	}
	
}
