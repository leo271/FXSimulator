
public class Debug {
	static void print(String str) {
		System.out.println(str);
	}
	
	static void print(double d) {
		print(Double.toString(d));
	}
	
	static void print(int i) {
		print(Integer.toString(i));
	}
	
	static void print(boolean b) {
		print(b ? "true" : "false");
	}
	
	static void printf(String str, Object ...objs) {
		System.out.println(String.format(str + '\n', objs));
	}
	
	
	
	static void at() {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		for(StackTraceElement st : ste) {
			System.out.println(st);
		}
	}
}
