package de.niklaskiefer.bpmnncl;

import org.w3c.dom.Attr;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Niklas Kiefer
 */
public class XMLWriter {

    /** ELEMENT **/
    private static final String DEFINITION_XML_TAG = "definitions";
    private static final String PROCESS_XML_TAG = "process";
    private static final String COLLABORATION_XML_TAG = "collaboration";
    private static final String PARTICIPANT_XML_TAG = "participant";
    private static final String BPMN_DIAGRAM_XML_TAG = "bpmndi:BPMNDiagram";
    private static final String BPMN_PLANE_XML_TAG = "bpmndi:BPMNPlane";

    /** ATTRIBUTES **/
    private static final String[] DEFINITION_ATTRIBUTE_XMLNS = {"xmlns", "http://www.omg.org/spec/BPMN/20100524/MODEL"};
    private static final String[] DEFINITION_ATTRIBUTE_XMLNS_BPMNDI = {"xmlns:bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI"};
    private static final String[] DEFINITION_ATTRIBUTE_XMLNS_OMGDC = {"xmlns:omgdc", "http://www.omg.org/spec/DD/20100524/DC"};
    private static final String[] DEFINITION_ATTRIBUTE_XMLNS_OMGDI = {"xmlns:omgdi", "http://www.omg.org/spec/DD/20100524/DI"};
    private static final String[] DEFINITION_ATTRIBUTE_XSI = {"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"};
    private static final String[] DEFINITION_ATTRIBUTE_EXPRESSION_LANGUAGE = {"expressionLanguage", "http://www.w3.org/TR/XPath"};
    private static final String[] DEFINITION_ATTRIBUTE_TYPE_LANGUAGE = {"typeLanguage", "http://www.w3.org/2001/XMLSchema"};
    private static final String[] DEFINITION_ATTRIBUTE_XSI_SCHEMA_LOCATION = {"xsi:schemaLocation", "http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd"};
    private static final String[] PROCESS_ATTRIBUTE_IS_CLOSED = {"isClosed", "false"};
    private static final String[] PROCESS_ATTRIBUTE_IS_EXECUTABLE = {"isExecutable", "false"};
    private static final String[] PROCESS_ATTRIBUTE_PROCESS_TYPE = {"processType", "None"};
    private static final String BPMN_ATTRIBUTE_ELEMENT = "bpmnElement";

    private static final String FILE_ID = "fid-" + UUID.randomUUID().toString();
    private static final String FILE_NAME = "process_" + "test_name";
    private Document document;
    private static final Logger logger = Logger.getLogger(XMLWriter.class.getName());

    public XMLWriter() {
        logger.setLevel(Level.INFO);
    }

    private Attr createAttribute(String id, String value) {
        Attr attr = document.createAttribute(id);
        attr.setValue(value);
        return attr;
    }

    private Element createBPMNDiagram(Element collaboration) throws Exception {
        Element bpmnDiagram = document.createElement(BPMN_DIAGRAM_XML_TAG);
        bpmnDiagram.setAttributeNode(createAttribute("id", "bid-" + UUID.randomUUID().toString()));

        Element bpmnPlane = document.createElement(BPMN_PLANE_XML_TAG);
        bpmnPlane.setAttributeNode(createAttribute("id", "bid-" + UUID.randomUUID().toString()));
        bpmnPlane.setAttributeNode(createAttribute(BPMN_ATTRIBUTE_ELEMENT, collaboration.getAttribute("id")));

        bpmnDiagram.appendChild(bpmnPlane);
        return bpmnDiagram;
    }

    private Element createCollaboration(Element process) throws Exception {
        Element collaboration = document.createElement(COLLABORATION_XML_TAG);

        collaboration.setAttributeNode(createAttribute("id", "cid-" + UUID.randomUUID().toString()));
        Element participant = document.createElement(PARTICIPANT_XML_TAG);
        participant.setAttributeNode(createAttribute("id", "paid-" + UUID.randomUUID().toString()));
        participant.setAttributeNode(createAttribute("name", process.getAttribute("name")));
        participant.setAttributeNode(createAttribute("processRef", process.getAttribute("id")));

        collaboration.appendChild(participant);
        return collaboration;
    }

    private Element createDefinitions() throws Exception {
        Element definitions = document.createElement(DEFINITION_XML_TAG);

        definitions.setAttributeNode(createAttribute("id", FILE_ID));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XMLNS[0], DEFINITION_ATTRIBUTE_XMLNS[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XMLNS_BPMNDI[0], DEFINITION_ATTRIBUTE_XMLNS_BPMNDI[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XMLNS_OMGDC[0], DEFINITION_ATTRIBUTE_XMLNS_OMGDC[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XMLNS_OMGDI[0], DEFINITION_ATTRIBUTE_XMLNS_OMGDI[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XSI[0], DEFINITION_ATTRIBUTE_XSI[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_EXPRESSION_LANGUAGE[0], DEFINITION_ATTRIBUTE_EXPRESSION_LANGUAGE[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_TYPE_LANGUAGE[0], DEFINITION_ATTRIBUTE_TYPE_LANGUAGE[1]));
        definitions.setAttributeNode(createAttribute(DEFINITION_ATTRIBUTE_XSI_SCHEMA_LOCATION[0], DEFINITION_ATTRIBUTE_XSI_SCHEMA_LOCATION[1]));

        Element process = createProcess();
        Element collaboration = createCollaboration(process);
        definitions.appendChild(collaboration);
        definitions.appendChild(process);
        definitions.appendChild(createBPMNDiagram(collaboration));
        return definitions;
    }

    private Element createProcess() throws Exception {
        Element process = document.createElement(PROCESS_XML_TAG);

        process.setAttributeNode(createAttribute("id", "pid-" + UUID.randomUUID().toString()));
        process.setAttributeNode(createAttribute("name", "test-process")); // todo
        process.setAttributeNode(createAttribute(PROCESS_ATTRIBUTE_IS_CLOSED[0], PROCESS_ATTRIBUTE_IS_CLOSED[1]));
        process.setAttributeNode(createAttribute(PROCESS_ATTRIBUTE_IS_EXECUTABLE[0], PROCESS_ATTRIBUTE_IS_EXECUTABLE[1]));
        process.setAttributeNode(createAttribute(PROCESS_ATTRIBUTE_PROCESS_TYPE[0], PROCESS_ATTRIBUTE_PROCESS_TYPE[1]));

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
            logger.warning(e.getMessage());
        }
    }
}
