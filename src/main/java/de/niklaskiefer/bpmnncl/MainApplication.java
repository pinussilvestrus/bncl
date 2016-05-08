package de.niklaskiefer.bpmnncl;

import java.io.Console;
import java.util.Scanner;

/**
 * @author Niklas Kiefer
 */
public class MainApplication {

    private static String testBncl =
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

    public static void main(String[] args) {
        startConsoleProgram();
    }

    private static void startConsoleProgram() {
        Console console = null;
        String input = null;

        try{
            System.out.println("Welcome to the Bncl-Parser demo console program!");
            console = System.console();
            if (console != null) {
                input = console.readLine("> ");
                BnclToXmlWriter writer = new BnclToXmlWriter();
                writer.createBPMNFile(input);
            }
        }catch(Exception ex){
            System.out.println("Oh, there was a problem in parsing Bncl!");
        }
    }
}
