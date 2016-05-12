package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.StartEvent;

import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclEventParser extends BnclElementParser {

    // event types
    private final String START_EVENT_KEYWORD = "startevent";
    private final String MESSAGE_START_EVENT_KEYWORD = "messagestartevent";

    private final String END_EVENT_KEYWORD = "endevent";
    private final String MESSAGE_END_EVENT_KEYWORD = "messageendevent";

    private final String CATCH_EVENT_KEYWORD = "catchevent";
    private final String THROW_EVENT_KEYWORD = "throwevent";

    public BnclEventParser(BPMNModelBuilder builder) {
        super(builder);
    }

    public void parseEvent(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
         logger().info(word);
         }**/

        String id;
        Class type;
        Class definitionType = null;

        if(!BnclParser.checkWords(withoutSpaces)) {
            return;
        }

        logger().info(withoutSpaces.get(0).toLowerCase());

        switch (withoutSpaces.get(0).toLowerCase()) {
            case CATCH_EVENT_KEYWORD:
                type = IntermediateCatchEvent.class;
                break;
            case THROW_EVENT_KEYWORD:
                type = IntermediateThrowEvent.class;
                break;
            case START_EVENT_KEYWORD:
                type = StartEvent.class;
                break;
            case END_EVENT_KEYWORD:
                type = EndEvent.class;
                break;
            case MESSAGE_START_EVENT_KEYWORD:
                type = StartEvent.class;
                definitionType = MessageEventDefinition.class;
                break;
            case MESSAGE_END_EVENT_KEYWORD:
                type = EndEvent.class;
                definitionType = MessageEventDefinition.class;
                break;
            default:
                return;
        }

        Map<String, String> attributes = parseAttributes(withoutSpaces);
        BpmnModelElementInstance elementInstance = builder.createElement(builder.getProcess(), type, attributes);

        // if it's special end or event type, build definitions for it, e.g. message
        if (definitionType != null) {
            builder.createEventDefinition(elementInstance, builder.getProcess(), definitionType);
        }

    }
}
