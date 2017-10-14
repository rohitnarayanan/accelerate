package accelerate.utils.batch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;

/**
 * JUnit test for {@link AccelerateBatch}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@SpringBootTest(classes = { AccelerateUtilsTest.class, TestBatchConfig.class })
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class AccelerateBatchTest {
	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#initialize()}.
	 */
	@Test
	public void testInitialize() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#shutdown()}.
	 */
	@Test
	public void testShutdown() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#AccelerateBatch(java.lang.String, int)}.
	 */
	@Test
	public void testAccelerateBatchStringInt() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#AccelerateBatch(java.lang.String, int, int)}.
	 */
	@Test
	public void testAccelerateBatchStringIntInt() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#shutdown(java.util.concurrent.TimeUnit, long)}.
	 */
	@Test
	public void testShutdownTimeUnitLong() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#registerTaskPostProcessor(java.util.function.Consumer)}.
	 */
	@Test
	public void testRegisterTaskPostProcessor() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#pause()}.
	 */
	@Test
	public void testPause() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#resume()}.
	 */
	@Test
	public void testResume() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#submitTasks(accelerate.utils.batch.AccelerateTask...)}.
	 */
	@Test
	public void testSubmitTasks() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#submitTasks(java.util.List)}.
	 */
	@Test
	public void testSubmitTasksListOfT() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#getBatchName()}.
	 */
	@Test
	public void testGetBatchName() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#isInitialized()}.
	 */
	@Test
	public void testIsInitialized() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#isPaused()}.
	 */
	@Test
	public void testIsPaused() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateBatch#getTasks()}.
	 */
	@Test
	public void testGetTasks() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateBatch#getCompletedTaskCount()}.
	 */
	@Test
	public void testGetCompletedTaskCount() {
		assertTrue("Not yet implemented", true);
	}
}
