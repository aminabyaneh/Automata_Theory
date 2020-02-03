package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import entries.CFGEntry;

/**
 * The Class CFG.
 */
public class CFG {

    /** The data. */
    private CFGEntry data;

    /** Special characters. */
    private static String stateE = "q2";
    private static String stateS = "q0";
    private static String stateL = "q1";
    private static String space = " ";
    private static String epsilon = "\u03B5";
    private static String dollar = "$";

    /** Logger is initiated. */
    @SuppressWarnings("unused")
    static final Logger LOGGER =
    Logger.getLogger(RegEx.class.getName());

    /** The pda. */
    private ArrayList<String> PDA;

    /** The stack variables. */
    private ArrayList<String> stackVariables;

    /**
     * Instantiates a new CFG.
     *
     * @param data the data
     */
    public CFG(CFGEntry data) {

        this.data = data;
        PDA = new ArrayList<String>();
        stackVariables = new ArrayList<String>();
    }

    /**
     * Task handler.
     * Useless here as we just need to print the PDA.
     *
     */
    public void taskHandler() {

        switch (data.getTask()) {

        case PDA:
            /** Make and print the PDA. */
            this.makePDA();
            this.printPDA();
            break;

        default:
            LOGGER.warning("Task is not recognized.");
        }
    }

    /**
     * Prints the PDA.
     */
    private void printPDA() {

        /**‌ Print the start state. */
        System.out.println(CFG.stateS);

        /** Print the stack initialization. */
        System.out.println(CFG.dollar);

        /** Print the final state. */
        System.out.println(CFG.stateE);

        /** Print the terminals. */
        for (String terminal : this.data.getTerminals()) {

            System.out.print(terminal);
            System.out.print(CFG.space);
        }
        System.out.println("");

        /** Print the stack variables, not all the variables are stack. */
        for (String var : this.stackVariables) {

            System.out.print(var);
            System.out.print(CFG.space);
        }
        System.out.println("");

        /** Print the states. */
        System.out.print(CFG.stateS);
        System.out.print(CFG.space);
        System.out.print(CFG.stateL);
        System.out.print(CFG.space);
        System.out.print(CFG.stateE);
        System.out.println("");

        /** Print the phrase numbers. */
        System.out.println(this.PDA.size());

        /** Print all the lines. */
        for (String line : PDA) {

            System.out.println(line);
        }
    }

    private void makePDA() {

        String line = null;

        /** Add the start state. */
        line = CFG.stateS + CFG.space + CFG.epsilon +
                CFG.space + CFG.epsilon +
                CFG.space + CFG.stateL + CFG.space +
                this.data.getStartSymbol();
        this.PDA.add(line);

        /** For each rule we have to print A -> w. */
        for (Map.Entry<String, ArrayList<String>> entry :
            this.data.getRules().entrySet()) {

            for (String rhs : entry.getValue()) {

                line = CFG.stateL + CFG.space + CFG.epsilon +
                        CFG.space + entry.getKey() + CFG.space +
                        CFG.stateL + CFG.space +
                        rhs;


                /** Learn stack variable. */
                for (int i = 0; i < rhs.length(); i++) {

                    String var = Character.toString(rhs.charAt(i));
                    if (var.equals(CFG.epsilon))
                        continue;

                    stackVariables.add(var);
                }

                /* Construct the PDA. */
                this.PDA.add(line);
            }
        }

        /** For each terminal we have to print a -> e. */
        for (String terminal : this.data.getTerminals()) {

            line = CFG.stateL + CFG.space + CFG.epsilon +
                    CFG.space + terminal + CFG.space +
                    CFG.stateL + CFG.space +
                    CFG.epsilon;

            stackVariables.add(terminal);
            this.PDA.add(line);
        }

        /** Add the end state. */
        line = CFG.stateL + CFG.space + CFG.epsilon +
                CFG.space + CFG.dollar +
                CFG.space + CFG.stateE + CFG.space +
                CFG.epsilon;
        this.PDA.add(line);

        /** Remove redundant variables. */
        Set<String> set = new HashSet<>(this.stackVariables);
        this.stackVariables.clear();
        this.stackVariables.addAll(set);
        System.out.println(this.stackVariables.toString());
    }

}
