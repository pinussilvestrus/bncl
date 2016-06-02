package de.niklaskiefer.bpmnncl.parser;

import de.niklaskiefer.bpmnncl.BPMNModelBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Niklas Kiefer
 */
public class BnclElementParserTest {
    private BnclElementParser bnclElementParser;

    @Before
    public void setUp() {
        BPMNModelBuilder builder = new BPMNModelBuilder();
        builder.createDefinitions();
        builder.createProcess();
        bnclElementParser = new BnclElementParser(builder);
    }

    @Test
    public void testParseAttributesWithoutSpaces() {
        String bncl = "startevent signed startevent1 called testEvent";
        List<String> words = BnclParser.getWordsWithoutSpaces(bncl);
        try {
            Map<String, String> attributes = bnclElementParser.parseAttributes(words);
            assertNotNull(attributes);
            assertTrue(attributes.size() == 2);
            assertNotNull(attributes.get("id"));
            assertEquals("startevent1", attributes.get("id"));
            assertNotNull(attributes.get("name"));
            assertEquals("testEvent", attributes.get("name"));
        } catch (Exception e) {
            assertEquals(1, 2);
        }
    }

    @Test
    public void testParseAttributesWithSpaces() {
        String bncl = "startevent signed startevent1 called very important message incoming";
        List<String> words = BnclParser.getWordsWithoutSpaces(bncl);
        try {
            Map<String, String> attributes = bnclElementParser.parseAttributes(words);
            assertNotNull(attributes);
            assertTrue(attributes.size() == 2);
            assertNotNull(attributes.get("id"));
            assertEquals("startevent1", attributes.get("id"));
            assertNotNull(attributes.get("name"));
            assertEquals("very important message incoming", attributes.get("name"));
        } catch (Exception e) {
            assertEquals(1, 2);
        }
    }

    @Test
    public void testParseAttributesNoId() {
        String bncl = "startevent called very important message incoming";
        List<String> words = BnclParser.getWordsWithoutSpaces(bncl);
        try {
            bnclElementParser.parseAttributes(words);
        } catch (Exception e) {
            assertEquals(1, 1);
        }
    }

}
