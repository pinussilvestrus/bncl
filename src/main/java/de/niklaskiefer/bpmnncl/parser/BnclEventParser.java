package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.StartEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclEventParser extends BnclElementParser {

    private List<EventElement> eventTypes = new ArrayList<>();

    public BnclEventParser(BPMNModelBuilder builder) {
        super(builder);
        initEventTypes();
    }

    public void parseEvent(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        String id;
        Class type = null;
        Class definitionType = null;

        if(!BnclParser.checkWords(withoutSpaces)) {
            return;
        }

        //logger().info(withoutSpaces.get(0).toLowerCase());

        String first = withoutSpaces.get(0).toLowerCase();
        for(EventElement event : eventTypes) {
            if (first.equals(event.getKeyword())) {
                type = event.eventType;
                if (event.getDefinitionType() != null) {
                    definitionType = event.definitionType;
                }
                break;
            }
        }

        if (type == null) {
            return;
        }

        Map<String, String> attributes = parseAttributes(withoutSpaces);
        BpmnModelElementInstance elementInstance = builder.createElement(builder.getProcess(), type, attributes);

        // if it's special end or event type, build definitions for it, e.g. message
        if (definitionType != null) {
            builder.createEventDefinition(elementInstance, builder.getProcess(), definitionType);
        }

    }

    private void initEventTypes() {
        this.eventTypes.add(new EventElement("startevent", StartEvent.class));
        this.eventTypes.add(new EventElement("messagestartevent", StartEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("endevent", EndEvent.class));
        this.eventTypes.add(new EventElement("messageendevent", EndEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("catchevent", IntermediateCatchEvent.class));
        this.eventTypes.add(new EventElement("throwevent", IntermediateThrowEvent.class));
    }

    private class EventElement {

        private String keyword = "";

        private Class eventType;

        private Class definitionType;

        public EventElement(String keyword, Class eventType) {
            this.keyword = keyword;
            this.eventType = eventType;
        }

        public EventElement(String keyword, Class eventType, Class definitionType) {
            this.keyword = keyword;
            this.eventType = eventType;
            this.definitionType = definitionType;
        }

        public Class getDefinitionType() {
            return definitionType;
        }

        public void setDefinitionType(Class definitionType) {
            this.definitionType = definitionType;
        }

        public Class getEventType() {
            return eventType;
        }

        public void setEventType(Class eventType) {
            this.eventType = eventType;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
