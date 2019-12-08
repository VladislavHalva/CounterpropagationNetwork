package Application.Model;

import Application.Enums.CPType;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

public class OutputLayerNeuron extends Neuron {

    ArrayList<Double> weights = new ArrayList<>();
    ArrayList<Line> weightLines = new ArrayList<>();

    public OutputLayerNeuron(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons, CPType cpType){
        super(cpType);
        createConnectionLines(hiddenLayerNeurons);
        initWeightsVector();
    }

    /**
     * Initializes all weight to zero.
     */
    private void initWeightsVector() {
        for (int i = 0; i < 6; i++) {
            weights.add(0.0);
        }
    }

    /**
     * Generates random weights from <0,1>.
     */
    @Override
    public void generateRandomWeights(){
        Random rd = new Random();

        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, rd.nextDouble());
        }
    }

    /**
     * Creates the line, that represents the connections of input and hidden layer neurons.
     * @param hiddenLayerNeurons
     */
    private void createConnectionLines(ArrayList<HiddenLayerNeuron> hiddenLayerNeurons) {
        for(HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            Line connection = new Line();

            connection.startXProperty().bind(hiddenLayerNeuron.centerXProperty());
            connection.startYProperty().bind(hiddenLayerNeuron.centerYProperty());

            connection.endXProperty().bind(this.centerXProperty());
            connection.endYProperty().bind(this.centerYProperty());

            weightLines.add(connection);
        }
    }

    @Override
    public ArrayList<Line> getConnections(){
        return weightLines;
    }

    public ArrayList<Double> getWeights(){
        return weights;
    }
}
