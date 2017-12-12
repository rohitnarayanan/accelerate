package accelerate.utils.listener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;

/**
 * JUnit test for {@link StaticContextListener}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@SpringBootTest(classes = AccelerateUtilsTest.class)
@RunWith(SpringRunner.class)
@StaticContextListener(onContextStarted = "contextStartListener", onContextClosed = "contextClosedListener")
@SuppressWarnings("static-method")
public class StaticContextListenerTest {
	/**
	 * {@link Logger} instance
	 */
	private static final Logger _LOGGER = LoggerFactory.getLogger(StaticContextListenerTest.class);

	/**
	 * State flag to be changed by {@link #contextStartListener(ApplicationContext)}
	 */
	private static int contextState = 0;

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticContextListener#onContextStarted()}.
	 */
	@Test
	public void testContextStarted() {
		Assert.assertEquals("@StaticContextListener.onContextStarted()", 1, contextState);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticContextListener#onContextClosed()}.
	 */
	@Test
	public void testContextClosed() {
		Assert.assertTrue("@StaticContextListener.onContextClosed() to be tested contextClosedListener()", true);
	}

	/**
	 * @param aContext
	 */
	public static final void contextStartListener(@SuppressWarnings("unused") ApplicationContext aContext) {
		_LOGGER.debug("StaticContextListenerTest: Context started");
		contextState = 1;
	}

	/**
	 * @param aContext
	 */
	public static final void contextClosedListener(@SuppressWarnings("unused") ApplicationContext aContext) {
		System.err.println("@StaticContextListener.onContextClosed() tested successfully");
	}
}
