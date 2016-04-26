package about;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class About {
    public Pane aboutPage;

    public About() {
        aboutPage = new Pane();
        Text t = new Text(10, 50, "For use by solo queue plats and higher");
        aboutPage.getChildren().add(t);
    }

}
