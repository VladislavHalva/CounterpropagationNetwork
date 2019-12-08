package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.CPType;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;

public class ForwardOnlyCPModel extends CPModel {

    private ArrayList<InputPoint> inputVector = new ArrayList<>();
    private ArrayList<HiddenLayerNeuron> hiddenLayerNeurons =  new ArrayList<>();
    private ArrayList<OutputLayerNeuron> outputLayerNeurons = new ArrayList<>();

    ForwardOnlyCPModel(int steps, double learningCoeff, int datasetSize, CLocator locator) {
        super(steps, learningCoeff, datasetSize, locator);
        loadNetworkToCanvas();
    }

    @Override
    protected void generateHiddenLayerWeights() {
        for(HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            hiddenLayerNeuron.generateRandomWeights();
        }
        locator.getCanvasPaneController().highlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseOne() {
        //clean after previous steps
        locator.getCanvasPaneController().removeHighlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
        cleanAfterUpdateHiddenLayerWeights();

        //attach the input vector
        Double[] inputColor = {0.0,0.0,0.0};
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getKey()[i]);
            inputColor[i] = dataset.get(CurrentStep%dataset.size()).getKey()[i];
        }

        //highlightInputs
        locator.getCanvasPaneController().setFirstInputColor(inputColor);
        locator.getCanvasPaneController().highlightInputs(inputVector);
    }

    @Override
    protected void runCompetitionPhaseOne() {
        //clean after previous step
        locator.getCanvasPaneController().removeHighlightInputs(inputVector);

        //actual competition
        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightsVector();
        victoriousHLNeuron = closestNeuron;
        victoriousHLNeuron.setOutput(1.0);

        //show neurons outputs
        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.showValue(locator);
        }
        //highlight winner
        locator.getCanvasPaneController().highlightVictoriousNeuron(closestNeuron);
    }

    @Override
    protected void updateHiddenLayerWeights() {
        //clean after previous step
        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.hideValue(locator);
        }

        updateNeuronWeights(victoriousHLNeuron);

        //highlights updated weights
        locator.getCanvasPaneController().highlightWeightsOfSingleNeuron(victoriousHLNeuron);
    }

    @Override
    protected void initializePhaseTwo() {
        cleanAfterUpdateHiddenLayerWeights();
    }

    @Override
    protected void generateOutputLayerWeights() {
        for(OutputLayerNeuron neuron : outputLayerNeurons){
            neuron.generateRandomWeights();
        }

        //show updated weights
        locator.getCanvasPaneController().highlightConnectionsOfGivenNeurons(outputLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseTwo() {
        cleanAfterOutputLayerUpdate();

        //attach the input vector
        Double[] inputColor = {0.0,0.0,0.0};
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getKey()[i]);
            inputColor[i] = dataset.get(CurrentStep%dataset.size()).getKey()[i];
        }

        //show input values
        locator.getCanvasPaneController().setFirstInputColor(inputColor);
        locator.getCanvasPaneController().highlightInputs(inputVector);
    }

    @Override
    protected void runCompetitionPhaseTwo() {
        //clean after previous step
        locator.getCanvasPaneController().removeHighlightInputs(inputVector);

        //actual competition
        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightsVector();
        victoriousHLNeuron = closestNeuron;
        victoriousHLNeuron.setOutput(1);

        //show victorious neuron
        locator.getCanvasPaneController().highlightVictoriousNeuron(closestNeuron);
    }

    @Override
    protected void updateOutputLayerWeights() {
        int victoriousNeuronIndex = getVictoriousNeuronIndex();

        for (int outputNIndex = 0; outputNIndex < outputLayerNeurons.size(); outputNIndex++) {
            //v(k) = v(k-1) + mu*(d - v(k-1))
            outputLayerNeurons.get(outputNIndex).weights.set(victoriousNeuronIndex,
                    outputLayerNeurons.get(outputNIndex).weights.get(victoriousNeuronIndex) +
                    LearningCoeff*(dataset.get(CurrentStep%dataset.size()).getValue()[outputNIndex] -
                            outputLayerNeurons.get(outputNIndex).weights.get(victoriousNeuronIndex)));
        }

        //highlight output layer neurons outputs
        locator.getCanvasPaneController().showOutputNeuronsColor(outputLayerNeurons, null, victoriousNeuronIndex, CPType.FORWARD_ONLY);
        locator.getCanvasPaneController().highlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }

    private void createNeuronsAndInputPoints() {
        for (int i = 0; i < 3; i++) {
            inputVector.add(new InputPoint());
        }
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.add(new HiddenLayerNeuron(inputVector));
        }
        for (int i = 0; i < 3; i++) {
            outputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons, CPType.FORWARD_ONLY));
        }
    }

    private HiddenLayerNeuron getHLNeuronWithClosestWeightsVector() {
        Pair<Integer, Double> closestNeuron = new Pair<>(0,Double.MAX_VALUE);

        for(int i = 0; i < hiddenLayerNeurons.size(); i++){
            double distance = 0.0;
            //d = sqrt((w1-i1)^2+(w2-i2)^2+(w3-i2)^2)
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
        for (int i = 0; i < neuron.weightsFirst.size(); i++) {
            //v_r(k) = v_r(k-1) + mu*(d - v_r(k-1))
            neuron.weightsFirst.set(i, neuron.weightsFirst.get(i) +
                    LearningCoeff*(inputVector.get(i).getValue() - neuron.weightsFirst.get(i)));
        }
        neuron.updateNeuronColorAccordingToWeights();
    }

    @Override
    protected void cleanAfterLastOutputLayerUpdate() {
        locator.getBottomBarController().cleanStatusBar();

        locator.getCanvasPaneController().hideOutputNeuronsColor(outputLayerNeurons, null);
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }

    private void cleanAfterOutputLayerUpdate(){
        locator.getCanvasPaneController().hideOutputNeuronsColor(outputLayerNeurons, null);
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
        if(victoriousHLNeuron != null){
            victoriousHLNeuron.setOutput(0);
        }
        victoriousHLNeuron = null;
        locator.getCanvasPaneController().removeHighlightConnectionsOfGivenNeurons(outputLayerNeurons);
    }

    private void cleanAfterPossiblePreviousRecognition() {
        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.setOutput(0);
        }
        if(victoriousHLNeuron != null){
            for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
                neuron.hideValue(locator);
            }
            locator.getCanvasPaneController().hideOutputNeuronsColor(outputLayerNeurons, null);
            locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
            locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
            victoriousHLNeuron = null;
        }
    }

    private void cleanAfterUpdateHiddenLayerWeights(){
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightWeightsOfSingleNeuron(victoriousHLNeuron);
        if(victoriousHLNeuron != null){
            victoriousHLNeuron.setOutput(0);
        }
        victoriousHLNeuron = null;
    }

    private ArrayList<Line> getConnectionsFromOutputToWinnerNeuron(){
        int winnerNeuron = 0;
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            if(hiddenLayerNeurons.get(i) == victoriousHLNeuron){
                winnerNeuron = i;
            }
        }

        ArrayList<Line> connections = new ArrayList<>();
        for(OutputLayerNeuron neuron : outputLayerNeurons){
            connections.add(neuron.weightLines.get(winnerNeuron));
        }

        return connections;
    }

    private int getVictoriousNeuronIndex() {
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            if(hiddenLayerNeurons.get(i) == victoriousHLNeuron){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void runRecognition(Double[] vector1, Double[] vector2) {
        cleanAfterPossiblePreviousRecognition();

        //attach the input vector
        for (int i = 0; i < inputVector.size(); i++) {
            inputVector.get(i).setValue(vector1[i]);
        }
        locator.getCanvasPaneController().setFirstInputColor(vector1);

        //run competition
        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightsVector();
        victoriousHLNeuron = closestNeuron;
        victoriousHLNeuron.setOutput(1);
        locator.getCanvasPaneController().highlightVictoriousNeuron(closestNeuron);
        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.showValue(locator);
        }

        //show result
        locator.getCanvasPaneController().showOutputNeuronsColor(outputLayerNeurons, null, getVictoriousNeuronIndex(), CPType.FORWARD_ONLY);
        locator.getCanvasPaneController().highlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());

        locator.getAppStateCarrier().recognitionFinished();
    }

    @Override
    public void loadNetworkToCanvas() {
        createNeuronsAndInputPoints();
        locator.getCanvasPaneController().drawForwardCPNetwork(
                hiddenLayerNeurons,
                outputLayerNeurons,
                inputVector);
    }
}
