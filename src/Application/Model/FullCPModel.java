package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.CPType;
import javafx.scene.shape.Line;
import javafx.util.Pair;

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
    protected void generateHiddenLayerWeights() {
        for (HiddenLayerNeuron hiddenLayerNeuron : hiddenLayerNeurons){
            hiddenLayerNeuron.generateRandomWeights();
        }
        locator.getCanvasPaneController().hightlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseOne() {
        //remove highlight after generating weights
        locator.getCanvasPaneController().removeHightlightConnectionsOfGivenNeurons(hiddenLayerNeurons);
        //remove highlight of neuron in case of learning loop
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightWeightsOfSingleNeuron(victoriousHLNeuron);
        if(victoriousHLNeuron != null){
        victoriousHLNeuron.setOutput(0);
        }
        victoriousHLNeuron = null;

        Double[] firstInputColor = {0.0,0.0,0.0};
        for (int i = 0; i < firstInputVector.size(); i++) {
            firstInputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getKey()[i]);
            firstInputColor[i] = dataset.get(CurrentStep%dataset.size()).getKey()[i];
        }

        Double[] secondInputColor = {0.0,0.0,0.0};
        for (int i = 0; i < secondInputVector.size(); i++) {
            secondInputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getValue()[i]);
            secondInputColor[i] = dataset.get(CurrentStep%dataset.size()).getValue()[i];
        }

        locator.getCanvasPaneController().setFirstInputColor(firstInputColor);
        locator.getCanvasPaneController().setSecondInputColor(secondInputColor);
        locator.getCanvasPaneController().highlightInputs(firstInputVector);
        locator.getCanvasPaneController().highlightInputs(secondInputVector);
    }

    @Override
    protected void runCompetitionPhaseOne() {
        locator.getCanvasPaneController().removeHighlightInputs(firstInputVector);
        locator.getCanvasPaneController().removeHighlightInputs(secondInputVector);

        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightVectors();
        victoriousHLNeuron = closestNeuron;
        victoriousHLNeuron.setOutput(1);
        locator.getCanvasPaneController().highlightWinnerNeuron(closestNeuron);

        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.showValue(locator);
        }
    }

    @Override
    protected void updateHiddenLayerWeights() {
        for(HiddenLayerNeuron neuron : hiddenLayerNeurons){
            neuron.hideValue(locator);
        }

        updateNeuronWeights(victoriousHLNeuron);
        locator.getCanvasPaneController().highlightWeightsOfSingleNeuron(victoriousHLNeuron);
    }

    @Override
    protected void initializePhaseTwo() {
        //remove highlight of neuron in case of the end of a learning loop
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightWeightsOfSingleNeuron(victoriousHLNeuron);
        if(victoriousHLNeuron != null){
            victoriousHLNeuron.setOutput(0);
        }
        victoriousHLNeuron = null;
    }

    @Override
    protected void generateOutputLayerWeights() {
        for(OutputLayerNeuron neuron : firstOutputLayerNeurons){
            neuron.generateRandomWeights();
        }
        for(OutputLayerNeuron neuron : secondOutputLayerNeurons){
            neuron.generateRandomWeights();
        }

        locator.getCanvasPaneController().hightlightConnectionsOfGivenNeurons(firstOutputLayerNeurons);
        locator.getCanvasPaneController().hightlightConnectionsOfGivenNeurons(secondOutputLayerNeurons);
    }

    @Override
    protected void attachInputVectorsPhaseTwo() {
        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        if(victoriousHLNeuron != null){
            victoriousHLNeuron.setOutput(0);
        }
        victoriousHLNeuron = null;
        locator.getCanvasPaneController().removeHightlightConnectionsOfGivenNeurons(firstOutputLayerNeurons);
        locator.getCanvasPaneController().removeHightlightConnectionsOfGivenNeurons(secondOutputLayerNeurons);
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());

        Double[] firstInputColor = {0.0,0.0,0.0};
        for (int i = 0; i < firstInputVector.size(); i++) {
            firstInputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getKey()[i]);
            firstInputColor[i] = dataset.get(CurrentStep%dataset.size()).getKey()[i];
        }

        Double[] secondInputColor = {0.0,0.0,0.0};
        for (int i = 0; i < secondInputVector.size(); i++) {
            secondInputVector.get(i).setValue(dataset.get(CurrentStep%dataset.size()).getValue()[i]);
            secondInputColor[i] = dataset.get(CurrentStep%dataset.size()).getValue()[i];
        }

        locator.getCanvasPaneController().setFirstInputColor(firstInputColor);
        locator.getCanvasPaneController().setSecondInputColor(secondInputColor);
        locator.getCanvasPaneController().highlightInputs(firstInputVector);
        locator.getCanvasPaneController().highlightInputs(secondInputVector);
    }

    @Override
    protected void runCompetitionPhaseTwo() {
        locator.getCanvasPaneController().removeHighlightInputs(firstInputVector);
        locator.getCanvasPaneController().removeHighlightInputs(secondInputVector);

        HiddenLayerNeuron closestNeuron = getHLNeuronWithClosestWeightVectors();
        victoriousHLNeuron = closestNeuron;
        victoriousHLNeuron.setOutput(1);
        locator.getCanvasPaneController().highlightWinnerNeuron(closestNeuron);
    }

    @Override
    protected void updateOutputLayerWeights() {
        var winnerNeuronIndex = getWinnerNeuronIndex();

        for (int outputNIndex = 0; outputNIndex < firstOutputLayerNeurons.size(); outputNIndex++) {
            //v(k) = v(k-1) + mu*(d - v(k-1))
            firstOutputLayerNeurons.get(outputNIndex).weights.set(winnerNeuronIndex,
                    firstOutputLayerNeurons.get(outputNIndex).weights.get(winnerNeuronIndex) +
                            LearningCoeff*(dataset.get(CurrentStep%dataset.size()).getKey()[outputNIndex] -
                                    firstOutputLayerNeurons.get(outputNIndex).weights.get(winnerNeuronIndex)));
        }
        for (int outputNIndex = 0; outputNIndex < secondOutputLayerNeurons.size(); outputNIndex++) {
            //v(k) = v(k-1) + mu*(d - v(k-1))
            secondOutputLayerNeurons.get(outputNIndex).weights.set(winnerNeuronIndex,
                    secondOutputLayerNeurons.get(outputNIndex).weights.get(winnerNeuronIndex) +
                            LearningCoeff*(dataset.get(CurrentStep%dataset.size()).getValue()[outputNIndex] -
                                    secondOutputLayerNeurons.get(outputNIndex).weights.get(winnerNeuronIndex)));
        }

        locator.getCanvasPaneController().highlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }

    @Override
    protected void cleanAfterLastOutputLayerUpdate() {
        locator.getBottomBarController().cleanStatusBar();

        locator.getCanvasPaneController().removeHighlightWinnerNeuron(victoriousHLNeuron);
        locator.getCanvasPaneController().removeHighlightOutputsConnectionsToWinner(getConnectionsFromOutputToWinnerNeuron());
    }




    private void createNeuronsAndInputPoints() {
        for (int i = 0; i < 3; i++) {
            firstInputVector.add(new InputPoint());
            secondInputVector.add(new InputPoint());
        }
        for (int i = 0; i < 6; i++) {
            hiddenLayerNeurons.add(new HiddenLayerNeuron(firstInputVector, secondInputVector, CPType.FULL));
        }
        for (int i = 0; i < 3; i++) {
            firstOutputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons, CPType.FULL));
            secondOutputLayerNeurons.add(new OutputLayerNeuron(hiddenLayerNeurons, CPType.FULL));
        }
    }

    private HiddenLayerNeuron getHLNeuronWithClosestWeightVectors() {
        Pair<Integer, Double> closestNeuron = new Pair<>(0,Double.MAX_VALUE);

        for(int i = 0; i < hiddenLayerNeurons.size(); i++){
            double distance = 0.0;
            distance += Math.sqrt(Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(0) - firstInputVector.get(0).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(1) - firstInputVector.get(1).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsFirst.get(2) - firstInputVector.get(2).getValue(),2)
            );
            distance += Math.sqrt(Math.pow(hiddenLayerNeurons.get(i).weightsSecond.get(0) - secondInputVector.get(0).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsSecond.get(1) - secondInputVector.get(1).getValue(),2) +
                    Math.pow(hiddenLayerNeurons.get(i).weightsSecond.get(2) - secondInputVector.get(2).getValue(),2)
            );

            if(distance < closestNeuron.getValue()){
                closestNeuron = new Pair<>(i, distance);
            }
        }
        return hiddenLayerNeurons.get(closestNeuron.getKey());
    }

    private void updateNeuronWeights(HiddenLayerNeuron neuron) {
        System.out.println("Input1: " + firstInputVector.get(0).getValue() + ", " + firstInputVector.get(1).getValue() + ", " + firstInputVector.get(2).getValue());
        System.out.println("Input2: " + secondInputVector.get(0).getValue() + ", " + secondInputVector.get(1).getValue() + ", " + secondInputVector.get(2).getValue());

        System.out.println("Old1: " + neuron.weightsFirst.get(0) + " " + neuron.weightsFirst.get(1) + " " + neuron.weightsFirst.get(2));
        System.out.println("Old2: " + neuron.weightsSecond.get(0) + " " + neuron.weightsSecond.get(1) + " " + neuron.weightsSecond.get(2));

        for (int i = 0; i < neuron.weightsFirst.size(); i++) {
            neuron.weightsFirst.set(i, neuron.weightsFirst.get(i) +
                    LearningCoeff*(firstInputVector.get(i).getValue() - neuron.weightsFirst.get(i)));

            System.out.println("Diff first " + i + ": " + (neuron.weightsFirst.get(i) +
                    LearningCoeff*(firstInputVector.get(i).getValue() - neuron.weightsFirst.get(i))));
        }

        for (int i = 0; i < neuron.weightsSecond.size(); i++) {
            neuron.weightsSecond.set(i, neuron.weightsSecond.get(i) +
                    LearningCoeff*(secondInputVector.get(i).getValue() - neuron.weightsSecond.get(i)));

            System.out.println("Diff second " + i + ": " + (neuron.weightsSecond.get(i) +
                    LearningCoeff*(secondInputVector.get(i).getValue() - neuron.weightsSecond.get(i))));
        }
        neuron.updateNeuronColorAccordingToWeights();

        System.out.println("New1: " + neuron.weightsFirst.get(0) + " " + neuron.weightsFirst.get(1) + " " + neuron.weightsFirst.get(2));
        System.out.println("New2: " + neuron.weightsSecond.get(0) + " " + neuron.weightsSecond.get(1) + " " + neuron.weightsSecond.get(2) + "\n");
    }


    private ArrayList<Line> getConnectionsFromOutputToWinnerNeuron(){
        int winnerNeuron = 0;
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            if(hiddenLayerNeurons.get(i) == victoriousHLNeuron){
                winnerNeuron = i;
            }
        }

        ArrayList<Line> connections = new ArrayList<>();
        for(OutputLayerNeuron neuron : firstOutputLayerNeurons){
            connections.add(neuron.weightLines.get(winnerNeuron));
        }
        for(OutputLayerNeuron neuron : secondOutputLayerNeurons){
            connections.add(neuron.weightLines.get(winnerNeuron));
        }

        return connections;
    }

    private int getWinnerNeuronIndex() {
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            if(hiddenLayerNeurons.get(i) == victoriousHLNeuron){
                return i;
            }
        }
        return 0;
    }
}
