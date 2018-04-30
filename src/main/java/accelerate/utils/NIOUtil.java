package accelerate.utils;

import static accelerate.utils.CommonConstants.DOT_CHAR;
import static accelerate.utils.CommonConstants.EMPTY_STRING;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

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
	 * {@link Logger} instance
	 */
	private static final Logger _LOGGER = LoggerFactory.getLogger(NIOUtil.class);

	/**
	 * hidden constructor
	 */
	private NIOUtil() {
	}

	/**
	 * Method to consistently return path in UNIX format with '/' separator instead
	 * of '\\' used in windows.
	 * 
	 * @param aPath
	 * @return Path String in UNIX format
	 */
	public static String getPathString(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String pathString = org.springframework.util.StringUtils.cleanPath(aPath.toString());
		_LOGGER.trace("getPathString: [{}] [{}]", aPath, pathString);

		return pathString;
	}

	/**
	 * @param aPath
	 * @return File name
	 */
	public static String getFileName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = aPath.getFileName().toString();
		_LOGGER.trace("getFileName: [{}] [{}]", aPath, fileName);

		return fileName;
	}

	/**
	 * @param aPath
	 * @return File name without extension
	 */
	public static String getBaseName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = getFileName(aPath);
		int extnIndex = fileName.lastIndexOf(CommonConstants.DOT_CHAR);
		extnIndex = (extnIndex == -1) ? fileName.length() : extnIndex;
		String baseName = fileName.substring(0, extnIndex);

		_LOGGER.trace("getBaseName: [{}] [{}]", aPath, baseName);

		return baseName;
	}

	/**
	 * @param aPath
	 * @return File extension
	 */
	public static String getFileExtn(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = getFileName(aPath);
		int extnIndex = fileName.lastIndexOf(CommonConstants.DOT_CHAR);
		String fileExtn = (extnIndex == -1) ? CommonConstants.EMPTY_STRING : fileName.substring(extnIndex + 1);

		_LOGGER.trace("getFileExtn: [{}] [{}]", aPath, fileExtn);

		return fileExtn;
	}

	/**
	 * @param aPath
	 * @param aLevel
	 * @return Relative path
	 */
	public static Path getParent(Path aPath, int aLevel) {
		if (aPath == null) {
			return Paths.get(EMPTY_STRING);
		}

		if (aLevel == 0) {
			return aPath;
		}

		Path parent = aPath.getParent();
		for (int idx = 1; idx < aLevel; idx++) {
			parent = parent.getParent();
		}

		_LOGGER.trace("getParent: [{}] [{}] [{}]", aPath, aLevel, parent);

		return parent;
	}

	/**
	 * @param aRoot
	 * @param aPath
	 * @return Relative path
	 */
	public static String getRelativePath(Path aRoot, Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String relativePath = getPathString(aRoot.relativize(aPath));
		_LOGGER.trace("getRelativePath: [{}] [{}] [{}]", aRoot, aPath, relativePath);

		return relativePath;
	}

	/**
	 * @param aPath
	 * @param aNewName
	 * @return Relative path
	 * @throws IOException
	 */
	public static Path rename(Path aPath, String aNewName) throws IOException {
		if (aPath == null) {
			return Paths.get(EMPTY_STRING);
		}

		if (CommonUtils.isEmptyAll(aNewName)) {
			return aPath;
		}

		String extn = getFileExtn(aPath);
		Path newPath = Files.move(aPath, aPath.getParent().resolve(aNewName + DOT_CHAR + extn),
				StandardCopyOption.ATOMIC_MOVE);
		_LOGGER.trace("getRelativePath: [{}] [{}] [{}]", aPath, aNewName, newPath);

		return newPath;
	}

	/**
	 * @param aRootPath
	 *            path to the file or folder of files
	 * @param aNamePattern
	 *            text to be searched in the filename
	 * @return {@link Map} of {@link Path} that match the search criteria
	 * @throws AccelerateException
	 *             thrown by
	 *             {@link #walkFileTree(Path, Function, BiFunction, BiFunction, Function, Function, BiFunction)}
	 */
	public static Map<String, Path> searchByName(Path aRootPath, String aNamePattern) throws AccelerateException {
		return walkFileTree(aRootPath, null, null, null,
				aPath -> StringUtil.grepCheck(aNamePattern, getBaseName(aPath)), null, null);
	}

	/**
	 * @param aRootPath
	 *            path to the file or folder of files
	 * @param aSearchExtn
	 *            extension of the file
	 * @return {@link Map} of {@link Path} that match the search criteria
	 * @throws AccelerateException
	 *             thrown by
	 *             {@link #walkFileTree(Path, Function, BiFunction, BiFunction, Function, Function, BiFunction)}
	 */
	public static Map<String, Path> searchByExtn(Path aRootPath, String aSearchExtn) throws AccelerateException {
		return walkFileTree(aRootPath, null, null, null, aPath -> CommonUtils.compare(getFileExtn(aPath), aSearchExtn),
				null, null);
	}

	/**
	 * @param aRootPath
	 * @param aDirectoryFilter
	 * @param aPreVisitDirectory
	 * @param aPostVisitDirectory
	 * @param aFileFilter
	 * @param aVisitFile
	 * @param aSelector
	 * @return {@link Map} containing files picked up by given Selector
	 * @throws AccelerateException
	 *             {@link IOException} thrown by
	 *             {@link Files#walkFileTree(Path, FileVisitor)}
	 */
	public static Map<String, Path> walkFileTree(Path aRootPath, final Function<Path, Boolean> aDirectoryFilter,
			final BiFunction<Path, BasicFileAttributes, FileVisitResult> aPreVisitDirectory,
			final BiFunction<Path, IOException, FileVisitResult> aPostVisitDirectory,
			final Function<Path, Boolean> aFileFilter, final Function<Path, FileVisitResult> aVisitFile,
			final BiFunction<Path, FileVisitResult, Boolean> aSelector) throws AccelerateException {
		final Map<String, Path> fileMap = new TreeMap<>();

		try {
			Files.walkFileTree(aRootPath, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path aPath, BasicFileAttributes aAttrs) {
					if (aDirectoryFilter != null && !aDirectoryFilter.apply(aPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					try {
						if (Files.isSameFile(aPath, aRootPath)) {
							return FileVisitResult.CONTINUE;
						}
					} catch (IOException error) {
						AccelerateException.checkAndThrow(error);
					}

					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aPreVisitDirectory != null) {
						visitResult = aPreVisitDirectory.apply(aPath, aAttrs);
					}

					if ((aSelector != null) && aSelector.apply(aPath, visitResult)) {
						fileMap.put(getRelativePath(aRootPath, aPath), aPath);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path aPath, IOException aError) {
					if (aPostVisitDirectory != null) {
						return aPostVisitDirectory.apply(aPath, aError);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path aPath, BasicFileAttributes aAttrs) {
					if (aFileFilter != null && !aFileFilter.apply(aPath)) {
						return FileVisitResult.CONTINUE;
					}

					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aVisitFile != null) {
						visitResult = aVisitFile.apply(aPath);
					}

					if ((aSelector != null) && aSelector.apply(aPath, visitResult)) {
						fileMap.put(getRelativePath(aRootPath, aPath), aPath);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult visitFileFailed(Path aPath, IOException aError) throws IOException {
					if (aError != null) {
						throw aError;
					}

					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException error) {
			throw new AccelerateException(error);
		}

		_LOGGER.trace("walkFileTree: root=[{}], selectCount=[{}]", aRootPath, fileMap.size());
		return fileMap;
	}
}