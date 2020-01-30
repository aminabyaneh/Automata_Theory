package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utils.Chars;

/**
 * The Class StateTransitionMatrix.
 * TODO: localize some of the methods.
 * TODO: restrict passage of NFAs
 */
public class StateTransitionMatrix extends
ArrayList<ArrayList<ArrayList<Integer>>> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new STM.
     *
     */
    public StateTransitionMatrix() {

        super();
    }

    /**
     * Prints the ST matrix as required.
     */
    public void print(HashMap<String, Integer> states,
            ArrayList<Integer> finalStates) {

        HashMap<Integer, String> statesPrint = this.reverseHash(states);

        /** Print start state. */
        System.out.println(statesPrint.get(this.get(1).get(0).get(0)));

        /** Print final states. */
        for (Integer state : finalStates) {

            System.out.print(statesPrint.get(state));
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print alphabet. */
        for (ArrayList<Integer> cell : this.get(0)) {

            if (cell.get(0) == 0 || cell.get(0) == NFA.epsilon)
                continue;

            System.out.print((char) cell.get(0).intValue());
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print all states. */
        for (ArrayList<ArrayList<Integer>> col : this) {

            if (col.get(0).get(0) == 0)
                continue;

            System.out.print(statesPrint.get(col.get(0).get(0)));
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print state transmission matrix. */
        for (ArrayList<ArrayList<Integer>> col : this) {

            if (col.get(0).get(0) == 0)
                continue;

            boolean isFirst = true;
            for (ArrayList<Integer> cell : col) {

                if (isFirst == true) {
                    isFirst = false;
                    continue;
                }

                if (cell.isEmpty()) {

                    System.out.print(Chars.none);
                }
                else {

                    int indexx = 0;
                    for (Integer state : cell) {

                        System.out.print(statesPrint.get(state));

                        if (indexx < cell.size() - 1)
                            System.out.print(Chars.comma);

                        indexx++;
                    }
                }
                System.out.print(Chars.space);
            }
            System.out.println(Chars.empty);
        }
    }

    /**
     * Reverse hash map.
     *
     * @param hash the hash map needed to be reversed.
     * @return the reversed hash map
     */
    private HashMap<Integer, String> reverseHash(
            HashMap<String, Integer> hash) {

        HashMap<Integer, String> reversedHash =
                new HashMap<Integer, String>();

        for (Map.Entry<String, Integer> entry : hash.entrySet()) {

            reversedHash.put(entry.getValue(), entry.getKey());
        }

        return reversedHash;
    }

    /**
     * Prints the ST matrix as required.
     */
    public void print() {

        /** Print start state. */
        System.out.println(this.nameState(this.get(1).get(0).get(0)));

        /** Print final state. */
        System.out.println(this.nameState(this.get(this.size() - 1)
                .get(0).get(0)));

        /** Print alphabet. */
        for (ArrayList<Integer> cell : this.get(0)) {

            if (cell.get(0) == 0 || cell.get(0) == NFA.epsilon)
                continue;

            System.out.print((char) cell.get(0).intValue());
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print all states. */
        for (ArrayList<ArrayList<Integer>> col : this) {

            if (col.get(0).get(0) == 0)
                continue;

            System.out.print(this.nameState(col.get(0).get(0)));
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print state transmission matrix. */
        for (ArrayList<ArrayList<Integer>> col : this) {

            if (col.get(0).get(0) == 0)
                continue;

            boolean isFirst = true;
            for (ArrayList<Integer> cell : col) {

                if (isFirst == true) {
                    isFirst = false;
                    continue;
                }

                if (cell.isEmpty()) {

                    System.out.print(Chars.none);
                }
                else {

                    int indexx = 0;
                    for (Integer state : cell) {

                        System.out.print(this.nameState(state));

                        if (indexx < cell.size() - 1)
                            System.out.print(Chars.comma);

                        indexx++;
                    }
                }
                System.out.print(Chars.space);
            }
            System.out.println(Chars.empty);
        }
    }

    /**
     * Name state based on q + number.
     *
     * @param integer the integer
     * @return the string
     */
    private String nameState(Integer integer) {

        return ("q" + integer.toString());
    }

    /**
     * Adds the epsilon to an existing NFA table.
     * Epsilon is always added to the last line of the table.
     *
     * @param nfaA the input NFA which lacks epsilon.
     */
    public static void addEpsilon(NFA nfaA) {

        if (!(StateTransitionMatrix.hasLetter(nfaA, NFA.epsilon))) {

            StateTransitionMatrix.addLetter(nfaA, NFA.epsilon);
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
        column = nfa.stmat.get(0);

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
    static void renameStates(NFA nfaA, NFA nfaB) {

        int maxStateNumber = 0;
        for (ArrayList<ArrayList<Integer>> column : nfaA.stmat) {

            if (column.get(0).get(0) >= maxStateNumber)
                maxStateNumber = column.get(0).get(0);
        }

        for (ArrayList<ArrayList<Integer>> column : nfaB.stmat) {

            if (nfaB.stmat.indexOf(column) == 0)
                continue;

            Integer oldState = column.get(0).get(0);
            Integer newState = maxStateNumber + oldState;

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : nfaB.stmat)
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
    static void expandAlphabets(NFA nfaA, NFA nfaB) {

        ArrayList<Integer> alphabetA = StateTransitionMatrix.extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = StateTransitionMatrix.extractAlphabet(nfaB);

        for (Integer letter : alphabetB) {

            if (alphabetA.contains(letter))
                continue;

            StateTransitionMatrix.addLetter(nfaA, letter);
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
        for (ArrayList<Integer> cell : nfa.stmat.get(0)) {

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
        for (ArrayList<ArrayList<Integer>> c : nfaA.stmat) {

            if (nfaA.stmat.indexOf(c) == 0) {

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
    static void buildConcatNFA(NFA nfaA, NFA nfaB) {

        for (ArrayList<ArrayList<Integer>> columnB : nfaB.stmat) {

            /** Skip alphabet column. */
            if (columnB.get(0).get(0) == 0)
                continue;

            ArrayList<ArrayList<Integer>> newColumn =
                    new ArrayList<ArrayList<Integer>>();

            /** Case that it is the first state it should
             *  be entered an edge e via previous end. */
            if (nfaB.stmat.indexOf(columnB) == 1) {

                /** Last column must enter the state column via epsilon. */
                ArrayList<ArrayList<Integer>> lastColumn =
                        nfaA.stmat.get(nfaA.stmat.size() - 1);

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

                    changeCell = StateTransitionMatrix.
                            retrieveCell(nfaB.stmat,
                                    columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            nfaA.stmat.add(newColumn);
        }
    }

    /**
     * Builds the union NFA.
     *
     * @param nfaA the input NFA A
     * @param nfaB the input NFA B
     */
    static void buildUnionNFA(NFA nfaA, NFA nfaB) {

        ArrayList<Integer> startStates = new ArrayList<Integer>();
        ArrayList<Integer> endStates = new ArrayList<Integer>();

        startStates.add(nfaA.stmat.get(1).get(0).get(0) + 1);
        startStates.add(nfaB.stmat.get(1).get(0).get(0) + 1);

        endStates.add(nfaA.stmat.get(nfaA.stmat.size() - 1)
                .get(0).get(0) + 1);
        endStates.add(nfaB.stmat.get(nfaB.stmat.size() - 1)
                .get(0).get(0) + 1);

        ArrayList<Integer> changeCell = null;

        /** Add state as a new column. */
        ArrayList<Integer> alphabetA = extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = extractAlphabet(nfaB);

        for (ArrayList<ArrayList<Integer>> columnB : nfaB.stmat) {

            /** Skip alphabet column. */
            if (columnB.get(0).get(0) == 0)
                continue;

            ArrayList<ArrayList<Integer>> newColumn =
                    new ArrayList<ArrayList<Integer>>();

            /** Build state 0. */
            changeCell = new ArrayList<Integer>();
            changeCell.add(columnB.get(0).get(0));
            newColumn.add(changeCell);

            for (Integer letter: alphabetA) {

                if (alphabetB.contains(letter)) {

                    changeCell = StateTransitionMatrix.
                            retrieveCell(nfaB.stmat,
                                    columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            nfaA.stmat.add(newColumn);
        }

        /** Increment state numbers to add the new states. */
        StateTransitionMatrix.incrementStateNumbers(nfaA);

        /** Add two new states for the start and for the end. */
        ArrayList<ArrayList<Integer>> startColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> firstColumn =
                nfaA.stmat.get(1);

        Integer index = -1;
        for (ArrayList<Integer> c : firstColumn) {

            index++;
            changeCell = new ArrayList<Integer>();

            if (index == 0)
                changeCell.add(c.get(0) - 1);
            else if (index == firstColumn.size() - 1)
                changeCell.addAll(startStates);

            startColumn.add(changeCell);
        }

        ArrayList<ArrayList<Integer>> endColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> lastColumn =
                nfaA.stmat.get(nfaA.stmat.size() - 1);

        index = -1;
        for (ArrayList<Integer> c : lastColumn) {

            index++;
            changeCell = new ArrayList<Integer>();
            if (index == 0)
                changeCell.add(c.get(0) + 1);

            endColumn.add(changeCell);
        }

        for (ArrayList<ArrayList<Integer>> column : nfaA.stmat) {

            if (endStates.contains(column.get(0).get(0)))
                column.get(column.size() - 1).add(endColumn.get(0).get(0));
        }

        /** Add start and end column to main NFA. */
        nfaA.stmat.add(1, startColumn);
        nfaA.stmat.add(nfaA.stmat.size(), endColumn);
    }

    /**
     * Builds the star NFA.
     *
     * @param nfaA the NFA A
     */
    static void buildStarNFA(NFA nfaA) {

        ArrayList<Integer> changeCell = null;

        /** Add the feed back epsilon arrow. */
        ArrayList<ArrayList<Integer>> columnEnd;
        columnEnd = nfaA.stmat.get(nfaA.stmat.size() - 1);

        ArrayList<Integer> cell = columnEnd.get(columnEnd.size() - 1);
        if (!cell.contains(1))
            cell.add(1);

        /** Increment state numbers to add the new states. */
        StateTransitionMatrix.incrementStateNumbers(nfaA);

        /** Add two new states for the start and for the end. */
        ArrayList<ArrayList<Integer>> startColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> firstColumn =
                nfaA.stmat.get(1);

        ArrayList<ArrayList<Integer>> endColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> lastColumn =
                nfaA.stmat.get(nfaA.stmat.size() - 1);

        Integer index = -1;
        for (ArrayList<Integer> c : firstColumn) {

            index++;
            changeCell = new ArrayList<Integer>();

            if (index == 0) {

                changeCell.add(c.get(0) - 1);
            }
            else if (index == firstColumn.size() - 1) {

                changeCell.add(firstColumn.get(0).get(0));
                changeCell.add(lastColumn.get(0).get(0) + 1);
            }

            startColumn.add(changeCell);
        }

        index = -1;
        for (ArrayList<Integer> c : lastColumn) {

            index++;
            changeCell = new ArrayList<Integer>();
            if (index == 0)
                changeCell.add(c.get(0) + 1);

            endColumn.add(changeCell);
        }

        /** Add epsilon arrow from last column to end column. */
        lastColumn.get(lastColumn.size() - 1).add(endColumn.get(0).get(0));

        /** Add start and end column to main NFA. */
        nfaA.stmat.add(1, startColumn);
        nfaA.stmat.add(nfaA.stmat.size(), endColumn);
    }

    /**
     * Increment state numbers.
     *
     * @param nfa the input NFA
     */
    private static void incrementStateNumbers(NFA nfa) {

        Collections.reverse(nfa.stmat);
        for (ArrayList<ArrayList<Integer>> column : nfa.stmat) {

            Integer oldState = column.get(0).get(0);
            Integer newState = 1 + oldState;

            if (oldState == 0)
                continue;

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : nfa.stmat)
                for (ArrayList<Integer> cell : c)
                    if (cell.contains(oldState))
                        cell.set(cell.indexOf(oldState), newState);
        }
        Collections.reverse(nfa.stmat);
    }

    /**
     * Retrieve an specific cell in NFA table
     * corresponding to letter and state.
     *
     * @param table of state transition matrix.
     * @param state the input state
     * @param letter the alphabet letter
     * @return the cell found in the NFA table
     */
    static ArrayList<Integer> retrieveCell(StateTransitionMatrix table,
            int state, int letter) {

        int index = 0;
        for (ArrayList<Integer> cell : table.get(0)) {

            if (cell.get(0) == letter)
                index = table.get(0).indexOf(cell);
        }

        for (ArrayList<ArrayList<Integer>> column : table) {
            if (column.get(0).get(0) == state)
                return column.get(index);
        }

        return null;
    }

    /**
     * Retrieve an specific column in NFA table
     * corresponding to a state.
     *
     * @param table of state transition matrix.
     * @param state the input state
     * @return the column found in the NFA table
     */
    static ArrayList<ArrayList<Integer>> retrieveColumn(
            StateTransitionMatrix table, int state) {

        for (ArrayList<ArrayList<Integer>> column : table) {
            if (column.get(0).get(0) == state)
                return column;
        }

        return null;
    }

    /**
     * Gets the epsilon closure of a state.
     * This function recursively extracts all epsilon closures.
     *
     * @param state the state
     * @return the epsilon closure
     */
    public ArrayList<Integer> getEpsilonClosure(Integer state) {

        ArrayList<Integer> eClosure = new ArrayList<Integer>();


        ArrayList<ArrayList<Integer>> retrievedColumn =
                StateTransitionMatrix.retrieveColumn(this, state);

        if (retrievedColumn.get(retrievedColumn.size() - 1).isEmpty()) {

            /** Add the state itself. */
            eClosure.add(state);
            return eClosure;
        }


        /** Add where the state can go using epsilon. */
        for (Integer s : retrievedColumn.get(retrievedColumn.size() - 1)) {

            eClosure.addAll(this.getEpsilonClosure(s));
        }

        eClosure.add(state);

        /** Sort just  for more ordered states. */
        Collections.sort(eClosure);

        return eClosure;
    }
}
