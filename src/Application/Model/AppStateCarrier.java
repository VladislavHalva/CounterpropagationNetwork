package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.AppStates;
import Application.Enums.CPType;

public class AppStateCarrier {
    private CLocator locator;

    /**
     * Holds the apps state - control point.
     */
    private AppStates State = AppStates.READY;


    private CPType ChosenCPType = null;
    private CPModel RunningCP = null;

    public AppStateCarrier(CLocator locator){
        this.locator = locator;
    }

    public void startLearning(CPType chosenCPType, int steps, double learningCoeffs, int datasetSize) {
        if(State == AppStates.READY){
            State = AppStates.LEARNING_RUNNING;
            this.ChosenCPType = chosenCPType;
            switchButtonActivityAccordingToState(AppStates.LEARNING_RUNNING);

            if(chosenCPType == CPType.FULL){
                this.RunningCP = new FullCPModel(steps, learningCoeffs, datasetSize);
            }
            else{
                this.RunningCP = new ForwardOnlyCPModel(steps, learningCoeffs, datasetSize);
            }
        }
    }

    public void learningSuccesfullyFinished(){
        if(State == AppStates.LEARNING_RUNNING){
            State = AppStates.LEARNED;
            switchButtonActivityAccordingToState(AppStates.LEARNED);

            //TODO ready to evaluation
        }
    }

    public void learningInterrupted(){
        if(State == AppStates.LEARNING_RUNNING){
            State = AppStates.READY;
            this.ChosenCPType = null;
            this.RunningCP = null;
            switchButtonActivityAccordingToState(AppStates.READY);
        }
    }

    public void switchButtonActivityAccordingToState(AppStates newState){
        locator.getLeftMenuController().enableButtonsAccordingToState(newState);
    }
}
