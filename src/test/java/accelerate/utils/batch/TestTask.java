package accelerate.utils.batch;

import accelerate.utils.batch.AccelerateTask;
import accelerate.utils.exception.AccelerateException;

/**
 * {@link AccelerateTask} implementation for testing
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 06, 2017
 */
public class TestTask extends AccelerateTask {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public TestTask() {
		super("TestTask");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.accelerate.utils.batch.AccelerateTask#execute()
	 */
	/**
	 * @throws AccelerateException
	 */
	@Override
	protected void execute() throws AccelerateException {
		System.err.println("TestTask.execute()");
	}
}
