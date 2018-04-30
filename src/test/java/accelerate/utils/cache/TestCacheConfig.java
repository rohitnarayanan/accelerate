package accelerate.utils.cache;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link TestConfiguration} for accelerate.utils.cache package
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@TestConfiguration
public class TestCacheConfig {
	/**
	 * @return
	 */
	@Bean(name = "CacheTestCache")
	public static PropertyCache cacheTestCache() {
		PropertyCache cache = new PropertyCache("CacheTestCache");
		cache.setConfigURL("TestCache.properties");
		return cache;
	}
}
