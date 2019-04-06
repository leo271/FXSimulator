import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SceneManager {
	private ArrayList<Scene> scenes;
	private int index;
	private Message msg;
	
	public SceneManager() {
		scenes = new ArrayList<>();
		index = 0;
		msg = new Message();
	}
	
	public void add(Scene scene) {
		scenes.add(scene);
	}
	
	public void add(Scene ... scene) {
		for(Scene sc : scene) {
			scenes.add(sc);
		}
	}
	
	public void Initialize() {
		for(Scene scene : scenes) {
			scene.Initialize(msg);
			scene.LoadContents();
		}
	}
	
	public void Update(long now) {
		scenes.get(index).Update(now);
		msg = scenes.get(index).ProcMessage(msg);
	}

	public void Draw(GraphicsContext g) {
		scenes.get(index).Draw(g);
	}

	public void MousePressed(MouseEvent e) {
		scenes.get(index).MousePressed(e);
	}

	public void MouseReleased(MouseEvent e) {
		scenes.get(index).MouseReleased(e);
	}

	public void MouseMoved(MouseEvent e) {
		scenes.get(index).MouseMoved(e);
	}

	public void MouseDragged(MouseEvent e) {
		scenes.get(index).MouseDragged(e);
	}

	public void KeyPressed(KeyEvent e) {
		scenes.get(index).KeyPressed(e);
	}

	public void KeyReleased(KeyEvent e) {
		scenes.get(index).KeyReleased(e);
	}
	
	public void NextScene() {
		int next_s = scenes.get(index).NextScene();
		if(next_s != -1 && next_s < scenes.size()) {
			scenes.get(index).Finalize();
			index = next_s;
			scenes.get(index).Initialize(msg);
		}
	}

}
