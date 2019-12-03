package Application.Model;

import Application.AdditionalClasses.DatasetMaker;
import Application.Controller.CLocator;
import javafx.util.Pair;

import java.util.ArrayList;


public abstract class CPModel {
    protected CLocator locator;

    protected int Steps;
    protected double LearningCoeff;
    protected ArrayList<Pair<double[], double[]>> dataset = null;

    public CPModel(int steps, double learningCoeff, int datasetSize, CLocator locator){
        this.Steps = steps;
        this.LearningCoeff = learningCoeff;
        this.locator = locator;

        createDataset(datasetSize);
    }

    public abstract void loadNetworkToCanvas();

    public abstract void makeStep();

    public abstract void run();

    public abstract void jumpToOutputLayerLearning();

    public abstract void stopLearning();

    private void createDataset(int size){
        dataset = DatasetMaker.createDataset(size);
    }
}

