
public class FXDat {
	final int year;
	final int month;
	final int day;
	final int hour;
	final int minute;
	final double open;
	final double high;
	final double low;
	final double close;
	
	public FXDat(int year, int month, int day, int hour, int minute, double open, double high, double low, double close) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}
	
	public FXDat(String data) {
		this.year = Integer.parseInt(data.substring(0, 4));
		this.month = Integer.parseInt(data.substring(5, 7));
		this.day = Integer.parseInt(data.substring(8, 10));
		this.hour = Integer.parseInt(data.substring(11, 13));
		this.minute = Integer.parseInt(data.substring(14, 16));
		this.open = Double.parseDouble(data.substring(20, 26));
		this.high = Double.parseDouble(data.substring(27, 33));
		this.low = Double.parseDouble(data.substring(34, 40));
		this.close = Double.parseDouble(data.substring(41, 47));
	}
	
	public String date() {
		return String.format("%d/%d/%d %02d:%02d", year, month, day, hour, minute);
	}
}
