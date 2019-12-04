package Application.Model;

import Application.Controller.CLocator;
import javafx.util.Pair;

import java.util.ArrayList;

public class ForwardOnlyCPModel extends CPModel {

    private ArrayList<HiddenLayerNeuron> hiddenLayerNeurons =  new ArrayList<>();
    private ArrayList<OutputLayerNeuron> outputLayerNeurons = new ArrayList<>();
    private ArrayList<InputPoint> inputVector = new ArrayList<>();



    /**
     * Ctor
     * @param steps
     * @param learningCoeff
     */
    public ForwardOnlyCPModel(int steps, double learningCoeff, int datasetSize, CLocator locator) {
        super(steps, learningCoeff, datasetSize, locator);
        loadNetworkToCanvas();
    }

    @Override
    public void loadNetworkToCanvas() {
        createNeuronsAndInputPoints();
        locator.getCanvasPaneController().drawForwardCPNetwork(
                hiddenLayerNeurons,
                outputLayerNeurons,
                inputVector);
    }

    @Override
    public void run() {

    }

    @Override
    public void jumpToOutputLayerLearning() {

    }

    @Override
    protected void updateOutputLayerWeights() {

    }

    @Override
    protected void runCompetitionPhaseTwo() {

    }

    @Override
    protected void attachInputVectorsPhaseTwo() {

    }

    @Override
    protected void generateOutputLayerWeights() {

    }

    @Override
    protected void initializePhaseTwo() {

    }

    @Override
    protected void updateHiddenLayerWeights() {

    }

    @Override
    protected void runCompetitionPhaseOne() {
        HiddenLayerNeuron closestNeuron = getHiddenLayerNeuronWithClosestWeightsVector();
        updateNeuronWeights(closestNeuron);
        locator.getCanvasPaneController().highlightWinnerNeuron(closestNeuron);
    }

    @Override
    protected void attachInputVectorsPhaseOne() {
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(dataset.get(i).getKey()[i]);
        }
    }

    @Override
    protected void generateHiddenLayerWeights() {
        for(HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            hiddenLayerNeuron.generateRandomWeightsForward();
        }
        for (HiddenLayerNeuron n : hiddenLayerNeurons){
            System.out.println(n.weightsFirst.get(0) + " " + n.weightsFirst.get(1) + " " + n.weightsFirst.get(2));
        }
        locator.getCanvasPaneController().visualizeWeightsGenerating(hiddenLayerNeurons);
    }

    private void createNeuronsAndInputPoints() {
        for (int i = 0; i < 3; i++) {
            inputVector.add(new InputPoint());
        }
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.add(new HiddenLayerNeuron(inputVector));
        }
        for (int i = 0; i < 3; i++) {
            outputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons));
        }
    }

    private HiddenLayerNeuron getHiddenLayerNeuronWithClosestWeightsVector() {
        Pair<Integer, Double> closestNeuron = new Pair<>(0,Double.MAX_VALUE);

        for(int i = 0; i < hiddenLayerNeurons.size(); i++){
            double distance = 0.0;
            distance = Math.sqrt(Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(0) - inputVector.get(0).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(1) - inputVector.get(1).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(2) - inputVector.get(2).getValue(),2)
            );

            if(distance < closestNeuron.getValue()){
                closestNeuron = new Pair<>(i, distance);
            }
        }
        return hiddenLayerNeurons.get(closestNeuron.getKey());
    }

    private void updateNeuronWeights(HiddenLayerNeuron closestNeuron) {
        for (int i = 0; i < closestNeuron.weightsFirst.size(); i++) {
            closestNeuron.weightsFirst.set(i, closestNeuron.weightsFirst.get(i) +
                    LearningCoeff*(inputVector.get(i).getValue() - closestNeuron.weightsFirst.get(i)));
        }
    }
}
