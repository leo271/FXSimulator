
public class Timer {
	int time;
	int n = 0;
	public Timer(int time) {
		this.time = time;
	}
	
	public boolean next() {
		n++;
		if(n % time == 0) {
			n = 0;
			return true;
		}
		return false;
	}
	
	public void reset() {
		n = 0;
	}
}
