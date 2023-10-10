package org.example.generators;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.example.models.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomParser {

    public List<Message> readXml(String path) {
        File xmlFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Message");

            List<Message> mesList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                mesList.add(getMessage(nodeList.item(i)));
            }
            return mesList;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }
    private static Message getMessage(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return new Message(getTagValue("message", element), getTagValue("to", element));
        }
        return null;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }


    public String getData(String data, File xmlFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName(data);
            Node node = nodeList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList nodeList1 = element.getElementsByTagName(data).item(0).getChildNodes();
                Node node1 = (Node) nodeList1.item(0);
                return node1.getNodeValue();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

}