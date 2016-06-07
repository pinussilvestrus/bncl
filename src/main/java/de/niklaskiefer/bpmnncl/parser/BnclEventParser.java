package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.*;

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

    public BnclEventParser() {
        initEventTypes();
    }

    public BpmnModelElementInstance parseEvent(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        String id;
        Class type = null;
        Class definitionType = null;

        if (!BnclParser.checkWords(withoutSpaces)) {
            return null;
        }

        //logger().info(withoutSpaces.get(0).toLowerCase());

        String first = withoutSpaces.get(0).toLowerCase();
        for (EventElement event : eventTypes) {
            if (first.equals(event.getKeyword())) {
                type = event.eventType;
                if (event.getDefinitionType() != null) {
                    definitionType = event.definitionType;
                }
                break;
            }
        }

        if (type == null) {
            return null;
        }

        Map<String, String> attributes = parseAttributes(withoutSpaces);
        BpmnModelElementInstance elementInstance = builder.createElement(builder.getProcess(), type, attributes);

        // if it's special end or event type, build definitions for it, e.g. message
        if (definitionType != null) {
            builder.createEventDefinition(elementInstance, builder.getProcess(), definitionType);
        }

        return elementInstance;

    }

    public List<EventElement> getEventTypes() {
        return this.eventTypes;
    }

    private void initEventTypes() {
        this.eventTypes.add(new EventElement("startevent", StartEvent.class));
        this.eventTypes.add(new EventElement("messagestartevent", StartEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("endevent", EndEvent.class));
        this.eventTypes.add(new EventElement("messageendevent", EndEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("catchevent", IntermediateCatchEvent.class));
        this.eventTypes.add(new EventElement("messagecatchevent", IntermediateCatchEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("throwevent", IntermediateThrowEvent.class));
        this.eventTypes.add(new EventElement("messagethrowevent", IntermediateThrowEvent.class, MessageEventDefinition.class));
        this.eventTypes.add(new EventElement("timerevent", IntermediateCatchEvent.class, TimerEventDefinition.class));
        this.eventTypes.add(new EventElement("timerstartevent", StartEvent.class, TimerEventDefinition.class));
        this.eventTypes.add(new EventElement("errorevent", IntermediateCatchEvent.class, ErrorEventDefinition.class));
        this.eventTypes.add(new EventElement("errorstartevent", StartEvent.class, ErrorEventDefinition.class));
        /**
         this.eventTypes.add(new EventElement("conditionalevent", IntermediateCatchEvent.class,
         ConditionalEventDefinition.class));
         this.eventTypes.add(new EventElement("conditionalstartevent", StartEvent.class, ConditionalEventDefinition
         .class));
         this.eventTypes.add(new EventElement("linkevent", IntermediateCatchEvent.class, LinkEventDefinition.class));**/
        this.eventTypes.add(new EventElement("signalcatchevent", IntermediateCatchEvent.class, SignalEventDefinition
                .class));
        this.eventTypes.add(new EventElement("signalthrowevent", IntermediateThrowEvent.class, SignalEventDefinition
                .class));
        this.eventTypes.add(new EventElement("signalstartevent", StartEvent.class, SignalEventDefinition.class));
        this.eventTypes.add(new EventElement("signalendevent", EndEvent.class, SignalEventDefinition.class));
        this.eventTypes.add(new EventElement("escalationcatchevent", IntermediateCatchEvent.class,
                EscalationEventDefinition.class));
        this.eventTypes.add(new EventElement("escalationthrowevent", IntermediateThrowEvent.class,
                EscalationEventDefinition.class));
        this.eventTypes.add(new EventElement("escalationstartevent", StartEvent.class, EscalationEventDefinition
                .class));
        this.eventTypes.add(new EventElement("escalationendevent", EndEvent.class, EscalationEventDefinition.class));
        this.eventTypes.add(new EventElement("terminationevent", EndEvent.class, TerminateEventDefinition.class));
        this.eventTypes.add(new EventElement("compensationcatchevent", IntermediateCatchEvent.class,
                CompensateEventDefinition.class));
        this.eventTypes.add(new EventElement("compensationthrowevent", IntermediateThrowEvent.class,
                CompensateEventDefinition.class));
        this.eventTypes.add(new EventElement("compensationstartevent", StartEvent.class, CompensateEventDefinition
                .class));
        this.eventTypes.add(new EventElement("compensationendevent", EndEvent.class, CompensateEventDefinition.class));
        this.eventTypes.add(new EventElement("cancelevent", IntermediateCatchEvent.class, CancelEventDefinition.class));
        this.eventTypes.add(new EventElement("cancelendevent", EndEvent.class, CancelEventDefinition.class));
    }

    public static class EventElement {

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


        public String getKeyword() {
            return keyword;
        }

    }
}
