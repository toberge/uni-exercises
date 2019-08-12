import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class MainApp extends Application /*implements EventHandler*/ {

    private ListView<Object> fromList;
    private ListView<Object> toList;

    private Button convertButton;

    private boolean fromSelected = false;
    private boolean toSelected = false;

    private static final Currency[] initialCurrencies = {
            new Currency("Euro", 8.10, 1), new Currency("US Dollar", 6.23, 1),
            new Currency("Britiske pund", 12.27, 1), new Currency("Svenske kroner", 88.96, 100),
            new Currency("Danske kroner", 108.75, 100), new Currency("Yen", 5.14, 100),
            new Currency("Islandske kroner", 9.16, 100), new Currency("Norske kroner", 100, 100)
    };

    private static final String CREATION_STRING = "New currency";

    private ObservableList<Object> currencyList;


    private CreationDialog creationDialog = new CreationDialog();
    private ConversionDialog conversionDialog = new ConversionDialog();

    private Currency getFrom() {

       // ObservableList<Object> selection = fromList.getSelectionModel().getSelectedItems();

        Object selection = fromList.getSelectionModel().getSelectedItem();

        return selection.equals(CREATION_STRING)? null : (Currency) selection;

    }

    private Currency getTo() {

        //ObservableList<Object> selection = toList.getSelectionModel().getSelectedItems();

        Object selection = toList.getSelectionModel().getSelectedItem();

        return selection.equals(CREATION_STRING)? null : (Currency) selection;

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("vALuTA ShALL CoNVeRT");

        // creating lists
        fromList = new ListView<>();
        toList = new ListView<>();

        // create the list, add currencies and creation option, assign to ListViews
        currencyList = FXCollections.observableArrayList(CREATION_STRING);
        currencyList.addAll((Object[]) initialCurrencies); // cast because warning about ambiguous multiplicity... Understandable.

        fromList.setItems(currencyList);
        toList.setItems(currencyList);


        // look what I found in the documentation - it doesn't work like this of course
        toList.getSelectionModel().selectionModeProperty().addListener((ObservableValue<? extends Object> observableValue, Object old, Object selected) -> {System.out.println(selected); convertButton.setText(selected.toString());});

        // create buttons
        convertButton = new Button("Convert");
        convertButton.setDisable(true);
        convertButton.setOnAction(e -> startConversion());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());

        Button createButton = new Button("New");
        createButton.setOnAction(e -> startCreation());

        // setting up event handling
        fromList.setOnMouseClicked(this::handleListClick);
        toList.setOnMouseClicked(this::handleListClick);
        //fromList.setOnContextMenuRequested(this::select);


        // creating the rest of the window using BorderPane layout with V/Hbox containers since that feels sensible here

        Label fromLabel = new Label("From");
        Label toLabel = new Label("To");

        VBox fromPane = new VBox();
        VBox toPane = new VBox();

        fromPane.setAlignment(Pos.CENTER);
        toPane.setAlignment(Pos.CENTER);

        fromPane.getChildren().addAll(fromLabel, fromList);

        toPane.getChildren().addAll(toLabel, toList);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createButton, convertButton, quitButton);

        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(fromPane);
        borderPane.setRight(toPane);
        borderPane.setBottom(buttonBox);

        Scene currencyScene = new Scene(borderPane);

        primaryStage.setScene(currencyScene);

        /* remove when you want
        Pane pane = new Pane();
        Circle circle1 = new Circle(23, 23, 10);
        Circle circle2 = new Circle(130, 34, 10);
        circle2.setFill(Paint.valueOf("white"));
        circle2.setStrokeWidth(5);

        Line line = new Line(23, 23, 130, 34);
        line.setStrokeWidth(7);
        circle2.setOnMouseClicked(e -> System.out.println("circle"));
        line.setOnMouseClicked(e -> System.out.println("line"));

        pane.getChildren().addAll(line, circle1, circle2);


        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(300);
        */


        primaryStage.show();

    }

    /*
    @Override
    public void handle(Event event) {

        if (event.getSource() == fromList) {
            if (event.getEventType().getName().equals("MOUSE_RELEASED")) System.out.println("rel rel");
            System.out.println("from " + event.getEventType().getName());
            ObservableList<Object> input = fromList.getSelectionModel().getSelectedItems();
            for (Object o : input) {
                System.out.println(o);
            }
        } else if (event.getSource() == toList) {
            System.out.println("to");
        }

    }
    */

    private void handleListClick(Event event) {

        // check source of event *and* if something is selected, then if the selected item is the creation option. if it isn't, we've selected something.
        if (event.getSource() == fromList && fromList.getSelectionModel().getSelectedIndex() > -1) {
            if (fromList.getSelectionModel().getSelectedItem().equals(CREATION_STRING)) {
                fromSelected = false;
                if(((MouseEvent) event).getClickCount() >= 2) startCreation();
            } else {
                fromSelected = true;
            }
        } else if (event.getSource() == toList && toList.getSelectionModel().getSelectedIndex() > -1) {
            if (toList.getSelectionModel().getSelectedItem().equals(CREATION_STRING)) {
                toSelected = false;
                if(((MouseEvent) event).getClickCount() >= 2) startCreation();
            } else {
                toSelected = true;
            }
        }

        // check the bools then set the button's state
        if (fromSelected && toSelected) {
            convertButton.setDisable(false);
            if(((MouseEvent) event).getClickCount() >= 2) startConversion(); // go straight to conversion if the user doubleclicks
        } else {
            convertButton.setDisable(true);
        }

    }

    private void startConversion() {
        conversionDialog.show(getFrom(), getTo());
        //currencyList.remove(0, 3);
        //currencyList.clear();
    }

    private void startCreation() {
        Currency newCurrency = creationDialog.show();
        if (newCurrency != null && !currencyList.contains(newCurrency)) {
            currencyList.add(newCurrency);
        }
    }


    /*
    // TODO split this up
    public void select(Event event) {

        ObservableList<Object> fromSelection = fromList.getSelectionModel().getSelectedItems();
        ObservableList<Object> toSelection = toList.getSelectionModel().getSelectedItems();

        if (event.getSource() == fromList) {

            if (fromSelection.contains(CREATION_STRING)) {
                fromSelected = false;
                Currency newCurrency = creationDialog.show();
                if (!currencyList.contains(newCurrency)) currencyList.add(newCurrency);
            } else {
                fromSelected = true;
                if (toSelected && !toSelection.contains(CREATION_STRING)) { // only if valuta in both
                    conversionDialog.show(getFrom(), getTo());
                }
            }

        } else if (event.getSource() == toList) {

            if (toSelection.contains(CREATION_STRING)) {
                toSelected = false;
                Currency newCurrency = creationDialog.show();
                if (!currencyList.contains(newCurrency)) currencyList.add(newCurrency);
            } else {
                toSelected = true;
                if (fromSelected && !fromSelection.contains(CREATION_STRING)) { // only if valuta in both
                    conversionDialog.show(getFrom(), getTo());
                }
            }

        }

    }
    */

}
