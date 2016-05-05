package de.niklaskiefer.bpmnncl;

/**
 * @author Niklas Kiefer
 */
public class MainApplication {
    public static void main(String[] args) {
        XMLWriter writer = new XMLWriter();
        writer.createBPMNFile();
    }
}
