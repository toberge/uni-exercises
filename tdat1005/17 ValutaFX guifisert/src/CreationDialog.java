import com.sun.istack.internal.Nullable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

class CreationDialog {

    private Stage window;

    private TextField nameField;
    private TextField unitField;
    private TextField factorField;

    private Label feedbackLabel;

    private Currency createdCurrency = null;

    private static String NO_INPUT = "No input in one or more of the boxes";
    private static String INVALID_INPUT = "A currency requires proper numbers";
    private static String WEIRD_ERROR = "Could not create currency (!)";

    public @Nullable Currency show() {

        window = new Stage();
        window.setTitle("Currency Incubator");
        window.initModality(Modality.APPLICATION_MODAL);

        // labels and text fields
        Label nameLabel = new Label("Name of currency:");
        Label unitLabel = new Label("NOK to compare against:");
        Label factorLabel = new Label("Corresponding amount:");

        feedbackLabel = new Label("Please type proper values in the boxes");

        nameField = new TextField();
        //nameField.setOnAction(e -> create());
        unitField = new TextField();
        //unitField.setOnAction(e -> create());
        factorField = new TextField();
        //factorField.setOnAction(e -> create());


        // adding to Pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(unitLabel, 0, 1);
        gridPane.add(unitField, 1, 1);
        gridPane.add(factorLabel, 0, 2);
        gridPane.add(factorField, 1, 2);
        gridPane.add(feedbackLabel, 0, 3, 2,1);

        // the rest of the window
        Button createButton = new Button("Add");
        createButton.setOnAction(e -> create());
        createButton.setDefaultButton(true);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(createButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane);
        window.setScene(scene);

        window.setMinWidth(borderPane.getWidth());
        window.setMinWidth(borderPane.getWidth());


        window.showAndWait();

        return createdCurrency; // might be NULL

    }

    private void create() {

        String name = nameField.getText();
        String factorText = factorField.getText();
        String unitText = unitField.getText();

        if (!Validator.isText(name) || !Validator.isText(factorText) || !Validator.isText(unitText)) {

            feedbackLabel.setText(NO_INPUT);

        } else if (!Validator.isPositiveDouble(factorText) || !Validator.isPositiveInteger(unitText)) {

            feedbackLabel.setText(INVALID_INPUT);

        } else {

            double factor = Double.parseDouble(factorText);
            int unit = Integer.parseInt(unitText);

            try {
                createdCurrency = new Currency(name, factor, unit);
                window.close();
            } catch (IllegalArgumentException e) {
                System.err.println("error creating currency, WHY?");
                feedbackLabel.setText(WEIRD_ERROR);

            }

        }

    }

    /*
    private static void create() {

        String name = nameLabel.getText();
        String factorText = factorLabel.getText();
        String unitText = unitLabel.getText();

        if (name.trim().equals("") || factorText.trim().equals("") || unitText.trim().equals("")) {

            System.out.println("No input in one of the boxes");

        } else {

            double factor = 0.0;
            int unit = 0;
            boolean ok = false;

            try {

                factor = Double.parseDouble(factorText);
                unit = Integer.parseInt(unitText);

                if (factor < 0.0 || unit < 0) ok = false;

            } catch (NumberFormatException e) {
                System.out.println("one of dem not be number"); // TODO bug where this happens even with valid numbers YOU FOOL YOU TOOK INPUT FROM LABELS OMG
                ok = false;
            }

            if (ok) {

                try {
                    createdCurrency = new Currency(name, factor, unit);
                } catch (IllegalArgumentException e) {
                    System.out.println("error creating currency");
                }


            } else {

                System.out.println("uhh error already reported?");

            }

        }

    }
    */

}
