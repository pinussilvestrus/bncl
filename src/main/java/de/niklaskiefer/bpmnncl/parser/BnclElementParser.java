package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer structure of BnclElement: WITH STARTEVENT called [NAME]
 *
 *         WITH is keyword for BnclElement
 */
public class BnclElementParser extends AbstractBnclParser {

    // attribute types
    protected static final String ATTRIBUTE_NAME = "called";
    protected static final String ATTRIBUTE_ID = "signed";

    private static final String NO_ID_EXCEPTION = "This element has not id! Please add a \'signed\' attribute";

    protected BPMNModelBuilder builder;

    public BnclElementParser(BPMNModelBuilder builder) {
        this.builder = builder;
    }

    protected Map<String, String> parseAttributes(List<String> components) throws Exception {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            String word = components.get(i);
            switch (word.toLowerCase()) {
                case ATTRIBUTE_NAME:
                    attributes.put("name", components.get(i + 1));
                    break;
                case ATTRIBUTE_ID:
                    attributes.put("id", components.get(i + 1));
                    break;
                default:
                    break;
            }
        }

        if (!attributes.containsKey("id")) {
            throw new Exception(NO_ID_EXCEPTION);
        }

        return attributes;
    }
}
