package accelerate.utils;

/**
 * Class containing basic set of constants that are usually used in app
 * development
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@SuppressWarnings("all")
public final class CommonConstants {

	/**
	 * hidden constructor
	 */
	private CommonConstants() {
	}

	// General Property Constants
	public static final String EMPTY_STRING = "";
	public static final String NULL_STRING = "null";

	public static final String ENV = "env";
	public static final String DEV = "dev";
	public static final String PROD = "prod";

	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String ENABLED = "enabled";
	public static final String DISABLED = "disabled";

	// Character Constants
	public static final String ASTERIX_CHAR = "*";
	public static final String BRACE_CHAR_CLOSE = "}";
	public static final String BRACE_CHAR_OPEN = "{";
	public static final String BRACKET_CHAR_CLOSE = ")";
	public static final String BRACKET_CHAR_OPEN = "(";
	public static final String COMPLEX_DELIMITER = "|#|";
	public static final String COMMA_CHAR = ",";
	public static final String COLON_CHAR = ":";
	public static final String DOT_CHAR = ".";
	public static final String DOUBLE_QUOTE_CHAR = "\"";
	public static final String EQUALS_CHAR = "=";
	public static final String HYPHEN_CHAR = "-";
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String PERCENT_CHAR = "%";
	public static final String PIPE_CHAR = "|";
	public static final String POUND_CHAR = "#";
	public static final String QUESTION_CHAR = "?";
	public static final String SEMICOLON_CHAR = ";";
	public static final String SINGLE_QUOTE_CHAR = "'";
	public static final String SPACE_CHAR = " ";
	public static final String SQ_BRACKET_CHAR_CLOSE = "]";
	public static final String SQ_BRACKET_CHAR_OPEN = "[";
	public static final String UNDERSCORE_CHAR = "_";
	public static final String UNIX_PATH_CHAR = "/";
	public static final String WINDOWS_PATH_CHAR = "\\";
}