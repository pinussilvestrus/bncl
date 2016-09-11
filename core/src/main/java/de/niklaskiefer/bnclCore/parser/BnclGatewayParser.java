package de.niklaskiefer.bnclCore.parser;

import de.niklaskiefer.bnclCore.BPMNModelBuilder;

import org.camunda.bpm.model.bpmn.instance.EventBasedGateway;
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Niklas Kiefer
 */
public class BnclGatewayParser extends BnclElementParser {

    private List<GatewayType> gatewayTypes = new ArrayList<>();

    public BnclGatewayParser(BPMNModelBuilder builder) {
        super(builder);
        initGatewayTypes();
    }

    public BnclGatewayParser() {
        initGatewayTypes();
    }

    public Gateway parseGateway(String elementString) throws Exception {
        List<String> withoutSpaces = BnclParser.getWordsWithoutSpaces(elementString);


        if (!BnclParser.checkWords(withoutSpaces)) {
            return null;
        }

        String first = withoutSpaces.get(0).toLowerCase();
        for (GatewayType gatewayType : gatewayTypes) {
            if (first.equals(gatewayType.getKeyword())) {
                Map<String, String> attributes = parseAttributes(withoutSpaces);
                return builder.createGateway(builder.getProcess(), attributes, gatewayType.getType());
            }
        }

        return null;
    }

    private void initGatewayTypes() {
        gatewayTypes.add(new GatewayType("parallelgateway", ParallelGateway.class));
        gatewayTypes.add(new GatewayType("exclusivegateway", ExclusiveGateway.class));
        gatewayTypes.add(new GatewayType("inclusivegateway", InclusiveGateway.class));
        gatewayTypes.add(new GatewayType("eventbasedgateway", EventBasedGateway.class));
    }

    public List<GatewayType> getGatewayTypes() {
        return gatewayTypes;
    }

    public static class GatewayType {
        private String keyword;
        private Class type;

        private GatewayType(String keyword, Class type) {
            this.keyword = keyword;
            this.type = type;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }
    }
}
