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
        createConnectionToHiddenLayer(hiddenLayerNeurons);
        initWeightsVector();
    }

    /**
     *
     */
    private void initWeightsVector() {
        for (int i = 0; i < 3; i++) {
            weights.add(0.0);
        }
    }

    /**
     *
     */
    @Override
    public void generateRandomWeights(){
        Random rd = new Random();

        for(Double weight : weights){
            weight = rd.nextDouble();
        }
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

    @Override
    public ArrayList<Line> getConnections(){
        return weightLines;
    }

    public Line getConnectionToNthHiddenNeuron(int neuronIndex){
        return weightLines.get(neuronIndex);
    }

}
