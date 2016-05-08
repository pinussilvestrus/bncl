package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.*;

/**
 * @author Niklas Kiefer
 * structure of BnclElement:
 *  WITH STARTEVENT
 *      called [NAME]
 *
 * WITH is keyword for BnclElement
 */
public class BnclElementParser extends AbstractBnclParser {

    // attribute types
    protected final String ATTRIBUTE_NAME = "called";
    protected final String ATTRIBUTE_ID = "signed";

    protected BPMNModelBuilder builder;

    public BnclElementParser(BPMNModelBuilder builder) {
        this.builder = builder;
    }

    protected Map<String,String> parseAttributes(List<String> components) {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            String word = components.get(i);
            switch (word.toLowerCase()) {
                case ATTRIBUTE_NAME:
                    attributes.put("name", components.get(i+1));
                    break;
                case ATTRIBUTE_ID:
                    attributes.put("id", components.get(i+1));
                    break;
                default: break;
            }
        }

        return  attributes;
    }
}
