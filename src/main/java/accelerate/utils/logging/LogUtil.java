package accelerate.utils.logging;

import static accelerate.utils.JSONUtil.serialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import accelerate.utils.JSONUtil;
import accelerate.utils.bean.DataBean;
import accelerate.utils.exception.AccelerateException;

/**
 * Class providing utility logging methods
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public class LogUtil {
	/**
	 * @param aLogger
	 * @param aLogLevel
	 * @param aRequest
	 * @throws AccelerateException
	 *             thrown due to {@link JSONUtil#serialize(Object)}
	 */
	public static void logRequest(Logger aLogger, Level aLogLevel, HttpServletRequest aRequest)
			throws AccelerateException {
		if (!checkLogger(aLogger, aLogLevel)) {
			return;
		}

		log(aLogger, aLogLevel, null, serialize(aRequest.getParameterMap()));
	}

	/**
	 * @param aLogger
	 * @param aLogLevel
	 * @param aBean
	 * @throws AccelerateException
	 *             thrown due to {@link JSONUtil#serialize(Object)}
	 */
	public static void logBean(Logger aLogger, Level aLogLevel, Object aBean) throws AccelerateException {
		if (!checkLogger(aLogger, aLogLevel)) {
			return;
		}

		String message = null;
		if (aBean instanceof DataBean) {
			message = ((DataBean) aBean).toJSON();
		} else {
			message = serialize(aBean);
		}

		log(aLogger, aLogLevel, null, message);
	}

	/**
	 * @param aLogger
	 * @param aLogLevel
	 * @param aError
	 * @param aMessage
	 * @param aArgs
	 */
	public static void log(Logger aLogger, Level aLogLevel, Throwable aError, String aMessage, Object... aArgs) {
		if (!checkLogger(aLogger, aLogLevel)) {
			return;
		}

		Object args = aArgs;

		// if provided add exception to arg list
		if (aError != null) {
			List<Object> argList = (args == null) ? new ArrayList<>() : Arrays.asList(aArgs);
			argList.add(aError);
			args = argList.toArray();
		}

		switch (aLogLevel) {
		case TRACE:
			aLogger.trace(aMessage, args);
			break;
		case DEBUG:
			aLogger.debug(aMessage, args);
			break;
		case INFO:
			aLogger.info(aMessage, args);
			break;
		case WARN:
			aLogger.warn(aMessage, args);
			break;
		case ERROR:
			aLogger.error(aMessage, args);
			break;
		}
	}

	/**
	 * @param aLogger
	 * @param aLogLevel
	 * @return
	 */
	public static boolean checkLogger(Logger aLogger, Level aLogLevel) {
		switch (aLogLevel) {
		case TRACE:
			return aLogger.isTraceEnabled();
		case DEBUG:
			return aLogger.isDebugEnabled();
		case INFO:
			return aLogger.isInfoEnabled();
		case WARN:
			return aLogger.isWarnEnabled();
		case ERROR:
			return aLogger.isErrorEnabled();
		}

		return false;
	}
}