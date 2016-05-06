package de.niklaskiefer.bpmnncl;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.niklaskiefer.bpmnncl.parser.BnclParser;

/**
 * @author Niklas Kiefer
 */
public class XMLWriter {

    private String fileName;
    private static final Logger logger = Logger.getLogger(XMLWriter.class.getName());

    public XMLWriter() {
        logger.setLevel(Level.INFO);
        fileName = "test_process";
    }

    public void createBPMNFile(String bncl) {
        try {
            BpmnModelInstance bpmnModelInstance = createBPMModelInstance(bncl);
            String xmlString = Bpmn.convertToString(bpmnModelInstance);

            File fileBPMN = new File("out/" + fileName + ".bpmn");
            File fileXML = new File("out/" + fileName + ".xml");
            Bpmn.writeModelToFile(fileBPMN, bpmnModelInstance);
            Bpmn.writeModelToFile(fileXML, bpmnModelInstance);

            logger.info("Successfully save bpmn file to " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }

    private BpmnModelInstance createBPMModelInstance(String bncl) {
        BnclParser parser = new BnclParser();
        BpmnModelInstance modelInstance;
        try {
            modelInstance = parser.parseBncl(bncl);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return modelInstance;
    }
}
