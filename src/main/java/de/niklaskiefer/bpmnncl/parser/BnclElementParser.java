package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.*;

/**
 * @author Niklas Kiefer
 * structure of BnclElement:
 *  WITH USERTASK
 *      called [NAME]
 *
 * WITH is keyword for BnclElement
 */
public class BnclElementParser extends AbstractBnclParser {

    // element types
    private final String START_EVENT_KEYWORD = "STARTEVENT";
    private final String USER_TASK_KEYWORD = "USERTASK";
    private final String END_EVENT_KEYWORD = "ENDEVENT";

    // attribute types
    private final String ATTRIBUTE_NAME = "Called";
    private BPMNModelBuilder builder;

    public BnclElementParser(BPMNModelBuilder builder) {
        this.builder = builder;
    }

    public void parseProcessElement(String elementString) {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
            logger().info(word);
        }**/

        String id;
        Class type;
        switch (withoutSpaces.get(0)) {
            case START_EVENT_KEYWORD:
                id = "startEvent1"; // TODO: 07.05.2016
                type = StartEvent.class;
                break;
            case END_EVENT_KEYWORD:
                id = "endEvent1"; // TODO: 07.05.2016
                type = EndEvent.class;
                break;
            case USER_TASK_KEYWORD:
                id = "userTask1"; // TODO: 07.05.2016
                type = UserTask.class;
                break;
            default:
                return;
        }

        Map<String, String> attributes = parseAttributes(withoutSpaces);
        builder.createElement(builder.getProcess(), id, type, attributes);
    }

    private Map<String,String> parseAttributes(List<String> components) {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            String word = components.get(i);
            switch (word) {
                case ATTRIBUTE_NAME:
                    attributes.put("name", components.get(i+1));
                    break;
                default: break;
            }
        }

        return  attributes;
    }
}
