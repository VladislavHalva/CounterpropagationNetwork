package Application.Controller;

import Application.Model.AppStateCarrier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LeftMenuController implements Initializable {

    private CLocator locator;

    private double LearnCoeff = 0.0;
    private int Steps = 0;

    @FXML
    Button ForwardCPButton;

    @FXML
    Button FullCPButton;

    @FXML
    TextField StepsField;

    @FXML
    Slider LearnCoeffSlider;

    @FXML
    Label LearnCoeffValue;

    @FXML
    Button InitializeButton;

    @FXML
    Button StepButton;

    @FXML
    Button StopButton;

    @FXML
    Button RunButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initLearnCoeffSlider();
        initStepsTextField();
        initInitializeButton();
    }

    private void initInitializeButton() {
        InitializeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Steps = Integer.parseInt(StepsField.textProperty().getValue());
                locator.getAppStateCarrier().startLearning();
            }
        });
    }

    private void initStepsTextField() {
        int defaultValue = 0;

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
                new IntegerStringConverter(),
                defaultValue,
                c -> {
                    return Pattern.matches("\\d{0,3}", c.getText()) ? c : null;
                });

        StepsField.setTextFormatter(formatter);
    }

    private void initLearnCoeffSlider() {
        LearnCoeffSlider.setMin(0);
        LearnCoeffSlider.setMax(1);
        LearnCoeffSlider.setValue(0.5);
        LearnCoeffSlider.setShowTickLabels(true);
        LearnCoeffSlider.setShowTickMarks(true);
        LearnCoeffSlider.setMajorTickUnit(0.5);

        LearnCoeffSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                LearnCoeffValue.setText(String.format("%.2f", newVal));
                LearnCoeff = newVal.doubleValue();
            }
        });
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }
}
