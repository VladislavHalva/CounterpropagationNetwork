package Application.Controller;

import Application.Enums.AppStates;
import Application.Enums.CPType;
import Application.View.FullCPNet;
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

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class LeftMenuController implements Initializable {

    private CLocator locator;

    private double LearnCoeff = 0.5;
    private int Steps = 10;
    private int DatasetSize = 20;
    private int StepsLimit = 100;

    private CPType chosenCPType = CPType.FULL;

    @FXML
    Button ForwardCPButton;

    @FXML
    Button FullCPButton;

    @FXML
    TextField StepsField;

    @FXML
    TextField DatasetSizeField;

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
        initDatasetTextField();
        initInitializeButton();
        initStopButton();
        initCPTypeButtons();
        initLearningControlButtons();
    }

    private void initLearningControlButtons() {
        StepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                locator.getAppStateCarrier().makeLearningStep();
            }
        });

        RunButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                locator.getAppStateCarrier().runLearning();
            }
        });

        ToOutLayerLearningButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(locator.getAppStateCarrier().getRunningCPModel() != null){
                    locator.getAppStateCarrier().getRunningCPModel().jumpToOutputLayerLearning();
                }
            }
        });
    }

    private void initCPTypeButtons() {
        //first is always set Full
        FullCPButton.setDisable(true);

        ForwardCPButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ForwardCPButton.setDisable(true);
                FullCPButton.setDisable(false);
                chosenCPType = CPType.FORWARD_ONLY;
            }
        });

        FullCPButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FullCPButton.setDisable(true);
                ForwardCPButton.setDisable(false);
                chosenCPType = CPType.FULL;
            }
        });
    }

    private void initInitializeButton() {
        InitializeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Steps = Integer.parseInt(StepsField.textProperty().getValue());
                DatasetSize = Integer.parseInt(DatasetSizeField.textProperty().getValue());

                if(Steps > 0 && Steps <= StepsLimit && LearnCoeff > 0 && DatasetSize > 0){
                    locator.getAppStateCarrier().startLearning(chosenCPType, Steps, LearnCoeff, DatasetSize);
                }
                else{
                    //wrong values for learning
                    showMessage("", "Number of steps has to be between 1 and " + StepsLimit +
                            " and learning coefficient can not be zero. Dataset size must be more than zero too.",
                            new Color(1,0,0,1));
                }
            }
        });
    }

    private void initStopButton() {
        StopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                locator.getAppStateCarrier().learningInterrupted();
                chosenCPType = CPType.FULL;
            }
        });
    }

    private void initStepsTextField() {
        int defaultValue = 10;

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
                new IntegerStringConverter(),
                defaultValue,
                c -> {
                    return Pattern.matches("\\d{0,3}", c.getText()) ? c : null;
                });

        StepsField.setTextFormatter(formatter);
    }

    private void initDatasetTextField() {
        int defaultValue = 20;

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
                new IntegerStringConverter(),
                defaultValue,
                c -> {
                    return Pattern.matches("\\d{0,3}", c.getText()) ? c : null;
                });

        DatasetSizeField.setTextFormatter(formatter);
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
        StopButton.setDisable(newState != AppStates.LEARNING_RUNNING && newState != AppStates.LEARNED);
        ToOutLayerLearningButton.setDisable(newState != AppStates.LEARNING_RUNNING);
        RunRecognitionButton.setDisable(newState != AppStates.LEARNED);
        ForwardCPButton.setDisable(newState != AppStates.READY);
        FullCPButton.setDisable(true);

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
