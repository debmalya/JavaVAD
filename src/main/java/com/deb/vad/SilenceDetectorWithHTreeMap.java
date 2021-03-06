package com.deb.vad;

import com.deb.vad.utility.CommonUtil;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author debmalyajash
 *
 */
public class SilenceDetectorWithHTreeMap implements SoundCache {
	/**
	 * 
	 */
	private static final int ARRAY_SIZE = 8;
	public static byte[] SILENCE = new byte[ARRAY_SIZE];

	private static Logger LOGGER = LoggerFactory.getLogger(SilenceDetectorWithHTreeMap.class);

	/**
	 * In Memory DB. DBMaker.memoryDB().make();
	 */
	private static DB db = DBMaker.memoryDirectDB().make();

	/**
	 * Time based audio input map. Key is the current system time. value is byte
	 * array retrieved from the audio input system.  
	 */
	private static HTreeMap<Long, byte[]> timeBasedAudioInputMap = db
			.hashMap("long_byte_array_map", Serializer.LONG, Serializer.BYTE_ARRAY).create();

	/**
	 * If size exceeds it will be stored here.
	 */
	private static DB expirationDB = null;
	static {
		try {
			expirationDB = DBMaker.fileDB("InDisk.db").make();
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(),th);
		} finally {
			if (expirationDB != null) {
//				expirationDB.close();
			}

		}
	}

	private static HTreeMap onDisk = expirationDB.hashMap("_onDisk").createOrOpen();

	private static HTreeMap<Long, byte[]> sizeBasedAudioInputMap = (HTreeMap<Long, byte[]>) DBMaker
			.memoryShardedHashMap(16).expireAfterUpdate().expireStoreSize(1 * 1024 * 1024 * 1024).expireOverflow(onDisk)
			.createOrOpen();

	public static void main(final String args[]) {
		Thread.currentThread().setName("DebMain");
		SilenceDetectorWithHTreeMap me = new SilenceDetectorWithHTreeMap();
		AudioFormat format = CommonUtil.getAudioFormat();

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

			AudioInputStream ais = new AudioInputStream(line);

			Thread voiceRetrieverThread = new Thread(new VoiceRetriever(me));
			voiceRetrieverThread.setName("DebVoiceRetriever");
			voiceRetrieverThread.start();

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
				} else {

					// timeBasedAudioInputMap.put(System.currentTimeMillis(),
					// b);
					sizeBasedAudioInputMap.put(System.currentTimeMillis(), b);
				}
				// putting everything into map (Silence and Sound)

				// The AudioSystem class provides methods for reading and
				// writing sounds in different file formats, and for converting
				// between different data formats.

				// b = null;

			}
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (line != null) {
				line.flush();
				line.close();
			}
			if (expirationDB != null) {
				expirationDB.close();
			}
		}

	}

	public static HTreeMap<Long, byte[]> getTimeBasedAudioInputMap() {
		// return timeBasedAudioInputMap;
		return sizeBasedAudioInputMap;
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
