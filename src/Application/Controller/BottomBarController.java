package Application.Controller;

import Application.Enums.LearningStates;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BottomBarController implements Initializable {

    private CLocator locator;

    public String initMessage ="Learning initialized - hidden layer neurons learning" +
            "\n Hidden layer neurons colors represent their weights values in relevant color model.";
    public String weightsGenHiddenMessage = "Generating random hidden layer neuron's weights from <0,1>";
    public String vectorAttachPhaseOneMessage = "Vector from training set attached to input";
    public String competitionPhaseOneMessage = "Competition of neurons of the hidden layer" +
            "\n Outputs of the hidden layer neurons are shown inside of them.";
    public String updateWeightsPhaseOneMessage = "Updating victorious hidden layer neuron's weights";
    public String phaseTwoInitMessage = "Output layer neurons learning initialized";
    public String weightsGenOutMessage = "Generating random output layer neuron's weights from <0,1>";
    public String vectorAttachPhaseTwoMessage = "Vector from training set attached to input";
    public String competitionPhaseTwoMessage = "Competition of neurons of the hidden layer";
    public String updateWeightsPhaseTwoMessage = "Updating output layer neuron's weights "+
            "(those leading to the hidden layer's victorious neuron)" +
            "\n Neurons output values are shown inside them and their color represents the value in relevant color model.";

    @FXML
    AnchorPane StatusBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }

    public void addMessageToStatusBar(String message){
        StatusBar.getChildren().clear();
        Label text = new Label(message);
        text.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        StatusBar.getChildren().add(text);
    }

    public void cleanStatusBar(){
        StatusBar.getChildren().clear();
    }
}
