package com.tlv8.base.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author 陈乾
 *
 */
public class Xml {
	/**
	 * 将xml字符串转换成HashMap
	 */
	public static HashMap<String, String> XmlStrToMap(String xmlstr)
			throws SAXException, IOException, ParserConfigurationException {
		HashMap<String, String> m = new HashMap<String, String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
			Element cmd = doc.getDocumentElement();
			if (xmlstr.split("/").length < 3) {
				String newXML = "<root>" + xmlstr.toString() + "</root>";
				doc = db.parse(new ByteArrayInputStream(newXML.getBytes("UTF-8")));
				cmd = doc.getDocumentElement();
			}
			NodeList NLC = cmd.getChildNodes();
			for (int i = 0; i < NLC.getLength(); i++) {
				String TagName = NLC.item(i).getNodeName();
				NodeList nl = doc.getElementsByTagName(TagName);
				String TagValue = nl.item(0).getTextContent();
				m.put(TagName.toUpperCase(), TagValue);
			}
		} catch (SAXException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ParserConfigurationException e) {
			throw e;
		}
		return m;
	}

	/**
	 * 将xml字符串转换成NodeList
	 */
	public static NodeList XmlStrToNodeList(String xmlstr)
			throws SAXException, IOException, ParserConfigurationException {
		NodeList NLC = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
			Element cmd = doc.getDocumentElement();
			if (xmlstr.split("/").length < 3) {
				String newXML = "<root>" + xmlstr.toString() + "</root>";
				doc = db.parse(new ByteArrayInputStream(newXML.getBytes("UTF-8")));
				cmd = doc.getDocumentElement();
			}
			NLC = cmd.getChildNodes();
		} catch (SAXException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ParserConfigurationException e) {
			throw e;
		}
		return NLC;
	}

	public static void main(String[] args) {
		String strXML = "<root><fcode>chenqian</fcode><fName>陈乾</fName></root>";
		try {
			System.out.println(XmlStrToMap(strXML));
			System.out.println(XmlStrToNodeList(strXML).item(0).getNodeName());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
