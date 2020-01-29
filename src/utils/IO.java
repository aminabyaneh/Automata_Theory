package utils;

import java.util.ArrayList;
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
    private DataType dataType;

    /** The input data. */
    Object data;

    /**
     * Instantiates a new IO interface.
     */
    public IO() {

        /** Initiate scanner object. */
        this.sc = new Scanner(System.in);

        this.dataType = readDataType();

        /** check the output for next action. */
        switch (this.dataType) {

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
    public DataType getDataType() {

        return dataType;
    }

    /**
     * Read the first line of input and return the function.
     *
     * @return the functions
     */
    private DataType readDataType() {

        String input = this.sc.nextLine();

        /** map strings to corresponding enumerations. */
        switch (input) {

        case "CFG":
            return DataType.CFG;
        case "NFA":
            return DataType.NFA;
        case "RegEx":
            return DataType.RegEx;

        default:
            return DataType.Unknown;
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

        NFAEntry inputNFA = new NFAEntry();

        /** Read the start state. */
        String str = sc.nextLine();
        inputNFA.setStartState(str);

        /** Read all the final states. */
        str = sc.nextLine();
        inputNFA.setFinalStates(str.split("\\s+"));

        /** Read the alphabet. */
        str = sc.nextLine();
        inputNFA.setAlphabet(str.split("\\s+"));

        /** The states are required. */
        str = sc.nextLine();
        inputNFA.setStates(str.split("\\s+"));

        /** Read matrix until command arrives. */
        str = sc.nextLine();
        ArrayList<String> matrix = new ArrayList<String>();

        while (this.taskToTasks(str) == Tasks.Unknown) {

            matrix.add(str);
            str = sc.nextLine();
        }

        inputNFA.setStateTransitionMat(matrix);
        inputNFA.setTask(this.taskToTasks(str));

        return inputNFA;
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

