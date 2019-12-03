package Application.Model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class HiddenLayerNeuron extends Circle {

    ArrayList<Double> weightsFirst = new ArrayList<>();
    ArrayList<Double> weightsSecond = new ArrayList<>();
    ArrayList<Line> firstWeightLines = new ArrayList<>();
    ArrayList<Line> secondWeightLines = new ArrayList<>();

    /**
     * Forward only ctor
     * @param firstInputVector
     */
    public HiddenLayerNeuron(ArrayList<InputPoint> firstInputVector){
        super();
        createConnectionToInput(firstInputVector);

        //TODO values handling
        //TODO output values and inner value ?
    }

    /**
     * Full ctor
     * @param firstInputVector
     * @param secondInputVector
     */
    public HiddenLayerNeuron(ArrayList<InputPoint> firstInputVector, ArrayList<InputPoint> secondInputVector){
        super();
        createConnectionsToInputs(firstInputVector, secondInputVector);
    }

    /**
     * Forward only connections
     * @param firstInputVector
     */
    private void createConnectionToInput(ArrayList<InputPoint> firstInputVector) {
        createConnectionLines(firstInputVector, firstWeightLines);
    }

    /**
     * Full connections
     * @param firstInputVector
     * @param secondInputVector
     */
    private void createConnectionsToInputs(ArrayList<InputPoint> firstInputVector, ArrayList<InputPoint> secondInputVector) {
        createConnectionLines(firstInputVector, firstWeightLines);
        createConnectionLines(secondInputVector, secondWeightLines);
    }

    private void createConnectionLines(ArrayList<InputPoint> inputVector, List<Line> weightLines) {
        for(InputPoint firstInput : inputVector){
            Line connection = new Line();
            connection.startXProperty().bind(firstInput.centerXProperty());
            connection.startYProperty().bind(firstInput.centerYProperty());

            connection.endXProperty().bind(this.centerXProperty());
            connection.endYProperty().bind(this.centerYProperty());

            firstWeightLines.add(connection);
        }
    }

    public ArrayList<Line> getConnections(){
        var connections = new ArrayList<Line>();
        connections.addAll(firstWeightLines);
        connections.addAll(secondWeightLines);
        return connections;
    }
}
