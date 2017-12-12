package accelerate.utils.batch;

import static accelerate.utils.CollectionUtil.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;

import accelerate.utils.JSONUtil;
import accelerate.utils.bean.DataMap;
import accelerate.utils.exception.AccelerateException;

/**
 * Wrapper class providing easy-to-use version of spring's
 * {@link ThreadPoolTaskExecutor}
 *
 * @param <T>
 *            extension of {@link AccelerateTask} that this batch handles
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@ManagedResource(description = "Wrapper class providing easy-to-use version of spring's ThreadPoolTaskExecutor")
public class AccelerateBatch<T extends AccelerateTask> extends ThreadPoolTaskExecutor {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Logger} instance
	 */
	private static final Logger _LOGGER = LoggerFactory.getLogger(AccelerateBatch.class);

	/**
	 * Name of the Batch
	 */
	private String batchName = null;

	/**
	 * Flag to indicate whether this instance is initialized or not
	 */
	private boolean initialized = false;

	/**
	 * Flag to indicate whether the batch is currently paused
	 */
	private boolean paused = false;

	/**
	 * Semaphore for pausing the tasks
	 */
	private final Object monitor = new Object();

	/**
	 * Semaphore for pausing the tasks
	 */
	private Consumer<T> taskPostProcessor = null;

	/**
	 * {@link Map} of tasks submitted to the batch
	 */
	private final Map<String, T> tasks = new HashMap<>();

	/**
	 * Count of tasks processed by this batch
	 */
	protected long completedTaskCount = 0l;

	/**
	 * Default constructor
	 *
	 * @param aBatchName
	 * @param aThreadPoolSize
	 */
	public AccelerateBatch(String aBatchName, int aThreadPoolSize) {
		this(aBatchName, aThreadPoolSize, aThreadPoolSize);
	}

	/**
	 * Constructor 2
	 *
	 * @param aBatchName
	 * @param aThreadPoolSize
	 * @param aMaxPoolSize
	 */
	public AccelerateBatch(String aBatchName, int aThreadPoolSize, int aMaxPoolSize) {
		this.batchName = aBatchName;
		setCorePoolSize(aThreadPoolSize);
		setMaxPoolSize(aMaxPoolSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#
	 * initialize()
	 */
	/**
	 */
	@Override
	@ManagedOperation(description = "This methods activates the batch")
	public synchronized void initialize() {
		Assert.state(!this.initialized, "Batch already initialized");

		setThreadGroupName(this.batchName);
		setThreadNamePrefix("AccelerateTask");

		super.initialize();
		this.completedTaskCount = 0l;
		this.initialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#
	 * shutdown()
	 */
	/**
	 */
	@Override
	@ManagedOperation(description = "This method shuts down the current batch instance")
	public void shutdown() {
		Assert.state(this.initialized, "Batch not initialized");
		super.shutdown();
	}

	/**
	 * This method shuts down the current batch instance and blocks the caller till
	 * it shuts down or the timeout provided elapses, whichever is earlier.
	 *
	 * @param aTimeUnit
	 * @param aTimeout
	 */
	@ManagedOperation(description = "This method shuts down the current batch instance and blocks the caller till it shuts down or the timeout provided elapses, whichever is earlier.")
	public synchronized void shutdown(TimeUnit aTimeUnit, long aTimeout) {
		Assert.state(this.initialized, "Batch not initialized");

		_LOGGER.debug("Shutdown requested in [{}] seconds", aTimeUnit.toSeconds(aTimeout));
		setAwaitTerminationSeconds((int) aTimeUnit.toSeconds(aTimeout));
		shutdown();
	}

	/**
	 * @param aConsumer
	 */
	public final synchronized void registerTaskPostProcessor(Consumer<T> aConsumer) {
		this.taskPostProcessor = aConsumer;
	}

	/**
	 * This method handles a pause interrupt via by JMX and attempts to pause the
	 * execution of all running tasks
	 */
	@ManagedOperation(description = "This method handles a pause interrupt sent by JMX and attempts to pause the execution of all running tasks")
	public synchronized void pause() {
		Assert.state(this.initialized, "Batch not initialized");

		_LOGGER.debug("Pausing [{}] tasks ", this.tasks.size());
		this.paused = true;
		this.tasks.values().forEach(task -> task.pause(this.monitor));
	}

	/**
	 * This method handles a resume interrupt via by the JMX and attempts to resume
	 * the execution of all paused tasks
	 */
	@ManagedOperation(description = "This method handles a resume interrupt via by the JMX and attempts to resume the execution of all paused tasks")
	public synchronized void resume() {
		Assert.state(this.initialized, "Batch not initialized");

		_LOGGER.debug("Resuming [{}] tasks ", this.tasks.size());
		this.paused = false;
		this.tasks.values().forEach(task -> task.resume());

		synchronized (this.monitor) {
			this.monitor.notifyAll();
		}
	}

	/**
	 * This method returns JSON string with basic status information on the batch
	 *
	 * @return JSON string
	 * @throws AccelerateException
	 *             wrapping {@link JsonProcessingException} thrown from
	 *             {@link JSONUtil#serialize(Object)}
	 */
	@ManagedOperation(description = "This method returns JSON string with basic status information on the batch")
	public String getStatus() throws AccelerateException {
		DataMap dataBean = DataMap.buildMap("1.name", this.batchName, "2.corePoolSize", getCorePoolSize(),
				"3.maxPoolSize", getMaxPoolSize(), "4.initialized", this.initialized, "5.completedTasks",
				this.completedTaskCount, "6.taskCount", this.tasks.size());

		return JSONUtil.serialize(dataBean);
	}

	/**
	 * @param aTaskList
	 */
	@SafeVarargs
	public final void submitTasks(T... aTaskList) {
		this.submitTasks(toList(aTaskList));
	}

	/**
	 * @param aTaskList
	 */
	@SuppressWarnings("unchecked")
	public final synchronized void submitTasks(List<T> aTaskList) {
		Assert.state(this.initialized, "Batch not initialized");
		Assert.notEmpty(aTaskList, "No tasks provided");

		_LOGGER.debug("Submitting [{}] tasks ", aTaskList.size());

		aTaskList.stream().forEach(task -> {
			this.tasks.put(task.getTaskKey(), task);

			task.registerPostProcessor(_task -> {
				this.tasks.remove(_task.getTaskKey());
				this.completedTaskCount++;
				if (this.taskPostProcessor != null) {
					this.taskPostProcessor.accept((T) _task);
				}
			});

			if (this.paused) {
				task.pause(this.monitor);
			}

			task.submitted(super.submit(task));
		});
	}

	/**
	 * Getter method for "batchName" property
	 * 
	 * @return batchName
	 */
	public String getBatchName() {
		return this.batchName;
	}

	/**
	 * Getter method for "initialized" property
	 * 
	 * @return initialized
	 */
	public boolean isInitialized() {
		return this.initialized;
	}

	/**
	 * Getter method for "paused" property
	 * 
	 * @return paused
	 */
	public boolean isPaused() {
		return this.paused;
	}

	/**
	 * Getter method for "tasks" property
	 * 
	 * @return tasks
	 */
	public Map<String, T> getTasks() {
		return this.tasks;
	}

	/**
	 * Getter method for "completedTaskCount" property
	 * 
	 * @return completedTaskCount
	 */
	public long getCompletedTaskCount() {
		return this.completedTaskCount;
	}
}