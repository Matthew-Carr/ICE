package connection;

import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.JSONException;

public class ConnectionTask {
    public JSONObject data = null;
    public String raw_data = null;

    public ConnectionTask(String url) throws Exception {
        try {
            data = toJSON(readUrl(url));
        } catch (JSONException e) {
            raw_data = readUrl(url);
        }
    }

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public JSONObject toJSON(String text) throws JSONException {
        JSONObject obj = new JSONObject(text);
        return obj;
    }

}






























































































































































/*package connection;

import java.io.BufferedReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class ConnectionTask {
	public JSONObject data = null;
	ExecutorService executor;
	
	
    public ConnectionTask(String url) {
    	executor = Executors.newFixedThreadPool(1);
    	try {
    		//data = toJSON(readUrl(url));
    		GetJSON test = new GetJSON(this, url);
    		executor.execute(test);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	executor.shutdown();
    }

    class GetJSON implements Runnable {
    	private String url = "";
    	private ConnectionTask caller = null;
    	
    	public void run() {
        	try {
				caller.data = toJSON(readUrl(url));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	public GetJSON(ConnectionTask _caller, String _url) {
    		this.caller = _caller;
    		this.url = _url;
    	}
    	
    	
    	public String readUrl(String urlString) throws Exception {
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                return buffer.toString();
            } finally {
                if (reader != null)
                    reader.close();
            }
        }
        
        public JSONObject toJSON(String text) throws JSONException {
        	JSONObject obj = new JSONObject(text);   	
        	return obj;
        }
    }
}
*/