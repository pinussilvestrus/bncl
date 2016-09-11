package de.niklaskiefer.bnclCore.parser;

import de.niklaskiefer.bnclCore.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.util.List;

/**
 * @author Niklas Kiefer
 */
public class BnclSequenceFlowParser extends AbstractBnclParser {

    public static final String SEQUENCE_FLOW_KEYWORD = "sequenceflow";

    // attributes
    public static final String COMES_FROM = "comesfrom";
    public static final String GOES_TO = "goesto";

    private BPMNModelBuilder builder;

    public BnclSequenceFlowParser(BPMNModelBuilder builder) {
        this.builder = builder;
    }

    public SequenceFlow parseSequenceFlow(String elementString) {
        List<String> wordsWithoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : wordsWithoutSpaces) {
         logger().info(word);
         }**/

        if (!BnclParser.checkWords(wordsWithoutSpaces)) {
            return null;
        }

        String fromId = "";
        String toId = "";
        if (wordsWithoutSpaces.get(0).toLowerCase().equals(SEQUENCE_FLOW_KEYWORD)) {
            for (int i = 1; i < wordsWithoutSpaces.size(); i++) {
                String word = wordsWithoutSpaces.get(i);
                switch (word.toLowerCase()) {
                    case COMES_FROM:
                        fromId = wordsWithoutSpaces.get(i + 1);
                        break;
                    case GOES_TO:
                        toId = wordsWithoutSpaces.get(i + 1);
                        break;
                    default:
                        break;
                }
            }
        }

        if (!fromId.equals("") && !toId.equals("")) {
            return builder.createSequenceFlow(builder.getProcess(), fromId, toId);
        }

        return null;
    }
}
