package de.niklaskiefer.bpmnncl;

import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnDiagram;

/**
 * @author Niklas Kiefer
 */
public class BPMNModelBuilder {

  private static final String CAMUNDA_NAMESPACE = "http://camunda.org/examples";

  private BpmnModelInstance bpmnModelInstance;
  private Process process;

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

  public <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass, Map<String, String> attributes) {
    T element = createElement(parentElement, id, elementClass);
    for (Map.Entry<String, String> s : attributes.entrySet()) {
      element.setAttributeValue(s.getKey(), s.getValue(), false);
    }
    return element;
  }

  public <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
    T element = bpmnModelInstance.newInstance(elementClass);
    element.setAttributeValue("id", id, true);
    parentElement.addChildElement(element);
    return element;
  }

  public BpmnModelInstance getModel() {
    return bpmnModelInstance;
  }

  @Deprecated
  public void createTestElements(Process process) {
    StartEvent startEvent = createElement(process, "start", StartEvent.class);
    UserTask task1 = createElement(process, "task1", UserTask.class);
    task1.setName("User Task");
    EndEvent endEvent = createElement(process, "end", EndEvent.class);

    // create the connections between the elements
    createSequenceFlow(process, startEvent, task1);
    createSequenceFlow(process, task1, endEvent);
  }

  public Process getProcess() {
    return process;
  }
}
