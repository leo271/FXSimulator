
public class Palse {
	boolean on;
	public boolean palse(boolean n) {
		if(!n) {
			on = false;
			return false;
		}
		if(on) {
			return false;
		}
		on = true;
		return true;
	}
}
