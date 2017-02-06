package de.niklaskiefer.bnclDemo;

import de.niklaskiefer.bnclCore.BnclToXmlWriter;
import de.niklaskiefer.bnclCore.parser.BnclElementParser;
import de.niklaskiefer.bnclCore.parser.BnclEventParser;
import de.niklaskiefer.bnclCore.parser.BnclGatewayParser;
import de.niklaskiefer.bnclCore.parser.BnclParser;
import de.niklaskiefer.bnclCore.parser.BnclSequenceFlowParser;
import de.niklaskiefer.bnclCore.parser.BnclTaskParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private static final String BNCL_PARSER_ERROR = "Error in parsing bncl statement!";
    private static final String BNCL_PARSER_SUCCESS = "Successfully parsing bncl statement!";

    private static final String testBncl =
            "lets create a process with " +
                    "signalstartevent signed startEvent1 called message incoming with " +
                    "errorevent signed event1 called 3 Stunden vergangen with " +
                    "scripttask signed usertask1 called dosomething with " +
                    "usertask signed usertask2 with " +
                    "parallelgateway signed gateway1 with " +
                    "parallelgateway signed gateway2 with " +
                    "sequenceflow comesfrom startevent1 goesto event1 with " +
                    "sequenceflow comesfrom event1 goesto gateway1 with " +
                    "sequenceflow comesfrom gateway1 goesto usertask1 with " +
                    "sequenceflow comesfrom gateway1 goesto usertask2 with " +
                    "sequenceflow comesfrom usertask1 goesto gateway2 with " +
                    "sequenceflow comesfrom usertask2 goesto gateway2 with " +
                    "messagethrowevent signed endevent1 called terminated with " +
                    "sequenceflow comesfrom gateway2 goesto endevent1";

    @ModelAttribute("bnclWords")
    public List<String> bnclWords() {
        return getAllBnclWords();
    }

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("xml", "");
        return "main";
    }

    @RequestMapping(value = "/convertBncl", method = RequestMethod.POST)
    public String convert(@RequestParam(value="bncl", required=true) String bncl, Model model) {
        model.addAttribute("bncl", bncl);
        try {
            model.addAttribute("xml", convertBnclToXML(bncl));
            model.addAttribute("notificationMessage", BNCL_PARSER_SUCCESS);
        } catch (Exception e) {
            LOGGER.info(BNCL_PARSER_ERROR);
            model.addAttribute("err", true);
            model.addAttribute("notificationMessage", e.getMessage());
            model.addAttribute("xml", "");
        }
        return "main";
    }

    private String convertBnclToXML(String bncl) throws Exception {
        BnclToXmlWriter writer = new BnclToXmlWriter();
        return writer.convertBnclToXML(bncl);
    }

    private List<String> getAllBnclWords() {
        final List<String> words = new ArrayList<>();

        // basic words
        words.add(BnclParser.BEGINNING_GROUP);
        words.add(BnclParser.ELEMENT_BEGINNING);

        // attributes
        BnclElementParser elementParser = new BnclElementParser();
        for(BnclElementParser.AttributeElement element : elementParser.getAttributeTypes()) {
            words.add(element.getBncl());
        }

        // events
        BnclEventParser eventParser = new BnclEventParser();
        for(BnclEventParser.EventElement element : eventParser.getEventTypes()) {
            words.add(element.getKeyword());
        }

        // tasks
        BnclTaskParser taskParser = new BnclTaskParser();
        for(BnclTaskParser.TaskElement element : taskParser.getTaskTypes()) {
            words.add(element.getKeyword());
        }

        // gateways
        BnclGatewayParser gatewayParser = new BnclGatewayParser();
        for(BnclGatewayParser.GatewayType element : gatewayParser.getGatewayTypes()) {
            words.add(element.getKeyword());
        }

        // sequence flows
        words.add(BnclSequenceFlowParser.COMES_FROM);
        words.add(BnclSequenceFlowParser.SEQUENCE_FLOW_KEYWORD);
        words.add(BnclSequenceFlowParser.GOES_TO);

        return words;
    }
}
