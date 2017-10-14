package accelerate.utils.logging;

import static org.junit.Assert.assertTrue;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;
import accelerate.utils.bean.DataMap;

/**
 * JUnit test for {@link LoggerAspect}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 07, 2017
 */
@SpringBootTest(classes = AccelerateUtilsTest.class)
@RunWith(SpringRunner.class)
@Import(LoggerAspectTestComponent.class)
@SuppressWarnings("static-method")
public class LoggerAspectTest {
	/**
	 *
	 */
	@Autowired
	private LoggerAspectTestComponent testComponent;

	/**
	 *
	 */
	private static PrintStream _syserr = null;

	/**
	 * 
	 */
	@Before
	public void setupTest() {
		_syserr = System.err;
	}

	/**
	 * 
	 */
	@After
	public void cleanupTest() {
		System.setErr(_syserr);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#catchMethodInAnnotatedClass(org.aspectj.lang.ProceedingJoinPoint)}.
	 */
	@Test
	public void testCatchMethodInAnnotatedClass() {
		this.testComponent.method4("method4", DataMap.buildMap("value2Key", "method4"));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#catchAnnotatedMethod(org.aspectj.lang.ProceedingJoinPoint, accelerate.utils.logging.Log)}.
	 */
	@Test
	public void testCatchAnnotatedMethod() {
		this.testComponent.method1();
		this.testComponent.method2(DataMap.buildMap("name", "method2"));
		this.testComponent.method3();
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#logInputParams(java.lang.String, java.lang.Object[])}.
	 */
	// @Test
	public void testLogInputParams() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#logMethodEntry(java.lang.String)}.
	 */
	// @Test
	public void testLogMethodEntry() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#logMethodTime(java.lang.String, org.springframework.util.StopWatch)}.
	 */
	// @Test
	public void testLogMethodTime() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#logMethodExit(java.lang.String, java.lang.Throwable)}.
	 */
	// @Test
	public void testLogMethodExit() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.logging.LoggerAspect#logReturnValue(java.lang.String, java.lang.String, java.lang.Object)}.
	 */
	// @Test
	public void testLogReturnValue() {
		assertTrue("Not yet implemented", true);
	}
}
