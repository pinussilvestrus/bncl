package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.Gateway;

import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclGatewayParser extends BnclElementParser {

    // types
    private static final String PARALLEL_GATEWAY = "parallelgateway";

    public BnclGatewayParser(BPMNModelBuilder builder) {
        super(builder);
    }

    public Gateway parseGateway(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);

        /**for (String word : withoutSpaces) {
         logger().info(word);
         }**/

        String id;
        Class type;

        if (!BnclParser.checkWords(withoutSpaces)) {
            return null;
        }

        switch (withoutSpaces.get(0).toLowerCase()) {
            case PARALLEL_GATEWAY:
                Map<String, String> attributes = parseAttributes(withoutSpaces);
                return builder.createParallelGateway(builder.getProcess(), attributes);
            default:
                return null;
        }
    }
}
