package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private CLocator locator;

    @FXML
    LeftMenuController leftMenuController;

    @FXML
    BottomBarController bottomBarController;

    @FXML
    CanvasPaneController canvasPaneController;

    @FXML
    AnchorPane MainWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createAndInjectLocator();
    }

    private void createAndInjectLocator()
    {
        //create new locator instance and inject it to other controllers
        this.locator = new CLocator(this, leftMenuController, canvasPaneController, bottomBarController);

        this.leftMenuController.storeCLocatorReference(locator);
        this.bottomBarController.storeCLocatorReference(locator);
        this.canvasPaneController.storeCLocatorReference(locator);

    }
}
