package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.AppStates;
import Application.Enums.CPType;

public class AppStateCarrier {
    private CLocator locator;

    /**
     * Holds the apps state - control point.
     */
    private AppStates AppState = AppStates.READY;

    private CPType ChosenCPType = null;
    private CPModel RunningCP = null;

    public AppStateCarrier(CLocator locator){
        this.locator = locator;
    }

    public void startLearning(CPType chosenCPType, int steps, double learningCoeffs, int datasetSize) {
        if(AppState == AppStates.READY){
            AppState = AppStates.LEARNING_RUNNING;
            this.ChosenCPType = chosenCPType;
            switchButtonActivityAccordingToState(AppStates.LEARNING_RUNNING, chosenCPType);

            if(chosenCPType == CPType.FULL){
                this.RunningCP = new FullCPModel(steps, learningCoeffs, datasetSize, locator);
            }
            else{
                this.RunningCP = new ForwardOnlyCPModel(steps, learningCoeffs, datasetSize, locator);
            }
        }
    }

    public void learningSuccesfullyFinished(){
        if(AppState == AppStates.LEARNING_RUNNING){
            AppState = AppStates.LEARNED;
            switchButtonActivityAccordingToState(AppStates.LEARNED, ChosenCPType);

            locator.getLeftMenuController().showMessage("LEARNING FINISHED", "", null);
            locator.getBottomBarController().addMessageToStatusBar("First input vector is RGB color representation. Second vector " +
                    "is HSV color representation." +
                    "\n All values are from <0,1> range. In case of hue in HSV, the required value is hue/360°." +
                    "\n To view a vector color preview, hit ENTER when values are set.");
        }
    }

    public void learningInterrupted(){
            AppState = AppStates.READY;
            this.ChosenCPType = CPType.FULL;
            this.RunningCP = null;
            locator.getBottomBarController().cleanStatusBar();
            locator.getCanvasPaneController().cleanCanvas();
            switchButtonActivityAccordingToState(AppStates.READY, ChosenCPType);
    }

    public void makeLearningStep(){
        if(AppState == AppStates.LEARNING_RUNNING) {
            RunningCP.makeStep();
        }
    }

    public void runLearning() {
        if (AppState == AppStates.LEARNING_RUNNING) {
            RunningCP.run();
        }
    }

    public void switchButtonActivityAccordingToState(AppStates newState, CPType cpType){
        locator.getLeftMenuController().enableButtonsAccordingToState(newState, cpType);
    }

    public void jumpToOutputLayerLearning(){
        if(RunningCP != null && AppState == AppStates.LEARNING_RUNNING){
            RunningCP.jumpToOutputLayerLearning();
        }
    }

    public void runRecognition(Double[] vector1, Double[] vector2){
        if(AppState == AppStates.LEARNED){
            AppState = AppStates.RECOGNITION_RUNNING;
            RunningCP.runRecognition(vector1, vector2);
        }
    }

    public void recognitionFinished() {
        if(AppState == AppStates.RECOGNITION_RUNNING){
            AppState = AppStates.LEARNED;
        }
    }

    public CPModel getRunningCPModel(){
        return RunningCP;
    }

}
