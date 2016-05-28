package de.niklaskiefer.bpmnncl;

import java.io.Console;

/**
 * @author Niklas Kiefer
 */
public class MainApplication {

    private static String testBncl =
            "lets create a process with messagestartevent signed startEvent1 called startevent1 with scripttask signed usertask1 called dosomething with usertask signed usertask2 with parallelgateway signed gateway1 with parallelgateway signed gateway2 with sequenceflow comesfrom startevent1 goesto gateway1 with sequenceflow comesfrom gateway1 goesto usertask1 with sequenceflow comesfrom gateway1 goesto usertask2 with sequenceflow comesfrom usertask1 goesto gateway2 with sequenceflow comesfrom usertask2 goesto gateway2 with messagethrowevent signed endevent1 called terminated with sequenceflow comesfrom gateway2 goesto endevent1";

    public static void main(String[] args) {
        startConsoleProgram();
    }

    private static void startConsoleProgram() {
        Console console = null;
        String input = null;

        try {

            console = System.console();
            if (console != null) {
                System.out.println("Welcome to the Bncl-Parser demo console program!");
                input = console.readLine("> ");
                BnclToXmlWriter writer = new BnclToXmlWriter();
                writer.createBPMNFile(input);
            } else {
                BnclToXmlWriter writer = new BnclToXmlWriter();
                writer.createBPMNFile(testBncl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Oh, there was a problem in parsing Bncl!");
        }
    }
}
