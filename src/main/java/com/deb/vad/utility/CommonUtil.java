/**
 * 
 */
package com.deb.vad.utility;

import javax.sound.sampled.AudioFormat;

/**
 * @author debmalyajash
 *
 */
public class CommonUtil {
	
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
		float sampleRate = 16000;  // line buffer size
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
		float sampleRate = 16000;  // line buffer size
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}


}
