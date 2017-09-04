/**
 * Copyright 2015-2016 Knowesis Pte Ltd.
 * 
 */
package com.deb.ehcache;

import org.apache.log4j.Logger;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @author debmalyajash
 *
 */
public class EhCacheEventListener<K,V> implements CacheEventListener<K, V> {
	
	private static final Logger  LOGGER = Logger.getLogger(EhCacheEventListener.class);

	/* (non-Javadoc)
	 * @see org.ehcache.event.CacheEventListener#onEvent(org.ehcache.event.CacheEvent)
	 */
	@Override
	public void onEvent(CacheEvent<K, V> event) {
		LOGGER.debug(event.getType().toString());
		
		
	}

}
