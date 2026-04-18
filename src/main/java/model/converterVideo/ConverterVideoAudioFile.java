package model.converterVideo;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.logger.ErrorLogger;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ConverterVideoAudioFile {
    private static final Encoder encoder = new Encoder();
    public static File nameFileAfter;

    /**
     * Основной метод для конвертации видео и аудио с раздельными битрейтами.
     */
    public static CompletableFuture<Boolean> convert(File file,
                                                     File pathForSave,
                                                     int videoBitrate, int audioBitrate,
                                                     int channels, int samplingRate, int fps,
                                                     String videoCodec, String audioCodec, String output_format, String resolution, String typeConvert,
                                                     Consumer<Double> progressConsumer) {
        if (!checkingFileStatic(file)) {
            return CompletableFuture.completedFuture(false);
        }

        File target;
        if (pathForSave.isDirectory()) {
            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf('.');
            String nameWithoutExtension = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
            target = new File(pathForSave, nameWithoutExtension + "_" + UUID.randomUUID().toString().replace("-", "") + "." + output_format);
            nameFileAfter = target;
        } else {
            target = pathForSave;
        }

        return CompletableFuture.supplyAsync(() -> {
            ErrorLogger.info("Starting async conversion...");
            try {
                EncodingAttributes attrs = new EncodingAttributes();
                attrs.setOutputFormat(output_format);

                AudioAttributes audio = new AudioAttributes();
                audio.setCodec(audioCodec);
                if (audioBitrate > 0) {
                    audio.setBitRate(audioBitrate * 1000);
                }
                audio.setChannels(channels);
                audio.setSamplingRate(samplingRate);
                attrs.setAudioAttributes(audio);

                if ("video".equalsIgnoreCase(typeConvert)) {
                    VideoAttributes video = new VideoAttributes();
                    video.setCodec(videoCodec);
                    if (videoBitrate > 0) {
                        video.setBitRate(videoBitrate * 1000);
                    }
                    video.setPixelFormat("yuv420p");
                    
                    if (fps > 0) {
                        video.setFrameRate(fps);
                    }
                    if (resolution != null && resolution.contains("x")) {
                        try {
                            String[] res = resolution.split("x");
                            int width = Integer.parseInt(res[0]);
                            int height = Integer.parseInt(res[1]);
                            
                            if (width % 2 != 0) width--;
                            if (height % 2 != 0) height--;
                            
                            video.setSize(new VideoSize(width, height));
                        } catch (Exception e) {
                            ErrorLogger.warn("Invalid resolution format: " + resolution);
                        }
                    }
                    attrs.setVideoAttributes(video);
                }

                ErrorLogger.info("Starting encoding: " + file.getName() + " [V-BR: " + videoBitrate + ", A-BR: " + audioBitrate + "]");
                
                encoder.encode(new MultimediaObject(file), target, attrs, new EncoderProgressListener() {
                    @Override
                    public void sourceInfo(MultimediaInfo info) {
                        ErrorLogger.info("Source info: " + info.toString());
                    }

                    @Override
                    public void progress(int permille) {
                        if (progressConsumer != null) {
                            progressConsumer.accept(permille / 1000.0);
                        }
                    }

                    @Override
                    public void message(String message) {
                        ErrorLogger.info("FFmpeg: " + message);
                    }
                });

                ErrorLogger.info("Conversion successful!");
                return true;
            } catch (Exception e) {
                handleError(e, target);
                return false;
            }
        });
    }

    /**
     * Перегрузка для обратной совместимости (например, для MP3-конвертера).
     */
    public static CompletableFuture<Boolean> convert(File file,
                                                     File pathForSave,
                                                     int bitRate, int channels, int samplingRate,
                                                     String audioCodec, String output_format,
                                                     Consumer<Double> progressConsumer) {
        return convert(file, pathForSave, -1, bitRate, channels, samplingRate, -1, null, audioCodec, output_format, null, "audio", progressConsumer);
    }

    public static void cancelConversion() {
        encoder.abortEncoding();
    }

    public static MultimediaInfo getMetadata(File file) {
        try {
            MultimediaObject obj = new MultimediaObject(file);
            return obj.getInfo();
        } catch (Exception e) {
            ErrorLogger.log(114, ErrorLogger.Level.ERROR, "Exception while getting multimedia info", e);
            return null;
        }
    }

    private static boolean checkingFileStatic(File file) {
        if (file == null || !file.exists()) {
            Platform.runLater(() -> ErrorLogger.alertDialog(Alert.AlertType.WARNING, "WARN",
                    "File missing!", "The selected file was not found or is empty."));
            return false;
        }
        return true;
    }

    private static void handleError(Exception e, File target) {
        String msg = e.getMessage();
        boolean isCancelled = msg != null && (msg.contains("Encoding interrupted") || msg.contains("Stream Closed"));

        if (isCancelled) {
            ErrorLogger.info("Conversion was cancelled by user.");
            if (target.exists() && target.delete()) {
                ErrorLogger.info("Partial file deleted.");
            }
        } else {
            ErrorLogger.info("Conversion failed: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> ErrorLogger.alertDialog(Alert.AlertType.WARNING, "ERROR", "Conversion Error", "FFmpeg Error: " + e.getMessage()));
            ErrorLogger.log(109, ErrorLogger.Level.ERROR, "Exception during conversion", e);
        }
    }
}
