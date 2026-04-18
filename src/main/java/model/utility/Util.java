package model.utility;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.logger.ErrorLogger;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import java.io.File;

public class Util {
    public static File setPathForSave(Stage stage, File currentDirectory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select directory for saving");
        File initialDirectory = resolveInitialDirectory(currentDirectory);
        if (initialDirectory != null) {
            directoryChooser.setInitialDirectory(initialDirectory);
        }
        return directoryChooser.showDialog(stage);
    }

    public static File resolveInitialDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            return directory;
        }
        return null;
    }

    public static void setupClearMessageTimer(Label label, PauseTransition timer) {
        setupClearMessageTimer(label, null, timer);
    }

    public static void setupClearMessageTimer(Label label, ProgressBar bar, PauseTransition timer) {
        timer.setOnFinished(_ -> hideSuccessMessage(label, bar, timer));
    }

    public static void showSuccessMessage(Label label, String format, PauseTransition timer) {
        label.setStyle("-fx-text-fill: #32CD32;");
        label.setText("Successfully converted to " + format.toUpperCase());
        label.setVisible(true);
        timer.playFromStart();
    }

    public static void showProgressBar(ProgressBar bar, PauseTransition timer) {
        bar.setProgress(1.0);
        timer.playFromStart();
    }

    public static void showErrorMessage(Label label, String message, PauseTransition timer) {
        label.setStyle("-fx-text-fill: RED;");
        label.setText(message);
        label.setVisible(true);
        timer.playFromStart();
    }

    public static void showErrorMessage(Label label, ProgressBar bar, String message, PauseTransition timer) {
        bar.setStyle("-fx-border-color: RED;");
        showErrorMessage(label, message, timer);
    }

    public static void hideSuccessMessage(Label label, PauseTransition timer) {
        hideSuccessMessage(label, null, timer);
    }

    public static void hideSuccessMessage(Label label, ProgressBar bar, PauseTransition timer) {
        if (timer != null) timer.stop();
        label.setVisible(false);
        label.setText("");
        if (bar != null) {
            bar.setProgress(0.0);
            bar.setStyle("");
        }
    }

    public static Stage getStage(Control control) {
        return (Stage) control.getScene().getWindow();
    }

    public static int parseComboBoxStringToInt(ComboBox<String> cb) {
        return Integer.parseInt(cb.getValue().replaceAll("[^0-9]", ""));
    }

    public static MultimediaInfo getMetadata(File file) {
        if (file == null || !file.exists()) return null;
        try {
            return new MultimediaObject(file).getInfo();
        } catch (Exception e) {
            ErrorLogger.log(111, ErrorLogger.Level.ERROR, "Failed to get metadata", e);
            return null;
        }
    }

    public static int parseChannels(MultimediaInfo info) {
        return (info != null && info.getAudio() != null) ? info.getAudio().getChannels() : -1;
    }

    public static int parseSamplingRate(MultimediaInfo info) {
        return (info != null && info.getAudio() != null) ? info.getAudio().getSamplingRate() : -1;
    }

    public static int parseBitrate(MultimediaInfo info) {
        if (info == null) return -1;
        if (info.getVideo() != null && info.getVideo().getBitRate() > 0) return info.getVideo().getBitRate() / 1000;
        if (info.getAudio() != null && info.getAudio().getBitRate() > 0) return info.getAudio().getBitRate() / 1000;
        return -1;
    }

    public static int parseVideoBitrate(MultimediaInfo info) {
        if (info != null && info.getVideo() != null && info.getVideo().getBitRate() > 0) {
            return info.getVideo().getBitRate() / 1000;
        }
        return -1;
    }

    public static int parseAudioBitrate(MultimediaInfo info) {
        if (info != null && info.getAudio() != null && info.getAudio().getBitRate() > 0) {
            return info.getAudio().getBitRate() / 1000;
        }
        return -1;
    }

    public static String parseResolution(MultimediaInfo info) {
        if (info != null && info.getVideo() != null && info.getVideo().getSize() != null) {
            return info.getVideo().getSize().getWidth() + "x" + info.getVideo().getSize().getHeight();
        }
        return null;
    }

    public static int parseFps(MultimediaInfo info) {
        return (info != null && info.getVideo() != null) ? (int) info.getVideo().getFrameRate() : -1;
    }
}
