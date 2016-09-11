package de.niklaskiefer.bnclCore;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EventDefinition;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Niklas Kiefer
 */
public class BPMNModelBuilder {

    private static final String CAMUNDA_NAMESPACE = "http://camunda.org/examples";

    private BpmnModelInstance bpmnModelInstance;
    private Process process;
    private List<BpmnModelElementInstance> elements = new ArrayList<>();

    public BPMNModelBuilder() {
        String id = "pid-" + UUID.randomUUID().toString();
        bpmnModelInstance = Bpmn.createExecutableProcess(id)
                .startEvent()
                .userTask()
                .endEvent()
                .done();
    }


    public Process createProcess() {
        String id = "pid-" + UUID.randomUUID().toString();
        process = createElement(bpmnModelInstance.getDefinitions(), id, Process.class);
        return process;
    }

    public void createDefinitions() {
        Definitions definitions = bpmnModelInstance.newInstance(Definitions.class);
        definitions.setTargetNamespace(CAMUNDA_NAMESPACE);
        bpmnModelInstance.setDefinitions(definitions);
    }

    public <T extends EventDefinition> T createEventDefinition(BpmnModelElementInstance parentElement, Process process, Class<T> eventDefinitionClass) {
        T element = bpmnModelInstance.newInstance(eventDefinitionClass);
        parentElement.addChildElement(element);
        elements.add(element);
        return element;
    }

    public SequenceFlow createSequenceFlow(Process process, String idFrom, String idTo) {
        FlowNode from = null;
        FlowNode to = null;
        for (BpmnModelElementInstance element : elements) {
            if (element.getAttributeValue("id").equals(idFrom)) {
                from = (FlowNode) element;
            } else if (element.getAttributeValue("id").equals(idTo)) {
                to = (FlowNode) element;
            }
        }

        if (from != null && to != null) {
            return createSequenceFlow(process, from, to);
        }

        return null;
    }

    public Gateway createGateway(Process process, Map<String, String> attributes, Class type) {
        return (Gateway) createElement(process, type, attributes);
    }

    public SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {
        String identifier = from.getId() + "-" + to.getId();
        SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class);
        process.addChildElement(sequenceFlow);
        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);
        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);
        return sequenceFlow;
    }

    public <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, Class<T> elementClass, Map<String, String> attributes) {
        T element = createElement(parentElement, "dump", elementClass);
        for (Map.Entry<String, String> s : attributes.entrySet()) {
            element.setAttributeValue(s.getKey(), s.getValue(), false);
        }
        return element;
    }

    public <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
        T element = bpmnModelInstance.newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);
        elements.add(element); // save all elements for later referencing
        return element;
    }

    public BpmnModelInstance getModel() {
        return bpmnModelInstance;
    }


    public Process getProcess() {
        return process;
    }
}
