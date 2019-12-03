package Application.Model;

import Application.Model.HiddenLayerNeuron;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OutputLayerNeuron extends Circle {

    ArrayList<Double> weights = new ArrayList<>();
    ArrayList<Line> weightLines = new ArrayList<>();
    double Output = 0;
    Text OutputLabel; //TODO change when calculated sthg

    public OutputLayerNeuron(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons){
        super();
        OutputLabel = new Text(String.valueOf(Output));
        createConnectionToHiddenLayer(hiddenLayerNeurons);

        //TODO values handling
        //TODO output values and inner value ?
    }

    private void createConnectionToHiddenLayer(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons) {
        for(HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            Line connection = new Line();

            connection.startXProperty().bind(hiddenLayerNeuron.centerXProperty());
            connection.startYProperty().bind(hiddenLayerNeuron.centerYProperty());

            connection.endXProperty().bind(this.centerXProperty());
            connection.endYProperty().bind(this.centerYProperty());

            weightLines.add(connection);
        }
    }

    public ArrayList<Line> getConnections(){
        return weightLines;
    }
}
