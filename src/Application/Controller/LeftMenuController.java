package Application.Controller;

import Application.Enums.AppStates;
import Application.Enums.CPType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class LeftMenuController implements Initializable {

    private CLocator locator;

    private double LearnCoeff = 0.0;
    private int Steps = 0;
    private int StepsLimit = 100;

    private CPType chosenCPType = CPType.FULL; //TODO change if Forward only will be implemented

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

    @FXML
    Button ToOutLayerLearningButton;

    @FXML
    Button RunRecognitionButton;

    @FXML
    VBox MessageBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initLearnCoeffSlider();
        initStepsTextField();
        initInitializeButton();
        initStopButton();
    }

    private void initInitializeButton() {
        InitializeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Steps = Integer.parseInt(StepsField.textProperty().getValue());

                if(Steps > 1 && Steps <= StepsLimit && LearnCoeff > 0){
                    //TODO read dataset size
                    locator.getAppStateCarrier().startLearning(chosenCPType, Steps, LearnCoeff, 20);
                }
                else{
                    //wrong values for learning
                    showMessage("", "Number of steps has to be between 1 and " + StepsLimit +
                            " and learning coefficient can not be zero.", new Color(1,0,0,1));
                }
            }
        });
    }

    private void initStopButton() {
        StopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                locator.getAppStateCarrier().learningInterrupted();
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


    public void enableButtonsAccordingToState(AppStates newState) {
        InitializeButton.setDisable(newState != AppStates.READY);
        StepButton.setDisable(newState != AppStates.LEARNING_RUNNING);
        RunButton.setDisable(newState != AppStates.LEARNING_RUNNING);
        StopButton.setDisable(newState != AppStates.LEARNING_RUNNING);
        ToOutLayerLearningButton.setDisable(newState != AppStates.LEARNING_RUNNING);
        RunRecognitionButton.setDisable(newState != AppStates.LEARNED);

        //TODO add other controls
    }

    public void showMessage(String title, String text, Paint textColor){
        //create message from arguments
        var titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-weight: bold;");

        var textLabel = new Label(text);
        textLabel.setWrapText(true);

        if(textColor != null){
            try{
                textLabel.setTextFill(textColor);
            }
            catch (Exception e){}
        }

        //add message to left menu
        MessageBox.getChildren().clear();
        MessageBox.getChildren().addAll(titleLabel, textLabel);

        //remove message after 10 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> MessageBox.getChildren().clear());
            }
        }, 10000);
    }

    public void clearMessageBox(){
        MessageBox.getChildren().clear();
    }
}
