package converter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControllerView {

    @FXML private StackPane rightPane;
    @FXML private VBox leftPane;
    @FXML private VBox homeView;
    @FXML private VBox converterImageView;
    @FXML private HomeViewController homeViewController;
    @FXML private ConverterImageViewController converterImageViewController;
    @FXML private Button navHomeButton;
    @FXML private Button navConverterButton;

    @FXML
    public void initialize() {
        if (homeViewController != null) {
            homeViewController.setMainController(this);
        }

        showHomePage();
    }

    @FXML
    public void showHomePage() {
        setActivePage(homeView, navHomeButton);
    }

    @FXML
    public void showConverterPage() {
        setActivePage(converterImageView, navConverterButton);
    }

    private void setActivePage(VBox pageToShow, Button activeButton) {
        homeView.setVisible(pageToShow == homeView);
        homeView.setManaged(pageToShow == homeView);
        converterImageView.setVisible(pageToShow == converterImageView);
        converterImageView.setManaged(pageToShow == converterImageView);

        navHomeButton.setStyle(getNavButtonStyle(activeButton == navHomeButton));
        navConverterButton.setStyle(getNavButtonStyle(activeButton == navConverterButton));
    }

    private String getNavButtonStyle(boolean active) {
        if (active) {
            return "-fx-background-color: #32CD32; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 8;";
        }
        return "-fx-background-color: #323232; -fx-text-fill: white; -fx-background-radius: 8;";
    }
}
