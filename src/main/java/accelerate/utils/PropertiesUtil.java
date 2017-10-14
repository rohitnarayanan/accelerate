package accelerate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;

import accelerate.utils.exception.AccelerateException;

/**
 * Class providing utility methods for {@link Properties} operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class PropertiesUtil {
	/**
	 * hidden constructor
	 */
	private PropertiesUtil() {
	}

	/**
	 * @param aApplicationContext
	 * @param aConfigPath
	 *            URL/Path to the config file.
	 * @return Property Map
	 * @throws AccelerateException
	 */
	public static Properties LoadProperties(ApplicationContext aApplicationContext, String aConfigPath)
			throws AccelerateException {
		try {
			return LoadProperties(aApplicationContext.getResource(aConfigPath).getInputStream());
		} catch (IOException error) {
			throw new AccelerateException(error);
		}
	}

	/**
	 * @param aInputStream
	 *            {@link InputStream} instance to be loaded
	 * @return {@link Properties} instance
	 * @throws AccelerateException
	 */
	public static Properties LoadProperties(InputStream aInputStream) throws AccelerateException {
		try {
			Properties properties = new Properties();
			properties.load(aInputStream);
			return properties;
		} catch (IOException error) {
			throw new AccelerateException(error);
		} finally {
			IOUtils.closeQuietly(aInputStream);
		}
	}
}