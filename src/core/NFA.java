package core;

import java.util.ArrayList;
import java.util.logging.Logger;

import utils.Phrase;

/**
 * The Class NFA.
 */
public class NFA {

    /** The NFA table. */
    ArrayList<ArrayList<ArrayList<Integer>>> NFATable;

    /** Logger is initiated. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER =
    Logger.getLogger(RegEx.class.getName());

    public static int epsilon = -1;

    public NFA() {

        super();
    }

    /**
     * Instantiates a new trivial NFA.
     *
     * @param p the phrase on which an NFA is built.
     */
    public NFA(Phrase p) {

        NFATable = new ArrayList<ArrayList<ArrayList<Integer>>>();

        ArrayList<ArrayList<Integer>> column = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(0);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add((int)p.string.charAt(0));
        column.add(cell);

        NFATable.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(1);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);

        NFATable.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);
        cell = new ArrayList<Integer>();
        column.add(cell);

        NFATable.add(column);
    }

    public static NFA unionNFA(ArrayList<NFA> nfas) {

        NFA nfaMerged = new NFA();

        return nfaMerged;
    }

    /**
     * Calculates the concatenation of an array of NFAs.
     *
     * @param nfas the input ArrayList of NFAs
     * @return the final NFA
     */
    public static NFA concatNFA(ArrayList<NFA> nfas) {

        NFA nfaLargest = NFA.getLargestNFA(nfas);
        NFA nfaMerged = nfaLargest;

        for (NFA nfa : nfas) {

            /** Skip the first one. */
            if (nfas.indexOf(nfa) == nfas.indexOf(nfaLargest))
                continue;

            /** Rename the second one states as in continue of the first one. */
            NFA.renameStates(nfaMerged, nfa);

            /** Add new alphabet letters to the second table. */
            NFA.expandAlphabets(nfaMerged, nfa);

            /** Add new states to first NFA. */
            NFA.buildConcatNFA(nfaMerged, nfa);
            System.out.println("Conact result:" + nfaMerged.NFATable.toString());
        }

        return nfaMerged;
    }

    /**
     * Calculates the star of a given NFA.
     *
     * @param nfaA the stared NFA
     */
    public static void starNFA(NFA nfaA) {

        ArrayList<ArrayList<Integer>> columnEnd;
        columnEnd = nfaA.NFATable.get(nfaA.NFATable.size() - 1);

        ArrayList<Integer> cell = columnEnd.get(columnEnd.size() - 1);
        if (!cell.contains(1))
            cell.add(1);
    }

    /**
     * Adds the epsilon to an existing NFA table.
     * Epsilon is always added to the last line of the table.
     *
     * @param nfaA the input NFA which lacks epsilon.
     */
    public static void addEpsilon(NFA nfaA) {

        if (!(NFA.hasLetter(nfaA, NFA.epsilon))) {

            NFA.addLetter(nfaA, NFA.epsilon);
        }
    }

    /**
     * Checks for an specific letter inside an NFA table.
     *
     * @param nfa the input NFA
     * @param letter the letter which is searched for
     * @return true, if successful
     */
    private static boolean hasLetter(NFA nfa, int letter) {

        ArrayList<ArrayList<Integer>> column;
        column = nfa.NFATable.get(0);

        for (ArrayList<Integer> cell : column) {

            if (cell.contains(letter))
                return true;
        }
        return false;
    }

    /**
     * Rename states of two input NFAs.
     * This solves the problem of same states at the time of concatenation.
     *
     * @param nfaA the input NFA A
     * @param nfaB the input NFA B
     */
    private static void renameStates(NFA nfaA, NFA nfaB) {

        int maxStateNumber = 0;
        for (ArrayList<ArrayList<Integer>> column : nfaA.NFATable) {

            if (column.get(0).get(0) >= maxStateNumber)
                maxStateNumber = column.get(0).get(0);
        }

        for (ArrayList<ArrayList<Integer>> column : nfaB.NFATable) {

            if (nfaB.NFATable.indexOf(column) == 0)
                continue;

            Integer oldState = column.get(0).get(0);
            Integer newState = maxStateNumber + nfaB.NFATable.indexOf(column);

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : nfaB.NFATable)
                for (ArrayList<Integer> cell : c)
                    if (cell.contains(oldState))
                        cell.set(cell.indexOf(oldState), newState);
        }
    }

    /**
     * Expands nfaA alphabet to add letters in nfaB.
     *
     * @param nfaA the input NFA A
     * @param nfaB the input NFA B
     */
    private static void expandAlphabets(NFA nfaA, NFA nfaB) {

        ArrayList<Integer> alphabetA = NFA.extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = NFA.extractAlphabet(nfaB);

        for (Integer letter : alphabetB) {

            if (alphabetA.contains(letter))
                continue;

            NFA.addLetter(nfaA, letter);
        }
    }

    /**
     * Extract alphabet of a given NFA.
     *
     * @param nfa the input NFA
     * @return the array list of all letters (alphabet)
     */
    private static ArrayList<Integer> extractAlphabet(NFA nfa) {

        ArrayList<Integer> alphabet = new ArrayList<Integer>();
        for (ArrayList<Integer> cell : nfa.NFATable.get(0)) {

            if (cell.get(0) == 0)
                continue;
            alphabet.add(cell.get(0));
        }
        return alphabet;
    }

    /**
     * Adds a new letter to an existing NFA table.
     *
     * @param nfaA the input NFA of A.
     * @param letter the new letter to add.
     */
    private static void addLetter(NFA nfaA, int letter) {

        ArrayList<Integer> cell;
        for (ArrayList<ArrayList<Integer>> c : nfaA.NFATable) {

            if (nfaA.NFATable.indexOf(c) == 0) {

                cell = new ArrayList<Integer>();
                cell.add(letter);

                if (letter == NFA.epsilon)
                    c.add(cell);
                else
                    c.add(c.size() - 1, cell);
            }
            else {

                cell = new ArrayList<Integer>();

                if (letter == NFA.epsilon)
                    c.add(cell);
                else
                    c.add(c.size() - 1, cell);
            }
        }
    }

    /**
     * Builds the concatenation of two nfaA and nfaB.
     * Assumes that #states in nfaA is not less than in nfaB.
     *
     * @param nfaA the input NFA A
     * @param nfaB the input NFA B
     */
    private static void buildConcatNFA(NFA nfaA, NFA nfaB) {

        for (ArrayList<ArrayList<Integer>> columnB : nfaB.NFATable) {

            /** Skip alphabet column. */
            if (columnB.get(0).get(0) == 0)
                continue;

            ArrayList<ArrayList<Integer>> newColumn =
                    new ArrayList<ArrayList<Integer>>();

            /** Case that it is the first state it should
             *  be entered an edge e via previous end. */
            if (nfaB.NFATable.indexOf(columnB) == 1) {

                /** Last column must enter the state column via epsilon. */
                ArrayList<ArrayList<Integer>> lastColumn =
                        nfaA.NFATable.get(nfaA.NFATable.size() - 1);

                ArrayList<Integer> epsilonCell =
                        lastColumn.get(lastColumn.size() - 1);
                epsilonCell.add(columnB.get(0).get(0));
            }

            /** Add state as a new column. */
            ArrayList<Integer> alphabetA = extractAlphabet(nfaA);
            ArrayList<Integer> alphabetB = extractAlphabet(nfaB);

            ArrayList<Integer> changeCell;

            /** Build state 0. */
            changeCell = new ArrayList<Integer>();
            changeCell.add(columnB.get(0).get(0));
            newColumn.add(changeCell);

            for (Integer letter: alphabetA) {

                if (alphabetB.contains(letter)) {

                    changeCell = NFA.retrieveCell(nfaB,
                            columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            nfaA.NFATable.add(newColumn);
        }
    }

    /**
     * Retrieve an specific cell in NFA table
     * corresponding to letter and state.
     *
     * @param nfa the input NFA in which the cell lies.
     * @param state the input state
     * @param letter the alphabet letter
     * @return the cell found in the NFA table
     */
    private static ArrayList<Integer> retrieveCell(NFA nfa, int state, int letter) {

        int index = 0;
        for (ArrayList<Integer> cell : nfa.NFATable.get(0)) {

            if (cell.get(0) == letter)
                index = nfa.NFATable.get(0).indexOf(cell);
        }

        for (ArrayList<ArrayList<Integer>> column : nfa.NFATable) {
            if (column.get(0).get(0) == state)
                return column.get(index);
        }

        return null;
    }

    /**
     * Gets the largest NFA out of an ArrayList.
     *
     * @param nfas the array of NFAs in each stage of operation.
     * @return the largest NFA.
     */
    private static NFA getLargestNFA(ArrayList<NFA> nfas) {

        NFA largest = null;
        Integer maxSeen = 0;

        for (NFA element : nfas) {

            if (element.NFATable.size() > maxSeen) {

                maxSeen = element.NFATable.size();
                largest = element;
            }
        }

        return largest;
    }
}
