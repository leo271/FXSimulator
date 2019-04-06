import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataFormatter {

	static class Dates {
		int year, month, day, hour, minute;
		
		public Dates(FXDat dat) {
			year = dat.year;
			month = dat.month;
			day = dat.day;
			hour = dat.hour;
			minute = dat.minute;
		}
		
		public boolean equals(FXDat dat) {
			return year == dat.year && month == dat.month && day == dat.day && hour == dat.hour && minute == dat.minute;
		}
		
		public void inc() {
			minute++;
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
	}
	
	public static void main(String[] args) {
		for(String s : args) {
			makeData(s);
		}
	}
	
	private static void makeData(String filename) {
		FXDat[] datas = new MakeData(filename).getMergeData();
		ArrayList<FXDat> form = new ArrayList<>();
		long t;
		printl("Start ->" + filename);
		print("Format Data");
		t = millis();
		Dates date = new Dates(datas[0]);
		for(int i = 0; i < datas.length - 1; i++) {
			form.add(datas[i]);
			date.inc();
			while(!date.equals(datas[i + 1])) {
				form.add(d(date));
				date.inc();
			}
		}
		form.add(datas[datas.length - 1]);
		printl(" - Complete");
		printl(String.format("%dmillis", millis() - t));
		print("Write In File");
		t = millis();
		try {
			File file = new File(filename + ".txt");
			FileWriter fw = new FileWriter(file);
			for(FXDat f : form) {
				fw.write(String.valueOf(f.year) + "," + 
					String.valueOf(f.month) + "," + 
					String.valueOf(f.day) + "," + 
					String.valueOf(f.hour) + "," +
					String.valueOf(f.minute) + "," + 
					String.valueOf(f.open) + "," + 
					String.valueOf(f.high) + "," + 
					String.valueOf(f.low) + "," + 
					String.valueOf(f.close) + ",");
			}
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		printl(" - Complete");
		printl(String.format("%dmillis", millis()- t));
		printl("All Completed");
	}
	
	private static long millis() {
		return System.currentTimeMillis();
	}
	
	private static void printl(String text) {
		System.out.println(text);
	}
	
	private static void print(String text) {
		System.out.print(text);
	}
	
	private static FXDat d(Dates d) {
		return new FXDat(d.year, d.month, d.day, d.hour, d.minute, 0.0, 0.0, 0.0, 0.0);
	}
	
}


