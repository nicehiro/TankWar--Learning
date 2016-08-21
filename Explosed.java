import java.awt.*;
import java.awt.Toolkit;

public class Explosed {
	private int x;
	private int y;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private boolean init = false;
	
	private boolean live = true;
	
	private static Image[] images = {
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/0.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/1.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/2.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/3.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/4.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/5.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/6.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/7.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/8.gif")),
			tk.getImage(Explosed.class.getClassLoader().getResource("Image/9.gif")),
	};
	private int step = 0;
	
	private TankClient tc;
	
	public Explosed(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if (!live) {
			tc.exploseds.remove(this);
		}
		
		if (!init) {
			for (int i=0; i<images.length; i++) {
				g.drawImage(images[i], -100, -100, null);
			}
			this.init = true;
		}
		
		if (step == images.length) {
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.drawImage(images[step], x, y, null);
		g.setColor(c);
		step ++;
		
	}
	
}
