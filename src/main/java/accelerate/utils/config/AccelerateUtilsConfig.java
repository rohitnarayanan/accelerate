package accelerate.utils.config;

import org.springframework.context.annotation.Configuration;

import accelerate.utils.listener.StaticListenerUtil;
import accelerate.utils.logging.LoggerAspect;

/**
 * {@link Configuration} class to load accelerate components
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 07, 2017
 */
// @Configuration
// @ComponentScan("accelerate")
public class AccelerateUtilsConfig {
	// blank config class to load accelerate components

	/**
	 * @return
	 */
	// @Bean(name = "StaticListenerUtil")
	public static StaticListenerUtil staticListenerUtil() {
		return new StaticListenerUtil();
	}

	/**
	 * @return
	 */
	// @Bean(name = "LoggerAspect")
	// @ConditionalOnProperty("accelerate.utils.logging.audit")
	public static LoggerAspect loggerAspect() {
		return new LoggerAspect();
	}
}
