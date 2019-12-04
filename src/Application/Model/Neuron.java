package Application.Model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class Neuron extends Circle {

    protected double Output = 0.0;
    Text OutputLabel; //TODO change when calculated sthg

    public Neuron(){
        super();
        OutputLabel = new Text(String.valueOf(Output));
    }

    public abstract ArrayList<Line> getConnections();
}
