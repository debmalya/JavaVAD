/**
 * Copyright 2015-2016 Knowesis Pte Ltd.
 * 
 */
package com.deb.ehcache;


import com.deb.vad.SilenceDetector;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author debmalyajash
 *
 */
public class EhCacheEventListener<K,V> implements CacheEventListener<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(EhCacheEventListener.class);

	/* (non-Javadoc)
	 * @see org.ehcache.event.CacheEventListener#onEvent(org.ehcache.event.CacheEvent)
	 */
	@Override
	public void onEvent(CacheEvent<K, V> event) {
		if (logger.isDebugEnabled()) {
			logger.debug(event.getType().toString());
		}
		
		
	}

}
