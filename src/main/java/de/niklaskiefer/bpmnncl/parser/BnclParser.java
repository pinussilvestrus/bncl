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

  private final String BEGINNING_GROUP = "lets create a process";

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

  public static boolean checkWords(List<String> withoutSpaces) {
    try {
      withoutSpaces.get(0);
    } catch (Exception e) {
      return false ;
    }

    return true;
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
    if (bnclString.toLowerCase().indexOf(BEGINNING_GROUP) != 0) {
      throw new Exception(PARSING_FAILS_MESSAGE);
    }

    copy = bnclString;

    try {
      copy = copy.substring(bnclString.indexOf(BEGINNING_GROUP) + BEGINNING_GROUP.length() + 1, copy.length());
    } catch (StringIndexOutOfBoundsException e) {
      System.exit(1);
    }

    builder.createDefinitions();
    builder.createProcess();
    buildElements(copy);

    modelInstance = builder.getModel();
    return modelInstance;
  }

  private void buildElements(String bncl) throws Exception {
   String[] elements = bncl.toLowerCase().split("with");

    for (String element : elements) {
      eventParser.parseEvent(element);
      taskParser.parseTask(element);
      sequenceFlowParser.parseSequenceFlow(element);
    }
  }

}
