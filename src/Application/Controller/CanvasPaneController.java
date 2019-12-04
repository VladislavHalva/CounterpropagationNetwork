package Application.Controller;

import Application.Model.HiddenLayerNeuron;
import Application.Model.InputPoint;
import Application.Model.Neuron;
import Application.Model.OutputLayerNeuron;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class CanvasPaneController implements Initializable {

    private CLocator locator;

    @FXML
    AnchorPane CanvasPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    /**
     *
     * @param hiddenLayerNeurons
     * @param firstInputVector
     * @param secondInputVector
     * @param firstOutputLayerNeurons
     * @param secondOutputLayerNeurons
     */
    public void drawFullCPNetwork(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons, ArrayList<InputPoint> firstInputVector,
                                  ArrayList<InputPoint> secondInputVector, ArrayList<OutputLayerNeuron> firstOutputLayerNeurons,
                                  ArrayList<OutputLayerNeuron> secondOutputLayerNeurons) {
        //clean first
        CanvasPane.getChildren().clear();

        //add input points
        for (int i = 0; i < 3; i++) {
            firstInputVector.get(i).setCenterX(40+70*i);
            firstInputVector.get(i).setCenterY(80);
            firstInputVector.get(i).setRadius(8);

            //add value label
            firstInputVector.get(i).getValueLabel().setX(33+70*i);
            firstInputVector.get(i).getValueLabel().setY(65);
            firstInputVector.get(i).getValueLabel().setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #b5b0ff");
            CanvasPane.getChildren().addAll(firstInputVector.get(i).getValueLabel());

            secondInputVector.get(i).setCenterX(40+70*i);
            secondInputVector.get(i).setCenterY(400);
            secondInputVector.get(i).setRadius(8);

            //add value label
            secondInputVector.get(i).getValueLabel().setX(33+70*i);
            secondInputVector.get(i).getValueLabel().setY(427);
            secondInputVector.get(i).getValueLabel().setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #b5b0ff");
            CanvasPane.getChildren().addAll(secondInputVector.get(i).getValueLabel());
        }

        //add hidden layer and connections
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.get(i).setCenterX(80+i*90);
            hiddenLayerNeurons.get(i).setCenterY(240);
            hiddenLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(hiddenLayerNeurons.get(i).getConnections());
        }


        //add output layer and connections
        for (int i = 0; i < 3; i++) {
            firstOutputLayerNeurons.get(i).setCenterX(720+70*i);
            firstOutputLayerNeurons.get(i).setCenterY(100);
            firstOutputLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(firstOutputLayerNeurons.get(i).getConnections());

            secondOutputLayerNeurons.get(i).setCenterX(720+70*i);
            secondOutputLayerNeurons.get(i).setCenterY(380);
            secondOutputLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(secondOutputLayerNeurons.get(i).getConnections());
        }

        CanvasPane.getChildren().addAll(firstInputVector);
        CanvasPane.getChildren().addAll(secondInputVector);
        CanvasPane.getChildren().addAll(hiddenLayerNeurons);
        CanvasPane.getChildren().addAll(firstOutputLayerNeurons);
        CanvasPane.getChildren().addAll(secondOutputLayerNeurons);
    }

    /**
     *
     * @param hiddenLayerNeurons
     * @param outputLayerNeurons
     * @param inputVector
     */
    public void drawForwardCPNetwork(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons,
                                     ArrayList<OutputLayerNeuron> outputLayerNeurons,
                                     ArrayList<InputPoint> inputVector){
        //clear pane first
        CanvasPane.getChildren().clear();

        //add input points
        for (int i = 0; i < 3; i++) {
            inputVector.get(i).setCenterX(330+70*i);
            inputVector.get(i).setCenterY(400);
            inputVector.get(i).setRadius(8);

            //add value label
            inputVector.get(i).getValueLabel().setX(323+70*i);
            inputVector.get(i).getValueLabel().setY(430);
            inputVector.get(i).getValueLabel().setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #b5b0ff");
            CanvasPane.getChildren().addAll(inputVector.get(i).getValueLabel());
        }

        //add hidden layer and connections
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.get(i).setCenterX(180+i*90);
            hiddenLayerNeurons.get(i).setCenterY(240);
            hiddenLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(hiddenLayerNeurons.get(i).getConnections());
        }

        //add output layer and connections
        for (int i = 0; i < 3; i++) {
            outputLayerNeurons.get(i).setCenterX(330+70*i);
            outputLayerNeurons.get(i).setCenterY(100);
            outputLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(outputLayerNeurons.get(i).getConnections());
        }

        CanvasPane.getChildren().addAll(inputVector);
        CanvasPane.getChildren().addAll(hiddenLayerNeurons);
        CanvasPane.getChildren().addAll(outputLayerNeurons);
    }

    /**
     *
     * @param neurons
     */
    public void visualizeWeightsGenerating(ArrayList<HiddenLayerNeuron> neurons){
        Neuron neuron = neurons.get(0);

        //highlight first neuron
        ArrayList<Line> connections = neuron.getConnections();
        for(Line connection : connections) {
            connection.setStroke(new Color(0.0, 1.0, 0.0, 1.0));
            connection.setStrokeWidth(3);
        }

        //highlight one neuron each second
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
            private int i = 0;

            @Override
            public void handle(ActionEvent actionEvent) {
                //remove highlight from i neuron
                Neuron neuron1 = neurons.get(i);
                ArrayList<Line> connections1 = neuron1.getConnections();
                for(Line connection : connections1) {
                    connection.setStroke(new Color(0.0, 0.0, 0.0, 1.0));
                    connection.setStrokeWidth(1);
                }

                //add highlight to i + 1 neuron
                if((i+1) < neurons.size()) {
                    Neuron neuron2 = neurons.get(i+1);
                    ArrayList<Line> connections2 = neuron2.getConnections();
                    for (Line connection : connections2) {
                        connection.setStroke(new Color(0.0, 1.0, 0.0, 1.0));
                        connection.setStrokeWidth(3);
                    }
                }
                i++;
            }
        }));
        tl.setCycleCount(neurons.size());
        tl.play();
    }

    public void cleanCanvas(){
        CanvasPane.getChildren().clear();
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }

    public void highlightWinnerNeuron(HiddenLayerNeuron closestNeuron) {
        closestNeuron.setFill(new Color(0,1,0,1));
        //TODO write weights

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    closestNeuron.setFill(new Color(0,0,0,1));
                });
            }
        }, 4000);
    }
}
