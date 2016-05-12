package de.niklaskiefer.bpmnncl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

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
    for (BpmnModelElementInstance element: elements) {
      if (element.getAttributeValue("id").equals(idFrom)) {
        from = (FlowNode) element;
      } else if (element.getAttributeValue("id").equals(idTo)) {
        to = (FlowNode) element;
      }
    }

    if(from != null && to != null) {
      return createSequenceFlow(process, from, to);
    }

    return null;
  }

  public ParallelGateway createParallelGateway(Process process, Map<String, String> attributes) {
    return createElement(process, ParallelGateway.class, attributes);
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
