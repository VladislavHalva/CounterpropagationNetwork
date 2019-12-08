package Application.Model;

import Application.Enums.CPType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HiddenLayerNeuron extends Neuron {

    ArrayList<Double> weightsFirst = new ArrayList<>();
    //unused in case of Forward-only counterpropagation
    ArrayList<Double> weightsSecond = new ArrayList<>();

    //lines, that represent the connections of neurons and input
    ArrayList<Line> firstWeightLines = new ArrayList<>();
    ArrayList<Line> secondWeightLines = new ArrayList<>();

    /**
     * Constructor for Forward-only counterpropagation. Initialized weights to zero.
     * @param firstInputVector
     */
    public HiddenLayerNeuron(ArrayList<InputPoint> firstInputVector){
        super(CPType.FORWARD_ONLY);
        createConnectionToInput(firstInputVector);
        initWeightsVectors();
    }

    /**
     * Constructor for Full counterpropagation. Initializes weights to zero.
     * @param firstInputVector
     * @param secondInputVector
     */
    public HiddenLayerNeuron(ArrayList<InputPoint> firstInputVector, ArrayList<InputPoint> secondInputVector){
        super(CPType.FULL);
        createConnectionsToInputs(firstInputVector, secondInputVector);
        initWeightsVectors();
    }

    /**
     * Initializes all weights to zero.
     */
    private void initWeightsVectors() {
        for (int i = 0; i < 3; i++) {
            weightsFirst.add(0.0);
            weightsSecond.add(0.0);
        }
    }

    /**
     * Generates random weights from <0,1>. Sets the colors of neurons according to colors.
     */
    @Override
    public void generateRandomWeights(){
        Random rd = new Random();

        for (int i = 0; i < weightsFirst.size(); i++) {
            weightsFirst.set(i, rd.nextDouble());
        }
        for (int i = 0; i < weightsSecond.size(); i++) {
            weightsSecond.set(i, rd.nextDouble());
        }

        if(cpType == CPType.FULL){
            this.setStyle("-fx-fill:linear-gradient( from 100.0% 100.0% to 100.0% 0.0%, rgb("+
                    (weightsSecond.get(0)*255)+","
                    +(weightsSecond.get(1)*255)+","
                    +(weightsSecond.get(2)*255)+
                    ") 0.5,rgb("
                    +(weightsFirst.get(0)*255)+","
                    +(weightsFirst.get(1)*255)+","
                    +(weightsFirst.get(2)*255)+") 0.5);");
        }else{
            //FORWARD ONLY
            this.setFill(new Color(weightsFirst.get(0), weightsFirst.get(1), weightsFirst.get(2),1));
        }
    }

    /**
     * Creates connections from neurons to inputs - Forward-only CP
     * @param firstInputVector
     */
    private void createConnectionToInput(ArrayList<InputPoint> firstInputVector) {
        createConnectionLines(firstInputVector, firstWeightLines);
    }

    /**
     * Creates connections from neurons to inputs - Full CP
     * @param firstInputVector
     * @param secondInputVector
     */
    private void createConnectionsToInputs(ArrayList<InputPoint> firstInputVector, ArrayList<InputPoint> secondInputVector) {
        createConnectionLines(firstInputVector, firstWeightLines);
        createConnectionLines(secondInputVector, secondWeightLines);
    }


    /**
     * Creates the line, that represents the connections of input and hidden layer neurons.
     * @param inputVector
     * @param weightLines
     */
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
        ArrayList<Line> connections = new ArrayList<>();
        connections.addAll(firstWeightLines);
        connections.addAll(secondWeightLines);
        return connections;
    }

    /**
     * Changes the neurons color, when its weights values change.
     */
    public void updateNeuronColorAccordingToWeights(){
        if(cpType == CPType.FULL){
            this.setStyle("-fx-fill:linear-gradient( from 100.0% 100.0% to 100.0% 0.0%, rgb("+
                    (weightsSecond.get(0)*255)+","
                    +(weightsSecond.get(1)*255)+","
                    +(weightsSecond.get(2)*255)+
                    ") 0.5,hsb("
                    +Math.round((weightsFirst.get(0)*360))+","
                    +(weightsFirst.get(1)*100)+"%,"
                    +(weightsFirst.get(2)*100)+"%) 0.5);");
        }else{
            //FORWARD ONLY
            this.setFill(new Color(weightsFirst.get(0), weightsFirst.get(1), weightsFirst.get(2),1));
        }
    };


    /**
     * Sets the output property.
     * @param output
     */
    @Override
    public void setOutput(double output){
            Output = output;
            OutputLabel.setText(String.format("%.0f", Output));
    }
}
