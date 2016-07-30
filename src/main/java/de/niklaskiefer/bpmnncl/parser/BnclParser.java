package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Niklas Kiefer
 */
public class BnclParser extends AbstractBnclParser {

    public static final String BEGINNING_GROUP = "lets create a process";
    public static final String ELEMENT_BEGINNING = "with";
    public static final String INDEFINITE_ARTICLE_A = "a";
    public static final String INDEFINITE_ARTICLE_AN = "an";

    private static final String PARSING_FAILS_MESSAGE = "Parsing bncl failed";

    private BPMNModelBuilder builder;
    private BpmnModelInstance modelInstance;

    // granular parsers
    private BnclEventParser eventParser;
    private BnclTaskParser taskParser;
    private BnclSequenceFlowParser sequenceFlowParser;
    private BnclGatewayParser gatewayParser;

    private String copy;

    public BnclParser() {
        builder = new BPMNModelBuilder();
        eventParser = new BnclEventParser(builder);
        taskParser = new BnclTaskParser(builder);
        sequenceFlowParser = new BnclSequenceFlowParser(builder);
        gatewayParser = new BnclGatewayParser(builder);
        logger().setLevel(Level.INFO);
    }

    public static boolean checkWords(List<String> withoutSpaces) {
        try {
            withoutSpaces.get(0);
        } catch (Exception e) {
            return false;
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
            throw e;
        }

        builder.createDefinitions();
        builder.createProcess();
        buildElements(copy);

        modelInstance = builder.getModel();
        return modelInstance;
    }

    private void buildElements(String bncl) throws Exception {
        List<String> elements = splitBnclToElements(bncl);

        for (String element : elements) {
            eventParser.parseEvent(element);
            taskParser.parseTask(element);
            sequenceFlowParser.parseSequenceFlow(element);
            gatewayParser.parseGateway(element);
        }
    }

    private List<String> splitBnclToElements(String bncl) throws Exception {
        String[] splittedElements = bncl.toLowerCase().split(ELEMENT_BEGINNING);
        List<String> elements = new ArrayList<>();

        for(int i = 0; i < splittedElements.length; i++) {
            String element = splittedElements[i];

            // remove space on first position if necessary
            if (element.startsWith(" ")) {
                element = element.substring(1);
            }

            // check if 'a' or 'an' comes ofter 'with' and remove it
            if (element.startsWith(INDEFINITE_ARTICLE_A)) {
                element = element.substring(INDEFINITE_ARTICLE_A.length());
            } else if (element.startsWith(INDEFINITE_ARTICLE_AN)) {
                element = element.substring(INDEFINITE_ARTICLE_AN.length());
            }
            elements.add(element);
        }

        return elements;

    }

}
