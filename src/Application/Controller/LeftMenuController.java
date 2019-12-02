package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftMenuController implements Initializable {

    private CLocator locator;

    @FXML
    Button ForwardCPButton;

    @FXML
    Button FullCPButton;

    @FXML
    TextField StepsField;

    @FXML
    Slider LearnCoeffSlider;

    @FXML
    Button InitializeButton;

    @FXML
    Button StepButton;

    @FXML
    Button StopButton;

    @FXML
    Button RunButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }
}
