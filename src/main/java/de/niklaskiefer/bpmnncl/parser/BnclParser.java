package de.niklaskiefer.bpmnncl.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

/**
 * @author Niklas Kiefer
 */
public class BnclParser extends AbstractBnclParser {

  private final String BEGINNING_GROUP = "LETS CREATE A PROCESS";

  private final String PARSING_FAILS_MESSAGE = "Parsing bncl failed";

  private BPMNModelBuilder builder;
  private BpmnModelInstance modelInstance;

  // granular parsers
  private BnclElementParser elementParser;

  private String copy;

  public BnclParser() {
    builder = new BPMNModelBuilder();
    elementParser = new BnclElementParser(builder);
    logger().setLevel(Level.INFO);
  }

  public BpmnModelInstance parseBncl(String bnclString) throws Exception {
    if (bnclString.indexOf(BEGINNING_GROUP) != 0) {
      throw new Exception(PARSING_FAILS_MESSAGE);
    }

    copy = bnclString;
    copy = copy.substring(bnclString.indexOf(BEGINNING_GROUP) + BEGINNING_GROUP.length() + 1, copy.length());

    builder.createDefinitions();
    builder.createProcess();
    buildElements(copy);

    modelInstance = builder.getModel();
    return modelInstance;
  }

  private void buildElements(String bncl) {
   String[] splits = bncl.split("WITH");

    for (String split : splits) {
      elementParser.buildProcessElement(split);
    }
  }


}
