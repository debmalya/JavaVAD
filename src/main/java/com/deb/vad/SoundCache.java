/**
 * 
 */
package com.deb.vad;

import java.util.Map;

/**
 * @author debmalyajash
 *
 */
public interface SoundCache {

	Map<Long,byte[]> getAudioMap();
}
