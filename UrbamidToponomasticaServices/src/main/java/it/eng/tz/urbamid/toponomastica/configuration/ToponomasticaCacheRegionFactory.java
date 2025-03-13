package it.eng.tz.urbamid.toponomastica.configuration;

import javax.cache.Cache;

import org.hibernate.cache.jcache.internal.JCacheRegionFactory;

public class ToponomasticaCacheRegionFactory extends JCacheRegionFactory {

	private static final long serialVersionUID = -8018682101848012372L;

	@Override
	protected Cache<Object, Object> createCache(String regionName) {
		throw new IllegalArgumentException("Cache region non conosciuta: " + regionName);
	}
}
