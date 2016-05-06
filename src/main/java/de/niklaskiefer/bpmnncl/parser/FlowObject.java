package de.niklaskiefer.bpmnncl.parser;

import org.camunda.bpm.model.bpmn.instance.FlowNode;

/**
 * @author Niklas Kiefer
 */
public class FlowObject {
  private ProcessElement from;
  private ProcessElement to;

  public ProcessElement getTo() {
    return to;
  }

  public void setTo(ProcessElement to) {
    this.to = to;
  }

  public ProcessElement getFrom() {
    return from;
  }

  public void setFrom(ProcessElement from) {
    this.from = from;
  }
}
