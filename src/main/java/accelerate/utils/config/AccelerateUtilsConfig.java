package accelerate.utils.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * {@link Configuration} class to load/validate accelerate components
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 07, 2017
 */
@Configuration
public class AccelerateUtilsConfig {
	/**
	 * {@link Logger} instance
	 */
	protected static final Logger _logger = LoggerFactory.getLogger(AccelerateUtilsConfig.class);

	/**
	 * {@link Environment} instance
	 */
	private final Environment environment;

	/**
	 * @param aEnvironment
	 */
	public AccelerateUtilsConfig(Environment aEnvironment) {
		this.environment = aEnvironment;
	}

	/**
	 * Validates the application configuration
	 */
	@PostConstruct
	public void validateConfiguration() {
		Collection<String> activeProfiles = Arrays.asList(this.environment.getActiveProfiles());

		int accelerateProfileCount = activeProfiles.stream().filter(aProfile -> aProfile.startsWith("accelerate."))
				.collect(Collectors.counting()).intValue();

		if (accelerateProfileCount > 0) {
			String errorMessage = null;
			if (!activeProfiles.contains("accelerate")) {
				errorMessage = "You have misconfigured your application! Base profile 'accelerate' is mandatory to use other 'accelerate.*' profiles. "
						+ "Update the spring.profiles.active property in your configuration file and retry";
				_logger.error(errorMessage);
				throw new ApplicationContextException(errorMessage);
			}
		}
	}
}
