package masteryBuilder;

import api.LeagueAPI;
import api.Updater;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.BuilderPage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MasteryBuilder implements BuilderPage {
    public Pane masteryBuilderPage;
    JSONObject masteries;

    public MasteryBuilder() {
        masteryBuilderPage = new Pane();
        Text t = new Text(10, 50, "Mastery Builder");
        getData();

        TextArea ta = new TextArea();
        ta.setEditable(false);
        ta.setPrefHeight(450);
        ta.setStyle("-fx-font-family: monospace");
        ta.setWrapText(true);
        ta.setText(masteries.toString());
        masteryBuilderPage.getChildren().addAll(t, ta);
    }

    public void getData() {
        try {
            String c = new String(Files.readAllBytes(Paths.get("StaticData_getMasteries.txt")));
            masteries = new JSONObject(c);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
