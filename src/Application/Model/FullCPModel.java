package Application.Model;

import Application.Controller.CLocator;

import java.util.ArrayList;

public class FullCPModel extends CPModel{

    private ArrayList<HiddenLayerNeuron> hiddenLayerNeurons = new ArrayList<>();
    private ArrayList<OutputLayerNeuron> firstOutputLayerNeurons = new ArrayList<>();
    private ArrayList<OutputLayerNeuron> secondOutputLayerNeurons = new ArrayList<>();
    private ArrayList<InputPoint> firstInputVector = new ArrayList<>();
    private ArrayList<InputPoint> secondInputVector = new ArrayList<>();

    /**
     * Ctor
     * @param steps
     * @param learningCoeff
     */
    public FullCPModel(int steps, double learningCoeff, int datasetSize, CLocator locator) {
        super(steps, learningCoeff, datasetSize, locator);
        loadNetworkToCanvas();
    }

    @Override
    public void loadNetworkToCanvas() {
        createNeuronsAndInputPoints();
        locator.getCanvasPaneController().drawFullCPNetwork(
                hiddenLayerNeurons,
                firstInputVector,
                secondInputVector,
                firstOutputLayerNeurons,
                secondOutputLayerNeurons);
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
        //TODO first
    }

    @Override
    protected void attachInputVectorsPhaseOne() {
        for (int i = 0; i < firstInputVector.size(); i++) {
            firstInputVector.get(i).setValue(dataset.get(i).getKey()[i]);
        }
        for (int i = 0; i < secondInputVector.size(); i++) {
            secondInputVector.get(i).setValue(dataset.get(i).getValue()[i]);
        }
    }

    @Override
    protected void generateHiddenLayerWeights() {
        for (HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            hiddenLayerNeuron.generateRandomWeightsFull();
        }
        locator.getCanvasPaneController().visualizeWeightsGenerating(hiddenLayerNeurons);
    }

    private void createNeuronsAndInputPoints() {
        for (int i = 0; i < 3; i++) {
            firstInputVector.add(new InputPoint());
            secondInputVector.add(new InputPoint());
        }
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.add(new HiddenLayerNeuron(firstInputVector, secondInputVector));
        }
        for (int i = 0; i < 3; i++) {
            firstOutputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons));
            secondOutputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons));
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void jumpToOutputLayerLearning() {

    }
}
