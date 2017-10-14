package accelerate.utils;

import static accelerate.utils.BasicConstants.EMPTY_STRING;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Class providing utility methods for {@link String} operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class StringUtil {

	/**
	 * hidden constructor
	 */
	private StringUtil() {
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeTrim(CharSequence aString) {
		if (aString != null) {
			return aString.toString().trim();
		}

		return EMPTY_STRING;
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static int safeLength(CharSequence aString) {
		return safeTrim(aString).length();
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeToUpper(CharSequence aString) {
		return safeTrim(aString).toUpperCase();
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeToLower(CharSequence aString) {
		return safeTrim(aString).toLowerCase();
	}

	/**
	 * @param aPattern
	 *            - Pattern to be searched
	 * @param aCompareList
	 *            - Variable number of string inputs to search
	 * @return true if match found
	 */
	public static List<String> grep(String aPattern, String... aCompareList) {
		if (isEmpty(aPattern) || isEmpty((aCompareList))) {
			return Collections.emptyList();
		}

		Matcher matcher = Pattern.compile(aPattern.toString()).matcher(EMPTY_STRING);

		return Arrays.stream(aCompareList).filter(aValue -> {
			matcher.reset(aValue);
			return matcher.find();
		}).collect(Collectors.toList());
	}

	/**
	 * @param aPattern
	 *            - Pattern to be searched
	 * @param aCompareList
	 *            - Variable number of string inputs to search
	 * @return true if match found
	 */
	public static boolean grepCheck(String aPattern, String... aCompareList) {
		return !grep(aPattern, aCompareList).isEmpty();
	}

	/**
	 * @param aTargetString
	 *            - Target string to search
	 * @param aPatternList
	 *            - List of patterns to be searched
	 * @return true if match found
	 */
	public static boolean search(CharSequence aTargetString, List<String> aPatternList) {
		if (isEmpty(aTargetString) || isEmpty((aPatternList))) {
			return false;
		}

		return aPatternList.stream().filter(aPattern -> {
			Matcher matcher = Pattern.compile(aPattern.toString()).matcher(aTargetString);
			return matcher.find();
		}).collect(Collectors.toList()).isEmpty();
	}

	/**
	 * @param aInputString
	 * @param aRecordDelim
	 * @param aFieldDelim
	 * @return array of delimited values
	 */
	public static Map<String, String> multiSplit(String aInputString, String aRecordDelim, String aFieldDelim) {
		if (isEmpty(aInputString)) {
			return Collections.emptyMap();
		}

		return Arrays.stream(StringUtils.split(aInputString, aRecordDelim))
				.map(aLine -> StringUtils.split(aLine, aFieldDelim)).collect(Collectors.toMap(aValues -> aValues[0],
						aValues -> (aValues.length == 1) ? aValues[0] : aValues[1]));
	}
}