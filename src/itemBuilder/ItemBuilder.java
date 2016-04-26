package itemBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import main.BuilderPage;
import org.json.JSONException;
import org.json.JSONObject;

import api.LeagueAPI;
import api.Updater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import main.AutoCompleteComboBoxListener;
import main.ChampionObj;

public class ItemBuilder implements BuilderPage{
    private TextField champ_name;
    private TextArea stats;
    private LeagueAPI Lol_api;
    private ChampionObj champ;
    private ComboBox<String> item1;
    private ComboBox<String> item2;
    private ComboBox<String> item3;
    private ComboBox<String> item4;
    private ComboBox<String> item5;
    private ComboBox<String> item6;
    private ChampNameHandler champ_name_handler;
    private VBox fields = new VBox();
    private VBox items = new VBox();
    public BorderPane itemBuilderPage = new BorderPane();
    private Label item_label = createLabel("Choose your items");
    private Button applyItems;
    private Button clearItems;
    private Spinner<Integer> champ_level;
    private Button applyLevel;

    public JSONObject champion;
    public JSONObject item;
    public JSONObject staticChampion;

    public ItemBuilder() throws Exception {
        getData();
        makePane();
    }

    public void getData() throws Exception {
        Lol_api = LeagueAPI.getInstance();
        try {
            Updater.update();

            String data_dragon_version = new String(Files.readAllBytes(Paths.get("data_dragon_version.txt"))).trim();
            System.out.println("Data Dragon: " + data_dragon_version);
            System.out.println("reading data");

            String c = new String(Files.readAllBytes(Paths.get("Champion_getChampions.txt")));
            System.out.println(System.getProperty("user.dir"));
            champion = new JSONObject(c);

            String i = new String(Files.readAllBytes(Paths.get("Item_getItems.txt")));
            item = new JSONObject(i);

            String s = new String(Files.readAllBytes(Paths.get("StaticData_getChampions.txt")));
            staticChampion = new JSONObject(s);

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

    public void makePane() {
        champ_name_handler = new ChampNameHandler();
        champ_name = new TextField("Enter a champion name");
        champ_name.setOnAction(champ_name_handler);

        stats = new TextArea();
        stats.setEditable(false);
        stats.setPrefHeight(450);
        stats.setStyle("-fx-font-family: monospace");

        champ_level = new Spinner<Integer>();
        champ_level.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 18));
        champ_level.setEditable(true);

        applyLevel = new Button("Set level");
        applyLevel.setOnAction(new ChampLevelHandler(this));

        VBox level = new VBox();
        level.getChildren().addAll(champ_level, applyLevel);

        fields.getChildren().addAll(champ_name, stats);

        parseItemNames();
        applyItems = new Button("Apply");
        applyItems.setOnAction(new ApplyItemsHandler(this));

        clearItems = new Button("Clear");
        clearItems.setOnAction(new ClearItemHandler());

        HBox itemButtons = new HBox();
        itemButtons.getChildren().addAll(applyItems, clearItems);
        items.getChildren().add(itemButtons);

        //itemBuilder.getChildren().addAll(fields, items);
        itemBuilderPage.setRight(items);
        itemBuilderPage.setCenter(fields);
        itemBuilderPage.setLeft(level);
        itemBuilderPage.setLayoutY(35);
        itemBuilderPage.setLayoutX(75);
    }

    public Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    public void parseItemNames() {

        JSONObject data;
        try {
            data = item.getJSONObject("data");

            String[] item_names = new String[data.length()];

            for (int i = 0; i < data.names().length(); i++) {
                Object obj = data.get(data.names().getString(i));
                JSONObject jobj = new JSONObject(obj.toString());
                item_names[i] = jobj.getString("name");
            }
            Arrays.sort(item_names);
            this.makeItems(item_names);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public class ChampLevelHandler implements EventHandler<ActionEvent> {

        ItemBuilder caller;

        @Override
        public void handle(ActionEvent event) {
            try {
                champ.setOfficialBaseStats(caller.staticChampion, caller.stats);
                int level = Integer.parseInt(champ_level.getEditor().textProperty().get());
                champ.setLevel(caller.staticChampion, level);
                stats.setText(champ.display());
            } catch (NumberFormatException e) {
                champ_level.getEditor().textProperty().set("1");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public ChampLevelHandler(ItemBuilder caller) {
            this.caller = caller;
        }
    }

    public class ChampNameHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String str = champ_name.getText();
            champ = new ChampionObj(str);
            try {
                champ.setOfficialBaseStats(staticChampion, stats);
                int level = Integer.parseInt(champ_level.getEditor().textProperty().get());
                champ.setLevel(staticChampion, level);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class ClearItemHandler implements EventHandler<ActionEvent> {
        ItemBuilder caller;

        @Override
        public void handle(ActionEvent event) {
            item1.getSelectionModel().clearSelection();
            item2.getSelectionModel().clearSelection();
            item3.getSelectionModel().clearSelection();
            item4.getSelectionModel().clearSelection();
            item5.getSelectionModel().clearSelection();
            item6.getSelectionModel().clearSelection();

        }
    }

    public class ApplyItemsHandler implements EventHandler<ActionEvent> {
        private ItemBuilder caller;

        public void handle(ActionEvent e) {
            if (!caller.stats.getText().equals("")) {
                try {
                    champ.setOfficialBaseStats(caller.staticChampion, caller.stats);
                    Integer level = Integer.parseInt(champ_level.getEditor().textProperty().get());
                    System.out.println("setting level to " + level.toString());
                    System.out.println(champ.stats.ability_power);
                    champ.setLevel(caller.staticChampion, level);

                    champ.stats.addItem(caller, champ, item1.getValue());
                    champ.stats.addItem(caller, champ, item2.getValue());
                    champ.stats.addItem(caller, champ, item3.getValue());
                    champ.stats.addItem(caller, champ, item4.getValue());
                    champ.stats.addItem(caller, champ, item5.getValue());
                    champ.stats.addItem(caller, champ, item6.getValue());

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public ApplyItemsHandler(ItemBuilder caller) {
            this.caller = caller;
        }
    }

    public class ItemComboHandler implements EventHandler<ActionEvent> {

        private ItemBuilder caller;

        public void handle(ActionEvent e) {
            try {
                champ.setOfficialBaseStats(caller.staticChampion, caller.stats);
                Integer level = Integer.parseInt(champ_level.getEditor().textProperty().get());
                System.out.println("setting level to " + level.toString());
                champ.setLevel(caller.staticChampion, level);

                champ.stats.addItem(caller, champ, item1.getValue());
                champ.stats.addItem(caller, champ, item2.getValue());
                champ.stats.addItem(caller, champ, item3.getValue());
                champ.stats.addItem(caller, champ, item4.getValue());
                champ.stats.addItem(caller, champ, item5.getValue());
                champ.stats.addItem(caller, champ, item6.getValue());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        public ItemComboHandler(ItemBuilder caller) {
            this.caller = caller;
        }
    }

    public void displayStats() {
        stats.setText(champ.display());
    }

    public ComboBox<String> makeItemComboBox(String[] items) {
        ObservableList<String> options = FXCollections.observableArrayList(items);
        return new ComboBox<String>(options);
    }

    private void makeItems(String[] item_names) {

        item1 = makeItemComboBox(item_names);
        item2 = makeItemComboBox(item_names);
        item3 = makeItemComboBox(item_names);
        item4 = makeItemComboBox(item_names);
        item5 = makeItemComboBox(item_names);
        item6 = makeItemComboBox(item_names);

        ItemComboHandler item_handler = new ItemComboHandler(this);

        item1.setOnAction(item_handler);
        item2.setOnAction(item_handler);
        item3.setOnAction(item_handler);
        item4.setOnAction(item_handler);
        item5.setOnAction(item_handler);
        item6.setOnAction(item_handler);

        new AutoCompleteComboBoxListener<>(item1);
        new AutoCompleteComboBoxListener<>(item2);
        new AutoCompleteComboBoxListener<>(item3);
        new AutoCompleteComboBoxListener<>(item4);
        new AutoCompleteComboBoxListener<>(item5);
        new AutoCompleteComboBoxListener<>(item6);

        items.getChildren().addAll(item_label, item1, item2, item3, item4, item5, item6);
    }

}
