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
            switchButtonActivityAccordingToState(AppStates.LEARNING_RUNNING);

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
            switchButtonActivityAccordingToState(AppStates.LEARNED);

            locator.getLeftMenuController().showMessage("LEARNING FINISHED", "", null);

            //TODO ready to evaluation
        }
    }

    public void learningInterrupted(){
            AppState = AppStates.READY;
            this.ChosenCPType = CPType.FULL;
            this.RunningCP = null;
            locator.getBottomBarController().cleanStatusBar();
            locator.getCanvasPaneController().cleanCanvas();
            switchButtonActivityAccordingToState(AppStates.READY);
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

    public void switchButtonActivityAccordingToState(AppStates newState){
        locator.getLeftMenuController().enableButtonsAccordingToState(newState);
    }

    public CPModel getRunningCPModel(){
        return RunningCP;
    }
}
