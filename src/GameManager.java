import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class GameManager extends Application {
	protected Stage stage;
	protected Scene scene;

	private int width = 600;
	private int height = 480;

	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		stage.setFullScreen(false);
		LoadContents();
		Initialize(stage);
		class View extends Group {
			public View() {
				Canvas cvs = new Canvas(width, height);
				getChildren().add(cvs);
				GraphicsContext g = cvs.getGraphicsContext2D();
				AnimationTimer at = new AnimationTimer() {
					@Override
					public void handle(long now) {
						g.clearRect(0, 0, width, height);
						Update(now);
						Draw(g);
					}
				};
				at.start();
			}
		}
		scene = new Scene(new View(), width, height);
		stage.setScene(scene);
		stage.setWidth(width);
		stage.setHeight(height + 20);
		stage.show();
		scene.setOnMousePressed(this::MousePressed);
		scene.setOnMouseReleased(this::MouseReleased);
		scene.setOnMouseMoved(this::MouseMoved);
		scene.setOnMouseDragged(this::MouseDragged);
		scene.setOnKeyPressed(this::KeyPressed);
		scene.setOnKeyReleased(this::KeyReleased);

		this.stage = stage;
	}

	protected void size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	protected void Initialize(Stage stage) {
	};

	protected void LoadContents() {
	};

	protected void Update(long now) {
	};

	protected void Draw(GraphicsContext g) {
	};

	protected void MousePressed(MouseEvent e) {
	};

	protected void MouseReleased(MouseEvent e) {
	};

	protected void MouseMoved(MouseEvent e) {
	};

	protected void MouseDragged(MouseEvent e) {
	};

	protected void KeyPressed(KeyEvent e) {
	};

	protected void KeyReleased(KeyEvent e) {
	};
}
