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
	 
	         // InputStream(�o�C�g�X�g���[��)�̂܂܂ł�HTML�͎擾�ł��邪������������
	         InputStream is = url.openStream();
	         // InputStream��UTF8��InputStreamReader(�����X�g���[��)�ɕϊ�����
	         isr = new InputStreamReader(is,"UTF-8");
	         int i;
	         // �ꕶ�����ɓǂݍ���
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
