package de.niklaskiefer.bpmnncl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Niklas Kiefer
 */
public class BnclToXmlWriterTest {

    private BnclToXmlWriter bnclToXmlWriter;

    private final String testBncl = "lets create a process with startevent signed startEvent1 called startevent1 with endevent signed endevent1 called endevent1 with sequenceflow comesfrom startevent1 goesto endevent1";

    @Before
    public void setUp() {
        bnclToXmlWriter = new BnclToXmlWriter();
    }

    @Test
    public void testCreateBPMNFile() {
        try {
            String xml = bnclToXmlWriter.createBPMNFile(testBncl);
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
            falseBncl = "lets create process with startevent signed startEvent1 called startevent1 with endevent signed endevent1 called endevent1 with sequenceflow comesfrom startevent1 goesto endevent1";
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

}