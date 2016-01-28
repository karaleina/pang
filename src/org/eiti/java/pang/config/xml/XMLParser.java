package org.eiti.java.pang.config.xml;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

/**
 * An abstract class which does nothing but storing some variables useful for XML reading classes.
 */
public abstract class XMLParser {

	protected Document xmlDocument;
	protected XPath xpath;

}
