package de.niklaskiefer.bpmnncl;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.niklaskiefer.bpmnncl.parser.BnclParser;

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
        String xmlString = Bpmn.convertToString(bpmnModelInstance);

        File fileBPMN = new File(fileName + ".bpmn");
        File fileXML = new File(fileName + ".xml");
        Bpmn.writeModelToFile(fileBPMN, bpmnModelInstance);
        Bpmn.writeModelToFile(fileXML, bpmnModelInstance);

        logger.info("Successfully save bpmn file to " + fileName);
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
