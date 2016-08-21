import java.awt.*;

public class Blood {
	
	private int x, y, width, height;
	private TankClient tc;
	
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int step = 0;
	
	private int[][] pos = {
			{700,50},{750,50},{750,100},{700,100}
	};
	
	public Blood() {
		this.x = pos[0][0];
		this.y = pos[0][1];
		this.width = 10;
		this.height = 10;
	}
	
	public void draw(Graphics g) {
		
		if (!live) return;
		
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, width, height);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		
		step ++;
		
		if (step == pos.length) {
			step = 0;
			return;
		}
		
		this.x = pos[step][0];
		this.y = pos[step][1];
				
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
}
