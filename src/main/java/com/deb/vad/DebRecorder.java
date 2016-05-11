/**
 * 
 */
package com.deb.vad;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.deb.vad.utility.CommonUtil;

/**
 * @author debmalyajash
 *
 */
public class DebRecorder {
	
	/**
	 * File where sound will be recorded.
	 */
	private static File wavFile = null;
	

	
	/**
	 * When to stop recording.
	 */
	private static boolean stopped = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		record(1);
	}

	/**
	 * 
	 * @param audioType
	 *            1 for mono, any value other than 1 is stereo
	 */
	public static void record(int audioType) {
		AudioFormat format = null;
		if (audioType == 1) {
			format = CommonUtil.getAudioFormat();
		} else {
			format = CommonUtil.getAudioFormatStereo();
		}
		
		if (wavFile == null) {
			
			wavFile = new File(CommonUtil.getDate("ddMMyyyy_kkmmss")+".wav");
		}

		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			System.err.println("Line not supported. Sorry I am leaving...");
			System.exit(0);
		}

		TargetDataLine line = null;
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			int size = line.getBufferSize() / 5;
			
			ByteArrayOutputStream out = new ByteArrayOutputStream(size);
			int numBytesRead;
			byte[] data = new byte[size];
			

			// Begin audio capture.
			line.start();
			
			AudioInputStream ais = new AudioInputStream(line);

			// Here, stopped is a global boolean set by another thread.
			while (!stopped) {
				// Read the next chunk of data from the TargetDataLine.
				numBytesRead = line.read(data, 0, data.length);
				// Save this chunk of data.
				out.write(data, 0, numBytesRead);
//				AudioSystem.write(ais, CommonUtil.waveType, wavFile);
				AudioSystem.write(ais, CommonUtil.waveType, out);
				out.flush();
				System.out.println(".");
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
}
