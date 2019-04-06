import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.Arrays;


public class MakeData {
	final private String name;
	public MakeData(String filename) {		
		this.name = filename;
	}
	
	public FXDat[] makeData() {
		String text;
		try {
			text = readAll("\\data\\" + name + ".txt");
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		String[] datas = text.split(",");
		int size = datas.length / 9;
		FXDat[] data = new FXDat[size];
		for(int i = 0; i < size; i++) {
			data[i] = new FXDat(
					Integer.parseInt(datas[i * 9]),        //year
					Integer.parseInt(datas[i * 9 + 1]),    //month
					Integer.parseInt(datas[i * 9 + 2]),    //day
					Integer.parseInt(datas[i * 9 + 3]),    //hour
					Integer.parseInt(datas[i * 9 + 4]),    //minute
					Double.parseDouble(datas[i * 9 + 5]),  //open
					Double.parseDouble(datas[i * 9 + 6]),  //high
					Double.parseDouble(datas[i * 9 + 7]),  //low
					Double.parseDouble(datas[i * 9 + 8])); //close
		}
		return data;
	}
	
	public FXDat[] getMergeData() {
		FXDat[] d2018 = getDatas("2018");
		FXDat[] d2019 = getDatas("2019");
		FXDat[] merge = new FXDat[d2018.length + d2019.length];
		System.arraycopy(d2018, 0, merge, 0, d2018.length);
		System.arraycopy(d2019, 0, merge, d2018.length, d2019.length);
		
		return merge;
	}
	
	int within(FXDat d1, FXDat d2) {
		int year = d1.year;
		int month = d1.month;
		int day = d1.day;
		int hour = d1.hour;
		int minute = d1.minute;
		int w = 0;
		while(year != d2.year || month != d2.month || day != d2.day || hour != d2.hour || minute != d2.day) {
			System.out.println(String.format("%d/%d/%d %d:%d", year, month, day, hour, minute));
			w++;
			if(w == 10)
				break;
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
					case 2:
						if(day == 30)
							n = true;
						break;
					case 4:
					case 6:
					case 9:
						if(day == 31)
							n = true;
						break;
					}
					if(n) {
						day = 0;
						month++;
						if(month == 13) {
							month = 0;
							year++;
						}
					}
				}
			}
		}
		return w;
	}
	
	public FXDat[] getDatasFromDate(String date) {
		String[] dates = date.split("/");
		FXDat[] datas = getDatas(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);
		Palse starts = new Palse();
		boolean started = false;
		int sti = 0;
		int edi = 0;
		
		for(int i = 0; i < datas.length; i++) {
			if(starts.palse(datas[i].month == month && datas[i].day == day)) {
				started = true;
				sti = i;
			}
			if(started && !(datas[i].month == month && datas[i].day == day)) {
				edi = i;
				break;
			}
		}
		return (FXDat[])Arrays.copyOfRange(datas, sti, edi);
	}
	
	
	public FXDat[] getDatas(String year) {
		try {
			String org = readAll("\\" + name + "\\1min\\dat" + year + ".txt");
			String[] datas = org.split("\n");
			FXDat[] formated = new FXDat[datas.length];
			for(int i = 0; i < datas.length; i++) {
				formated[i] = new FXDat(datas[i]);
			}
			return formated;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String readAll(final String path) throws IOException {
	    return Files.lines(Paths.get(System.getProperty("user.dir") + "\\" + path), Charset.forName("UTF-8"))
	        .collect(Collectors.joining(System.getProperty("line.separator")));
	}
}
