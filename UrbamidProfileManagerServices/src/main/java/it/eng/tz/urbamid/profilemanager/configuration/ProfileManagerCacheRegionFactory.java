package it.eng.tz.urbamid.profilemanager.configuration;

import javax.cache.Cache;

import org.hibernate.cache.jcache.internal.JCacheRegionFactory;

public class ProfileManagerCacheRegionFactory extends JCacheRegionFactory {

	private static final long serialVersionUID = -8018682101848012372L;

	@Override
	protected Cache<Object, Object> createCache(String regionName) {
		throw new IllegalArgumentException("Cache region non conosciuta: " + regionName);
	}
}
