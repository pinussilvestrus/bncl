package de.niklaskiefer.bnclWeb;

import de.niklaskiefer.bnclCore.BnclToXmlWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/api/convert", method = RequestMethod.POST)
    public String convert(@RequestBody String bncl) throws Exception {
       return convertBnclToXML(bncl);
    }

    private String convertBnclToXML(String bncl) throws Exception {
        BnclToXmlWriter writer = new BnclToXmlWriter();
        return writer.convertBnclToXML(bncl);
    }
}
