import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface Scene {
	void Initialize(Message msg);
	
	void Finalize();

	void LoadContents();

	void Update(long now);

	void Draw(GraphicsContext g);

	void MousePressed(MouseEvent e);

	void MouseReleased(MouseEvent e);

	void MouseMoved(MouseEvent e);

	void MouseDragged(MouseEvent e);

	void KeyPressed(KeyEvent e);

	void KeyReleased(KeyEvent e);
	
	int NextScene();
	
	Message ProcMessage(Message msg);
}
