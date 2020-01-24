package core;

import utils.RegExEntry;
import utils.Tasks;

/**
 * The Class RegEx.
 * This class handles related conversions
 * and optimizations to regular expression inputs.
 */
public class RegEx {

    /** The main string of RegEx. */
    private String regex;

    /** The task in which the code has to perform. */
    private Tasks task;

    /**
     * Instantiates a new RegEx class.
     *
     * @param data the data
     */
    public RegEx(RegExEntry data) {

        /** Set the main variables. */
        this.regex = data.getInputRegEx();
        this.task = data.getTask();

        /** Call the task to action function. */
    }

}
