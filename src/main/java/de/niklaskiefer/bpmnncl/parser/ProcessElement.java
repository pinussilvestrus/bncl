package de.niklaskiefer.bpmnncl.parser;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;

/**
 * @author Niklas Kiefer
 */
public class ProcessElement {

  private Class type;
  private ProcessElement parent;
  private String id;

  public ProcessElement(Class type, ProcessElement parent, String id) {
    this.type = type;
    this.parent = parent;
    this.id = id;
  }

  public Class getType() {
    return type;
  }

  public void setType(Class type) {
    this.type = type;
  }

  public ProcessElement getParent() {
    return parent;
  }

  public void setParent(ProcessElement parent) {
    this.parent = parent;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
