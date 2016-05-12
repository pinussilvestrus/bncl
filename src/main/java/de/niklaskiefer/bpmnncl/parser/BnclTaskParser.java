package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclTaskParser extends BnclElementParser {

    private final String USER_TASK_KEYWORD = "usertask";

    private List<TaskElement> taskTypes = new ArrayList<>();

    public BnclTaskParser(BPMNModelBuilder builder) {
        super(builder);
        initTaskTypes();
    }

    public BpmnModelElementInstance parseTask(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
         logger().info(word);
         }**/

        if(!BnclParser.checkWords(withoutSpaces)) {
            return null;
        }

        String id;
        Class type = null;

        String first = withoutSpaces.get(0).toLowerCase();
        for(TaskElement task : taskTypes) {
            if (first.equals(task.getKeyword())) {
                type = task.taskType;
                break;
            }
        }

        if (type == null) {
            return null;
        }


        Map<String, String> attributes = parseAttributes(withoutSpaces);
        return builder.createElement(builder.getProcess(), type, attributes);
    }

    private void initTaskTypes() {
        this.taskTypes.add(new TaskElement("usertask", UserTask.class));
    }

    private class TaskElement {
        private String keyword = "";
        private Class taskType;

        public TaskElement(String keyword, Class taskTyp) {
            this.keyword = keyword;
            this.taskType = taskTyp;
        }

        public Class getTaskType() {
            return taskType;
        }

        public void setTaskType(Class taskType) {
            this.taskType = taskType;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
