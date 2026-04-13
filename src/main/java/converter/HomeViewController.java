package converter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HomeViewController {
    @FXML private VBox homePage;
    @FXML private Button btnOpenConverter;
    private ControllerView mainController;

    public void setMainController(ControllerView mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onOpenConverterPressed() {
        if (mainController != null) {
            mainController.showConverterPage();
        }
    }
}
