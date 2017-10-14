package accelerate.utils.batch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;

/**
 * JUnit test for {@link AccelerateTask}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
@SpringBootTest(classes = { AccelerateUtilsTest.class, TestBatchConfig.class })
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class AccelerateTaskTest {
	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#AccelerateTask(java.lang.String)}.
	 */
	@Test
	public void testAccelerateTask() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#registerPostProcessor(java.util.function.Consumer)}.
	 */
	@Test
	public void testRegisterPostProcessor() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#submitted(java.util.concurrent.Future)}.
	 */
	@Test
	public void testSubmitted() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#pause(java.lang.Object)}.
	 */
	@Test
	public void testPause() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#resume()}.
	 */
	@Test
	public void testResume() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#waitForCompletion()}.
	 */
	@Test
	public void testWaitForCompletion() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#call()}.
	 */
	@Test
	public void testCall() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#execute()}.
	 */
	@Test
	public void testExecute() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#checkPause()}.
	 */
	@Test
	public void testCheckPause() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getTaskKey()}.
	 */
	@Test
	public void testGetTaskKey() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#getPostProccessor()}.
	 */
	@Test
	public void testGetPostProccessor() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.batch.AccelerateTask#getSubmitTime()}.
	 */
	@Test
	public void testGetSubmitTime() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getStartTime()}.
	 */
	@Test
	public void testGetStartTime() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getEndTime()}.
	 */
	@Test
	public void testGetEndTime() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#isExecuting()}.
	 */
	@Test
	public void testIsExecuting() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#isComplete()}.
	 */
	@Test
	public void testIsComplete() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getTaskError()}.
	 */
	@Test
	public void testGetTaskError() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#isPause()}.
	 */
	@Test
	public void testIsPause() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getMonitor()}.
	 */
	@Test
	public void testGetMonitor() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for {@link accelerate.utils.batch.AccelerateTask#getThread()}.
	 */
	@Test
	public void testGetThread() {
		assertTrue("Not yet implemented", true);
	}
}
