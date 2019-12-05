package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.CPType;
import javafx.scene.shape.Line;
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
    protected void generateHiddenLayerWeights() {
        for(HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            hiddenLayerNeuron.generateRandomWeights();
        }
        locator.getCanvasPaneController().hightlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseOne() {
        //remove highlight after generating weights
        locator.getCanvasPaneController().removeHightlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
        //remove highlight of neuron in case of learning loop
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(winnerHLNeuron);
        locator.getCanvasPaneController().removeHighlightWeightsOfSingleNeuron(winnerHLNeuron);
        if(winnerHLNeuron != null){
            winnerHLNeuron.setOutput(0);
        }
        winnerHLNeuron = null;

        //attach the input vector
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(dataset.get(CurrentStep).getKey()[i]);
        }

        locator.getCanvasPaneController().highlightInputs(inputVector);
    }

    @Override
    protected void runCompetitionPhaseOne() {
        //remove inputs highlight
        locator.getCanvasPaneController().removeHighlightInputs(inputVector);

        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightsVector();
        winnerHLNeuron = closestNeuron;
        winnerHLNeuron.setOutput(1);
        locator.getCanvasPaneController().highlightWinnerNeuron(closestNeuron);
    }

    @Override
    protected void updateHiddenLayerWeights() {
        updateNeuronWeights(winnerHLNeuron);
        locator.getCanvasPaneController().highlightWeightsOfSingleNeuron(winnerHLNeuron);
    }

    @Override
    protected void initializePhaseTwo() {
        //remove highlight of neuron in case of the end of a learning loop
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(winnerHLNeuron);
        locator.getCanvasPaneController().removeHighlightWeightsOfSingleNeuron(winnerHLNeuron);
        if(winnerHLNeuron != null){
            winnerHLNeuron.setOutput(0);
        }
        winnerHLNeuron = null;

        locator.getLeftMenuController().showMessage("phase two", "", null);
    }

    @Override
    protected void generateOutputLayerWeights() {
        for(OutputLayerNeuron neuron : outputLayerNeurons){
            neuron.generateRandomWeights();
        }
        locator.getCanvasPaneController().hightlightConnectionsOfGivenNeurons(outputLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseTwo() {
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(winnerHLNeuron);
        if(winnerHLNeuron != null){
            winnerHLNeuron.setOutput(0);
        }
        winnerHLNeuron = null;
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
        locator.getCanvasPaneController().removeHightlightConnectionsOfGivenNeurons(outputLayerNeurons);

        //attach the input vector
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(dataset.get(CurrentStep).getKey()[i]);
        }

        locator.getCanvasPaneController().highlightInputs(inputVector);
    }

    @Override
    protected void runCompetitionPhaseTwo() {
        //remove inputs highlight
        locator.getCanvasPaneController().removeHighlightInputs(inputVector);

        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightsVector();
        winnerHLNeuron = closestNeuron;
        winnerHLNeuron.setOutput(1);
        locator.getCanvasPaneController().highlightWinnerNeuron(closestNeuron);
    }

    @Override
    protected void updateOutputLayerWeights() {
        locator.getCanvasPaneController().highlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }

    @Override
    protected void cleanAfterLastOutputLayerUpdate() {
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(winnerHLNeuron);
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }



    private void createNeuronsAndInputPoints() {
        for (int i = 0; i < 3; i++) {
            inputVector.add(new InputPoint());
        }
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.add(new HiddenLayerNeuron(inputVector, CPType.FORWARD_ONLY));
        }
        for (int i = 0; i < 3; i++) {
            outputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons, CPType.FORWARD_ONLY));
        }
    }

    private HiddenLayerNeuron getHLNeuronWithClosestWeightsVector() {
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

    private void updateNeuronWeights(HiddenLayerNeuron neuron) {
        System.out.println("Input: " + inputVector.get(0).getValue() + ", " + inputVector.get(1).getValue() + ", " + inputVector.get(2).getValue());
        System.out.println("Old: " + neuron.weightsFirst.get(0) + " " + neuron.weightsFirst.get(1) + " " + neuron.weightsFirst.get(2));

        for (int i = 0; i < neuron.weightsFirst.size(); i++) {
            neuron.weightsFirst.set(i, neuron.weightsFirst.get(i) +
                    LearningCoeff*(inputVector.get(i).getValue() - neuron.weightsFirst.get(i)));

            System.out.println("Diff " + i + ": " + (neuron.weightsFirst.get(i) +
                    LearningCoeff*(inputVector.get(i).getValue() - neuron.weightsFirst.get(i))));
        }
        neuron.updateNeuronColorAccordingToWeights();

        System.out.println("New: " + neuron.weightsFirst.get(0) + " " + neuron.weightsFirst.get(1) + " " + neuron.weightsFirst.get(2) + "\n");
    }

    private ArrayList<Line> getConnectionsFromOutputToWinnerNeuron(){
        int winnerNeuron = 0;
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            if(hiddenLayerNeurons.get(i) == winnerHLNeuron){
                winnerNeuron = i;
            }
        }

        ArrayList<Line> connections = new ArrayList<>();
        for(OutputLayerNeuron neuron : outputLayerNeurons){
            connections.add(neuron.weightLines.get(winnerNeuron));
        }

        return connections;
    }
}
