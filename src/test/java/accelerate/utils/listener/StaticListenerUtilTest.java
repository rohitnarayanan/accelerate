package accelerate.utils.listener;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;

/**
 * JUnit test for {@link StaticListenerUtil}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@SpringBootTest(classes = AccelerateUtilsTest.class)
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class StaticListenerUtilTest {
	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticListenerUtil#initialize()}.
	 */
	@Test
	public void testInitialize() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticListenerUtil#destroy()}.
	 */
	@Test
	public void testDestroy() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticListenerUtil#onApplicationEvent(org.springframework.boot.context.event.ApplicationReadyEvent)}.
	 */
	@Test
	public void testOnApplicationEvent() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticListenerUtil#notifyCacheLoad(accelerate.utils.cache.AccelerateCache)}.
	 */
	@Test
	public void testNotifyCacheLoad() {
		assertTrue("Not yet implemented", true);
	}
}
