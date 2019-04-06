import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;



public class ListShower {
	ArrayList<ListData> list;
	ScrollBar sb;
	int width;
	int height;
	int cheight;
	int focus = -1;
	
	class ListData{
		final int year;
		final int month;
		final int day;
		final int hour;
		final int minute;
		final int second;
		final double rate;
		final double rot;
		final boolean isBuy;
		
		public ListData(FXDat dat, int second, double rot, boolean isBuy) {
			this.year = dat.year;
			this.month = dat.month;
			this.day = dat.day;
			this.hour = dat.hour;
			this.minute = dat.minute;
			this.second = second;
			this.rate = dat.close;
			this.rot = rot;
			this.isBuy = isBuy;
		}
		
		public ListData(ListData other) {
			this.year = other.year;
			this.month = other.month;
			this.day = other.day;
			this.hour = other.hour;
			this.minute = other.minute;
			this.second = other.second;
			this.rate = other.rate;
			this.rot = other.rot;
			this.isBuy = other.isBuy;
			
		}
		
		@Override
		public String toString() {
			return String.format("%04d/%02d/%02d %02d:%02d:%02d %.2f %.1f", year, month, day, hour, minute, second, rate, rot);
		}
		
	}
	
	public ListShower(ScrollBar sb, int width, int cheight){
		this.list = new ArrayList<>();
		this.sb = sb;
		this.width = width;
		this.height = sb.height;
		this.cheight = cheight;
	}
	
	public void add(ListData t) {
		list.add(t);
	}
	
	public void add(FXDat dat, int second, double rot, boolean isBuy) {
		list.add(new ListData(dat, second, rot, isBuy));
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	public void Draw(GraphicsContext g) {
		int x = sb.x - width + sb.width * 3 / 4;
		int y = sb.y;
		double idx = sb.getIndex() * (list.size() - height / cheight);
		int index = (int)Math.floor(idx);
		double dy = (idx - index) * cheight;
		for(int i = index; i <= Math.min(index + Math.ceil(height / (double)cheight), list.size() - 1); i++) {
			g.setFill(list.get(i).isBuy ? Color.RED : Color.BLUE);
			g.fillRect(x, y - dy + (i - index) * cheight, width, cheight - 5);
			g.setFill(Color.WHITE);
			g.fillText(list.get(i).toString(), x + 10, y - dy + (i - index + 0.3) * cheight + g.getFont().getSize() / 2);
			if(i == focus) {
				g.setFill(new Color(0, 0, 0, 0.5));
				g.fillRect(x, y - dy + (i - index) * cheight, width, cheight - 5);
			}
		}
		sb.Draw(g);
		g.setFill(Color.BLACK);
		g.fillRect(x, y - 1 - cheight, width + sb.width, cheight);
		g.fillRect(x, y + height + 1, width + sb.width, cheight + 10);
		
	}
	
	public void update() {
		sb.setSize((double)height / (list.size() * cheight));
	}
	
	public ListData MousePressed(MouseEvent e) {
		sb.MousePressed(e);
		if(!e.isSecondaryButtonDown())
			return null;
		if(e.getSceneX() < sb.x - width + sb.width * 3 / 4) {
			focus = -1;
			return null;
		}
		if(e.getSceneX() > sb.x - width + sb.width * 3 / 4 + width) {
			focus = -1;
			return null;
		}
		if(e.getSceneY() < sb.y) {
			focus = -1;
			return null;
		}
		if(e.getSceneY() > sb.y + sb.height) {
			focus = -1;
			return null;
		}
		double idx = sb.getIndex() * (list.size() - height / cheight);
		
		int index = (int)(Math.floor((e.getSceneY() - sb.y + (idx - Math.floor(idx)) * cheight) / (double)cheight) + Math.floor(idx));
		if(index == focus) {
			// cash
			ListData d = new ListData(list.get(index));
			list.remove(index);
			focus = -1;
			return d;
		} else {
			focus = index;
			return null;
		}
	}
	
	public void MouseMoved(MouseEvent e) {
		sb.MouseMoved(e);
	}
	
	public void MouseDragged(MouseEvent e) {
		sb.MouseDragged(e);
	}
	
	public void MouseReleased(MouseEvent e) {
		sb.MouseReleased(e);
	}
}
