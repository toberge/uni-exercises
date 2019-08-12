import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class ConversionDialog {

    // TODO remove the need for buttons
    private Button convertButton;
    private Button cancelButton;

    private Label conversionLabel;
    private TextField amountField;

    private final String NO_INPUT = "No amount to convert";
    private final String INVALID_INPUT = "Only positive decimal numbers accepted";

    private Stage window;

    public void show(Currency from, Currency to) {

        window = new Stage();
        window.setTitle("Conversion");
        window.initModality(Modality.APPLICATION_MODAL);

        //window.setWidth(300);
        //window.setHeight(150);

        //Label topLabel = new Label("Specify amount to convert");

        convertButton = new Button("Convert");
        convertButton.setOnAction((e) -> convert(from, to));
        convertButton.setDefaultButton(true); // ENTER

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction((e) -> window.close());

        conversionLabel = new Label("Please type an amount to convert");

        Label amountLabel = new Label("Amount:");
        amountField = new TextField();
        //amountField.setOnAction((e) -> convert(from, to));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setMinWidth(250);
        //gridPane.setMinHeight(60);
        //gridPane.add(topLabel, 0, 0, 2, 1);
        gridPane.add(amountLabel, 0, 0);
        gridPane.add(amountField, 1, 0);
        gridPane.add(conversionLabel, 0, 1, 2, 1);

        HBox buttonBox = new HBox(convertButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        //VBox bottomBox = new VBox(conversionLabel, buttonBox);
        //bottomBox.setAlignment(Pos.CENTER);

        gridPane.add(buttonBox, 0, 3, 2, 1);

        /*
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setBottom(buttonBox);
        */

        Scene scene = new Scene(gridPane);
        window.setScene(scene);
        window.show();

    }

    private void convert(Currency from, Currency to) { // assuming they aren't NULL

        String amountString = amountField.getText();

        if (!Validator.isText(amountString)) {

            conversionLabel.setText(NO_INPUT); // no need to try

        } else if (!Validator.isPositiveDouble(amountString)) {

            conversionLabel.setText(INVALID_INPUT);

        } else {

            double amount = Double.parseDouble(amountString);

            String converted = String.format("%.2f", to.convert(amount, from));
            amountString = String.format("%.2f", amount);

            conversionLabel.setText(amountString + " " + from.getName() + " is " + converted + " " + to.getName());

        }

    }

    /*
    private static void convert(Currency from, Currency to) {

        if (from == null && to == null) {

            System.err.println("THERE HAS BEEN A SERIOUS ERROR");

        } else if (amountField.getText().trim().equals("")) {

            conversionLabel.setText(NO_INPUT); // no need to try

        } else {

            double amount = 0.0;
            boolean ok = true;

            try {
                amount = Double.parseDouble(amountField.getText());
                if (amount < 0) ok = false;

            } catch (NumberFormatException e) {
                System.out.println("Not a Double");
                ok = false;
            }

            if (ok) {

                String converted = String.format("%.2f", to.convert(amount, from));
                String amountString = String.format("%.2f", amount);
                conversionLabel.setText(amountString + " " + from.getName() + " is " + converted + " " + to.getName());

            } else {

                conversionLabel.setText(INVALID_INPUT);

            }

        }

    }
    */

}
