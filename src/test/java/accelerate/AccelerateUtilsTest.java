package accelerate;

import static accelerate.utils.CommonConstants.COMMA_CHAR;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import accelerate.utils.listener.StaticContextListener;

/**
 * Main test class for test.accelerate-utils
 *
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@SpringBootApplication
public class AccelerateUtilsTest {
	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		try {
			// i = 1;
			if (i > 0) {
				runApplication();
			} else {
				quickTest();
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	/**
	 * method to quickly test code
	 * 
	 * @throws Exception
	 */
	private static void quickTest() throws Exception {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(StaticContextListener.class));

		Set<BeanDefinition> componentSet = new HashSet<>();
		// provider.findCandidateComponents("accelerate.*")
		Arrays.stream(StringUtils.split("accelerate", COMMA_CHAR)).parallel().filter(packageName -> {
			System.err.println("filtering package: " + packageName);
			return true;// !CommonUtils.compare(packageName, "accelerate");
		}).forEach(packageName -> {
			System.err.println("scanning package: " + packageName);
			componentSet.addAll(provider.findCandidateComponents(packageName));
		});

		System.err.println("componentSet: " + componentSet.size());
	}

	/**
	 * Method to run spring boot application
	 */
	private static void runApplication() {
		// String[] arguments = new String[] { "--spring.profiles.active=none" };
		// SpringApplication springApplication = new
		// SpringApplication(AccelerateUtilsTest.class);
		// springApplication.setAdditionalProfiles("profile1", "profile2");
		// springApplication.run(arguments);

		SpringApplication.run(AccelerateUtilsTest.class);
	}
}
