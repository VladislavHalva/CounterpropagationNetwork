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
import java.util.*;

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
    public void hightlightConnectionsOfGivenNeurons(ArrayList<? extends Neuron> neurons){
        for(Neuron neuron : neurons){
            for(Line connection : neuron.getConnections()){
                connection.setStroke(new Color(0.0, 1.0, 0.0, 1.0));
                connection.setStrokeWidth(3);
            }
        }
    }

    public void removeHightlightConnectionsOfGivenNeurons(ArrayList<? extends Neuron> neurons){
        if(neurons != null) {
            for (Neuron neuron : neurons) {
                for (Line connection : neuron.getConnections()) {
                    connection.setStroke(new Color(0.0, 0.0, 0.0, 1.0));
                    connection.setStrokeWidth(1);
                }
            }
        }
    }

    public void cleanCanvas(){
        CanvasPane.getChildren().clear();
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }

    public void highlightWinnerNeuron(HiddenLayerNeuron neuron) {
        neuron.setStroke(new Color(0,1,0,1));
        neuron.setStrokeWidth(3);
    }

    public void removeHighlightWinnerNeuron(HiddenLayerNeuron neuron){
        if(neuron != null) {
            neuron.setStroke(new Color(0,0,0,1));
            neuron.setStrokeWidth(0);
        }
        neuron = null;
    }

    public void highlightWeightsOfSingleNeuron(HiddenLayerNeuron winnerHLNeuron) {
        for(Line connection : winnerHLNeuron.getConnections()){
            connection.setStroke(new Color(0.0, 1.0, 0.0, 1.0));
            connection.setStrokeWidth(3);
        }
    }

    public void removeHighlightWeightsOfSingleNeuron(HiddenLayerNeuron winnerHLNeuron) {
        if(winnerHLNeuron != null) {
            for (Line connection : winnerHLNeuron.getConnections()) {
                connection.setStroke(new Color(0.0, 0.0, 0.0, 1.0));
                connection.setStrokeWidth(1);
            }
        }
    }

    public void highlightInputs(ArrayList<InputPoint> inputVector) {
        for(InputPoint inputPoint : inputVector){
            inputPoint.setFill(new Color(0,1,0,1));
            inputPoint.getValueLabel().setFill(new Color(0,1,0,1));
        }
    }

    public void removeHighlightInputs(ArrayList<InputPoint> inputVector) {
        for(InputPoint inputPoint : inputVector){
            inputPoint.setFill(new Color(0,0,0,1));
            inputPoint.getValueLabel().setFill(new Color(0,0,0,1));
        }
    }

    public void highlightOutputsConnectionsToWinner(ArrayList<Line> connections) {
        for(Line connection : connections){
            connection.setStroke(new Color(0,1,0,1));
            connection.setStrokeWidth(3);
        }
    }

    public void removeHighlightOutputsConnectionsToWinner(ArrayList<Line> connections) {
        for(Line connection : connections){
            connection.setStroke(new Color(0,0,0,1));
            connection.setStrokeWidth(1);
        }
    }
}
