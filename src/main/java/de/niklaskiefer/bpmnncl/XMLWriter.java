package de.niklaskiefer.bpmnncl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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
    private static final String PROCESS_XML_TAG = "process";

    private static final String FILE_NAME = "process";
    private Document document;
    private static final Logger logger = Logger.getLogger(XMLWriter.class.getName());

    public XMLWriter() {
        logger.setLevel(Level.INFO);
    }

    private Element createDefinitions() throws Exception {
        Element definitions = document.createElement(DEFINITION_XML_TAG);
        definitions.appendChild(createProcess());
        return definitions;
    }

    private Element createProcess() throws Exception {
        Element process = document.createElement(PROCESS_XML_TAG);
        return process;
    }

    public void createBPMNFile() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            document = documentBuilder.newDocument();
            Element definitions = createDefinitions();
            document.appendChild(definitions);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("out/" + FILE_NAME + ".bpmn")); // BPMN-File
            StreamResult resultXML = new StreamResult(new File("out/" + FILE_NAME + ".xml")); // XML-File

            transformer.transform(source, result);
            transformer.transform(source, resultXML);

            logger.info("Successfully creating BPMN-XML-File called " + FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
