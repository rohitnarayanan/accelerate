package accelerate.utils.exception;

/**
 * This is an extension of {@link AccelerateException} class. Its main purpose
 * is to allow developers to skip code blocks and manage code flow instead of
 * writing verbose conditional statements.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public class FlowControlException extends AccelerateException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 * 
	 * @param aMessage
	 */
	public FlowControlException(String aMessage) {
		super(aMessage);
	}
}