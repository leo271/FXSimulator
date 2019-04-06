
public class Message {
	int fxid = 0;
	String date = "2018/5/1";
	int money = 1000000;
	String name = "guest";
	
	
	static final String[] fxname = {"USDJPY", "EURJPY", "EURUSD"};
	public String getFxname() {
		return fxname[fxid].substring(0, 3) + "/" + fxname[fxid].substring(3, 6);
	}
}
