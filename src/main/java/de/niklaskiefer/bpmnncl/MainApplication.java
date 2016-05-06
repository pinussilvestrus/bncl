package de.niklaskiefer.bpmnncl;

/**
 * @author Niklas Kiefer
 */
public class MainApplication {
    public static void main(String[] args) {
        XMLWriter writer = new XMLWriter();

        String testBncl =
            "LETS CREATE A PROCESS " +
                "Called  test_process " +
                "WITH  STARTEVENT " +
                    "Called startEvent1 " +
                "WITH USERTASK " +
                    "Called userTask1 " +
                "WITH ENDEVENT " +
                    "Called endEvent1 " +
                "WITH SEQUENCEFLOW " +
                    "ComesFrom startEvent1 " +
                    "GoesTo userTask1 " +
                "WITH SEQUENCEFLOW " +
                    "ComesFrom userTask1 " +
                    "GoesTo endEvent1";

        writer.createBPMNFile(testBncl);
    }
}
