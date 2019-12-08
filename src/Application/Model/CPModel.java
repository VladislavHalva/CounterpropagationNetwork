package Application.Model;

import Application.AdditionalClasses.DatasetGenerator;
import Application.Controller.CLocator;
import Application.Enums.LearningStates;
import javafx.util.Pair;

import java.util.ArrayList;


public abstract class CPModel {
    CLocator locator;

    //Learning cycles
    private int Steps;
    int CurrentStep = 1;
    double LearningCoeff;

    //dataset - loaded in ctor, according to dataset size
    ArrayList<Pair<double[], double[]>> dataset = null;

    //temporarily stores ref. to a victorious neuron between learning steps
    HiddenLayerNeuron victoriousHLNeuron = null;

    //Learning States managing variables
    private Boolean LearningFinished = false;
    private Boolean LearningPhaseTwoInitialized = false;
    private LearningStates LearningState = LearningStates.INITIALIZED;


    /**
     * Randomly generates dataset of given size and stores required vars in attributes.
     * @param steps
     * @param learningCoeff
     * @param datasetSize
     * @param locator
     */
    CPModel(int steps, double learningCoeff, int datasetSize, CLocator locator){
        this.Steps = steps;
        this.LearningCoeff = learningCoeff;
        this.locator = locator;

        createDataset(datasetSize);
    }

    /**
     * Called when Step button is hit. According to current learning state executes relevant operation.
     */
    void makeStep(){
        switch (LearningState){
            case INITIALIZED:
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().initMessage);
                LearningState = LearningStates.HIDDEN_LAYER_WEIGHTS_GENERATE;
                break;
            case HIDDEN_LAYER_WEIGHTS_GENERATE:
                generateHiddenLayerWeights();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().weightsGenHiddenMessage);
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_ONE:
                attachInputVectorsPhaseOne();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().vectorAttachPhaseOneMessage);
                LearningState = LearningStates.COMPETITION_PHASE_ONE;
                break;
            case COMPETITION_PHASE_ONE:
                runCompetitionPhaseOne();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().competitionPhaseOneMessage);
                LearningState = LearningStates.UPDATING_HIDDEN_LAYER_WEIGHTS;
                break;
            case UPDATING_HIDDEN_LAYER_WEIGHTS:
                updateHiddenLayerWeights();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().updateWeightsPhaseOneMessage);
                if(CurrentStep < Steps*dataset.size()) {
                    LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                    CurrentStep++;
                }else{
                    LearningState = LearningStates.PHASE_TWO_INITIALIZED;
                    CurrentStep = 1;
                }
                break;
            case PHASE_TWO_INITIALIZED:
                initializePhaseTwo();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().phaseTwoInitMessage);
                LearningPhaseTwoInitialized = true;
                LearningState = LearningStates.OUTPUT_LAYER_WEIGHTS_GENERATE;
                break;
            case OUTPUT_LAYER_WEIGHTS_GENERATE:
                generateOutputLayerWeights();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().weightsGenOutMessage);
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_TWO;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_TWO:
                attachInputVectorsPhaseTwo();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().vectorAttachPhaseTwoMessage);
                LearningState = LearningStates.COMPETITION_PHASE_TWO;
                break;
            case COMPETITION_PHASE_TWO:
                runCompetitionPhaseTwo();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().competitionPhaseTwoMessage);
                LearningState = LearningStates.UPDATING_OUTER_LAYER_WEIGHTS;
                break;
            case UPDATING_OUTER_LAYER_WEIGHTS:
                updateOutputLayerWeights();
                locator.getBottomBarController().addMessageToStatusBar(locator.getBottomBarController().updateWeightsPhaseTwoMessage);
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

    /**
     * Called when run button is hit. Calls makeStep while learning is finished.
     */
    void run(){
        while(!LearningFinished){
            makeStep();
        }
    }

    /**
     * Called when jump to output layer learning button is hit. Calls makeStep while
     * output layer learning initialized state is reached.
     */
    void jumpToOutputLayerLearning(){
        while(!LearningPhaseTwoInitialized){
            makeStep();
        }
    }

    /**
     * Generates dataset of random values (color pair in RGB and HSV model).
     * @param size
     */
    private void createDataset(int size){
        dataset = DatasetGenerator.createDataset(size);
    }

    public abstract void loadNetworkToCanvas();

    public abstract void runRecognition(Double[] vector1, Double[] vector2);

    protected abstract void cleanAfterLastOutputLayerUpdate();

    /**
     * Following methods are called from makeStep and represent the learning process steps.
     */

    protected abstract void generateHiddenLayerWeights();

    protected abstract void attachInputVectorsPhaseOne();

    protected abstract void runCompetitionPhaseOne();

    protected abstract void updateHiddenLayerWeights();

    protected abstract void initializePhaseTwo();

    protected abstract void generateOutputLayerWeights();

    protected abstract void attachInputVectorsPhaseTwo();

    protected abstract void runCompetitionPhaseTwo();

    protected abstract void updateOutputLayerWeights();
}

