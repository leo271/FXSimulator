import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GetHtml {
	


	
	public String getHtml(String uri) {
	     URL url = null;
	     InputStreamReader isr = null;
	     String str = new String();
	     try {
	         url = new URL(uri);
	 
	         // InputStream(バイトストリーム)のままでもHTMLは取得できるが文字化けする
	         InputStream is = url.openStream();
	         // InputStreamをUTF8のInputStreamReader(文字ストリーム)に変換する
	         isr = new InputStreamReader(is,"UTF-8");
	         int i;
	         // 一文字毎に読み込む
	         while((i = isr.read()) != -1) {
	             str += (char)i;
	         }
	         
	     }catch (Exception e) {
	         System.out.println(e.getMessage());
	     }finally {
	         try {
	             isr.close();
	         }catch (Exception e) {
	             System.out.println(e.getMessage());
	         }
	     }
	     return str;
	}
}
