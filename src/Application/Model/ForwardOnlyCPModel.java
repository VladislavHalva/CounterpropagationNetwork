package Application.Model;

import Application.Controller.CLocator;

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

    @Override
    public void makeStep() {

    }

    @Override
    public void run() {

    }

    @Override
    public void jumpToOutputLayerLearning() {

    }

    @Override
    public void stopLearning() {

    }
}
