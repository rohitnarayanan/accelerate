package accelerate.utils.listener;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import accelerate.utils.cache.PropertyCache;

/**
 * {@link TestConfiguration} for accelerate.utils.listener package
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@TestConfiguration
public class TestListenerConfig {
	/**
	 * @return
	 */
	@Bean(name = "StaticListenerUtilTestCache")
	public static PropertyCache staticListenerUtilTestCache() {
		return new PropertyCache("StaticListenerUtilTestCache");
	}
}
