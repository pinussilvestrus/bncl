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
public class BnclParser {

  private final String BEGINNING_GROUP = "LETS CREATE A PROCESS";

  private final String PARSING_FAILS_MESSAGE = "Parsing bncl failed";

  // element types
  private final String START_EVENT = "STARTEVENT";
  private final String USER_TASK = "USERTASK";
  private final String END_EVENT = "ENDEVENT";

  private BPMNModelBuilder builder;
  private BpmnModelInstance modelInstance;
  private BnclObject bnclObject;

  private String copy;

  private static final Logger logger = Logger.getLogger(BnclParser.class.getName());

  public BnclParser() {
    builder = new BPMNModelBuilder();
    bnclObject = new BnclObject();
    logger.setLevel(Level.INFO);
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
      buildProcessElement(split);
    }
  }

  private void buildProcessElement(String elementString) {
    List<String> words = new ArrayList<>();
    Collections.addAll(words, elementString.split(" "));

    // remove spaces
    List<String> withoutSpaces = new ArrayList<>();
    for (String word : words) {
      if (!word.equals(" ") && !word.equals("")) {
        withoutSpaces.add(word);
      }
    }

    for (String word : withoutSpaces) {
      logger.info(word);
    }

    switch (withoutSpaces.get(0)) {
      case START_EVENT:
        builder.createElement(builder.getProcess(), "startEvent1", StartEvent.class);
        break;
      case END_EVENT:
        builder.createElement(builder.getProcess(), "endEvent1", EndEvent.class);
        break;
      case USER_TASK:
       builder.createElement(builder.getProcess(), "task1", UserTask.class);
      default: break;
    }
  }

}
