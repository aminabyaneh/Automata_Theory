package core;

import java.util.ArrayList;
import java.util.Collections;

public class Table {

    ArrayList<ArrayList<ArrayList<Integer>>> table;

    public Table() {

        table = new ArrayList<ArrayList<ArrayList<Integer>>>();
    }

    /**
     * Adds the epsilon to an existing NFA table.
     * Epsilon is always added to the last line of the table.
     *
     * @param nfaA the input NFA which lacks epsilon.
     */
    public static void addEpsilon(NFA nfaA) {

        if (!(Table.hasLetter(nfaA, NFA.epsilon))) {

            Table.addLetter(nfaA, NFA.epsilon);
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
        column = nfa.nfaTable.table.get(0);

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
        for (ArrayList<ArrayList<Integer>> column : nfaA.nfaTable.table) {

            if (column.get(0).get(0) >= maxStateNumber)
                maxStateNumber = column.get(0).get(0);
        }

        for (ArrayList<ArrayList<Integer>> column : nfaB.nfaTable.table) {

            if (nfaB.nfaTable.table.indexOf(column) == 0)
                continue;

            Integer oldState = column.get(0).get(0);
            Integer newState = maxStateNumber + oldState;

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : nfaB.nfaTable.table)
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

        ArrayList<Integer> alphabetA = Table.extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = Table.extractAlphabet(nfaB);

        for (Integer letter : alphabetB) {

            if (alphabetA.contains(letter))
                continue;

            Table.addLetter(nfaA, letter);
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
        for (ArrayList<Integer> cell : nfa.nfaTable.table.get(0)) {

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
        for (ArrayList<ArrayList<Integer>> c : nfaA.nfaTable.table) {

            if (nfaA.nfaTable.table.indexOf(c) == 0) {

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

        for (ArrayList<ArrayList<Integer>> columnB : nfaB.nfaTable.table) {

            /** Skip alphabet column. */
            if (columnB.get(0).get(0) == 0)
                continue;

            ArrayList<ArrayList<Integer>> newColumn =
                    new ArrayList<ArrayList<Integer>>();

            /** Case that it is the first state it should
             *  be entered an edge e via previous end. */
            if (nfaB.nfaTable.table.indexOf(columnB) == 1) {

                /** Last column must enter the state column via epsilon. */
                ArrayList<ArrayList<Integer>> lastColumn =
                        nfaA.nfaTable.table.get(nfaA.nfaTable.table.size() - 1);

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

                    changeCell = Table.retrieveCell(nfaB,
                            columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            nfaA.nfaTable.table.add(newColumn);
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

        startStates.add(nfaA.nfaTable.table.get(1).get(0).get(0) + 1);
        startStates.add(nfaB.nfaTable.table.get(1).get(0).get(0) + 1);

        endStates.add(nfaA.nfaTable.table.get(nfaA.nfaTable.table.size() - 1)
                .get(0).get(0) + 1);
        endStates.add(nfaB.nfaTable.table.get(nfaB.nfaTable.table.size() - 1)
                .get(0).get(0) + 1);

        ArrayList<Integer> changeCell = null;

        /** Add state as a new column. */
        ArrayList<Integer> alphabetA = extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = extractAlphabet(nfaB);

        for (ArrayList<ArrayList<Integer>> columnB : nfaB.nfaTable.table) {

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

                    changeCell = Table.retrieveCell(nfaB,
                            columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            nfaA.nfaTable.table.add(newColumn);
        }

        /** Increment state numbers. */
        Table.incrementStateNumbers(nfaA);

        /** Add two new states for the start and for the end. */
        ArrayList<ArrayList<Integer>> startColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> firstColumn =
                nfaA.nfaTable.table.get(1);

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
                nfaA.nfaTable.table.get(nfaA.nfaTable.table.size() - 1);

        index = -1;
        for (ArrayList<Integer> c : lastColumn) {

            index++;
            changeCell = new ArrayList<Integer>();
            if (index == 0)
                changeCell.add(c.get(0) + 1);

            endColumn.add(changeCell);
        }

        for (ArrayList<ArrayList<Integer>> column : nfaA.nfaTable.table) {

            if (endStates.contains(column.get(0).get(0)))
                column.get(column.size() - 1).add(endColumn.get(0).get(0));
        }

        /** Add start and end column to main NFA. */
        nfaA.nfaTable.table.add(1, startColumn);
        nfaA.nfaTable.table.add(nfaA.nfaTable.table.size(), endColumn);
    }

    /**
     * Increment state numbers.
     *
     * @param nfa the input NFA
     */
    private static void incrementStateNumbers(NFA nfa) {

        Collections.reverse(nfa.nfaTable.table);
        for (ArrayList<ArrayList<Integer>> column : nfa.nfaTable.table) {

            Integer oldState = column.get(0).get(0);
            Integer newState = 1 + oldState;

            if (oldState == 0)
                continue;

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : nfa.nfaTable.table)
                for (ArrayList<Integer> cell : c)
                    if (cell.contains(oldState))
                        cell.set(cell.indexOf(oldState), newState);
        }
        Collections.reverse(nfa.nfaTable.table);
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
        for (ArrayList<Integer> cell : nfa.nfaTable.table.get(0)) {

            if (cell.get(0) == letter)
                index = nfa.nfaTable.table.get(0).indexOf(cell);
        }

        for (ArrayList<ArrayList<Integer>> column : nfa.nfaTable.table) {
            if (column.get(0).get(0) == state)
                return column.get(index);
        }

        return null;
    }

}
