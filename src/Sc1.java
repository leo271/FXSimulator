import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Sc1 implements Scene {
	Button b1 = new Button(550, 300, 200, 80, Color.GREEN, Color.LIME, Color.BLACK, "START", "consolas", 50, 31, 55);
	Button b2 = new Button(850, 250, 50, 50, Color.GRAY, Color.LIGHTGRAY, Color.BLACK, "S", "consolas", 40, 13, 37);
	int n = -1;
	int index = 0;
	int year = 2019;
	int month = 1;
	int day = 2;
	
	@Override
	public void Initialize(Message msg) {

	}

	@Override
	public void LoadContents() {
		

	}

	@Override
	public void Update(long now) {
		

	}

	@Override
	public void Draw(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, 2000, 1000);
		g.setFill(Color.WHITE);
		g.setFont(new Font("consolas", 60));
		g.fillText("FX Simulation", 450, 200);
		g.fillText("< " + Message.fxname[index] + " >", 500, 500);
		b1.Draw(g);
		b2.Draw(g);
	}

	@Override
	public void MousePressed(MouseEvent e) {
		if(b1.isPushed(e)) {
			n = 1;
		}
		if(b2.isPushed(e)) {
			n = 2;
		}
	}

	@Override
	public void MouseReleased(MouseEvent e) {
		

	}

	@Override
	public void MouseMoved(MouseEvent e) {
		

	}

	@Override
	public void MouseDragged(MouseEvent e) {
		

	}

	@Override
	public void KeyPressed(KeyEvent e) {
		switch(e.getCode()) {
		default:
			break;
		case ENTER:
			n = 1;
			break;
		case LEFT:
			index--;
			if(index < 0)
				index = 2;
			break;
		case RIGHT:
			index++;
			if(index > 2)
				index = 0;
			break;
		}

	}

	@Override
	public void KeyReleased(KeyEvent e) {
		

	}
	
	@Override
	public void Finalize() {
		n = -1;
	}

	@Override
	public int NextScene() {
		return n;
	}

	@Override
	public Message ProcMessage(Message msg) {
		msg.fxid = index;
		return msg;
	}

}
