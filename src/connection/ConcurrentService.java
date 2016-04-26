package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/* This is my implementation of an asynchronous url retriever
 * usage:
 * 
 * ConcurrentService service = new ConcurrentService();
 * service.setUrl("https://global.api.pvp.net/api/lol/static-data/na/v1.2/item?itemListData=stats&api_key=7f057410-5d0b-4174-b2e4-ad93f77c18d6");
 * service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
 *
 *     @Override
 *     public void handle(WorkerStateEvent t) {
 * 	       area.setText("done:" + t.getSource().getValue());
 *     }
 * });
 * service.start();
 */
public class ConcurrentService extends Service<String> {
    private StringProperty url = new SimpleStringProperty();

    public final void setUrl(String value) {
        url.set(value);
    }

    public final String getUrl() {
        return url.get();
    }

    public final StringProperty urlProperty() {
        return url;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws IOException, MalformedURLException {
                BufferedReader reader = null;
                try {
                    URL url = new URL(getUrl());
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
        };
    }

}