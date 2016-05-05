package de.niklaskiefer.bpmnncl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Niklas Kiefer
 */
public class XMLWriter {

    private static final String DEFINITION_XML_TAG = "definitions";
    private static final String FILE_NAME = "process.bpmn";
    private Document document;
    private static final Logger logger = Logger.getLogger(XMLWriter.class.getName());

    public XMLWriter() {
        logger.setLevel(Level.INFO);
    }

    private Element createDefinitions() throws Exception {
        Element definitions = document.createElement(DEFINITION_XML_TAG);
        return definitions;
    }

    public void createBPMNFile() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            document = documentBuilder.newDocument();
            Element definitions = createDefinitions();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("out/" + FILE_NAME));

            transformer.transform(source, result);

            logger.info("Successfully creating BPMN-XML-File called " + FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
