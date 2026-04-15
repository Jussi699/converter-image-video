package model.workWithFiles;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static model.workWithFiles.Util.resolveInitialDirectory;

public class SelectAudioVideoFile implements SelectFile {
    @Override
    public File choiceFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select audio/video");
        File initialDirectory = resolveInitialDirectory(new File(System.getProperty("user.home")));
        if (initialDirectory != null) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Media Files",
                        "*.mp3", "*.wav", "*.ogg", "*.flac", "*.m4a", "*.aac", "*.wma",
                        "*.mp4", "*.avi", "*.mkv", "*.mov", "*.flv", "*.wmv")
        );
        return fileChooser.showOpenDialog(stage);
    }
}
