
public class Stock {
	public final int ID;
	public String company;
	public int prise;
	private String data;
	private GetHtml html;
	
	public Stock(int id) {
		this.ID = id;
		this.html = new GetHtml();
		update();
	}
	
	public void update() {
		data = html.getHtml("https://stocks.finance.yahoo.co.jp/stocks/detail/?code=" + String.valueOf(ID));
		data = GetBody(data, "<div class=\"stockMainTab clearFix\">", "</body>");
		if(data.indexOf("日本の証券市場は終了しました。") == -1) {
			prise = Integer.parseInt(GetBody(data,"<td class=\"stoksPrice\">","</td>"));
			company = GetBody(data, "<th class=\"symbol\"><h1>", "</h1>");
		}  else {
			prise = 0; 
			company = "本日の取引は終了しました。";
		}
	}
	
	public String GetBody(String org_str, String fst_key, String snd_key) {
		int start_p = org_str.indexOf(fst_key) + fst_key.length();
		if(start_p == -1) {
			System.out.println("Not Found first keyword!");
			return null;
		}
		int end_p = org_str.substring(start_p).indexOf(snd_key);
		if(end_p == -1) {
			System.out.println("Not Found second keyword!");
			return null;
		}

		return org_str.substring(start_p, start_p + end_p);
	}
}
