package Application.Controller;

import Application.Enums.CPType;
import Application.Model.HiddenLayerNeuron;
import Application.Model.InputPoint;
import Application.Model.OutputLayerNeuron;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CanvasPaneController implements Initializable {

    private CLocator locator;

    @FXML
    AnchorPane CanvasPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

            secondInputVector.get(i).setCenterX(40+70*i);
            secondInputVector.get(i).setCenterY(400);
            secondInputVector.get(i).setRadius(8);

            //TODO add input labels to pane
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

            //TODO add input labels to pane
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

    public void cleanCanvas(){
        CanvasPane.getChildren().clear();
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }
}
