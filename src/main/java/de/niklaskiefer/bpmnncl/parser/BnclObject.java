package de.niklaskiefer.bpmnncl.parser;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.bpmn.instance.Process;

/**
 * @author Niklas Kiefer
 */
public class BnclObject {

  private List<FlowObject> flowObjects = new ArrayList<FlowObject>();
  private List<ProcessElement> processElements = new ArrayList<ProcessElement>();

  public List<FlowObject> getFlowObjects() {
    return flowObjects;
  }

  public void setFlowObjects(List<FlowObject> flowObjects) {
    this.flowObjects = flowObjects;
  }

  public List<ProcessElement> getProcessElements() {
    return processElements;
  }

  public void setProcessElements(List<ProcessElement> processElements) {
    this.processElements = processElements;
  }
}
