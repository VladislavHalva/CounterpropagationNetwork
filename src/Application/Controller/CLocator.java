package Application.Controller;

import Application.Model.AppStateCarrier;

public class CLocator {

    private MainWindowController mainWindowController;
    private LeftMenuController leftMenuController;
    private CanvasPaneController canvasPaneController;
    private BottomBarController bottomBarController;

    private AppStateCarrier appStateCarrier;

    public CLocator(MainWindowController mainWindowController,
                     LeftMenuController leftMenuController,
                     CanvasPaneController canvasPaneController,
                     BottomBarController bottomBarController){

        this.mainWindowController = mainWindowController;
        this.leftMenuController = leftMenuController;
        this.canvasPaneController = canvasPaneController;
        this.bottomBarController = bottomBarController;

        this.appStateCarrier = new AppStateCarrier();
    }

    public LeftMenuController getLeftMenuController() {
        return leftMenuController;
    }

    public BottomBarController getBottomBarController() {
        return bottomBarController;
    }

    public CanvasPaneController getCanvasPaneController() {
        return canvasPaneController;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public AppStateCarrier getAppStateCarrier() {
        return appStateCarrier;
    }
}
