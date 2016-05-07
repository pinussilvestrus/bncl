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
public class BnclTaskParser extends BnclElementParser {

    private final String USER_TASK_KEYWORD = "USERTASK";

    public BnclTaskParser(BPMNModelBuilder builder) {
        super(builder);
    }

    public void parseTask(String elementString) {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
         logger().info(word);
         }**/

        String id;
        Class type;
        switch (withoutSpaces.get(0)) {
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
}
