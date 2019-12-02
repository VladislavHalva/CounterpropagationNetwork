package Application.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class BottomBarController implements Initializable {

    private CLocator locator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void storeCLocatorReference(CLocator locator){
        this.locator = locator;
    }
}
