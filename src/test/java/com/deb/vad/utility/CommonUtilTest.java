/**
 * 
 */
package com.deb.vad.utility;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import junit.framework.TestCase;

/**
 * @author debmalyajash
 *
 */
public class CommonUtilTest extends TestCase {

	/**
	 * Test method for {@link com.deb.vad.utility.CommonUtil#getAudioFormatMp3()}.
	 */
	public final void testGetAudioFormatMp3() {
//		package com.ibm.emb.test.mfb; study this package.
//		Not working [PCM_SIGNED, PCM_UNSIGNED, ALAW, ULAW]
//		interface TargetDataLine supporting format PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 16000 bytes/frame, 16.0 frames/second, big-endian
		
//		Working
//		interface TargetDataLine supporting format PCM_SIGNED 16000.0 Hz, 8 bit, mono, 1 bytes/frame, 

		AudioFormat mp3Format = CommonUtil.getAudioFormatMp3();
		Encoding[] encoding = AudioSystem.getTargetEncodings(mp3Format);
		System.out.println(Arrays.toString(encoding));
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, mp3Format);
		System.out.println(info.toString());
		if (!AudioSystem.isLineSupported(info)) {
			
			assertFalse("Line not supported. Sorry I am leaving...",true);
			
		}
	}

}
