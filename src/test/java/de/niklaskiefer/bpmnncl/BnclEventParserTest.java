package de.niklaskiefer.bpmnncl;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import de.niklaskiefer.bpmnncl.parser.BnclEventParser;

import static org.junit.Assert.*;

/**
 * @author Niklas Kiefer
 */
public class BnclEventParserTest {

  private BnclEventParser bnclEventParser;

  @Before
  public void setUp() {
    BPMNModelBuilder builder = new BPMNModelBuilder();
    builder.createDefinitions();
    builder.createProcess();
    bnclEventParser = new BnclEventParser(builder);
  }

  @Test
  public void testParseStartEvent() {
    String bncl = "startevent signed event1";
    try {
      StartEvent startEvent = (StartEvent) bnclEventParser.parseEvent(bncl);
      assertNotNull(startEvent);
      assertEquals(startEvent.getAttributeValue("id"), "event1");
    }
    catch (Exception e) {
      e.printStackTrace();
      assertEquals(2, 3);
    }
  }

  @Test
  public void testParseMessageEvent() {
    String bncl = "messagethrowevent signed event1";
    try {
      IntermediateThrowEvent startEvent = (IntermediateThrowEvent) bnclEventParser.parseEvent(bncl);
      assertNotNull(startEvent);
      assertEquals(startEvent.getAttributeValue("id"), "event1");
      assertNotNull(startEvent.getChildElementsByType(MessageEventDefinition.class));
    }
    catch (Exception e) {
      e.printStackTrace();
      assertEquals(2, 3);
    }
  }

  @Test
  public void testFalseEvent() {
    String bncl = "dump signed event1";
    try {
      BpmnModelElementInstance instance = bnclEventParser.parseEvent(bncl);
      assertNull(instance);
    }
    catch (Exception e) {
      e.printStackTrace();
      assertEquals(2, 3);
    }
  }

}
