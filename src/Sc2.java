import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Sc2 implements Scene{
	
	boolean n = false;
	
	Button ask = new Button(20, 660, 150, 50, Color.RED, Color.ORANGERED, Color.BLACK, "BUY", "consolas", 40, 40, 40);
	Button bid = new Button(190, 660, 150, 50, Color.BLUE, Color.ALICEBLUE, Color.BLACK, "SELL", "consolas", 40, 35, 40);
	Button gline = new Button(700, 420, 50, 50, Color.GRAY, Color.LIGHTGRAY, Color.BLACK, "L", "consolas", 40, 15, 38);
	Button gcandle = new Button(700, 420, 50, 50, Color.GRAY, Color.LIGHTGRAY, Color.BLACK, "C", "consolas", 40, 15, 38);
	Button uverw = new Button(800, 610, 50, 50, Color.ORANGE, Color.ORANGE, Color.BLACK, ">", "consolas", 40, 15, 38);
	Button dverw = new Button(750, 610, 50, 50, Color.AQUA, Color.AQUA, Color.BLACK, "<", "consolas", 40, 15, 38);
	Button miminute = new Button(420, 660, 50, 50, Color.BLUE, Color.AQUAMARINE, Color.BLACK, "分", "consolas", 40, 7, 38);
	Button mihour = new Button(470, 660, 50, 50, Color.BLUE, Color.AQUAMARINE, Color.BLACK, "時", "consolas", 40, 7, 38);
	Button miday = new Button(520, 660, 50, 50, Color.BLUE, Color.AQUAMARINE, Color.BLACK, "日", "consolas", 40, 7, 38);
	Button mimonth = new Button(570, 660, 50, 50, Color.BLUE, Color.AQUAMARINE, Color.BLACK, "月", "consolas", 40, 7, 38);
	Button urot = new Button(1100, 610, 50, 50, Color.ORANGE, Color.ORANGE, Color.BLACK, ">", "consolas", 40, 15, 38);
	Button drot = new Button(1050, 610, 50, 50, Color.AQUA, Color.AQUA, Color.BLACK, "<", "consolas", 40, 15, 38);
	Button tso = new Button(700, 510, 50, 50, Color.GREEN, Color.LIME, Color.BLACK, ">", "consolas", 40, 15, 38);
	Button tsf = new Button(700, 510, 50, 50, Color.GREEN, Color.LIME, Color.BLACK, ":", "consolas", 40, 15, 38);
	Button save = new Button(1200, 75, 150, 50, Color.BISQUE, Color.CADETBLUE, Color.BLACK, "SAVE", "consolas", 40, 30, 40);
	Button cash = new Button(750, 250, 50, 50, Color.GOLD, Color.LIGHTGOLDENRODYELLOW, Color.BLACK, "C", "consolas", 40, 12, 38);
	
	int index = 0;
	FXDat[] datas;
	int time = 0;
	Timer updTimer = new Timer(60 * 60);
	double rot = 1;
	Graph graph;
	String graphName = "line";
	double money;
	int second = 0;
	int su = 0;
	int dx;
	boolean stopping = false;
	String name;
	int STIME;
	double SMONEY;
	Message msg;
	ListShower ls;
	ScrollBar sb;
	boolean isBuy = true;
	int time_sp = 60;
	int err_overmoney = 0;
	GraphDrawer gd;
	boolean ingd = false;
	int draw_start = 0;
	double[] drawn_data = null;
	boolean gd_seeing = false;
	
	@Override
	public void Initialize(Message msg) {
		MakeData md = new MakeData(Message.fxname[msg.fxid]);
		datas = md.makeData();
		graph = new Graph(datas);
		Debug.print(msg.date);
		time = getTime(msg.date);
		money = msg.money;
		name = msg.name;
		STIME = time;
		SMONEY = money;
		loadFile();
		this.msg = msg;
		sb = new ScrollBar(20, 250, 1200, 250, Color.GOLD);
		ls = new ListShower(sb, 400, 50);
		gd = new GraphDrawer(graph, time);
	}
	
	private void loadFile() {
		if(name == "guest")
			return;       
		File save = new File("data\\" + name + ".txt");
		if(save.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(save));
				String[] dats = br.readLine().split(",");
				money = Integer.parseInt(dats[0]);
				time = Integer.parseInt(dats[1]);
				SMONEY = Integer.parseInt(dats[3]);
				STIME = Integer.parseInt(dats[2]);
				br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getTime(String date) {
		String[] data = date.split("/");
		int year = Integer.parseInt(data[0]);
		int month = Integer.parseInt(data[1]);
		int day = Integer.parseInt(data[2]);
		int t;
		for(t = 0; datas[t].year != year || datas[t].month != month || datas[t].day != day; t++);
		return t;
	}
	


	@Override
	public void LoadContents() {
		

	}

	@Override
	public void Update(long now) {
		if(ingd)
			return;
		if(stopping)
			return;
		
		if(updTimer.next()) {
			time++;
			if(time >= datas.length) {
				time = datas.length - 1;
			}
		}
		su++;
		if(su % 60 == 0) {
			second++;
			second = second % 60;
		}
		ls.update();
	}

	@Override
	public void Draw(GraphicsContext g) {
		if(ingd) {
			gd.Draw(g);
			if(!gd_seeing)
				return;
		}
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, 2000, 1000);
		double rate = datas[time].close;
		if(drawn_data != null) {
			graph.DrawOrgLine(g, drawn_data, time - draw_start);
		}
		graph.Draw(g, graphName, time, dx);
		graph.plotPoint(g, time + dx, ls.list);
		g.setFont(new Font("consolas", 20));
		ls.Draw(g);
		g.setFill(Color.BLACK);
		g.setFont(new Font("consolas", 20));
		ask.Draw(g);
		bid.Draw(g);
		cash.Draw(g);
		uverw.Draw(g);
		dverw.Draw(g);
		miminute.Draw(g);
		mihour.Draw(g);
		miday.Draw(g);
		mimonth.Draw(g);
		urot.Draw(g);
		drot.Draw(g);
		if(name != "guest")
			save.Draw(g);
		if(stopping)
			tso.Draw(g);
		else
			tsf.Draw(g);
		g.setFill(Color.WHITE);
		g.fillText(name, 1000, 50);
		g.fillText(String.format("縦幅：%.1f", graph.nowVW()), 730, 700);
		g.fillText(String.format("ロット：%.1f", rot), 1030, 700);
		g.fillText(String.format("%.0f円", money), 700, 50);
		g.fillText(String.format("%.2f", rate), 700, 150);
		g.fillText(datas[time].date() + String.format(":%02d", second), 700, 200);
		g.fillText(String.format("平均日収：%.1f円", (money - SMONEY) / ((time - STIME + 1) / (60.0 * 24))), 850, 600);
		g.fillText(msg.getFxname(), 50, 50);
		g.fillText(String.format("time_speed:%d", time_sp), 700, 750);
//		if(ownrate != 0) {
//			if(isBuy) {
//				am = (rate - ownrate) * (rot * 10000);
//				g.setFill(Color.RED);
//			} else {
//				am = (ownrate - rate) * (rot * 10000);
//				g.setFill(Color.BLUE);
//				
//			}
//			g.fillOval(230, 655, 40, 40);
//			g.setFill(Color.WHITE);
//			g.fillText(String.format("%+.2f", am), 700, 100);
//		}
		if(ls.list.size() != 0) {
			double add = 0;
			for(ListShower.ListData ld : ls.list) {
				add += ld.rot * 10000 * (ld.isBuy ? rate - ld.rate : ld.rate - rate);
			}
			g.setFill(Color.WHITE);
			g.fillText(String.format("%+.2f", add), 700, 100);
		}
		if(graphName == "line") {
			gcandle.Draw(g);
		} else {
			gline.Draw(g);
		}
		g.setFill(Color.DARKGOLDENROD);
		g.setFont(new Font("bold", 15));
		if(err_overmoney != 0) {
			err_overmoney--;
			g.fillText("所持金が足りません！", 100, 740);
		}
	}

	
	@Override
	public void MousePressed(MouseEvent e) {
		if(ingd) {
			double[] ds = gd.MousePressed(e);
			if(ds != null) {
				drawn_data = ds;
				ingd = false;
			}
			return;
		}
		double rate = datas[time].close;
		if(ask.isPushed(e) && (isBuy || ls.list.size() == 0) && chkRot(datas[time].close)) {
			ls.add(datas[time], second, rot, true);
			isBuy = true;
		}
		
		if(bid.isPushed(e) && (!isBuy || ls.list.size() == 0) && chkRot(datas[time].close)) {
			ls.add(datas[time], second, rot, false);
			isBuy = false;
		}
		
		if(graphName == "line" && gline.isPushed(e)) {
			graphName = "candle";
		} else if(graphName == "candle" && gcandle.isPushed(e)) {
			graphName = "line";
		}
		
		if(uverw.isPushed(e)) {
			graph.setVW(graph.nowVW() + 0.5);
		}
		if(dverw.isPushed(e)) {
			graph.setVW(graph.nowVW() - 0.5);
			if(graph.nowVW() < 0)
				graph.setVW(0);
		}
		
		if(miminute.isPushed(e)) {
			graph.setMode(0);
		}
		
		if(mihour.isPushed(e)) {
			graph.setMode(1);
		}
		
		if(miday.isPushed(e)) {
			graph.setMode(2);
		}
		
		if(mimonth.isPushed(e)) {
			graph.setMode(3);
		}

		
		if(urot.isPushed(e)) {
			if(chkRot(datas[time].close + 0.1)) {
				rot += 0.1;
			}
		}
		
		if(drot.isPushed(e)) {
			if(rot > 0.1) {
				rot -= 0.1;
			}
		}
		
		
		if(tso.isPushed(e) && tsf.isPushed(e)) {
			stopping = !stopping;
		}
		
		if(save.isPushed(e) && name != "guest") {
			saveData();
		}
		
		ListShower.ListData ld = ls.MousePressed(e);
		if(ld != null) {
			money += (ld.isBuy ? rate - ld.rate : ld.rate - rate) * ld.rot * 10000;
		}
		
		if(cash.isPushed(e)) {
			for(ListShower.ListData l : ls.list) {
				money += l.rot * 10000 * (l.isBuy ? rate - l.rate : l.rate - rate);
			}
			while(ls.list.size() != 0) {
				ls.remove(0);
			}
		}
	}

	@Override
	public void MouseReleased(MouseEvent e) {
		ls.MouseReleased(e);

	}

	@Override
	public void MouseMoved(MouseEvent e) {
		ls.MouseMoved(e);

	}

	@Override
	public void MouseDragged(MouseEvent e) {
		ls.MouseDragged(e);
		gd.MouseDragged(e);

	}

	@Override
	public void KeyPressed(KeyEvent e) {
		if(ingd) {
			int n = gd.KeyPressed(e);
			if(n == 1) {
				gd_seeing = true;
			}
			return;
		}
		
		//final double rate = datas[time].close;
		switch(e.getCode()) {
		case ENTER:
			second += time_sp;
			time += Math.floor(second / 60.0);
			while(datas[time].close == 0) {
				time++;
			}
			if(time >= datas.length)
				time = datas.length - 1;
			updTimer.reset();
			second = second % 60;
			break;
		case UP:
			graph.moveY(10);
			break;
		case DOWN:
			graph.moveY(-10);
			break;
		case E:
			graph.setVW(graph.nowVW() + 0.5);
			break;
		case D:
			if(graph.nowVW() > 0.5)
				graph.setVW(graph.nowVW() - 0.5);
			break;
		case SPACE:
			graph.normalize(datas[time].close);
			dx = 0;
			break;
		case RIGHT:
			dx += graph.timespeed * 10;
			if(dx > 0)
				dx = 0;
			break;
		case LEFT:
			dx -= graph.timespeed * 10;
			if(time + dx < STIME)
				dx = STIME - time;
			break;
		case B:
			if(chkRot(datas[time].close) && (isBuy || ls.list.size() == 0)) {
				ls.add(datas[time], second, rot, true);
				isBuy = true;
			}
			break;
		case S:
			if(chkRot(datas[time].close) && (!isBuy || ls.list.size() == 0)) {
				ls.add(datas[time], second, rot, false);
				isBuy = false;
			}
			break;
		case C:
			double rate = datas[time].close;
			for(ListShower.ListData ld : ls.list) {
				money += ld.rot * 10000 * (ld.isBuy ? rate - ld.rate : ld.rate - rate);
			}
			while(ls.list.size() != 0) {
				ls.remove(0);
			}
			break;
		case R:
			if(chkRot(datas[time].close + 0.1)) {
				rot += 0.1;
			}
			break;
		case F:
			if(rot > 0.1) {
				rot -= 0.1;
			}
			break;
		case BACK_SPACE:
			n = true;
			break;
		case T:
			time_sp++;
			break;
		case G:
			time_sp--;
			break;
		case Z:
			ingd = true;
			gd = new GraphDrawer(graph, time);
			draw_start = time;
			break;
		default:
			break;
		}
	}
	
	boolean chkRot(double f) {
		double amon = f * rot * 10000;
		for(ListShower.ListData ld : ls.list) {
			amon += ld.rot * 10000 * ld.rate;
		}
		if(!(amon <= (money * 10))) {
			err_overmoney = 60;
		}
		return amon <= (money * 10);
	}
	
	private void saveData() {
		try {
			File file = new File("data\\" + name + ".txt");
			FileWriter fw = new FileWriter(file);
			fw.write(String.format("%d,%d,%d,%d", (int)money, time, STIME, (int)SMONEY));
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void KeyReleased(KeyEvent e) {
		int n = gd.KeyReleased(e);
		if(n == 1) {
			gd_seeing = false;
		}
	}
	
	@Override
	public void Finalize() {
		n = false;
	}

	@Override
	public int NextScene() {
		if(n)
			return 0;
		return -1;
	}

	@Override
	public Message ProcMessage(Message msg) {
		index = msg.fxid;
		this.msg = msg;
		return msg;
	}
	
	
	public void print(double i) {
		System.out.println(String.valueOf(i));
	}
	
	public void print(int i) {
		System.out.println(String.valueOf(i));
	}
	
	public void print(String s) {
		System.out.println(s);
	}

}
