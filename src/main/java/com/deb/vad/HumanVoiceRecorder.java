package com.deb.vad;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
/**
 * 
 */
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * @author debmalyajash
 *
 */
public class HumanVoiceRecorder {
	/**
	 * 
	 */
	private static final int ARRAY_SIZE = 256;
	public static byte[] SILENCE = new byte[ARRAY_SIZE];

	public static void main(final String args[]) {
		AudioFormat format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		// checks if system supports the data line
		if (!AudioSystem.isLineSupported(info)) {
			System.err.println("Line not supported");
			System.exit(0);
		}
		TargetDataLine line = null;
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			Arrays.fill(SILENCE, (byte)0);
			while (true) {
				AudioInputStream ais = new AudioInputStream(line);
				byte[] b = new byte[ARRAY_SIZE];
				ais.read(b, 0, ARRAY_SIZE);
				if (Arrays.equals(SILENCE, b)) {
					System.out.println("Slience :" + Arrays.toString(b));
				} else {
					System.out.println("Not Slience :" + Arrays.toString(b));
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (line != null) {
				line.flush();
				line.close();
			}
		}

	}

	/**
	 * Defines an audio format
	 */
	private static AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}
}
