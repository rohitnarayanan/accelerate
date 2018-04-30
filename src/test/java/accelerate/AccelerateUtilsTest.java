package accelerate;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import accelerate.utils.NIOUtil;

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
			i = 1;
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
		Path PATH = Paths.get("C:/Temp/test.txt");
		System.out.println(NIOUtil.getFileName(PATH));
		System.out.println(NIOUtil.getBaseName(PATH));
		System.out.println(NIOUtil.getFileExtn(PATH));
	}

	/**
	 * Method to run spring boot application
	 */
	private static void runApplication() {
		SpringApplication.run(AccelerateUtilsTest.class);
	}
}
