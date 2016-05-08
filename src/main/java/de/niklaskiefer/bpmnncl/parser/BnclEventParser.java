package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclEventParser extends BnclElementParser {

    // event types
    private final String START_EVENT_KEYWORD = "startevent";
    private final String END_EVENT_KEYWORD = "endevent";

    public BnclEventParser(BPMNModelBuilder builder) {
        super(builder);
    }

    public void parseEvent(String elementString) {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
         logger().info(word);
         }**/

        String id;
        Class type;

        if(!BnclParser.checkWords(withoutSpaces)) {
            return;
        }

        switch (withoutSpaces.get(0).toLowerCase()) {
            case START_EVENT_KEYWORD:
                type = StartEvent.class;
                break;
            case END_EVENT_KEYWORD:
                type = EndEvent.class;
                break;
            default:
                return;
        }

        Map<String, String> attributes = parseAttributes(withoutSpaces);
        builder.createElement(builder.getProcess(), type, attributes);
    }
}
