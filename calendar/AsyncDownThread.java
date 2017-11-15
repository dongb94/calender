package calendar;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.logging.Handler;



public class AsyncDownThread extends Thread {
	public static String result;
	private String addr;

	public AsyncDownThread(String addr) {
		this.addr = addr;

	}
	public static String removeTag(String html) throws Exception {
		return html.replaceAll("<[^>]*>", "").replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");

	}


	public void run() {
		result = downloadHtml(addr);
		try {
			result = removeTag(result);
			System.out.println(result);
		//********************************스레드 통신 필요한부분 result를 메인스레드로 가져오기만 하면됨
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("url접속완료");	
		

	}

	private String downloadHtml(String addr) {
		StringBuilder html = new StringBuilder();
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						html.append(line+"\n");
						
					}
					br.close();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html.toString();
	}
}
