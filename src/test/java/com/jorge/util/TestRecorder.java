package com.jorge.util;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_QUICKTIME_JPEG;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.springframework.stereotype.Component;

import com.jorge.util.Constants;

@Component
public class TestRecorder {

	private ScreenRecorder screenRecorder;

	public void startRecording() throws Exception {
		GraphicsConfiguration gc = GraphicsEnvironment//
				.getLocalGraphicsEnvironment()//
				.getDefaultScreenDevice()//
				.getDefaultConfiguration();

		// Create a instance of ScreenRecorder with the required configurations
		/*screenRecorder = new ScreenRecorder(gc,
	            gc.getBounds(),
	            new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
	            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                    DepthKey, (int) 24, FrameRateKey, Rational.valueOf(15),
	                    QualityKey, 1.0f, 
	                    KeyFrameIntervalKey, (int) (15 * 60)),
	            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
	            null,
	            new File(Constants.VIDEO_FOLDER));*/
		
		screenRecorder = new ScreenRecorder(gc,
                gc.getBounds(),
                new Format(
                        MediaTypeKey,
                        FormatKeys.MediaType.FILE,
                        MimeTypeKey, MIME_QUICKTIME),
                new Format(MediaTypeKey,
                        FormatKeys.MediaType.VIDEO,
                        EncodingKey,
                        ENCODING_QUICKTIME_JPEG,
                        CompressorNameKey,
                        ENCODING_QUICKTIME_JPEG,
//                        COMPRESSOR_NAME_QUICKTIME_JPEG,
                        DepthKey,
                        24,
                        FrameRateKey,
                        Rational.valueOf(15),
                        QualityKey,
                        0.5f,
                        KeyFrameIntervalKey,
                        15 * 60),
                new Format(
                        MediaTypeKey,
                        FormatKeys.MediaType.VIDEO,
                        EncodingKey,
                        "black",
                        FrameRateKey,
                        Rational.valueOf(30)),
                null,
	            new File(Constants.VIDEO_FOLDER));

		screenRecorder.start();
	}

	public void stopRecording() throws Exception {
		screenRecorder.stop();
	}
}
