//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import javafx.scene.canvas.GraphicsContext;
//import java.util.Calendar;
import javafx.scene.input.KeyEvent;
//import javafx.scene.text.Font;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends GameManager{
//	Stock stock = new Stock(1332);
//	Calendar cal = Calendar.getInstance();
	SceneManager sm = new SceneManager();
	Stage stage;
	
//	int getting_speed = 5;
//	int[] stock_prises = new int[400];
//	final double stock_per = 400 / (12.0 * 60.0);
	
	public static void main(String[] args) {
		launch(args);
	}
	
	protected void Initialize(Stage stage) {
		sm.add(new Sc1(), new Sc2(), new Sc3());
		stage.setTitle("FX Simulation");
		java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
		// desktopBoundsにデスクトップ領域を表すRectangleが代入される
		java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
		size(desktopBounds.width, desktopBounds.height + 200);
		stage.setFullScreen(true);
		this.stage = stage;
	}
	
	protected void Draw(GraphicsContext g) {
		sm.Draw(g);
//		cal = Calendar.getInstance();
//		Color c = Color.BLACK;
//		Font font = new Font("Georgia", 20);
//		g.setFill(c);
//		g.setFont(font);
//		g.fillText(String.valueOf(cal.get(Calendar.MINUTE)), 8, 50);
//		g.fillText(stock.company, 8, 50);
//		g.fillText("株価: " + String.valueOf(stock.prise) + "円", 200, 50);
//		
//		for(int i = 0; i < 400; i++) {
//			if(stock_prises[i] != 0) {
//				g.setStroke(Color.AQUA);
//				g.strokeLine(i + 50, 400 - stock_prises[i] + 600, i + 50, 400);
//			}
//		}
	}
	
	protected void Update(long now) {
		sm.Update(now);
//		if(cal.get(Calendar.MINUTE) % getting_speed == 0)
//			updateStock();
		sm.NextScene();
	}
	
	protected void KeyPressed(KeyEvent e) {
		
		sm.KeyPressed(e);
		
		switch(e.getCode()) {
		default:
			break;
		case P:
			stage.setFullScreen(true);
			break;
//		case ENTER:
//			updateStock();
//			break;
		}
	}
	
	public void KeyReleased(KeyEvent e) {
		sm.KeyReleased(e);
	}
	
	protected void MousePressed(MouseEvent e) {
		sm.MousePressed(e);
	}
	
	protected void MouseReleased(MouseEvent e) {
		sm.MouseReleased(e);
	}
	
	protected void MouseDragged(MouseEvent e) {
		sm.MouseDragged(e);
	}
	
	protected void MouseMoved(MouseEvent e) {
		sm.MouseMoved(e);
	}
	
//	private void updateStock() {
//		stock.update();
//		int n = (int)(((cal.get(Calendar.HOUR_OF_DAY) - 8) * 60 + cal.get(Calendar.MINUTE)) * stock_per);
//		if(n >= 0 && n < 400) {
//			stock_prises[n] = stock.prise;
//		}
//		System.out.println(n);
//	}
//	while(true) {
//		System.out.print("Input Stock ID: ");
//		
//	    BufferedReader br =
//	      new BufferedReader(new InputStreamReader(System.in));
//	    String str;
//	    int id;
//		try {
//			str = br.readLine();
//			id = Integer.parseInt(str);
//			Stock stock = new Stock(id);
//			System.out.print("company : ");
//			System.out.println(stock.company);
//			System.out.print("prise : ");
//			System.out.println(stock.prise);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//	}
}
