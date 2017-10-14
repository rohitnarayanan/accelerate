package accelerate.utils.logging;

import static accelerate.utils.BasicConstants.SPACE_CHAR;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import accelerate.utils.CommonUtils;
import accelerate.utils.JSONUtil;
import accelerate.utils.logging.Log.LogType;

/**
 * Spring {@link Aspect} to allow applications to log various information for
 * auditing, profiling, and debugging purposes.
 * 
 * NOTE: As spring aspects provide load time weaving only, this class is able to
 * capture method calls on spring managed beans. It does not capture
 * constructors, or internal method calls.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty("accelerate.utils.logging.enabled")
public class LoggerAspect {
	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

	/**
	 *
	 */
	@Value("${accelerate.utils.logging.audit:false}")
	private boolean auditFlag = false;

	/**
	 *
	 */
	@Value("${accelerate.utils.logging.metrics:false}")
	private boolean metricsFlag = false;

	/**
	 *
	 */
	@Value("${accelerate.utils.logging.debug:false}")
	private boolean debugFlag = false;

	/**
	 * This method catches execution of all methods that belong to a class annotated
	 * with {@link Log}. It filters out methods that are themselves annotated with
	 * {@link Log} as those will be handled by
	 * {@link #catchAnnotatedMethod(ProceedingJoinPoint, Log)}
	 * 
	 * @param aJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* *.*(..)) && within(@accelerate.utils.logging.Log *) && !@annotation(accelerate.utils.logging.Log)")
	public Object catchMethodInAnnotatedClass(ProceedingJoinPoint aJoinPoint) throws Throwable {
		System.err.println("+++" + aJoinPoint.getSignature());

		return log(aJoinPoint, aJoinPoint.getTarget().getClass().getDeclaredAnnotation(Log.class));
	}

	/**
	 * This method catches execution of all methods that belong to a class annotated
	 * with {@link Log}
	 * 
	 * @param aJoinPoint
	 * @param aLog
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* *.*(..)) && @annotation(aLog)")
	public Object catchAnnotatedMethod(ProceedingJoinPoint aJoinPoint, Log aLog) throws Throwable {
		return log(aJoinPoint, aLog);
	}

	/**
	 * This method logs various information of the annotated method based on the
	 * parameters passed to {@link Log}.
	 * 
	 * @param aJoinPoint
	 *            the {@link JoinPoint} caught by the {@link Around} advice
	 * @param aLog
	 * @return the object returned by the target method
	 * @throws Throwable
	 */
	public Object log(ProceedingJoinPoint aJoinPoint, Log aLog) throws Throwable {
		if (!LOGGER.isDebugEnabled()) {
			// System.err.println(String.format("[%s] [%s] [%s]", aJoinPoint.getSignature(),
			// aLoggable.value(),
			// JSONUtil.serialize(aLoggable.log())));
			return aJoinPoint.proceed();
		}

		Throwable error = null;
		Object returnObject = null;
		boolean[] flags = { false, false, false, false };
		String[] signature = StringUtils.split(aJoinPoint.getSignature().toString(), SPACE_CHAR);
		String returnType = signature[0];
		String methodName = signature[1];
		Set<LogType> logTypes = new HashSet<>(Arrays.asList(aLog.log()));
		if (logTypes.isEmpty()) {
			logTypes.add(aLog.value());
		}

		for (Log.LogType logType : logTypes) {
			if (this.debugFlag && CommonUtils.compareAny(logType, LogType.DEBUG_INPUT, LogType.ALL)) {
				flags[0] = true;
			}

			if (this.auditFlag && CommonUtils.compareAny(logType, LogType.AUDIT, LogType.PROFILE, LogType.ALL)) {
				flags[1] = true;
			}

			if (this.metricsFlag && CommonUtils.compareAny(logType, LogType.METRICS, LogType.PROFILE, LogType.ALL)) {
				flags[2] = true;
			}

			if (this.debugFlag && CommonUtils.compareAny(logType, LogType.DEBUG_OUTPUT, LogType.ALL)) {
				flags[3] = true;
			}
		}

		if (flags[0]) {
			logInputParams(methodName, aJoinPoint.getArgs());
		}

		if (flags[1]) {
			logMethodEntry(methodName);
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			returnObject = aJoinPoint.proceed();
		} catch (Throwable throwable) {
			error = throwable;
		}
		stopWatch.stop();

		if (flags[2]) {
			logMethodTime(methodName, stopWatch);
		}

		if (flags[1]) {
			logMethodExit(methodName, error);
		}

		if (error != null) {
			throw error;
		}

		if (flags[3]) {
			logReturnValue(methodName, returnType, returnObject);
		}

		return returnObject;
	}

	/**
	 * @param aMethodName
	 * @param aInputParams
	 */
	public static final void logInputParams(String aMethodName, Object[] aInputParams) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		LOGGER.debug("{},{},{}", aMethodName, "INPUT",
				(aInputParams.length == 0) ? "[]" : JSONUtil.serialize(aInputParams));
	}

	/**
	 * @param aMethodName
	 */
	public static final void logMethodEntry(String aMethodName) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		LOGGER.debug("{},{},{}", aMethodName, "START", System.currentTimeMillis());
	}

	/**
	 * @param aMethodName
	 * @param aStopWatch
	 */
	public static final void logMethodTime(String aMethodName, StopWatch aStopWatch) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		LOGGER.debug("{},{},{}", aMethodName, "TIME", aStopWatch.getTotalTimeMillis());
	}

	/**
	 * @param aMethodName
	 * @param aError
	 */
	public static final void logMethodExit(String aMethodName, Throwable aError) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		LOGGER.debug("{},{},{}", aMethodName, (aError != null) ? "EXIT" : "END", System.currentTimeMillis());
	}

	/**
	 * @param aMethodName
	 * @param aReturnType
	 * @param aReturnValue
	 */
	public static final void logReturnValue(String aMethodName, String aReturnType, Object aReturnValue) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		LOGGER.debug("{},{},{}", aMethodName, "RETURN",
				CommonUtils.compare("void", aReturnType) ? "VOID" : JSONUtil.serialize(aReturnValue));
	}
}