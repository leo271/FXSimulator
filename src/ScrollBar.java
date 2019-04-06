import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ScrollBar {
	int width;
	int height;
	int x;
	int y;
	int bary;
	Color c;
	Color bc;
	boolean focus;
	boolean keeping;
	double size;
	double ky;
	
	public ScrollBar(int width, int height, int x, int y, Color c) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.bary = 0;
		this.c = c;
		this.bc = new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.2);
		this.focus = false;
		this.keeping = false;
		this.size = 1.0;
	}
	
	public void Draw(GraphicsContext g) {
		if(size > 1)
			return;
		int width = (int)(this.width * (focus ? 1 : 0.25));
		int x = this.x + this.width - width;
		g.setFill(bc);
		g.fillRect(x, y, width, height);
		int barheight = (int)Math.max(height * size, height / 10.0);
		g.setFill(c);
		g.fillRect(x, y + bary, width, barheight);
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public void MouseMoved(MouseEvent e) {
		if(e.getSceneX() < x - 15 || e.getSceneX() > x + width) {
			focus = false;
			return;
		}
		if(e.getSceneY() < y || e.getSceneY() > y + height) {
			focus = false;
			return;
		}
		focus = true;
	}
	
	public void MousePressed(MouseEvent e) {
		
		if(size > 1)
			return;

		int barheight = (int)Math.max(height * size, height / 10.0);
		if(e.getSceneX() < x || e.getSceneX() > x + width) {
			keeping = false;
			return;
		}

		if(e.getSceneY() < bary + y || e.getSceneY() > bary + barheight + y) {
			keeping = false;
			return;
		}
		
		
		ky = -e.getSceneY() + (y + bary);
		keeping = true;
		
	}
	
	public void MouseReleased(MouseEvent e) {
		keeping = false;
		ky = 0;
	}
	
	public void MouseDragged(MouseEvent e) {
		if(!keeping)
			return;
		int barheight = (int)Math.max(height * size, height / 10.0);
		if(y > e.getSceneY() + (int)ky) {
			bary = 0;
			return;
		}

		if(y + height - barheight < e.getSceneY() + (int)ky) {
			bary = height - barheight;
			return;
		}
		
		bary = (int)e.getSceneY() - y + (int)ky;
	}
	
	public void print(int i) {
		System.out.println(i);
	}
	
	public double getIndex() {
		if(size >= 1)
			return 0;
		return bary / (height - Math.max(height * size, height / 10.0));
	}
}