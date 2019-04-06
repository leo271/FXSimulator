import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class GraphDrawer {
	int height = 700;
	int width = 1100;
	double start_v;
	double y;
	double vw;
	double[] prises;
	double ox = 0;
	double oy = 0;
	double timespeed;
	int mode = 0;
	final int[] time_sp = {
			1, 10, 60
	};
	int stock_border;
	Date date;
	double st_price;
	
	public GraphDrawer(Graph g, int time) {
		this.start_v = g.start_v;
		this.y = g.nowY();
		this.vw = g.nowVW();
		this.prises = new double[width];
		for(int i = 0; i < width; i++) {
			prises[i] = getY_(height / 2);
		}
		this.stock_border = g.stock_border[g.modeid];
		this.date = new Date(g.datas[time]);
		this.st_price = g.datas[time].close;
	}
	
	public void Draw(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, 1500, 1000);
		g.setStroke(Color.GRAY);
		g.strokeRect(0, 0, width, height);
		DrawLine(g);
		DrawInfo(g);
		g.setFill(Color.RED);
		g.fillOval(0, getY(st_price) + y - 1, 2, 2);
	}
	
	public void DrawInfo(GraphicsContext g) {
		final double sv = floorOn(getY_(height), 2);
		final double ev = floorOn(getY_(0), 2);
		g.setFill(Color.WHITE);
		g.setStroke(Color.GRAY);
		g.setFont(new Font("consolas", 15));
		double val = sv;
		
		while(val < ev) {
			val += 0.01;
			if((int)(val * 100) % stock_border == 0.0) {
				g.fillText(String.format("%.2f", floorOn(val, 2)), width + 5, getY(val) + y);
				g.strokeLine(0, getY(val) + y, width, getY(val) + y);
			}
		}
		int t = 0;
		Date d = date.copy();
		while(t < width) {
			g.fillText(d.two(mode), t, height + 5 + g.getFont().getSize() / 2);
			t += 80;
			d.add(time_sp[mode] * 80);
		}
		
		g.setFont(new Font("console", 30));
		g.fillText(String.format("mode:%d", mode + 1), width + 100, 20);
	}
	
	public double floorOn(double val, int place) {
		return Math.floor(val * Math.pow(10, place)) / Math.pow(10, place);
	}
	
	public double DevDouble(double a, double b) {
		return a / b - Math.floor(a / b);
	}
	
	public int KeyPressed(KeyEvent e) {
		switch(e.getCode()) {
		case SPACE:
			for(int i = 0; i < prises.length; i++) {
				prises[i] = getY_(height / 2);
			}
			break;
		case ENTER:
			return 1;
		case UP:
			mode++;
			mode %= 3;
			break;
		case DOWN:
			mode--;
			if(mode < 0)
				mode = 2;
			break;
		default:
			break;
		}
		return 0;
	}   
	
	public int KeyReleased(KeyEvent e) {
		switch(e.getCode()) {
		case ENTER:
			return 1;
		default:
			break;
		}
		return 0;
	}
	
	public double[] MousePressed(MouseEvent e) {
		if(e.getSceneX() >= width + 50)
			return makeData();
		return null;
	}
	
	public void MouseDragged(MouseEvent e) {
		if(e.getSceneX() < 0) {
			ox = oy = 0;
			return;
		}
		if(e.getSceneX() > width) {
			if(ox != 0 && oy != 0) {
				setData(width, e.getSceneY());
			}
			ox = oy = 0;
			return;
		}
		if(e.getSceneY() < 0) {
			ox = oy = 0;
			return;
		}
		if(e.getSceneY() >= height) {
			ox = oy = 0;
			return;
		}
		if(ox == 0 && oy == 0) {
			ox = (int)e.getSceneX();
			oy = (int)e.getSceneY();
			return;
		}
		
//		prises[(int)e.getSceneX()] = getY_(e.getSceneY());
		setData(e.getSceneX(), e.getSceneY());
		ox = (int)e.getSceneX();
		oy = (int)e.getSceneY();
	}
	
	private void setData(double scx, double scy) {
		double sx = Math.min(scx, ox);
		double sy = sx == ox ? oy : scy;
		
		double ex = Math.max(scx, ox);
		double ey = ex == ox ? oy : scy;
		double ny = sy;
		for(double i = sx; i < ex; i++, ny += (ey - sy) / (ex - sx)) {
			prises[(int)i] = getY_(ny);
		}
	}
	
	public void MouseReleased(MouseEvent e) {
		ox = oy = 0;
	}
	
	public void DrawLine(GraphicsContext g) {
		g.setStroke(Color.AQUA);
		for(int i = 0; i < prises.length - 1; i++) {
			g.strokeLine(i, getY(prises[i]) + y, i + 1, getY(prises[i + 1]) + y);
		}
	}
	
	
	
	private double getY(double r) {
		return height / 2.0 - (r - start_v) * vw * 50;
	}
	
	private double getY_(double y) {
		return -(y - height / 2.0 - this.y) / (vw * 50) + start_v;
	}
	
	private double[] makeData() {
		double[] datas = new double[prises.length / time_sp[mode]];
		for(int i = 0; i < prises.length; i++) {
			for(int n = 0; n < time_sp[mode]; n++) {
				datas[i * time_sp[mode] + n] =  prises[i];
			}
		}
		return datas;
	}
}

