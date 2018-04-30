package accelerate.utils.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * Configuration to auto-wire {@link Logger} instances for fields annotated by
 * {@link AutowireLogger}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since December 11, 2017
 */
@Profile("accelerate.logging")
@Component
public class LoggerConfig implements BeanPostProcessor {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	/**
	 * @param aBean
	 * @param aBeanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessBeforeInitialization(Object aBean, String aBeanName) throws BeansException {
		return aBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	/**
	 * @param aBean
	 * @param aBeanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object aBean, String aBeanName) throws BeansException {
		ReflectionUtils.doWithFields(aBean.getClass(), aField -> {
			ReflectionUtils.makeAccessible(aField);
			if (aField.getAnnotation(AutowireLogger.class) != null) {
				Logger logger = LoggerFactory.getLogger(aBean.getClass());
				aField.set(aBean, logger);
			}
		});

		return aBean;
	}
}
