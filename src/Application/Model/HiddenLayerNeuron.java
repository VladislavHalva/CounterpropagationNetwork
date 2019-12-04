package Application.Model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HiddenLayerNeuron extends Neuron {

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
        initWeightsVectors();
    }

    /**
     * Full ctor
     * @param firstInputVector
     * @param secondInputVector
     */
    public HiddenLayerNeuron(ArrayList<InputPoint> firstInputVector, ArrayList<InputPoint> secondInputVector){
        super();
        createConnectionsToInputs(firstInputVector, secondInputVector);
        initWeightsVectors();
    }

    /**
     *
     */
    private void initWeightsVectors() {
        for (int i = 0; i < 3; i++) {
            weightsFirst.add(0.0);
            weightsSecond.add(0.0);
        }
    }

    /**
     *
     */
    public void generateRandomWeightsFull(){
        Random rd = new Random();

        for (int i = 0; i < weightsFirst.size(); i++) {
            weightsFirst.set(i, rd.nextDouble());
        }
        for (int i = 0; i < weightsSecond.size(); i++) {
            weightsSecond.set(i, rd.nextDouble());
        }
    }

    /**
     *
     */
    public void generateRandomWeightsForward(){
        Random rd = new Random();

        for (int i = 0; i < weightsFirst.size(); i++) {
            weightsFirst.set(i, rd.nextDouble());
        }
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

    @Override
    public ArrayList<Line> getConnections(){
        var connections = new ArrayList<Line>();
        connections.addAll(firstWeightLines);
        connections.addAll(secondWeightLines);
        return connections;
    }
}
