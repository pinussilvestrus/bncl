package de.niklaskiefer.bnclCore;

import de.niklaskiefer.bnclCore.BnclToXmlWriter;
import de.niklaskiefer.bnclCore.MainApplication;
import de.niklaskiefer.bnclCore.commons.BnclRandomUtils;
import de.niklaskiefer.bnclCore.parser.BnclEventParser;
import de.niklaskiefer.bnclCore.parser.BnclTaskParser;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Niklas Kiefer
 */
public class BnclToXmlWriterTest {

    private BnclToXmlWriter bnclToXmlWriter;
    private BnclEventParser eventParser;
    private BnclTaskParser taskParser;

    private final String testBncl = MainApplication.testBncl;
    @Before
    public void setUp() {
        bnclToXmlWriter = new BnclToXmlWriter();
        eventParser = new BnclEventParser();
        taskParser = new BnclTaskParser();
    }

    @Test
    public void testCreateBPMNFile() {
        try {
            String xml = bnclToXmlWriter.convertBnclToXML(testBncl);
            assertNotNull(xml); // todo: test xml components
        } catch (Exception e) {
            assertEquals(1, 2); //test failed
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateBPMNFileFalseBeginning() {
        String falseBncl;
        boolean failed = false;
        try {
            falseBncl = "lets create process with startevent " +
                    "signed startEvent1 called startevent1 with endevent" +
                    "signed endevent1 called endevent1 with sequenceflow " +
                    "comesfrom startevent1 goesto endevent1";
            bnclToXmlWriter.createBPMNFile(falseBncl);
        } catch (Exception e) {
            failed = true;
        }

        assertTrue(failed);
    }

    @Test
    public void testCreateBPMNFileNoId() {
        String falseBncl;
        boolean failed = false;
        try {
            falseBncl = "lets create a process with startevent called startevent1 with endevent signed endevent1 called endevent1 with sequenceflow comesfrom startevent1 goesto endevent1";
            bnclToXmlWriter.createBPMNFile(falseBncl);
        } catch (Exception e) {
            failed = true;
        }
        assertTrue(failed);
    }

    @Test
    public void testParsingAllEventAndTaskTypes() {
        String bncl = generateTestBnclWithEverything();
        assertNotNull(bncl);
        try {
            String xml = bnclToXmlWriter.convertBnclToXML(bncl);
            assertNotNull(xml);

            xml = xml.replace("\r", "").replace("\n", "");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            assertNotNull(doc);

        } catch (Exception e) {
            assertEquals(1, 2); //test failed
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertBnclFileToXML() {
        String fileName = "test.bncl";
        try {
            String xml = bnclToXmlWriter.convertBnclFileToXML(fileName);
            assertNotNull(xml);
        } catch (Exception e) {
            assertEquals(1, 2); //test failed
            e.printStackTrace();
        }
    }

    private String generateTestBnclWithEverything() {
        StringBuilder sb = new StringBuilder();
        sb.append("lets create a process ");

        for (BnclEventParser.EventElement element : eventParser.getEventTypes()) {
            sb.append(" with ");
            sb.append(element.getKeyword());
            sb.append(" signed ");
            sb.append(BnclRandomUtils.getSaltString());
        }

        for (BnclTaskParser.TaskElement element : taskParser.getTaskTypes()) {
            sb.append(" with ");
            sb.append(element.getKeyword());
            sb.append(" signed ");
            sb.append(BnclRandomUtils.getSaltString());
        }

        return sb.toString();
    }


}