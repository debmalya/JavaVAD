/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deb.ehcache;

import java.util.EnumSet;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.PooledExecutionServiceConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;

/**
 * @author debmalyajash
 *
 */
public class EhCacheDao {
	/**
	 * Cache Manager.
	 */
	private CacheManager cacheManager = null;

	/**
	 * This will hold ID/MSISDN as key and JsonObject as value.
	 */
	private Cache<Long, byte[]> cache;

	CacheEventListener<Long, byte[]> listener = new EhCacheEventListener<Long, byte[]>();

	/**
	 * To initiate EhCacheDao with cacheManager and cache.
	 * 
	 * @param cacheName
	 *            name of the cache.
	 * @param cacheSize
	 *            cache size.
	 */
	public EhCacheDao(final String cacheName, final long cacheSize) {

		setCacheManager(CacheManagerBuilder.newCacheManagerBuilder()
				.using(PooledExecutionServiceConfigurationBuilder.newPooledExecutionServiceConfigurationBuilder() 
						.defaultPool("dflt", 0, 10)
				        .pool("defaultEventPool", 1, 3)
				        .pool("cache2Pool", 2, 2)
				        .build())
				    .withDefaultEventListenersThreadPool("defaultEventPool")
				.withCache(cacheName, CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, byte[].class, ResourcePoolsBuilder.heap(cacheSize))
						.build())
				.build(true));

		setCache(cacheManager.getCache(cacheName, Long.class, byte[].class));
	}

	/**
	 * This may need to close the cache manager.
	 * 
	 * @return Cache Manager.
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 
	 * @param cacheManager
	 *            setting cache manager.
	 */
	private void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 
	 * @return Cache which will work with String as Key and JsonObject as value.
	 */
	public Cache<Long, byte[]> getCache() {
		return cache;
	}

	/**
	 * 
	 * @param cache
	 *            to set.
	 */
	private void setCache(Cache<Long, byte[]> cache) {
		this.cache = cache;
		cache.getRuntimeConfiguration().registerCacheEventListener(listener, EventOrdering.ORDERED,
				EventFiring.ASYNCHRONOUS, EnumSet.of(EventType.REMOVED, EventType.EVICTED));
	}

	/**
	 * To stored key (id, MSISDN) and related indicator JsonObject.
	 * 
	 * @param key
	 *            to be stored.
	 * @param value
	 *            with associated value.
	 */
	public void put(final Long key, final byte[] value) {
		cache.put(key, value);
	}

	/**
	 * @param key
	 *            whose JsonObject will be retrieved.
	 * @return JsonObject if available in the cache, otherwise null.
	 */
	public byte[] get(final Long key) {
		return cache.get(key);
	}
}
