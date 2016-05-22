/**
 * 
 */
package com.deb.vad;

import java.io.ByteArrayInputStream;
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
public class MP3Recorder {
	
	/**
	 * File where sound will be recorded.
	 */
	private static File mp3File = null;
	
	/**
	 * When to stop recording.
	 */
	private static boolean stopped = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		record();

	}
	
	/**
	 * 
	 * @param audioType
	 *            1 for mono, any value other than 1 is stereo
	 */
	public static void record() {
		AudioFormat format = CommonUtil.getAudioFormatMp3();
		

		final String fileName = CommonUtil.getDate("ddMMyyyy_kkmmss") + ".mp3";
		mp3File = new File(fileName);
		

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

			int numBytesRead;

			// Begin audio capture.
			line.start();

			// AudioInputStream ais = new AudioInputStream(line);

			// Here, stopped is a global boolean set by another thread.
			while (!stopped) {
//				mp3File = new File(fileName);
//				ByteArrayOutputStream out = new ByteArrayOutputStream(size);
//				byte[] data = new byte[size];
				// Read the next chunk of data from the TargetDataLine.
//				numBytesRead = line.read(data, 0, data.length);
				// Save this chunk of data.
//				out.write(data, 0, numBytesRead);

//				ByteArrayInputStream bais = new ByteArrayInputStream(data);
//				AudioInputStream outputAIS = new AudioInputStream(bais, format,
//						data.length / format.getFrameSize());
//				int nWrittenBytes = AudioSystem.write(outputAIS,
//						CommonUtil.waveType, mp3File);
				
				AudioInputStream outputAIS = new AudioInputStream(line);
				AudioSystem.write(outputAIS, CommonUtil.waveType, mp3File);

//				out.flush();
//				bais = null;
//				outputAIS = null;
//				data = null;
//				out = null;
//				mp3File = null;
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (line != null) {
				line.flush();
				line.close();
			}

			mp3File = null;
			System.out.println("Bye Bye");
		}
	}

}
