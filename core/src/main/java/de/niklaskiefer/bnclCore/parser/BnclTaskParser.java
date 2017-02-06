package de.niklaskiefer.bnclCore.parser;

import de.niklaskiefer.bnclCore.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.BusinessRuleTask;
import org.camunda.bpm.model.bpmn.instance.ManualTask;
import org.camunda.bpm.model.bpmn.instance.ReceiveTask;
import org.camunda.bpm.model.bpmn.instance.ScriptTask;
import org.camunda.bpm.model.bpmn.instance.SendTask;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclTaskParser extends BnclElementParser {

    private List<TaskElement> taskTypes = new ArrayList<>();

    public BnclTaskParser(BPMNModelBuilder builder) {
        super(builder);
        initTaskTypes();
    }

    public BnclTaskParser() {
        initTaskTypes();
    }

    public BpmnModelElementInstance parseTask(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);


        if (!BnclParser.checkWords(withoutSpaces)) {
            return null;
        }

        String id;
        Class type = null;

        String first = withoutSpaces.get(0).toLowerCase();
        for (TaskElement task : taskTypes) {
            if (first.equals(task.getKeyword())) {
                type = task.getTaskType();
                break;
            }
        }

        if (type == null) {
            return null;
        }


        Map<String, String> attributes = parseAttributes(withoutSpaces);
        return builder.createElement(builder.getProcess(), type, attributes);
    }

    public List<TaskElement> getTaskTypes() {
        return this.taskTypes;
    }

    private void initTaskTypes() {
        this.taskTypes.add(new TaskElement("usertask", UserTask.class));
        this.taskTypes.add(new TaskElement("sendtask", SendTask.class));
        this.taskTypes.add(new TaskElement("receivetask", ReceiveTask.class));
        this.taskTypes.add(new TaskElement("manualtask", ManualTask.class));
        this.taskTypes.add(new TaskElement("servicetask", ServiceTask.class));
        this.taskTypes.add(new TaskElement("businessruletask", BusinessRuleTask.class));
        this.taskTypes.add(new TaskElement("scripttask", ScriptTask.class));
    }

    public static class TaskElement {
        private String keyword = "";
        private Class taskType;

        public TaskElement(String keyword, Class taskTyp) {
            this.keyword = keyword;
            this.taskType = taskTyp;
        }

        public Class getTaskType() {
            return taskType;
        }


        public String getKeyword() {
            return keyword;
        }

    }
}
