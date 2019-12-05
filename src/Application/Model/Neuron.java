package Application.Model;

import Application.Controller.CLocator;
import Application.Enums.CPType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class Neuron extends Circle {

    protected CPType cpType;
    protected double Output = 0.0;
    Text OutputLabel;

    public Neuron(CPType cpType){
        super();
        this.cpType = cpType;
        OutputLabel = new Text(String.format("%.0f", Output));

        OutputLabel.xProperty().bind(this.centerXProperty().subtract(6));
        OutputLabel.yProperty().bind(this.centerYProperty().add(5));
    }

    public void setOutput(double output) {
        Output = output;
        OutputLabel.setText(String.format("%.2f", Output));
    }

    public void showValue(CLocator locator){
        locator.getCanvasPaneController().getCanvasPane().getChildren().add(OutputLabel);
    }

    public void hideValue(CLocator locator){
        locator.getCanvasPaneController().getCanvasPane().getChildren().remove(OutputLabel);
    }

    public abstract ArrayList<Line> getConnections();
    public abstract void generateRandomWeights();
}
