package accelerate.utils.listener;

import static accelerate.utils.CommonConstants.COMMA_CHAR;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import accelerate.utils.ReflectionUtil;
import accelerate.utils.bean.DataMap;
import accelerate.utils.cache.AccelerateCache;
import accelerate.utils.exception.AccelerateException;
import accelerate.utils.logging.Log;
import accelerate.utils.logging.LoggerAspect;

/**
 * Class providing static access to spring components
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@Component
public class StaticListenerUtil implements ApplicationListener<ApplicationReadyEvent>, Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Logger} instance
	 */
	private static final Logger _LOGGER = LoggerFactory.getLogger(StaticListenerUtil.class);

	/**
	 * static {@link ApplicationContext} instance, to provide spring beans access to
	 * all classes.
	 */
	@Autowired
	private transient ApplicationContext applicationContext = null;

	/**
	 * list of base packages to scan for static listeners
	 */
	@Value("${accelerate.utils.staticListener.enabled:false}")
	private boolean enabled;

	/**
	 * list of base packages to scan for static listeners
	 */
	@Value("${accelerate.utils.staticListener.scanBasePackages:}")
	private String scanBasePackages;

	/**
	 * {@link Set} of packages derived from {@link #scanBasePackages}
	 */
	private Set<String> scanBasePackagesSet = null;

	/**
	 * List of classes annotated with @AccelerateContextListener. Static reference
	 * is stores to avoid scanning classpath multiple times as it is an expensive
	 * operation
	 */
	private Map<String, Map<String, String>> staticContextListeners = null;

	/**
	 * List of classes annotated with @StaticCacheListener. Static reference is
	 * stores to avoid scanning classpath multiple times as it is an expensive
	 * operation
	 */
	private Map<String, Map<String, String>> staticCacheListeners = null;

	/**
	 * @throws AccelerateException
	 *             thrown due to {@link #initializeContextListenerMap()} and
	 *             {@link #initializeCacheListenerMap()}
	 * 
	 */
	@PostConstruct
	public void initialize() {
		Exception methodError = null;

		try {
			if (!this.enabled) {
				_LOGGER.debug("staticListenerUtil not enabled, listeners will not be scanned");
			}

			this.scanBasePackagesSet = new HashSet<>(
					Arrays.asList(StringUtils.split(this.scanBasePackages, COMMA_CHAR)));
			this.scanBasePackagesSet.add("accelerate");
			_LOGGER.debug("scanBasePackagesSet: {}", this.scanBasePackagesSet);

			initializeContextListenerMap();
			initializeCacheListenerMap();
		} catch (Exception error) {
			methodError = error;
			AccelerateException.checkAndThrow(error, "Error in initializing StaticListenerUtil");
		} finally {
			LoggerAspect.logMethodExit(String.format("%s.%s", this.getClass().getName(), "initialize()"), methodError);
		}
	}

	/**
	 * This method is called to notify that the application is shutting down or the
	 * context has been destroyed.
	 */
	@PreDestroy
	public void destroy() {
		notifyContextListener("onContextClosed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	/**
	 * This method initializes Accelerate components to register that a new
	 * {@link BeanFactory} has been initialized. It notifies any custom
	 * implementation of {@link StaticContextListener} provided by the application.
	 * It then notifies any static classes that maybe registered as a listener for
	 * spring context initialization or {@link AccelerateCache} reload event.
	 * 
	 * @param aEvent
	 */
	@Override
	@Log
	public void onApplicationEvent(ApplicationReadyEvent aEvent) {
		notifyContextListener("onContextStarted");
	}

	/**
	 * @throws AccelerateException
	 *             thrown due to
	 *             {@link #getAnnotationAttributes(BeanDefinition, Class)}
	 */
	private void initializeContextListenerMap() throws AccelerateException {
		this.staticContextListeners = findCandidateComponents(StaticContextListener.class).stream()
				.flatMap(beanDefinition -> {
					_LOGGER.debug("Registering StaticContextListener [{}]", beanDefinition.getBeanClassName());

					AnnotationAttributes annotationAttributes = getAnnotationAttributes(beanDefinition,
							StaticContextListener.class);
					return Stream.of(
							DataMap.buildMap("event", "onContextStarted", "listenerClass",
									beanDefinition.getBeanClassName(), "handleMethod",
									annotationAttributes.getString("onContextStarted")),
							DataMap.buildMap("event", "onContextClosed", "listenerClass",
									beanDefinition.getBeanClassName(), "handleMethod",
									annotationAttributes.getString("onContextClosed")));
				}).collect(Collectors.groupingBy(map -> map.get("event").toString(), () -> new HashMap<>(), Collectors
						.toMap(map -> map.get("listenerClass").toString(), map -> map.get("handleMethod").toString())));
	}

	/**
	 * @throws AccelerateException
	 *             thrown due to
	 *             {@link #getAnnotationAttributes(BeanDefinition, Class)}
	 */
	private void initializeCacheListenerMap() throws AccelerateException {
		this.staticCacheListeners = findCandidateComponents(StaticCacheListener.class).stream().map(beanDefinition -> {
			_LOGGER.debug("Registering StaticCacheListener [{}]", beanDefinition.getBeanClassName());

			AnnotationAttributes annotationAttributes = getAnnotationAttributes(beanDefinition,
					StaticCacheListener.class);
			return DataMap.buildMap("cacheName", annotationAttributes.getString("name"), "listenerClass",
					beanDefinition.getBeanClassName(), "handleMethod", annotationAttributes.getString("handler"));
		}).collect(Collectors.groupingBy(map -> map.get("cacheName").toString(), () -> new HashMap<>(), Collectors
				.toMap(map -> map.get("listenerClass").toString(), map -> map.get("handleMethod").toString())));
	}

	/**
	 * @param aAnnotationType
	 * @return
	 */
	private Set<BeanDefinition> findCandidateComponents(Class<? extends Annotation> aAnnotationType) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(aAnnotationType));

		Set<BeanDefinition> componentSet = new HashSet<>();
		this.scanBasePackagesSet.stream().parallel()
				.forEach(packageName -> componentSet.addAll(provider.findCandidateComponents(packageName)));
		return componentSet;
	}

	/**
	 * @param aBeanDefinition
	 * @param aAnnotationType
	 * @return
	 * @throws AccelerateException
	 *             thrown due to
	 *             {@link ReflectionUtil#getFieldValue(Class, Object, String)}
	 */
	private static AnnotationAttributes getAnnotationAttributes(BeanDefinition aBeanDefinition,
			Class<? extends Annotation> aAnnotationType) throws AccelerateException {
		Object metadata = ReflectionUtil.getFieldValue(aBeanDefinition.getClass(), aBeanDefinition, "metadata");
		@SuppressWarnings("unchecked")
		Map<String, LinkedList<AnnotationAttributes>> attributesMap = (Map<String, LinkedList<AnnotationAttributes>>) ReflectionUtil
				.getFieldValue(metadata.getClass(), metadata, "attributesMap");
		return attributesMap.get(aAnnotationType.getName()).get(0);
	}

	/**
	 * @param aContextEvent
	 */
	private void notifyContextListener(String aContextEvent) {
		Map<String, String> listenerMap = this.staticContextListeners.get(aContextEvent);
		if (ObjectUtils.isEmpty(listenerMap)) {
			return;
		}

		listenerMap.forEach((aClassName, aHandleMethod) -> {
			try {
				Class<?> targetClass = Class.forName(aClassName);
				ReflectionUtil.invokeMethod(targetClass, null, aHandleMethod,
						new Class<?>[] { ApplicationContext.class }, new Object[] { this.applicationContext });
			} catch (Exception error) {
				AccelerateException.checkAndThrow(error);
			}
		});
	}

	/**
	 * @param aCache
	 */
	@Log
	public void notifyCacheLoad(AccelerateCache<?, ?> aCache) {
		Map<String, String> listenerMap = this.staticCacheListeners.get(aCache.name());
		if (ObjectUtils.isEmpty(listenerMap)) {
			return;
		}

		listenerMap.forEach((aClassName, aHandleMethod) -> {
			try {
				Class<?> targetClass = Class.forName(aClassName);
				ReflectionUtil.invokeMethod(targetClass, null, aHandleMethod, new Class<?>[] { aCache.getClass() },
						new Object[] { aCache });
			} catch (Exception error) {
				AccelerateException.checkAndThrow(error);
			}
		});
	}
}