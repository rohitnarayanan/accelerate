package accelerate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import accelerate.utils.exception.AccelerateException;

/**
 * Class providing utility methods for XML operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class XMLUtil {
	/**
	 * {@link DocumentBuilder} singleton instance for SAX parsing
	 */
	private static DocumentBuilder builder = null;

	/**
	 * {@link XPathFactory} to query nodes
	 */
	public static XPathFactory xPathFactory = null;

	/**
	 * {@link Transformer} instance to write DOM to file
	 */
	public static Transformer transformer = null;

	/**
	 * hidden constructor
	 */
	private XMLUtil() {
	}

	/**
	 * @throws AccelerateException
	 */
	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			xPathFactory = XPathFactory.newInstance();

			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		} catch (Exception error) {
			throw new AccelerateException(error);
		}

	}

	/**
	 * @param aApplicationContext
	 * @param aConfigPath
	 *            URL/Path to the config file.
	 * @return Property Map
	 * @throws AccelerateException
	 */
	public static Document loadXML(ApplicationContext aApplicationContext, String aConfigPath)
			throws AccelerateException {
		try {
			return loadXML(aApplicationContext.getResource(aConfigPath).getInputStream());
		} catch (IOException error) {
			throw new AccelerateException(error);
		}
	}

	/**
	 * @param aInputStream
	 * @return {@link Document}
	 * @throws AccelerateException
	 */
	public static Document loadXML(InputStream aInputStream) throws AccelerateException {
		try {
			return builder.parse(aInputStream);
		} catch (SAXException | IOException error) {
			throw new AccelerateException(error);
		}
	}

	/**
	 * @param aDocument
	 * @return
	 * @throws AccelerateException
	 */
	public static String serialzeXML(Document aDocument) throws AccelerateException {
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(aDocument);
		try {
			transformer.transform(source, result);
		} catch (TransformerException error) {
			throw new AccelerateException(error);
		}
		return result.getWriter().toString();
	}

	/**
	 * @param aDocument
	 * @throws AccelerateException
	 */
	public static void printXML(Document aDocument) throws AccelerateException {
		System.out.println(serialzeXML(aDocument));
	}

	/**
	 * @param aDocument
	 * @param aXPathQuery
	 * @return
	 * @throws AccelerateException
	 */
	public static Node searchNode(Document aDocument, String aXPathQuery) throws AccelerateException {
		XPath xPath = xPathFactory.newXPath();
		try {
			return (Node) xPath.evaluate(aXPathQuery, aDocument, XPathConstants.NODE);
		} catch (XPathExpressionException error) {
			throw new AccelerateException(error);
		}
	}

	/**
	 * @param aDocument
	 * @param aXPathQuery
	 * @return
	 * @throws AccelerateException
	 */
	public static String searchValue(Document aDocument, String aXPathQuery) throws AccelerateException {
		XPath xPath = xPathFactory.newXPath();
		try {
			return (String) xPath.evaluate(aXPathQuery, aDocument, XPathConstants.STRING);
		} catch (XPathExpressionException error) {
			throw new AccelerateException(error);
		}
	}
}