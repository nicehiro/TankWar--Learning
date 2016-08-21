import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private boolean live = true;
	private TankClient tc;
	
	private boolean good;
	Direction dir;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImage;
	private static Map<String, Image>images = new HashMap<String, Image>();
	
	static {
		missileImage = new Image[] {
				tk.getImage(Missile.class.getResource("Image/missileL.gif")),
				tk.getImage(Missile.class.getResource("Image/missileLU.gif")),
				tk.getImage(Missile.class.getResource("Image/missileU.gif")),
				tk.getImage(Missile.class.getResource("Image/missileRU.gif")),
				tk.getImage(Missile.class.getResource("Image/missileR.gif")),
				tk.getImage(Missile.class.getResource("Image/missileRD.gif")),
				tk.getImage(Missile.class.getResource("Image/missileD.gif")),
				tk.getImage(Missile.class.getResource("Image/missileLD.gif")),
		};
		
		images.put("L", missileImage[0]);
		images.put("LU", missileImage[1]);
		images.put("U", missileImage[2]);
		images.put("RU", missileImage[3]);
		images.put("R", missileImage[4]);
		images.put("RD", missileImage[5]);
		images.put("D", missileImage[6]);
		images.put("LD", missileImage[7]);
	}
	
	public boolean isLive() {
		return live;
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Direction dir, boolean good, TankClient tc) {
		this(x, y, dir);
		this.tc = tc;
		this.good = good;
	}
	
	int x = 50;
	int y = 50;
	
	public void draw(Graphics g) {
		
		if (!live) {
			tc.missiles.remove(this);
			return;
		}
		
		switch (dir) {
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
	
	public void move() {
		
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
		
		if (x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if (this.getRectangle().intersects(t.getRectangle()) && t.isLive() && this.good != t.isGood()) {
			
			if (t.isGood()) {
				t.setLife(t.getLife()-20);
				if (t.getLife() <= 0) {
					t.setLive(false);
				}
			}
			else
				t.setLive(false);
			live = false;
			
			Explosed e = new Explosed(x, y, tc);
			tc.exploseds.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for (int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (hitTank(t))
				return true;
		}
		
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if (this.live && this.getRectangle().intersects(w.getRanctangle())) {
			this.live = false;
			return true;
		}
		
		return false;
	}
	
}























