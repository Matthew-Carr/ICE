package connection;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import main.AsyncResponse;

public class ConcurrentTask {
    public ConcurrentTask(AsyncResponse delegate, String url) {
        ConcurrentService service = new ConcurrentService();
        service.setUrl(url);
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                JSONObject result;
                try {
                    result = new JSONObject(t.getSource().getValue().toString());
                    delegate.processFinish(result);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
        service.start();
    }
}
