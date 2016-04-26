package main;

import about.About;
import itemBuilder.ItemBuilder;
import javafx.stage.Stage;
import masteryBuilder.MasteryBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.control.Label;

//paint and other useful classes
import javafx.scene.paint.Color;
import javafx.scene.text.*;//Font,FontWeight,FontPosture,TextAlignment

public class ICE extends Application {

    Button toAbout;
    Button toItemBuilder;
    Button toMasteryBuilder;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // root is the pane that contains all other panes & their buttons
        Pane root = new Pane();

        makeButtons();

        // make About page
        About about = new About();

        // make Item Builder page
        ItemBuilder itemBuilder = new ItemBuilder();

        // make Mastery Builder page
        MasteryBuilder masteryBuilder = new MasteryBuilder();

        //add everything to root
        root.getChildren().addAll(toAbout, toItemBuilder, toMasteryBuilder, about.aboutPage);

        // define what the buttons should do
        toItemBuilder.setOnAction(ae -> {
            root.getChildren()
                    .removeAll(itemBuilder.itemBuilderPage, about.aboutPage, masteryBuilder.masteryBuilderPage);
            root.getChildren().addAll(itemBuilder.itemBuilderPage);
        });

        toAbout.setOnAction(ae -> {
            root.getChildren()
                    .removeAll(itemBuilder.itemBuilderPage, about.aboutPage, masteryBuilder.masteryBuilderPage);
            root.getChildren().addAll(about.aboutPage);
        });

        toMasteryBuilder.setOnAction(ae -> {
            root.getChildren()
                    .removeAll(itemBuilder.itemBuilderPage, about.aboutPage, masteryBuilder.masteryBuilderPage);
            root.getChildren().addAll(masteryBuilder.masteryBuilderPage);
        });

        // display everything
        Scene scene = new Scene(root, 900, 600, Color.GRAY);
        primaryStage.setTitle("ICE - Item & Champion Emulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * creates a new Label object and returns it
     *
     * @param text The text the label should initially have
     * @return a Label object set to the text that was passed
     */
    public Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    public void makeButtons() {
        // Buttons for switching panes
        toAbout = new Button("About");
        toItemBuilder = new Button("Item Builder");
        toMasteryBuilder = new Button("Mastery Builder");
        toAbout.setLayoutX(100);
        toItemBuilder.setLayoutX(200);
        toMasteryBuilder.setLayoutX(300);
    }

}





