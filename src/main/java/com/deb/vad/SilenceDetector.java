package com.deb.vad;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
/**
 * 
 */
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.deb.vad.utility.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author debmalyajash
 *
 */
public class SilenceDetector implements SoundCache{
	private static Map<Long,byte[]> timeBasedAudioInputMap = new HashMap<>();
	/**
	 * 
	 */
	private static final int ARRAY_SIZE = 8;
	public static byte[] SILENCE = new byte[ARRAY_SIZE];

	private static final Logger logger = LoggerFactory.getLogger(SilenceDetector.class);

	public static void main(final String args[]) {
		Thread.currentThread().setName("DebSilenceDetector");
		AudioFormat format = CommonUtil.getAudioFormat();
		SilenceDetector me = new SilenceDetector();

		// A line is an element of the digital audio "pipeline" that is, a path
		// for moving audio into or out of the system. Usually the line is a
		// path into or out of a mixer (although technically the mixer itself is
		// also a kind of line).

		// Audio input and output ports are lines. These are analogous to the
		// microphones and speakers connected to a physical mixing console.
		// Another kind of line is a data path through which an application
		// program can get input audio from, or send output audio to, a mixer.
		// These data paths are analogous to the tracks of the multitrack
		// recorder connected to the physical mixing console.
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
//		Thread voiceRetrieverThread = new Thread(new VoiceRetriever(me));
//		voiceRetrieverThread.setName("DebVoiceRetriever");
//		voiceRetrieverThread.start();

		// checks if system supports the data line
		if (!AudioSystem.isLineSupported(info)) {
			System.err.println("Line not supported. Sorry I am leaving...");
			System.exit(0);
		}
		TargetDataLine line = null;
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			AudioInputStream ais = new AudioInputStream(line);
			// StringBuilder sb = new StringBuilder();
			
			

			
			while (true) {
				// An AudioInputStream is a subclass of the InputStream class,
				// which encapsulates a series of bytes that can be read
				// sequentially. To its superclass, the AudioInputStream class
				// adds knowledge of the bytes' audio data format (represented
				// by an AudioFormat object).

				int size = ais.available();
				byte[] b = new byte[size];
				SILENCE = new byte[size];
				Arrays.fill(SILENCE, (byte) 0);
				ais.read(b, 0, size);


				if (Arrays.equals(SILENCE, b)) {
                     if (logger.isInfoEnabled()){
                     	logger.info("~~~ SILENCE ~~~~");
					 }else {
						 System.out.println("~~~ SILENCE ~~~~");
					 }
				} else {
					if (logger.isInfoEnabled()){
						logger.info(String.format("~~~ NOISE ~~~~ %s",Arrays.toString( b )));
					}else{
						System.out.println(String.format("~~~ NOISE ~~~~ %s",Arrays.toString( b )));
					}
				}

				// The AudioSystem class provides methods for reading and
				// writing sounds in different file formats, and for converting
				// between different data formats.

				// b = null;
				
			}
		} catch (Throwable e) {
			if (logger.isInfoEnabled()){
				logger.error(e.getMessage(),e);
			}
		} finally {
			if (line != null) {
				line.flush();
				line.close();
			}
		}

	}

	private static void getInputRange(byte[] b) {
		int total;
		byte max = Byte.MIN_VALUE;
		byte min = Byte.MAX_VALUE;
		total = 0;
		for (byte each : b) {
			if (each < min) {
				min = each;
			} else if (each > max) {
				max = each;
			}
			total += each;
		}
		System.out.println(new Date() + " " + total + " Range " + min + " ~ " + max);
	}

	@Override
	public Map<Long, byte[]> getAudioMap() {
		return timeBasedAudioInputMap;
	}

}
