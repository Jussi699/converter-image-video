package converter;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.logger.ErrorLogger;
import model.workWithFiles.SelectAudioVideoFile;

import java.io.File;
import java.nio.file.Paths;

import static model.workWithFiles.Util.*;

public class ConverterMP3ViewController {
    private static final String DEFAULT_FILE_TEXT_SELECT_FILE = "Selected file: none";
    private static final int SUCCESS_MESSAGE_DURATION_SECONDS = 5;

    private final File DEFAULT_PATH = Paths.get(System.getProperty("user.home"), "Desktop").toFile();
    private final String DEFAULT_TEXT_BIT_RATE = "BitRate";
    private final String DEFAULT_TEXT_CHANNELS = "Channels";
    private final String DEFAULT_TEXT_SAMPLING_RATE = "Sampling Rate";
    private int BitRate;
    private int Channel;
    private int SamplingRate;
    private File outputPath;
    private File file;
    private final PauseTransition hideSuccessMessageTimer =
            new PauseTransition(Duration.seconds(SUCCESS_MESSAGE_DURATION_SECONDS));

    @FXML public VBox converterMP3Page;
    @FXML private Label labelConvertMP3;
    @FXML private Button btnSelectAudioVideoFile;
    @FXML private Button btnChoiceDirForSaveMP3;
    @FXML private Label labelSelectAudioName;
    @FXML private ToggleButton btnToMP3;
    @FXML private Button btnSubmitConvert;
    @FXML private Button btnReset;
    @FXML private Label labelSuccessConvert;
    @FXML private ComboBox<String> comboBoxChoiceBitRate;
    @FXML private ComboBox<String> comboBoxChoiceChannels;
    @FXML private ComboBox<String> comboBoxChoiceSamplingRate;


    @FXML
    public void initialize() {
        outputPath = DEFAULT_PATH;
        setupClearMessageTimer(labelSuccessConvert, hideSuccessMessageTimer);
        labelSelectAudioName.setText(DEFAULT_FILE_TEXT_SELECT_FILE);

        setupComboBox(comboBoxChoiceBitRate);
        setupComboBox(comboBoxChoiceChannels);
        setupComboBox(comboBoxChoiceSamplingRate);

        comboBoxChoiceBitRate.setValue(DEFAULT_TEXT_BIT_RATE);
        comboBoxChoiceChannels.setValue(DEFAULT_TEXT_CHANNELS);
        comboBoxChoiceSamplingRate.setValue(DEFAULT_TEXT_SAMPLING_RATE);

        comboBoxChoiceBitRate.getItems().addAll("128 kbps", "192 kbps", "256 kbps", "320 kbps");
        comboBoxChoiceChannels.getItems().addAll("1 Channels", "2 Channels");
        comboBoxChoiceSamplingRate.getItems().addAll("8000 kHz", "11025 kHz",
                                                        "12000 kHz", "16000 kHz",
                                                        "22050 kHz", "24000 kHz",
                                                        "32000 kHz", "44100 kHz", "48000 kHz");


    }

    @FXML
    public void onSelectAudioVideoPressed() {
        SelectAudioVideoFile selectAudioVideoFile = new SelectAudioVideoFile();
        Stage stage = (Stage) btnSelectAudioVideoFile.getScene().getWindow();
        file = selectAudioVideoFile.choiceFile(stage);

        if(file == null) return;

        ErrorLogger.info("User select file (video/audio): " + file.getAbsolutePath());
        labelSelectAudioName.setText("Select video/audio: " + file.getName());
    }

    @FXML
    public void onSelectOutputDirectoryPressed() {
    }

    @FXML
    public void onFormatMp3Pressed() {
    }

    @FXML
    public void onStartConversionPressed() {
    }

    @FXML
    public void onResetPressed() {
        comboBoxChoiceBitRate.setValue(DEFAULT_TEXT_BIT_RATE);
        comboBoxChoiceChannels.setValue(DEFAULT_TEXT_CHANNELS);
        comboBoxChoiceSamplingRate.setValue(DEFAULT_TEXT_SAMPLING_RATE);
    }

    public void onChoiceBitRate() {

    }

    public void onChoiceChannels() {

    }

    public void onChoiceSamplingRate() {

    }

    private void setupComboBox(ComboBox<String> cb) {
        cb.getStyleClass().add("combo-box-mp3");

        cb.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                }
            }
        });

        cb.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(cb.getValue());
                } else {
                    setText(item);
                }
            }
        });
    }
}
