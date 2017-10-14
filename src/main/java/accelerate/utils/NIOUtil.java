package accelerate.utils;

import static accelerate.utils.BasicConstants.EMPTY_STRING;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.utils.exception.AccelerateException;

/**
 * Class providing utility methods for java.nio.file operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class NIOUtil {
	/**
	 * 
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(NIOUtil.class);

	/**
	 * hidden constructor
	 */
	private NIOUtil() {
	}

	/**
	 * Method to consistently return path in unix format with '/' separator instead
	 * of '\\' used in windows.
	 * 
	 * @param aPath
	 * @return file name
	 */
	public static String getPathString(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		return org.springframework.util.StringUtils.cleanPath(aPath.toString());
	}

	/**
	 * @param aPath
	 * @return file name
	 */
	public static String getFileName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		return aPath.toFile().getName();
	}

	/**
	 * @param aPath
	 * @return file name
	 */
	public static String getBaseName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		return FilenameUtils.getBaseName(aPath.toFile().getName());
	}

	/**
	 * @param aPath
	 * @return file extension
	 */
	public static String getFileExtn(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		return FilenameUtils.getExtension(aPath.toFile().getName());
	}

	/**
	 * @param aFile
	 * @param aRoot
	 * @return short path
	 */
	public static String getRelativePath(Path aFile, Path aRoot) {
		if (aFile == null) {
			return EMPTY_STRING;
		}

		int rootNameCount = (aRoot == null) ? 0 : aRoot.getNameCount();
		return getPathString(aFile.subpath(rootNameCount, aFile.getNameCount()));
	}

	/**
	 * @param aRootPath
	 * @param aSelector
	 * @param aPreVisitDirectory
	 * @param aPostVisitDirectory
	 * @param aVisitFile
	 * @return
	 * @throws AccelerateException
	 *             {@link IOException} thrown by
	 *             {@link Files#walkFileTree(Path, FileVisitor)}
	 */
	public static Map<String, Path> walkFileTree(String aRootPath,
			final Function<Path, FileVisitResult> aPreVisitDirectory,
			final Function<Path, FileVisitResult> aPostVisitDirectory, final Function<Path, FileVisitResult> aVisitFile,
			final BiFunction<Path, FileVisitResult, Boolean> aSelector) throws AccelerateException {
		final Map<String, Path> fileMap = new TreeMap<>();
		final Path rootPath = Paths.get(aRootPath);

		try {
			Files.walkFileTree(rootPath, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) {
					if (CommonUtils.compare(NIOUtil.getPathString(aDir), NIOUtil.getPathString(rootPath))) {
						return FileVisitResult.CONTINUE;
					}

					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aPreVisitDirectory != null) {
						visitResult = aPreVisitDirectory.apply(aDir);
					}

					if (aSelector != null && aSelector.apply(aDir, visitResult)) {
						fileMap.put(getRelativePath(aDir, rootPath), aDir);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path aDir, IOException aError) throws IOException {
					if (aError != null) {
						throw aError;
					}

					if (aPostVisitDirectory != null) {
						return aPostVisitDirectory.apply(aDir);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) {
					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aVisitFile != null) {
						visitResult = aVisitFile.apply(aFile);
					}

					if (aSelector != null && aSelector.apply(aFile, visitResult)) {
						fileMap.put(getRelativePath(aFile, rootPath), aFile);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult visitFileFailed(Path aFile, IOException aError) throws IOException {
					if (aError != null) {
						throw aError;
					}

					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException error) {
			throw new AccelerateException(error);
		}

		return fileMap;
	}
}