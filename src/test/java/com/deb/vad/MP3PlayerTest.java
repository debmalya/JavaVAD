/**
 * 
 */
package com.deb.vad;

import junit.framework.TestCase;

/**
 * @author debmalyajash
 *
 */
public class MP3PlayerTest extends TestCase {

	/**
	 * Test method for {@link com.deb.vad.MP3Player#testPlay(java.lang.String)}.
	 */
	public final void testTestPlay() {
		try {
			
			MP3Player player = new MP3Player();
			player.testPlay("./src/main/resources/raju.mp3");
		} catch (Throwable th) {
			assertFalse(th.getMessage(), true);
		}
	}

}
