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
    protected HiddenLayerNeuron winnerHLNeuron = null;

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
        //System.out.println("FROM: " + LearningState.toString());
        switch (LearningState){
            case INITIALIZED:
                LearningState = LearningStates.HIDDEN_LAYER_WEIGHTS_GENERATE;
                break;
            case HIDDEN_LAYER_WEIGHTS_GENERATE:
                generateHiddenLayerWeights();
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_ONE:
                attachInputVectorsPhaseOne();
                LearningState = LearningStates.COMPETITION_PHASE_ONE;
                break;
            case COMPETITION_PHASE_ONE:
                runCompetitionPhaseOne();
                LearningState = LearningStates.UPDATING_HIDDEN_LAYER_WEIGHTS;
                break;
            case UPDATING_HIDDEN_LAYER_WEIGHTS:
                updateHiddenLayerWeights();
                if(CurrentStep < Steps) {
                    LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_ONE;
                    CurrentStep++;
                }else{
                    LearningState = LearningStates.PHASE_TWO_INITIALIZED;
                    CurrentStep = 1;
                }
                break;
            case PHASE_TWO_INITIALIZED:
                initializePhaseTwo();
                LearningState = LearningStates.OUTPUT_LAYER_WEIGHTS_GENERATE;
                break;
            case OUTPUT_LAYER_WEIGHTS_GENERATE:
                generateOutputLayerWeights();
                LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_TWO;
                break;
            case INPUT_VECTOR_ATTACH_PHASE_TWO:
                attachInputVectorsPhaseTwo();
                LearningState = LearningStates.COMPETITION_PHASE_TWO;
                break;
            case COMPETITION_PHASE_TWO:
                runCompetitionPhaseTwo();
                LearningState = LearningStates.UPDATING_OUTER_LAYER_WEIGHTS;
                break;
            case UPDATING_OUTER_LAYER_WEIGHTS:
                updateOutputLayerWeights();
                if(CurrentStep < Steps) {
                    LearningState = LearningStates.INPUT_VECTOR_ATTACH_PHASE_TWO;
                    CurrentStep++;
                }else{
                    cleanAfterLastOutputLayerUpdate();
                    locator.getAppStateCarrier().learningSuccesfullyFinished();
                }
                break;
        }
        //System.out.println("TO: " + LearningState.toString());
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

    public abstract void run();

    public abstract void jumpToOutputLayerLearning();

    private void createDataset(int size){
        dataset = DatasetMaker.createDataset(size);
    }
}

