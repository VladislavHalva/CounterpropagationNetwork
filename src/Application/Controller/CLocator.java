package Application.Controller;

public class CLocator {

    private MainWindowController mainWindowController;
    private LeftMenuController leftMenuController;
    private CanvasPaneController canvasPaneController;
    private BottomBarController bottomBarController;

    public CLocator(MainWindowController mainWindowController,
                     LeftMenuController leftMenuController,
                     CanvasPaneController canvasPaneController,
                     BottomBarController bottomBarController){

        this.mainWindowController = mainWindowController;
        this.leftMenuController = leftMenuController;
        this.canvasPaneController = canvasPaneController;
        this.bottomBarController = bottomBarController;
    }
}
