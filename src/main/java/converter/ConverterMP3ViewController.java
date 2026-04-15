package converter;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

import static model.workWithFiles.Util.*;

public class ConverterMP3ViewController {
    private static final String DEFAULT_FILE_TEXT_SELECT_FILE = "Selected file: none";
    private static final int SUCCESS_MESSAGE_DURATION_SECONDS = 5;

    private File outputPath;
    private final PauseTransition hideSuccessMessageTimer =
            new PauseTransition(Duration.seconds(SUCCESS_MESSAGE_DURATION_SECONDS));

    @FXML
    public VBox converterMP3Page;
    @FXML
    private Label labelConvertMP3;
    @FXML
    private Button btnSelectAudioFile;
    @FXML
    private Button btnChoiceDirForSaveMP3;
    @FXML
    private Label labelSelectAudioName;
    @FXML
    private ToggleButton btnToMP3;
    @FXML
    private Button btnSubmitConvert;
    @FXML
    private Button btnReset;
    @FXML
    private Label labelSuccessConvert;

    @FXML
    public void initialize() {
        outputPath = Paths.get(System.getProperty("user.home"), "Desktop").toFile();
        setupClearMessageTimer(labelSuccessConvert, hideSuccessMessageTimer);
        labelSelectAudioName.setText(DEFAULT_FILE_TEXT_SELECT_FILE);
    }

    @FXML
    public void onSelectAudioPressed() {
        // Logic for selecting audio file
    }

    @FXML
    public void onSelectOutputDirectoryPressed() {
        // Logic for selecting output directory
    }

    @FXML
    public void onFormatMp3Pressed() {
        // Logic for selecting MP3 format
    }

    @FXML
    public void onStartConversionPressed() {
        // Logic for starting conversion
    }

    @FXML
    public void onResetPressed() {
        // Logic for resetting fields
    }
}
