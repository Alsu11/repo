package org.example.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomGenerator {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element rootElement =
                    doc.createElementNS("http://www.w3.org/2001/XMLSchema-instance", "Data");

            doc.appendChild(rootElement);

            rootElement.appendChild(getLanguage(doc, "1", "baseUrl", "https://jut.su/"));
            rootElement.appendChild(getLanguage(doc, "2", "login", "PoshKamil"));
            rootElement.appendChild(getLanguage(doc, "3", "password", "danisdanis"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("C:\\ProjectsJava\\Tests\\Test6\\Tests6\\src\\main\\resources\\Settings.xml"));

            transformer.transform(source, console);
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getLanguage(Document doc, String id, String tag, String data) {
        Element language = doc.createElement(tag);
        language.setAttribute("id", id);
        language.appendChild(getElements(doc, language, tag, data));
        return language;
    }

    private static Node getElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}