package utils;

import java.util.Scanner;

import entries.CFGEntry;
import entries.NFAEntry;
import entries.RegExEntry;

/**
 * The Class IO.
 */
public class IO {

    /** The sc container for scanner object. */
    private Scanner sc;

    /** The data type to detect function from main. */
    private Functions dataType;

    /** The input data. */
    Object data;

    /**
     * Instantiates a new IO interface.
     */
    public IO() {

        /** Initiate scanner object. */
        this.sc = new Scanner(System.in);

        Functions func = readInput();
        this.setDataType(func);

        /** check the output for next action. */
        switch (func) {

        case CFG:
            this.data = readCFG();
            break;

        case NFA:
            this.data = readNFA();
            break;

        case RegEx:
            this.data = readRegEx();
            break;

        default:
            break;
        }
    }

    /**
     * Gets the data type.
     *
     * @return the data type
     */
    public Functions getDataType() {

        return dataType;
    }

    /**
     * Sets the data type.
     *
     * @param dataType the new data type
     */
    public void setDataType(Functions dataType) {

        this.dataType = dataType;
    }

    /**
     * Read the first line of input and return the function.
     *
     * @return the functions
     */
    private Functions readInput() {

        String input = this.sc.nextLine();

        /** map strings to corresponding enumerations. */
        switch (input) {

        case "CFG":
            return Functions.CFG;
        case "NFA":
            return Functions.NFA;
        case "RegEx":
            return Functions.RegEx;

        default:
            return Functions.Unknown;
        }
    }

    /**
     * Read CFG.
     *
     * @return the CFG entry
     */
    private CFGEntry readCFG() {
        return null;

        //TODO
    }

    /**
     * Read NFA.
     *
     * @return the NFA entry
     */
    private NFAEntry readNFA() {

        return null;

        //TODO
    }

    /**
     * Parse a regular expression.
     *
     * @return the RegExEntry class
     */
    private RegExEntry readRegEx() {

        RegExEntry inputRegEx = new RegExEntry();

        inputRegEx.setInputRegEx(sc.nextLine());
        inputRegEx.setTask(taskToTasks(sc.nextLine()));

        sc.close();

        return inputRegEx;
    }

    /**
     * Task in string to tasks enumerator.
     * Also used to recognize the end of the input sequences.
     *
     * @param task the task
     * @return task
     */
    private Tasks taskToTasks(String task) {

        switch (task) {

        case "DFA":
            return Tasks.DFA;
        case "NFA":
            return Tasks.NFA;
        case "PDA":
            return Tasks.PDA;
        case "RegEx":
            return Tasks.RegEx;

        default:
            return Tasks.Unknown;
        }
    }


    /**
     * Gets the data.
     *
     * @return the data
     */
    public Object getData() {

        return data;
    }
}

