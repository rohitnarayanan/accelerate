package accelerate.utils.exception;

import org.springframework.core.NestedRuntimeException;

import accelerate.utils.bean.DataMap;

/**
 * This is a simple wrapper exception class that is used by the Accelerate
 * library.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public class AccelerateException extends NestedRuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * custom data to be attached to the exception
	 */
	private DataMap dataMap = null;

	/**
	 * Default Constructor
	 *
	 * @param aCause
	 */
	public AccelerateException(Throwable aCause) {
		super(aCause.getMessage(), aCause);
	}

	/**
	 * Overloaded Constructor
	 *
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public AccelerateException(String aMessage, Object... aMessageArgs) {
		super(String.format(aMessage, aMessageArgs));
	}

	/**
	 * Overloaded Constructor
	 * 
	 * @param aCause
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public AccelerateException(Throwable aCause, String aMessage, Object... aMessageArgs) {
		super(String.format(aMessage, aMessageArgs), aCause);
	}

	/**
	 * @param <K>
	 *            Generic return type
	 * @param aError
	 *            see {@link #checkAndThrow(Exception, String, Object...)}
	 * @return see {@link #checkAndThrow(Exception, String, Object...)}
	 * @throws AccelerateException
	 *             checks instance throws original or wrapped exception
	 */
	public static <K> K checkAndThrow(Exception aError) throws AccelerateException {
		throw (aError instanceof AccelerateException) ? (AccelerateException) aError : new AccelerateException(aError);
	}

	/**
	 * @param <K>
	 *            Generic return type
	 * @param aError
	 * @param aMessage
	 * @param aMessageArgs
	 * @return need a return clause to satify compiler checks when this method gets
	 *         calls from within catch block of methods with a return clause
	 * @throws AccelerateException
	 *             checks instance throws original or wrapped exception
	 */
	public static <K> K checkAndThrow(Exception aError, String aMessage, Object... aMessageArgs)
			throws AccelerateException {
		throw (aError instanceof AccelerateException) ? (AccelerateException) aError
				: new AccelerateException(aError, aMessage, aMessageArgs);
	}

	/**
	 * Getter method for "dataMap" property
	 *
	 * @return dataMap
	 */
	public DataMap getDataMap() {
		return this.dataMap;
	}

	/**
	 * Setter method for "dataMap" property
	 *
	 * @param aDataMap
	 */
	public void setDataMap(DataMap aDataMap) {
		this.dataMap = aDataMap;
	}
}
