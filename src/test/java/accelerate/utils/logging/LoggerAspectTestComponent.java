package accelerate.utils.logging;

import org.springframework.boot.test.context.TestComponent;

import accelerate.utils.bean.DataMap;
import accelerate.utils.logging.Log.LogType;

/**
 * {@link TestComponent} for {@link LoggerAspect}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 6, 2017
 */
@Log(LogType.ALL)
@TestComponent
@SuppressWarnings({ "unused", "static-method" })
public class LoggerAspectTestComponent {
	/**
	 * 
	 */
	@Log
	public void method1() {
		// blank impl
	}

	/**
	 * @param aParam
	 */
	@Log(LogType.DEBUG_INPUT)
	public void method2(Object aParam) {
		// blank impl
	}

	/**
	 * @return
	 */
	@Log(log = { LogType.DEBUG_INPUT, LogType.METRICS })
	public Object method3() {
		return DataMap.buildMap("name", "method3");
	}

	/**
	 * @param aValue1
	 * @param aValue2
	 * @return
	 */
	public Object method4(String aValue1, Object aValue2) {
		return DataMap.buildMap("key1", aValue1, "key2", aValue2);
	}
}
