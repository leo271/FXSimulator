import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Graph {
	FXDat[] datas;
	int width = 630;
	int height = 630;
	int cwidth = 5;
	int t = 0;
	double start_v;
	
	double[] verwidths = {
		30, 10, 3, 0.5
	};
	final double[] timess = {
		0.2, 1, 30, 60 * 6
	};
	final int[] dx = {
		1, 0, 0, 0
	};
	double[] ys = {
		0, 0 , 0, 0
	};
	
	int[] stock_border = {
		5, 15, 15 * 10 / 3, 15 * 20
	};
	double timespeed;
	int modeid = 0;

	
	public Graph(FXDat[] datas) {
		this.datas = datas;
		boolean bb = false;
		for(int i = day * 60; i < datas.length; i++) {
			boolean b = ave(i, 5) > ave(i, 25);
			if(b && !bb) {
				t = i;
				break;
			}
			bb = b;
		}
		timespeed = timess[modeid];
		start_v = datas[0].close;
	}
	
	public void Draw(GraphicsContext g, String type, int time, int dx) {
		if(start_v == datas[0].close)
			start_v = datas[time].close;
		if(!((int)(time - width * timespeed) > 0)) {
			dx = 0;
		}
		if(time + dx >= datas.length) {
			return;
		}
//		if(ownrate != 0) {
//			double y = nowY() + getY(ownrate);
//			if(y < height) {
//				g.setStroke(Color.WHITE);
//				g.strokeLine(1, y, width - 1, y);
//				g.setFill(Color.GRAY);
//				g.fillText(String.format("%.2f", getY_(y)), width + 10, y + 10);
//			}
//		}
		DrawInfo(g);
		g.setStroke(Color.GRAY);
		g.strokeRect(1, 1, width, height);
		if(time + dx < datas.length)
			aveLine(g, time + dx);
		switch(type) {
		case "line":
			Line(g, time + dx);
			break;
		case "candle":
			Candle(g, time + dx);
			break;
		default:
			break;
		}
		g.setFill(Color.BLACK);
		g.fillRect(0, height + 1, width, 1000);
		g.setFont(new Font("consolas", 15));
		g.setFill(Color.WHITE);
//		for(int i = 0; i < height - 20; i += 100)
//			g.fillText(String.format("%.2f", getY_(i)), width + 10, i);
		int si = width > (int)((time + dx) / timespeed) ? (int)((time + dx) / timespeed) : width;
		
		for(int i = si; i >= 0; i -= 100) {
			int idx = (int)(time + dx + (i - si) * timespeed);
			g.fillText(sbmd(idx), i, 643);
		}
	}
	public void DrawOrgLine(GraphicsContext g, double[] data, int end_point) {
		final int sp = (int)Math.max(0, end_point - width * timespeed);
		final int ep = (int)Math.min(data.length - 1, end_point);
		g.setStroke(Color.GREENYELLOW);
		double oy = getY(data[ep]) + nowY();
		for(int i = ep - 1; i >= sp; i--) {
			double y = getY(data[i]) + nowY();
			g.strokeLine(width + (int)((i + 1 - ep) / timespeed), oy, width + (int)((i - ep) / timespeed), y);
			oy = y;
		}
	}
	
	public void DrawInfo(GraphicsContext g) {
		int stock_border;
		if(nowVW() <= 2.5) {
			stock_border = 15 * 20;
		} else if(nowVW() <= 9.5) {
			stock_border = 5 * 10;
		} else if(nowVW() <= 29.5) {
			stock_border = 15;
		} else {
			stock_border = 5;
		}
		final double sv = floorOn(getY_(height), 2);
		final double ev = floorOn(getY_(0), 2);
		g.setFont(new Font("consolas", 15));
		g.setFill(Color.WHITE);
		g.setStroke(Color.GRAY);
		double val = sv;
		while(val < ev) {
			val += 0.01;
			if((int)(val * 100) % stock_border == 0.0) {
				g.fillText(String.format("%.2f", val), width + 5, getY(val) + nowY());
				g.strokeLine(0, getY(val) + nowY(), width, getY(val) + nowY());
			}
		}
	}
	
	public double floorOn(double val, int place) {
		return Math.floor(val * Math.pow(10, place)) / Math.pow(10, place);
	}
	
	final int hour = 60;
	final int month = day * 31;
	final int year = month * 12;
	
	private boolean bigger(ListShower.ListData ld, FXDat fd) {
		long luni = ld.minute + ld.hour * hour + ld.day * day + ld.month * month + ld.year + year;
		long funi = fd.minute + fd.hour * hour + fd.day * day + fd.month * month + fd.year + year;
		return luni > funi;
	}
	
	public void plotPoint(GraphicsContext g, int time, ArrayList<ListShower.ListData> ld) {
		if(ld.size() == 0)
			return;
		int si = (int)(time - width * timespeed) > 0 ? (int)(time - width * timespeed) : 0;
		int n = 2;
		g.setFill(ld.get(0).isBuy ? Color.RED : Color.BLUE);
		g.setStroke(ld.get(0).isBuy ? Color.RED : Color.BLUE);
		for(ListShower.ListData l : ld) {
			if(bigger(l, datas[time]))
				continue;
			int i = time - within(l, datas[time]);
			double x = (i - si) / timespeed;
			double y = getY(datas[i].close) + nowY();
			g.fillOval(x - n, y - n, 2 * n + 1, 2 * n + 1);
			g.strokeLine(width + 1, y, width + 5, y);
		}
		
	}
	
	private String sbmd(int idx) {
		switch(modeid) {
		case 0:
		case 1:
			return String.format("%02d:%02d", datas[idx].hour, datas[idx].minute);
		case 2:
			return String.format("%d %02d", datas[idx].day, datas[idx].hour);
		case 3:
			return String.format("%d/%d", datas[idx].month, datas[idx].day);
		default:
			return null;
		}
	}
	
	private int within(ListShower.ListData ld, FXDat fd) {
		int times = 0;
		int minute = ld.minute;
		int hour = ld.hour;
		int day = ld.day;
		int month = ld.month;
		int year = ld.year;
		while(minute != fd.minute || hour != fd.hour || day != fd.day || month != fd.month || year != fd.year) {
			minute++;
			times++;
			if(minute == 60) {
				minute = 0;
				hour++;
				if(hour == 24) {
					hour = 0;
					day++;
					boolean n = false;
					switch(month) {
					case 1:
					case 3:
					case 5:
					case 7:
					case 8:
					case 10:
					case 12:
						if(day == 32)
							n = true;
						break;
					case 4:
					case 6:
					case 9:
					case 11:
						if(day == 31)
							n = true;
						break;
					case 2:
						if(day == 30)
							n = true;
						break;
					default:
						System.out.println("ERROR IN within @month");
						System.out.println(String.format("month=%d", month));
						break;
					}
					if(n) {
						day = 1;
						month++;
						if(month == 13) {
							month = 1;
							year++;
						}
					}
				}
			}
		}
		return times;
	}
	
	
	public void Line(GraphicsContext g, int time) {
		g.setStroke(Color.GREEN);
		int si = (int)(time - width * timespeed) > 0 ? (int)(time - width * timespeed) : 0;
		double by = getY(datas[si].close) + nowY();
		final double nul = getY(0) + nowY();
		for(double i = 1 + si - si % (int)Math.ceil(timespeed); i < time; i += timespeed) {
			double r = datas[(int)i].close;
			double y = getY(r) + nowY();
			if(by == nul ||  y == nul) {
				by = y;
				continue;
			}
			g.strokeLine(Math.floor((i - si) / timespeed) - 1, by, Math.floor((i - si) / timespeed), y);
			by = y;
		}
	}
	
	public void aveLine(GraphicsContext g, int time) {

		int si = (int)(time - width * timespeed) > 0 ? (int)(time - width * timespeed) : 0;
		double ba5 = getY(ave(si, 5)) + nowY();
		double ba25 = getY(ave(si, 25)) + nowY();
		for(double i = 1 + si - si % (int)Math.ceil(timespeed); i < time; i += timespeed) {
			if(datas[(int)i].close == 0)
				continue;
			double a5 = getY(ave((int)i, 5)) + nowY();
			double a25 = getY(ave((int)i, 25)) + nowY();
			double x = Math.floor((i - si) / timespeed);
			
			g.setStroke(Color.YELLOW);
			g.strokeLine(x - 1, ba5, x, a5);
			g.setStroke(Color.RED);
			g.strokeLine(x - 1, ba25, x, a25);
			ba5 = a5;
			ba25 = a25;
		}
	}
	
	@SuppressWarnings("unused")
	private static void print(String str) {
		System.out.println(str);
	}
	
	private static final int day = 60 * 24;
	
	private double ave(int time, int aves) {
//		System.out.println(datas[time].date() + datas[time - day].date());

		double d = 0.0;
		int a = 0;
		for(int i = 0; i < aves; i++) {
			if(time - i * day > 0 && datas[time - i * day].close != 0) {
				a++;
				d += datas[time - i * day].close;
			}
		}

		return d / a;
	}

	public void Candle(GraphicsContext g, int time) {
		int size = timespeed > 1 ? cwidth : (int)(cwidth / timespeed);
		int width = (int)Math.floor(size / 2.0);
		int si = (int)Math.floor((time - this.width * timespeed > 0 ? time - this.width * timespeed : 0) / (double)cwidth) * cwidth;
		si = si == 0 ? 0 : si + cwidth;
		final int st = (int)(size * timespeed);
		for(double i = si - si % st; i < time; i += st) {
			boolean n = false;
			for(int m = (int)i; m < (int)i + st; m++) {
				if(datas[m].close == 0)
					n = true;
			}
			if(n) continue;
			double open = datas[(int)i].open;
			double high = max(highes(datas, (int)i, st), 0, st - 1);
			double low = min(lows(datas, (int)i, st), 0, st - 1);
			double close = datas[(int)i + st - 1].close;
//			System.out.println(String.format("%dopen%fhigh%f,low%f,close%f", i, open, high, low, close));
			open = getY(open);
			high = getY(high);
			low = getY(low);
			close = getY(close);
			double x = Math.floor((i - si) / timespeed) - ((time - dx[modeid]) % st) / timespeed;
			if(open < close) {
				g.setFill(Color.GREEN);
				g.setStroke(Color.GREEN);
				g.strokeLine(x + width, high + nowY(), x + width, low + nowY());
				g.fillRect(x, open + nowY(), size - 1, close - open);
			} else {
				g.setFill(Color.WHITE);
				g.setStroke(Color.RED);
				g.strokeLine(x + width, high + nowY(), x + width, low + nowY());
				g.fillRect(x, close + nowY(), size - 1, open - close);
				g.setFill(Color.RED);
				g.strokeRect(x, close + nowY(), size - 1, open - close);
			}
			
		}
	}
	
	final long HOUR = 60;
	final long DAY = HOUR * 24;
	final long MONTH = DAY * 32;
	final long YEAR = MONTH * 13;
	

	public void normalize(double v) {
		start_v = v;
		ys[modeid] = height / 2 - getY(v);
	}
	
 	private double[] highes(FXDat[] row, int s, int size) {
		double[] highes = new double[size];
		for(int i = 0; i < size; i++) {
			highes[i] = datas[i + s].high;
		}
		return highes;
	}
	
	private double[] lows(FXDat[] row, int s, int size) {
		double[] lows = new double[size];
		for(int i = 0; i < size; i++) {
			lows[i] = datas[i + s].low;
		}
		return lows;
	}
	
	private double max(double[] v, int s, int e) {
		double max = v[s];
		for(int i = s + 1; i < e; i++) {
			if(v[i] > max) {
				max = v[i];
			}
		}
		return max;
	}
	
	private double min(double[] v, int s, int e) {
		double min = v[s];
		for(int i = s + 1; i < e; i++) {
			if(v[i] < min) {
				min = v[i];
			}
		}
		return min;
	}
	
	private double getY(double r) {
		return height / 2.0 - (r - start_v) * nowVW() * 50;
	}
	
	private double getY_(double y) {
		return -(y - height / 2.0 - nowY()) / (nowVW() * 50) + start_v;
	}
	
	public double nowY() {
		return ys[modeid];
	}
	
	public void moveY(double y) {
		ys[modeid] += y;
	}
	
	public double nowVW() {
		return verwidths[modeid];
	}
	
	public void setVW(double vw) {
		verwidths[modeid] = vw;
	}
	
	public void setMode(int id) {
		modeid = id;
		timespeed = timess[id];
	}
}
