package de.niklaskiefer.bnclCore.parser;

import java.util.logging.Logger;

/**
 * @author Niklas
 */
public class AbstractBnclParser {
    public Logger logger() {
        return Logger.getLogger(this.getClass().getName());
    }
}
