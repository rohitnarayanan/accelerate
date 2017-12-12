package accelerate.utils.logging;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;

/**
 * JUnit test for {@link LoggerConfig}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since December 11, 2017
 */
@SpringBootTest(classes = AccelerateUtilsTest.class)
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class LoggerConfigTest {
	/**
	 * 
	 */
	@AutowireLogger
	private Logger _logger = null;

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerConfig#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}.
	 */
	@Test
	public void testPostProcessBeforeInitialization() {
		assertTrue("Not required", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerConfig#postProcessAfterInitialization(java.lang.Object, java.lang.String)}.
	 */
	@Test
	public void testPostProcessAfterInitialization() {
		assertTrue("logger not injected", (this._logger != null));
	}
}
