import java.awt.*;

public class Wall {
	
	private int x, y, width, height;
	
	private TankClient tc;
	
	public Wall(int x, int y, int width, int height, TankClient tc) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(new Color(100, 100, 100));
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
	
	public Rectangle getRanctangle() {
		return new Rectangle(x, y, width, height);
	}
	
}
