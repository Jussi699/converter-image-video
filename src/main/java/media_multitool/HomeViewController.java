package media_multitool;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HomeViewController {
    @FXML private Button btnOpenImageCompressor;
    @FXML private VBox infoPage;
    @FXML private VBox homePage;
    @FXML private Button btnOpenImageConverter;
    @FXML private Button btnOpenVideoConverter;
    @FXML private Button btnOpenAudioConverter;
    @FXML private ControllerView mainController;

    public void setMainController(ControllerView mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onOpenImageConverterPressed() {
        if (mainController != null) {
            mainController.showConverterImagePage();
        }
    }

    @FXML
    private void onOpenVideoConverterPressed() {
        if (mainController != null) {
            mainController.showConverterVideoPage();
        }
    }

    @FXML
    private void onOpenAudioConverterPressed() {
        if (mainController != null) {
            mainController.showConverterAudioPage();
        }
    }

    @FXML
    private void onOpenImageCompressorPressed(){
        if (mainController != null) {
            mainController.showCompressorImagePage();
        }
    }
}
