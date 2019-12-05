package Application.Model;

import Application.Enums.CPType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class Neuron extends Circle {

    protected CPType cpType;
    protected double Output = 0.0;
    Text OutputLabel; //TODO change when calculated sthg

    public Neuron(CPType cpType){
        super();
        this.cpType = cpType;
        OutputLabel = new Text(String.valueOf(Output));
    }

    public void setOutput(double output) {
        Output = output;
    }

    public abstract ArrayList<Line> getConnections();
    public abstract void generateRandomWeights();
}
