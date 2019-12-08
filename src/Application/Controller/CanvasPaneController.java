package Application.Controller;

import Application.Enums.CPType;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.*;

public class CanvasPaneController implements Initializable {

    private CLocator locator;

    @FXML
    AnchorPane CanvasPane;

    Rectangle FirstInputColorPreview = new Rectangle(150,12,new Color(1,1,1,1));
    Rectangle SecondInputColorPreview = new Rectangle(150,12,new Color(1,1,1,1));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FirstInputColorPreview.setStroke(new Color(0,0,0,1));
        FirstInputColorPreview.setStrokeWidth(1);
        SecondInputColorPreview.setStroke(new Color(0,0,0,1));
        SecondInputColorPreview.setStrokeWidth(1);
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }

    public AnchorPane getCanvasPane(){
        return CanvasPane;
    }

    public void cleanCanvas(){
        CanvasPane.getChildren().clear();
    }

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
            hiddenLayerNeurons.get(i).setStroke(new Color(0,0,0,1));
            hiddenLayerNeurons.get(i).setStrokeWidth(1);

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

        //set input color preview rectangle
        FirstInputColorPreview.setX(33);
        FirstInputColorPreview.setY(35);
        SecondInputColorPreview.setX(33);
        SecondInputColorPreview.setY(437);
        CanvasPane.getChildren().add(FirstInputColorPreview);
        CanvasPane.getChildren().addAll(SecondInputColorPreview);

        Label input1Label = new Label("Input color RGB");
        input1Label.setLayoutX(37);
        input1Label.setLayoutY(32);
        CanvasPane.getChildren().add(input1Label);

        Label input2Label = new Label("Input color HSV");
        input2Label.setLayoutX(37);
        input2Label.setLayoutY(434);
        CanvasPane.getChildren().add(input2Label);

        //add output label
        Label outOneLabel = new Label("First output vector (RGB color)");
        Label outTwoLabel = new Label("Second output vector (HSV color)");
        outOneLabel.setLayoutX(705);
        outOneLabel.setLayoutY(60);
        outTwoLabel.setLayoutX(705);
        outTwoLabel.setLayoutY(400);

        CanvasPane.getChildren().add(outOneLabel);
        CanvasPane.getChildren().add(outTwoLabel);

        CanvasPane.getChildren().addAll(firstInputVector);
        CanvasPane.getChildren().addAll(secondInputVector);
        CanvasPane.getChildren().addAll(hiddenLayerNeurons);
        CanvasPane.getChildren().addAll(firstOutputLayerNeurons);
        CanvasPane.getChildren().addAll(secondOutputLayerNeurons);
    }

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
            hiddenLayerNeurons.get(i).setStroke(new Color(0,0,0,1));
            hiddenLayerNeurons.get(i).setStrokeWidth(1);

            CanvasPane.getChildren().addAll(hiddenLayerNeurons.get(i).getConnections());
        }

        //add output layer and connections
        for (int i = 0; i < 3; i++) {
            outputLayerNeurons.get(i).setCenterX(330+70*i);
            outputLayerNeurons.get(i).setCenterY(100);
            outputLayerNeurons.get(i).setRadius(20);

            CanvasPane.getChildren().addAll(outputLayerNeurons.get(i).getConnections());
        }

        //input color rectangle preview
        FirstInputColorPreview.setX(323);
        FirstInputColorPreview.setY(440);
        CanvasPane.getChildren().add(FirstInputColorPreview);

        Label input1Label = new Label("Input color RGB");
        input1Label.setLayoutX(330);
        input1Label.setLayoutY(437);
        CanvasPane.getChildren().add(input1Label);

        //add output label
        Label outLabel = new Label("Output vector (HSV color)");
        outLabel.setLayoutX(335);
        outLabel.setLayoutY(60);
        CanvasPane.getChildren().add(outLabel);

        CanvasPane.getChildren().addAll(inputVector);
        CanvasPane.getChildren().addAll(hiddenLayerNeurons);
        CanvasPane.getChildren().addAll(outputLayerNeurons);
    }

    public void highlightConnectionsOfGivenNeurons(ArrayList<? extends Neuron> neurons){
        for(Neuron neuron : neurons){
            for(Line connection : neuron.getConnections()){
                connection.setStroke(new Color(0.0, 1.0, 0.0, 1.0));
                connection.setStrokeWidth(3);
            }
        }
    }

    public void removeHighlightConnectionsOfGivenNeurons(ArrayList<? extends Neuron> neurons){
        if(neurons != null) {
            for (Neuron neuron : neurons) {
                for (Line connection : neuron.getConnections()) {
                    connection.setStroke(new Color(0.0, 0.0, 0.0, 1.0));
                    connection.setStrokeWidth(1);
                }
            }
        }
    }

    public void highlightVictoriousNeuron(HiddenLayerNeuron neuron) {
        neuron.setStroke(new Color(0,1,0,1));
        neuron.setStrokeWidth(3);
    }

    public void removeHighlightWinnerNeuron(HiddenLayerNeuron neuron){
        if(neuron != null) {
            neuron.setStroke(new Color(0,0,0,1));
            neuron.setStrokeWidth(1);
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

    public void setFirstInputColor(Double color[]){
        FirstInputColorPreview.setFill(new Color(color[0], color[1], color[2], 1));
    }

    public void setSecondInputColor(Double[] color){
        SecondInputColorPreview.setFill(Color.hsb(color[0]*360, color[1], color[2], 1));
    }

    public void showOutputNeuronsColor(ArrayList<OutputLayerNeuron> firstOutput,
                                       ArrayList<OutputLayerNeuron> secondOutput, int victoriousNeuronIndex, CPType cpType){
        Double[] firstColor = {0.0,0.0,0.0};
        Double[] secondColor = {0.0,0.0,0.0};

        if(cpType == CPType.FORWARD_ONLY){
            for (int i = 0; i < firstOutput.size(); i++) {
                firstColor[i] = firstOutput.get(i).getWeights().get(victoriousNeuronIndex);
            }
            for(int i = 0; i < firstOutput.size(); i++){
                firstOutput.get(i).setFill(Color.hsb(firstColor[0]*360, firstColor[1], firstColor[2],1));
                firstOutput.get(i).setOutput(firstColor[i]);
                firstOutput.get(i).showValue(locator);
            }
        }
        else{
            for (int i = 0; i < firstOutput.size(); i++) {
                firstColor[i] = firstOutput.get(i).getWeights().get(victoriousNeuronIndex);
            }
            for (int i = 0; i < secondOutput.size(); i++) {
                secondColor[i] = secondOutput.get(i).getWeights().get(victoriousNeuronIndex);
            }
            for (int i = 0; i < firstOutput.size(); i++){
                firstOutput.get(i).setFill(new Color(firstColor[0], firstColor[1], firstColor[2],1));
                firstOutput.get(i).setOutput(firstColor[i]);
                firstOutput.get(i).showValue(locator);
            }
            for (int i = 0; i < secondOutput.size(); i++){
                secondOutput.get(i).setFill(Color.hsb(secondColor[0]*360, secondColor[1], secondColor[2],1));
                secondOutput.get(i).setOutput(secondColor[i]);
                secondOutput.get(i).showValue(locator);
            }
        }
    }

    public void hideOutputNeuronsColor(ArrayList<OutputLayerNeuron> firstOutput, ArrayList<OutputLayerNeuron> secondOutput){
        for(OutputLayerNeuron neuron : firstOutput){
            neuron.setFill(new Color(0,0,0,1));
            neuron.hideValue(locator);
        }
        if(secondOutput != null) {
            //in case of Full CP
            for (OutputLayerNeuron neuron : secondOutput) {
                neuron.setFill(new Color(0, 0, 0, 1));
                neuron.hideValue(locator);
            }
        }
    }
}
