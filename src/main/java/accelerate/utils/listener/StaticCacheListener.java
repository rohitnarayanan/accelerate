package accelerate.utils.listener;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import accelerate.utils.cache.AccelerateCache;

/**
 * This annotation provides non spring managed classes, static access to
 * {@link AccelerateCache} instances
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@Retention(RUNTIME)
@Target(value = { TYPE })
@Documented
public @interface StaticCacheListener {
	/**
	 * Name of the cache the annotated class is listening to
	 *
	 * @return
	 */
	public abstract String name();

	/**
	 * Name of the method that will handle the callback
	 *
	 * @return
	 */
	public abstract String handler() default "handleCacheLoad";
}
