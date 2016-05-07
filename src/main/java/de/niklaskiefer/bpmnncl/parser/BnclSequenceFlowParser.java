package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import java.util.List;

/**
 * @author Niklas Kiefer
 */
public class BnclSequenceFlowParser extends AbstractBnclParser {

    private final String SEQUENCE_FLOW_KEYWORD = "SEQUENCEFLOW";

    // attributes
    private final String COMES_FROM = "ComesFrom";
    private final String GOES_TO = "GoesTo";

    private BPMNModelBuilder builder;

    public BnclSequenceFlowParser(BPMNModelBuilder builder) {
        this.builder = builder;
    }

    public void parseSequenceFlow(String elementString) {
        List<String> wordsWithoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : wordsWithoutSpaces) {
         logger().info(word);
        }**/

        String fromId = "";
        String toId = "";
        if (wordsWithoutSpaces.get(0).equals(SEQUENCE_FLOW_KEYWORD)) {
            for (int i = 1; i < wordsWithoutSpaces.size(); i++) {
                String word = wordsWithoutSpaces.get(i);
                switch (word) {
                    case COMES_FROM:
                        fromId = wordsWithoutSpaces.get(i+1);
                        break;
                    case GOES_TO:
                        toId = wordsWithoutSpaces.get(i+1);
                        break;
                    default: break;
                }
            }
        }

        if (!fromId.equals("") && !toId.equals("")) {
            builder.createSequenceFlow(builder.getProcess(), fromId, toId);
        }
    }
}
