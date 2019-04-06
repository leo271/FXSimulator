import java.io.File;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class Sc3 implements Scene{
	boolean n = false;
	Button b1 = new Button(50, 50, 100, 37, Color.BLUE, Color.ALICEBLUE, Color.BLACK, "BACK", "bold", 20, 25, 25);
	String[] settings = {
			"1000000", "guest", "2018/5/1"
	}; 
	String[] setname = {
			"所持金：", "名前：", "年月日:"
	};
	String[] files;
	int index = 0;
	int focus = -1;
	
	@Override
	public void Initialize(Message msg) {
		ArrayList<String> list = getSaveDatas("data/");
		files = new String[list.size()];
		for(int i = 0; i < files.length; i++) {
			files[i] = new String(list.get(i));
		}
	}

	@Override
	public void Finalize() {
		n = false;
	}

	@Override
	public void LoadContents() {
		
		
	}

	@Override
	public void Update(long now) {
		
		
	}

	@Override
	public void Draw(GraphicsContext g) {
		g.setFont(new Font("consolas", 40));
		g.setFill(Color.BLACK);
		g.fillText("Settings", 200, 80);
		b1.Draw(g);
		for(int i = 0; i < settings.length; i++) {
			int n = i % 2;
			
			g.fillText(setname[i] + settings[i], n * 250 + 50, (i / 2) * 80 + 150);
		}
		g.setStroke(Color.AQUA);
		g.strokeRect((index % 2) * 250 + 45, (index / 2) * 80 + 120, 200, 50);
		g.fillText("SaveData:", 45, 300);
		for(int i = 0; i < files.length; i++) {
			g.fillText(files[i], 145, 300 + 50 * i);
		}
		if(focus != -1) {
			g.setFill(new Color(0, 0, 0, 0.3));
			g.fillRect(135, 270 + 50 * focus, 200, 50);
		}
	}

	@Override
	public void MousePressed(MouseEvent e) {
		if(b1.isPushed(e))
			n = true;
		if(e.getSceneY() < 270) {
			return;
		}
		if(e.getSceneY() > 269 + files.length * 50) {
			return;
		}
		if(e.getSceneX() < 135) {
			return;
		}
		if(e.getSceneX() > 335) {
		}
		int index = (int)Math.floor((e.getSceneY() - 270) / 50.0);
		settings[1] = files[index];
	}

	@Override
	public void MouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void MouseMoved(MouseEvent e) {
		if(e.getSceneY() < 270) {
			focus  = -1;
			return;
		}
		if(e.getSceneY() > 269 + files.length * 50) {
			focus = -1;
			return;
		}
		if(e.getSceneX() < 135) {
			focus = -1;
			return;
		}
		if(e.getSceneX() > 335) {
			focus = -1;
		}
		focus = (int)Math.floor((e.getSceneY() - 270) / 50.0);
	}

	@Override
	public void MouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		
		switch(e.getCode()) {
		default:
			try {
				char c = e.getText().charAt(0);
				if((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '/')
					settings[index] += c;
			} catch(java.lang.StringIndexOutOfBoundsException n) {
				
			}
			break;
		case LEFT:
			index--;
			if(index < 0)
				index = settings.length - 1;
			break;
		case RIGHT:
			index++;
			if(index >= settings.length)
				index = 0;
			break;
		case UP:
			index -= 2;
			if(index < 0)
				index = settings.length - 1;
			break;
		case DOWN:
			index += 2;
			if(index >= settings.length)
				index = 0;
			break;
		case BACK_SPACE:
			if(settings[index].length() != 0)
				settings[index] = settings[index].substring(0, settings[index].length() - 1);
			break;
		}
		
	}

	@Override
	public void KeyReleased(KeyEvent e) {
		
		
	}

	@Override
	public int NextScene() {
		if(n)
			return 0;
		return -1;
	}
	
	@Override
	public Message ProcMessage(Message msg) {
		msg.date = settings[2];
		msg.money = Integer.parseInt(settings[0]);
		msg.name = settings[1];
		return msg;
	}
	
	private ArrayList<String> getSaveDatas(String path) {
		File dir = new File(path);
		String[] list = dir.list();
		ArrayList<String> lists = new ArrayList<>();
		for(String s : list) {
			if(s.equals("EURJPY.txt")) {
				continue;
			}
			if(s.equals("EURUSD.txt")) {
				continue;
			}
			if(s.equals("USDJPY.txt")) {
				continue;
			}
			if(!s.substring(s.length() - 4, s.length()).equals(".txt"))
				continue;
			lists.add(s.substring(0, s.length() - 4));
		}
		return lists;
	}

}
