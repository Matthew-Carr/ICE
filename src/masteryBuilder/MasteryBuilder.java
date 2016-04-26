package masteryBuilder;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MasteryBuilder {
    public Pane masteryBuilderPage;

    public MasteryBuilder() {
        masteryBuilderPage = new Pane();
        Text t = new Text(10, 50, "Mastery Builder");
        masteryBuilderPage.getChildren().add(t);
    }

}
