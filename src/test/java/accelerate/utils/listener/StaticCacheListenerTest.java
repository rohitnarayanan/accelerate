package accelerate.utils.listener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;
import accelerate.utils.cache.PropertyCache;

/**
 * JUnit test for {@link StaticCacheListener}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@SpringBootTest(classes = { AccelerateUtilsTest.class, TestListenerConfig.class })
@RunWith(SpringRunner.class)
@StaticCacheListener(name = "StaticListenerUtilTestCache", handler = "cacheListener")
@SuppressWarnings("static-method")
public class StaticCacheListenerTest {
	/**
	 * Number of keys in {@link TestListenerConfig#staticListenerUtilTestCache()}
	 */
	private static int keyCount = 0;

	/**
	 * Test method for
	 * {@link accelerate.utils.listener.StaticCacheListener#handler()}.
	 */
	@Test
	public void testListener() {
		Assert.assertEquals("@StaticCacheListener handler test", 1, keyCount);
	}

	/**
	 * @param aCache
	 */
	public static final void cacheListener(PropertyCache aCache) {
		aCache.put("testKey", "testValue");
		keyCount = aCache.size();
	}
}
