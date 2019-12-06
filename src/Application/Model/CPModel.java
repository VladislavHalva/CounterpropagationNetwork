package Application.Model;

import Application.AdditionalClasses.DatasetMaker;
import Application.Controller.CLocator;
import Application.Enums.LearningStates;
import javafx.util.Pair;

import java.util.ArrayList;


public abstract class CPModel {
    protected CLocator locator;

    protected int Steps;
    protected double LearningCoeff;
    protected ArrayList<Pair<double[], double[]>> dataset = null;
    protected HiddenLayerNeuron victoriousHLNeuron = null;

    //used for run, and jump to phase two
    protected Boolean LearningFinished = false;
    protected Boolean LearningPhaseTwoInitialized = false;

    protected int CurrentStep = 1;
    protected LearningStates LearningState = LearningStates.INITIALIZED;

    public CPModel(int steps, double learningCoeff, int datasetSize, CLocator locator){
        this.Steps = steps;
        this.LearningCoeff = learningCoeff;
        this.locator = locator;

        createDataset(datasetSize);
    }

    public abstract void loadNetworkToCanvas();

    public void makeStep(){
        switch (LearningState){
            case INITIALIZED:
                locator.getBottomBarController().addMessageToStatusBar("Learning initialized - hidden layer neurons learning" +
                        "\n Hidden layer neurons colors represent their weights values in relevant color model.");
                LearningState = LearningStates.HIDDEN_LAYER_WEIGHTS_GENERATE;
                break;
            case HIDDEN_LAYER_WEIGHTS_GENERATE:
                locator.getBottomBarController().addMessageToStatusBar("Generating random hidden layer neuron's weights from <0,1>");
                generateHiddenLayerWeights();
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_ONE:
                locator.getBottomBarController().addMessageToStatusBar("Vector from training set attached to input");
                attachInputVectorsPhaseOne();
                LearningState = LearningStates.COMPETITION_PHASE_ONE;
                break;
            case COMPETITION_PHASE_ONE:
                locator.getBottomBarController().addMessageToStatusBar("Competition of neurons of the hidden layer" +
                        "\n Outputs of the hidden layer neurons are shown inside of them.");
                runCompetitionPhaseOne();
                LearningState = LearningStates.UPDATING_HIDDEN_LAYER_WEIGHTS;
                break;
            case UPDATING_HIDDEN_LAYER_WEIGHTS:
                locator.getBottomBarController().addMessageToStatusBar("Updating victorious hidden layer neuron's weights");
                updateHiddenLayerWeights();
                if(CurrentStep < Steps*dataset.size()) {
                    LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                    CurrentStep++;
                }else{
                    LearningState = LearningStates.PHASE_TWO_INITIALIZED;
                    CurrentStep = 1;
                }
                break;
            case PHASE_TWO_INITIALIZED:
                locator.getBottomBarController().addMessageToStatusBar("Output layer neurons learning initialized");
                LearningPhaseTwoInitialized = true;
                initializePhaseTwo();
                LearningState = LearningStates.OUTPUT_LAYER_WEIGHTS_GENERATE;
                break;
            case OUTPUT_LAYER_WEIGHTS_GENERATE:
                locator.getBottomBarController().addMessageToStatusBar("Generating random output layer neuron's weights from <0,1>");
                generateOutputLayerWeights();
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_TWO;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_TWO:
                locator.getBottomBarController().addMessageToStatusBar("Vector from training set attached to input");
                attachInputVectorsPhaseTwo();
                LearningState = LearningStates.COMPETITION_PHASE_TWO;
                break;
            case COMPETITION_PHASE_TWO:
                locator.getBottomBarController().addMessageToStatusBar("Competition of neurons of the hidden layer");
                runCompetitionPhaseTwo();
                LearningState = LearningStates.UPDATING_OUTER_LAYER_WEIGHTS;
                break;
            case UPDATING_OUTER_LAYER_WEIGHTS:
                locator.getBottomBarController().addMessageToStatusBar("Updating output layer neuron's weights "+
                        "(those leading to the hidden layer's victorious neuron)" +
                        "\n Neurons output values are shown inside them and their color represents the value in relevant color model.");
                updateOutputLayerWeights();
                if(CurrentStep < Steps*dataset.size()) {
                    LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_TWO;
                    CurrentStep++;
                }else{
                    LearningState = LearningStates.LEARNING_FINISHED;
                }
                break;
            case LEARNING_FINISHED:
                cleanAfterLastOutputLayerUpdate();
                locator.getAppStateCarrier().learningSuccesfullyFinished();
                LearningFinished = true;
                break;
        }
    }

    protected abstract void cleanAfterLastOutputLayerUpdate();

    protected abstract void updateOutputLayerWeights();

    protected abstract void runCompetitionPhaseTwo();

    protected abstract void attachInputVectorsPhaseTwo();

    protected abstract void generateOutputLayerWeights();

    protected abstract void initializePhaseTwo();

    protected abstract void updateHiddenLayerWeights();

    protected abstract void runCompetitionPhaseOne();

    protected abstract void attachInputVectorsPhaseOne();

    protected abstract void generateHiddenLayerWeights();

    public void run(){
        while(!LearningFinished){
            makeStep();
        }
    }

    public void jumpToOutputLayerLearning(){
        while(!LearningPhaseTwoInitialized){
            makeStep();
        }
    }

    private void createDataset(int size){
        dataset = DatasetMaker.createDataset(size);
    }
}

