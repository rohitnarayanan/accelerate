package accelerate.utils;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import accelerate.AccelerateUtilsTest;
import accelerate.utils.batch.TestBatchConfig;

/**
 * JUnit test for {@link NIOUtil}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since December 11, 2017
 */
@SpringBootTest(classes = { AccelerateUtilsTest.class, TestBatchConfig.class })
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class NIOUtilTest {
	/**
	 * {@link Path} variable for test
	 */
	private static final Path PATH = Paths.get("C:\\Temp\\test.txt");

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#getPathString(java.nio.file.Path)}.
	 */
	@Test
	public void testGetPathString() {
		Assert.assertEquals("Path not in UNIX format", "C:/Temp/test.txt", NIOUtil.getPathString(PATH));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#getFileName(java.nio.file.Path)}.
	 */
	@Test
	public void testGetFileName() {
		Assert.assertEquals("Incorrect file name", "test.txt", NIOUtil.getFileName(PATH));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#getBaseName(java.nio.file.Path)}.
	 */
	@Test
	public void testGetBaseName() {
		Assert.assertEquals("Incorrect file basename", "test", NIOUtil.getBaseName(PATH));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#getFileExtn(java.nio.file.Path)}.
	 */
	@Test
	public void testGetFileExtn() {
		Assert.assertEquals("Incorrect file extension", "txt", NIOUtil.getFileExtn(PATH));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#getRelativePath(java.nio.file.Path, java.nio.file.Path)}.
	 */
	@Test
	public void testGetRelativePath() {
		Assert.assertEquals("Incorrect relative path", "Temp/test.txt",
				NIOUtil.getRelativePath(Paths.get("C:\\"), PATH));
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#searchByName(java.nio.file.Path, java.lang.String)}.
	 */
	@Test
	public void testSearchByName() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#searchByExtn(java.nio.file.Path, java.lang.String)}.
	 */
	@Test
	public void testSearchByExtn() {
		assertTrue("Not yet implemented", true);
	}

	/**
	 * Test method for
	 * {@link accelerate.utils.NIOUtil#walkFileTree(java.nio.file.Path, java.util.function.Function, java.util.function.BiFunction, java.util.function.BiFunction, java.util.function.Function, java.util.function.Function, java.util.function.BiFunction)}.
	 */
	@Test
	public void testWalkFileTree() {
		assertTrue("Not yet implemented", true);
	}
}
