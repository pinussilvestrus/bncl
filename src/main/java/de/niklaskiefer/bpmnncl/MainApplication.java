package de.niklaskiefer.bpmnncl;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niklas Kiefer
 * Command Line Interface for Bncl
 */
public class MainApplication {

    public static String testBncl =
            "lets create a process with " +
                    "a signalstartevent signed startEvent1 called message incoming with " +
                    "an errorevent signed event1 called 3 Stunden vergangen with " +
                    "a scripttask signed usertask1 called dosomething with " +
                    "usertask signed usertask2 with " +
                    "inclusivegateway signed gateway1 with " +
                    "parallelgateway signed gateway2 with " +
                    "sequenceflow comesfrom startevent1 goesto event1 with " +
                    "sequenceflow comesfrom event1 goesto gateway1 with " +
                    "sequenceflow comesfrom gateway1 goesto usertask1 with " +
                    "sequenceflow comesfrom gateway1 goesto usertask2 with " +
                    "sequenceflow comesfrom usertask1 goesto gateway2 with " +
                    "sequenceflow comesfrom usertask2 goesto gateway2 with " +
                    "messagethrowevent signed endevent1 called terminated with " +
                    "sequenceflow comesfrom gateway2 goesto endevent1";

    private static List<String> cliOptions = new ArrayList<>();
    private static Console console;

    public static void main(String[] args) {
        startConsoleProgram();
    }

    private static void startConsoleProgram() {
        initCLIOptions();

        try {

            console = System.console();
            if (console != null) {
                System.out.println("Welcome to the Bncl-Parser demo console program! Please chose an option: ");
                printCLIOptions();
                String option = console.readLine("> ");
                executeOption(option);
            } else {
                BnclToXmlWriter writer = new BnclToXmlWriter();
                writer.createBPMNFile(testBncl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Oh, there was a problem in parsing Bncl!");
        }
    }

    private static void executeOption(String option) throws Exception {
        switch (option) {
            case "1": simpleConverting(); break;
            case "2": fileConverting(); break;
            default:
                System.out.println("No valid option, please try again!");
                break;
        }
    }

    private static void simpleConverting() throws Exception {
        System.out.println("Input a Bncl-Statement: ");
        String input = console.readLine("> ");

        BnclToXmlWriter writer = new BnclToXmlWriter();
        writer.createBPMNFile(input);
    }

    private static void fileConverting() throws Exception {
        System.out.println("Input a Bncl-file: ");
        String fileName = console.readLine("> ");

        BnclToXmlWriter writer = new BnclToXmlWriter();
        writer.createBPMNFileFromFile(fileName);
    }

    private static void printCLIOptions() {
        for (String cliOption : cliOptions) {
            System.out.println(cliOption);
        }
    }

    private static void initCLIOptions() {
        cliOptions.add("1: Parse a single Bncl-statement");
        cliOptions.add("2: Parse a Bncl-file");
    }
}
