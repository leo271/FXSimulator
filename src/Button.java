import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public class Button {
	int x;
	int y;
	int width;
	int height;
	Color cedge;
	Color cbackground;
	Color ctext;
	String text;
	Font font;
	int tx;
	int ty;
	boolean isPushed = false;
	int n = 0;
	
	public Button(int x, int y, int width, int height, Color cedge, Color cbackground, Color ctext, String text, String font, int font_size, int tx, int ty) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.cedge = cedge;
		this.cbackground = cbackground;
		this.ctext = ctext;
		this.text = text;
		this.font = new Font(font, font_size);
		this.tx = tx;
		this.ty = ty;
	}
	
	public void Draw(GraphicsContext g) {
		g.setFont(font);
		g.setFill(cbackground);
		g.fillRect(x, y, width, height);
		g.setFill(ctext);
		g.fillText(text, x + tx, y + ty);
		g.setStroke(cedge);
		g.strokeRect(x, y, width, height);
		if(isPushed) {
			g.setFill(new Color(0, 0, 0, 0.5));
			g.fillRect(x, y, width, height);
			n++;
		}
		if(n == 10) {
			n = 0;
			isPushed = false;
		}
	}
	
	public boolean isPushed(MouseEvent e) {
		if(e.getSceneX() < x || e.getSceneX() > x + width) {
			isPushed = false;
			return false;
		}
		if(e.getSceneY() < y || e.getSceneY() > y + height) {
			isPushed = false;
			return false;
		}
		isPushed = true;
		return true;
	}
	
}
