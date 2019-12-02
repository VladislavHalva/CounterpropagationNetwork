package Application.Model;

public class AppStateCarrier {
    private Boolean FullCPChosen = true;
    private Boolean ForwardCPChosen = false;

    //processes
    private Boolean LearningRunning = false;
    private Boolean Learned = false;

    private Boolean anyProcessRunning(){
        return LearningRunning;
    }

    public void startLearning() {
        if(!anyProcessRunning()){
            //TODO start learning
            //TODO change buttons, state, read cp type, set running, create fullCPModel,...
        }
    }
}
