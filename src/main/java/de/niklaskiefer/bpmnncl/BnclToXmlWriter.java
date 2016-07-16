package de.niklaskiefer.bpmnncl;

import de.niklaskiefer.bpmnncl.parser.BnclParser;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Niklas Kiefer
 */
public class BnclToXmlWriter {

    private String fileName;
    private static final Logger logger = Logger.getLogger(BnclToXmlWriter.class.getName());

    public BnclToXmlWriter() {
        logger.setLevel(Level.INFO);
        fileName = "test_process";
    }

    public void createBPMNFile(String bncl) throws Exception {
        BpmnModelInstance bpmnModelInstance = createBPMModelInstance(bncl);

        File fileBPMN = new File(fileName + ".bpmn");
        File fileXML = new File(fileName + ".xml");
        Bpmn.writeModelToFile(fileBPMN, bpmnModelInstance);
        Bpmn.writeModelToFile(fileXML, bpmnModelInstance);

        logger.info("Successfully save bpmn file to " + fileName);
    }

    public String convertBnclToXML(String bncl) throws Exception {
        BpmnModelInstance bpmnModelInstance = createBPMModelInstance(bncl);
        String xmlString = Bpmn.convertToString(bpmnModelInstance);

        logger.info("Successfully convert bncl statement to xml");
        return xmlString;
    }

    public String convertBnclFileToXML(String fileName) throws Exception {
        String bncl = getStringFromFileReaderAndClose(fileName);
        return convertBnclToXML(bncl);
    }

    private BpmnModelInstance createBPMModelInstance(String bncl) throws Exception {
        BnclParser parser = new BnclParser();
        BpmnModelInstance modelInstance;
        modelInstance = parser.parseBncl(bncl);
        return modelInstance;
    }

    private String getStringFromFileReaderAndClose(String fileName) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while (reader.ready()) {
                result.append(reader.readLine());
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return result.toString();
    }
}
