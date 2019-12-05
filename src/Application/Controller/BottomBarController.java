package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BottomBarController implements Initializable {

    private CLocator locator;

    @FXML
    AnchorPane StatusBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }

    public void addMessageToStatusBar(String message){
        StatusBar.getChildren().clear();
        Label text = new Label(message);
        text.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        StatusBar.getChildren().add(text);
    }

    public void cleanStatusBar(){
        StatusBar.getChildren().clear();
    }
}
