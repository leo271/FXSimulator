public class Date{
	static final int MINUTE = 1;
	static final int HOUR = MINUTE * 60;
	static final int DAY = HOUR * 24;
	static final int MONTH = DAY * 31;
	static final int YEAR = MONTH * 12;
	static final int[] MONTHS = {
			31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};
	
	public Date(FXDat dat) {
		this.year = dat.year;
		this.month = dat.month;
		this.day = dat.day;
		this.hour = dat.hour;
		this.minute = dat.minute;
	}
	
	public Date(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public Date(Date other) {
		this.year = other.year;
		this.month = other.month;
		this.day = other.day;
		this.hour = other.hour;
		this.minute = other.minute;
	}
	
	int year;
	int month;
	int day;
	int hour;
	int minute;
	
	public void add(int n) {
		if(n <= 0)
			return;
		minute += n;
		if(minute < 60)
			return;
		hour += (int)Math.floor(minute / 60);
		minute = minute % 60;
		if(hour < 24)
			return;
		day += (int)Math.floor(hour / 24);
		hour = hour % 24;
		if(day < MONTHS[month - 1] + 1)
			return;
		while(true) {
			day -= MONTHS[month - 1] - 1;
			month += 1;
			if(day < MONTHS[month - 1] + 1)
				break;
		}
		if(month < 13)
			return;
		year += (int)Math.floor((month - 1) / 12);
	}
	
	public void dec(int n) {
		if(n <= 0)
			return;
		minute -= n;
		if(minute >= 0)
			return;
		hour += (int)Math.floor(minute / 60.0);
		minute = 60 + minute % 60;
		if(hour >= 0)
			return;
		day += (int)Math.floor(hour / 24.0);
		hour = 24 + hour % 24;
		if(day >= 1)
			return;
		do {
			month--;
			if(month <= 0) {
				month = 12;
				year--;
			}
			day += MONTHS[month - 1];
		} while(day <= 0);
		
	}
	
	public long unix() {
		return minute * MINUTE + hour * HOUR + day * DAY + month * MONTH + year * YEAR;
	}
	
	public Date copy() {
		return new Date(this);
	}
	
	public String two(int place) {
		switch(place) {
		case 0:
			return String.format("%02d:%02d", hour, minute);
		case 1:
			return String.format("%02d %02d", day, hour);
		case 2:
			return String.format("%02d/%02d", month, day);
		default:
			return "Error";
		}
	}
	
	public String toString() {
		return String.format("%04d/%02d/%02d %02d:%02d", year, month, day, hour, minute);
	}
}
