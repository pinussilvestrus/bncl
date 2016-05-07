package de.niklaskiefer.bpmnncl.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;

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
  private BnclEventParser eventParser;
  private BnclTaskParser taskParser;
  private BnclSequenceFlowParser sequenceFlowParser;

  private String copy;

  public BnclParser() {
    builder = new BPMNModelBuilder();
    eventParser = new BnclEventParser(builder);
    taskParser = new BnclTaskParser(builder);
    sequenceFlowParser = new BnclSequenceFlowParser(builder);
    logger().setLevel(Level.INFO);
  }

  public static List<String> getWordsWithoutSpaces(String element) {
    List<String> words = new ArrayList<>();
    Collections.addAll(words, element.split(" "));

    // remove spaces
    List<String> withoutSpaces = new ArrayList<>();
    for (String word : words) {
      if (!word.equals(" ") && !word.equals("")) {
        withoutSpaces.add(word);
      }
    }

    return withoutSpaces;
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
   String[] elements = bncl.split("WITH");

    for (String element : elements) {
      eventParser.parseEvent(element);
      taskParser.parseTask(element);
      sequenceFlowParser.parseSequenceFlow(element);
    }
  }

}
