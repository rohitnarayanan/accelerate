package accelerate.utils.batch;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link TestConfiguration} class for accelerate.utils.batch package
 *
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@TestConfiguration
public class TestBatchConfig {
	/**
	 * @return
	 */
	@Bean
	public static AccelerateBatch<TestTask> accelerateBatch() {
		return new AccelerateBatch<>("AccelerateBatchTest", 10);
	}
}
