package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import java.util.ArrayList;
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
    protected List<AttributeElement> attributeTypes = new ArrayList<>();
    private static final String NO_ID_EXCEPTION = "This element has no id! Please add a \'signed\' attribute";

    protected BPMNModelBuilder builder;

    public BnclElementParser(BPMNModelBuilder builder) {
        this.builder = builder;
        initAttributeTypes();
    }

    public BnclElementParser() {
    }

    protected Map<String, String> parseAttributes(List<String> components) throws Exception {
        Map<String, String> attributes = new HashMap<>();
        int i = 0;
        while (i < components.size()) {
            String word = components.get(i);
            AttributeElement match = findAttributeFromWord(word);
            if (match != null) {
                i++;
                String value = "";
                StringBuilder valueBuffer = new StringBuilder();
                // add values to attributes value as long another attribute type was found
                while (i < components.size() && findAttributeFromWord(components.get(i)) == null) {
                    valueBuffer.append(components.get(i)).append(" ");
                    i++;
                }

                value = valueBuffer.toString();

                if (value.lastIndexOf(' ') == value.length() - 1 && value.length() > 0) {
                    value = value.substring(0, value.length() - 1);
                }

                attributes.put(match.getId(), value);
                i--; // go back to start with another attribute
            }
            i++;
        }


        if (!attributes.containsKey("id")) {
            throw new Exception(NO_ID_EXCEPTION);
        }

        return attributes;
    }

    private AttributeElement findAttributeFromWord(String word) {
        for (AttributeElement attributeType : attributeTypes) {
            if (attributeType.getBncl().equals(word)) {
                return attributeType;
            }
        }
        return null;
    }

    private void initAttributeTypes() {
        attributeTypes.add(new AttributeElement("id", "signed"));
        attributeTypes.add(new AttributeElement("name", "called"));
    }

    private static class AttributeElement {
        private String id;
        private String bncl;

        public AttributeElement(String id, String bncl) {
            this.id = id;
            this.bncl = bncl;
        }

        public String getId() {
            return id;
        }

        public String getBncl() {
            return bncl;
        }
    }
}
