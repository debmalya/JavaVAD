package com.deb.vad;

import java.util.Arrays;
import java.util.Date;

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
	private static final int ARRAY_SIZE = 16;
	public static byte[] SILENCE = new byte[ARRAY_SIZE];

	public static void main(final String args[]) {
		AudioFormat format = getAudioFormat();

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
			Arrays.fill(SILENCE, (byte) 0);
			while (true) {
				// An AudioInputStream is a subclass of the InputStream class,
				// which encapsulates a series of bytes that can be read
				// sequentially. To its superclass, the AudioInputStream class
				// adds knowledge of the bytes' audio data format (represented
				// by an AudioFormat object).
				
				AudioInputStream ais = new AudioInputStream(line);
				byte[] b = new byte[ARRAY_SIZE];
				ais.read(b, 0, ARRAY_SIZE);
				if (Arrays.equals(SILENCE, b)) {
//					System.out.println("Slience :" + Arrays.toString(b));
				} else {
					System.out.println(System.currentTimeMillis() + Arrays.toString(b));
				}
				
				// The AudioSystem class provides methods for reading and
				// writing sounds in different file formats, and for converting
				// between different data formats.
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
	private static AudioFormat getAudioFormat() {
		float sampleRate = 16000;  // line buffer size
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}
}
