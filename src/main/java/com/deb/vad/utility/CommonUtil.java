/**
 * 
 */
package com.deb.vad.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import javazoom.spi.mpeg.sampled.file.MpegAudioFormat;
import javazoom.spi.mpeg.sampled.file.MpegEncoding;
import javazoom.spi.mpeg.sampled.file.MpegFileFormatType;

/**
 * @author debmalyajash
 *
 */
public class CommonUtil {

	/**
	 * WAG format type.
	 */
	public final static AudioFileFormat.Type waveType = AudioFileFormat.Type.WAVE;

	/**
	 * MP3 format type.
	 */
	public final static AudioFileFormat.Type mp3Type = MpegFileFormatType.MP3;

	public final static long DAY_IN_MILIS = 24 * 60 * 60 * 60 * 1000;

	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		Date d = Calendar.getInstance().getTime(); // Current time
		SimpleDateFormat sdf = new SimpleDateFormat(format); // Set your date
		// format
		return sdf.format(d);
	}

	/**
	 * Defines an audio format. Encoding technique, usually pulse code
	 * modulation (PCM) Number of channels (1 for mono, 2 for stereo, etc.)
	 * Sample rate (number of samples per second, per channel) Number of bits
	 * per sample (per channel) Frame rate Frame size in bytes Byte order
	 * (big-endian or little-endian).
	 * 
	 * PCM is one kind of encoding of the sound waveform. The Java Sound API
	 * includes two PCM encodings that use linear quantization of amplitude, and
	 * signed or unsigned integer values.
	 */
	public static AudioFormat getAudioFormat() {
		float sampleRate = 16000; // line buffer size
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}

	/**
	 * Defines an audio format. Encoding technique, usually pulse code
	 * modulation (PCM) Number of channels (1 for mono, 2 for stereo, etc.)
	 * Sample rate (number of samples per second, per channel) Number of bits
	 * per sample (per channel) Frame rate Frame size in bytes Byte order
	 * (big-endian or little-endian).
	 * 
	 * PCM is one kind of encoding of the sound waveform. The Java Sound API
	 * includes two PCM encodings that use linear quantization of amplitude, and
	 * signed or unsigned integer values.
	 */
	public static AudioFormat getAudioFormatStereo() {
		float sampleRate = 16000; // line buffer size
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}

	/**
	 * 
	 * @return
	 */
	public static AudioFormat getAudioFormatMp3() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("mp3.channels", "2");
		properties.put("duration", DAY_IN_MILIS);

		// interface TargetDataLine supporting format PCM_SIGNED 16000.0 Hz, 8
		// bit, stereo, 2 bytes/frame, - Working
		// interface TargetDataLine supporting format PCM_SIGNED 16000.0 Hz, 8
		// bit, stereo, 2 bytes/frame, 8.0 frames/second, - Wokring

		AudioFormat mp3AudioFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, (float) 16000.0, 8, 2, 2, 800,
				true, properties);

		return mp3AudioFormat;
	}

}
