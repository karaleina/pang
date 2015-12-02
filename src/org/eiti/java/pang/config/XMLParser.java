package org.eiti.java.pang.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;


public abstract class XMLParser {

	protected Document xmlDocument;
	protected Element root;
	protected XPath xpath;

	/**
	 * Metoda wyszukuje pojedynczy element.
	 * @param parent Element nadrzędny (rodzic)
	 * @param childName Nazwa elementu podrzędnego.
     * @return Element (węzeł) o zadanej nazwie.
     */
	protected Node findChildByName(Node parent, String childName) {
		NodeList children = parent.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals(childName)) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Tej metody nie ogarniam, zamirzam ją napisać od początku
	 * (nie by poprawić, ale żeby zrozumieć)
	 * @param children
	 * @return
     */
	protected List<Node> filterChildrenElements(NodeList children) {
		List<Node> filteredNodes = new ArrayList<Node>();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
				filteredNodes.add(children.item(i));
			}
		}
		return filteredNodes;
	}

}
