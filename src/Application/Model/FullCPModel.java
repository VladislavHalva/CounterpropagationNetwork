package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.CPType;
import javafx.scene.shape.Circle;

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
