package Application.Model;

public class FullCPModel extends CPModel{

    /**
     * Ctor
     * @param steps
     * @param learningCoeff
     */
    public FullCPModel(int steps, double learningCoeff, int datasetSize) {
        super(steps, learningCoeff, datasetSize);
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