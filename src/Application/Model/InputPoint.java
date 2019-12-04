package Application.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class InputPoint extends Circle {

    private double Value = 0.0;
    private Text ValueLabel;

    public InputPoint(){
        super();
        ValueLabel = new Text(String.valueOf(Value));

        //TODO values handling
    }

    public void setValue(double value){
        this.Value = value;
        ValueLabel.setText(String.format("%.2f", Value));
    }

    public double getValue() {
        return Value;
    }

    public Text getValueLabel(){
        return ValueLabel;
    }
}
