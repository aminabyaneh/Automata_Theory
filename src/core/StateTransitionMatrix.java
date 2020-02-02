package core;

import java.util.ArrayList;
import java.util.Collections;

import utils.Chars;

/**
 * The Class for a state transition matrix.
 * This class handles are the low level tasks regarding
 * to state transition table.
 *
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
     *
     */
    public void print() {

        /** Print start state. */
        System.out.println(this.nameState(this.get(1).get(0).get(0)));

        /** Print final state. */
        System.out.println(this.nameState(this.get(this.size() - 1)
                .get(0).get(0)));

        /** Print alphabet. */
        for (ArrayList<Integer> cell : this.get(0)) {

            if (cell.get(0) == 0 || cell.get(0) == FSM.epsilon)
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
     * Prints the desired output.
     *
     * @param startState the start state
     * @param finalStates the final states
     */
    public void print(Integer startState, ArrayList<Integer> finalStates) {

        /** Print start state. */
        System.out.println(this.nameState(startState));

        /** Print final states. */
        for (Integer state : finalStates) {

            System.out.print(this.nameState(state));
            System.out.print(Chars.space);
        }
        System.out.println(Chars.empty);

        /** Print alphabet. */
        for (ArrayList<Integer> cell : this.get(0)) {

            if (cell.get(0) == 0 || cell.get(0) == FSM.epsilon)
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
     * Adds the epsilon to an existing StateTransitionMatrix table.
     * Epsilon is always added to the last line of the table.
     *
     * @param stmatA the input StateTransitionMatrix which lacks epsilon.
     */
    public static void addEpsilon(StateTransitionMatrix stmatA) {

        if (!(StateTransitionMatrix.hasLetter(stmatA, FSM.epsilon))) {

            StateTransitionMatrix.addLetter(stmatA, FSM.epsilon);
        }
    }

    /**
     * Checks for an specific letter inside an StateTransitionMatrix table.
     *
     * @param stmat the input StateTransitionMatrix
     * @param letter the letter which is searched for
     * @return true, if successful
     */
    private static boolean hasLetter(StateTransitionMatrix stmat, int letter) {

        ArrayList<ArrayList<Integer>> column;
        column = stmat.get(0);

        for (ArrayList<Integer> cell : column) {

            if (cell.contains(letter))
                return true;
        }
        return false;
    }

    /**
     * Rename states of two input StateTransitionMatrix.
     * This solves the problem of same states at the time of concatenation.
     *
     * @param stmatA the input StateTransitionMatrix A
     * @param stmatB the input StateTransitionMatrix B
     */
    static void renameStates(StateTransitionMatrix stmatA, StateTransitionMatrix stmatB) {

        int maxStateNumber = 0;
        for (ArrayList<ArrayList<Integer>> column : stmatA) {

            if (column.get(0).get(0) >= maxStateNumber)
                maxStateNumber = column.get(0).get(0);
        }

        while (stmatB.get(1).get(0).get(0) < maxStateNumber + 1) {

            StateTransitionMatrix.incrementStateNumbers(stmatB);
        }
    }

    /**
     * Expands stmatA alphabet to add letters in stmatB.
     *
     * @param stmatA the input StateTransitionMatrix A
     * @param stmatB the input StateTransitionMatrix B
     */
    static void expandAlphabets(StateTransitionMatrix stmatA, StateTransitionMatrix stmatB) {

        ArrayList<Integer> alphabetA = StateTransitionMatrix.extractAlphabet(stmatA);
        ArrayList<Integer> alphabetB = StateTransitionMatrix.extractAlphabet(stmatB);

        for (Integer letter : alphabetB) {

            if (alphabetA.contains(letter))
                continue;

            StateTransitionMatrix.addLetter(stmatA, letter);
        }
    }

    /**
     * Extract alphabet of a given StateTransitionMatrix.
     *
     * @param stmat the input StateTransitionMatrix
     * @return the array list of all letters (alphabet)
     */
    private static ArrayList<Integer> extractAlphabet(StateTransitionMatrix stmat) {

        ArrayList<Integer> alphabet = new ArrayList<Integer>();
        for (ArrayList<Integer> cell : stmat.get(0)) {

            if (cell.get(0) == 0)
                continue;
            alphabet.add(cell.get(0));
        }
        return alphabet;
    }

    /**
     * Adds a new letter to an existing StateTransitionMatrix table.
     *
     * @param stmatA the input StateTransitionMatrix of A.
     * @param letter the new letter to add.
     */
    private static void addLetter(StateTransitionMatrix stmatA, int letter) {

        ArrayList<Integer> cell;
        for (ArrayList<ArrayList<Integer>> c : stmatA) {

            if (stmatA.indexOf(c) == 0) {

                cell = new ArrayList<Integer>();
                cell.add(letter);

                if (letter == FSM.epsilon)
                    c.add(cell);
                else
                    c.add(c.size() - 1, cell);
            }
            else {

                cell = new ArrayList<Integer>();

                if (letter == FSM.epsilon)
                    c.add(cell);
                else
                    c.add(c.size() - 1, cell);
            }
        }
    }

    /**
     * Builds the concatenation of two stmatA and stmatB.
     * Assumes that #states in stmatA is not less than in stmatB.
     *
     * @param stmatA the input StateTransitionMatrix A
     * @param stmatB the input StateTransitionMatrix B
     */
    static void buildConcat(StateTransitionMatrix stmatA, StateTransitionMatrix stmatB) {

        for (ArrayList<ArrayList<Integer>> columnB : stmatB) {

            /** Skip alphabet column. */
            if (columnB.get(0).get(0) == 0)
                continue;

            ArrayList<ArrayList<Integer>> newColumn =
                    new ArrayList<ArrayList<Integer>>();

            /** Case that it is the first state it should
             *  be entered an edge e via previous end. */
            if (stmatB.indexOf(columnB) == 1) {

                /** Last column must enter the state column via epsilon. */
                ArrayList<ArrayList<Integer>> lastColumn =
                        stmatA.get(stmatA.size() - 1);

                ArrayList<Integer> epsilonCell =
                        lastColumn.get(lastColumn.size() - 1);
                epsilonCell.add(columnB.get(0).get(0));
            }

            /** Add state as a new column. */
            ArrayList<Integer> alphabetA = extractAlphabet(stmatA);
            ArrayList<Integer> alphabetB = extractAlphabet(stmatB);

            ArrayList<Integer> changeCell;

            /** Build state 0. */
            changeCell = new ArrayList<Integer>();
            changeCell.add(columnB.get(0).get(0));
            newColumn.add(changeCell);

            for (Integer letter: alphabetA) {

                if (alphabetB.contains(letter)) {

                    changeCell = StateTransitionMatrix.
                            retrieveCell(stmatB,
                                    columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            stmatA.add(newColumn);
        }
    }

    /**
     * Builds the union StateTransitionMatrix.
     *
     * @param stmatA the input StateTransitionMatrix A
     * @param stmatB the input StateTransitionMatrix B
     */
    static void buildUnion(StateTransitionMatrix stmatA, StateTransitionMatrix stmatB) {

        ArrayList<Integer> startStates = new ArrayList<Integer>();
        ArrayList<Integer> endStates = new ArrayList<Integer>();

        startStates.add(stmatA.get(1).get(0).get(0) + 1);
        startStates.add(stmatB.get(1).get(0).get(0) + 1);

        endStates.add(stmatA.get(stmatA.size() - 1)
                .get(0).get(0) + 1);
        endStates.add(stmatB.get(stmatB.size() - 1)
                .get(0).get(0) + 1);

        ArrayList<Integer> changeCell = null;

        /** Add state as a new column. */
        ArrayList<Integer> alphabetA = extractAlphabet(stmatA);
        ArrayList<Integer> alphabetB = extractAlphabet(stmatB);

        for (ArrayList<ArrayList<Integer>> columnB : stmatB) {

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
                            retrieveCell(stmatB,
                                    columnB.get(0).get(0), letter);

                    newColumn.add(changeCell);
                }
                else {

                    changeCell = new ArrayList<Integer>();
                    newColumn.add(changeCell);
                }
            }

            stmatA.add(newColumn);
        }

        /** Increment state numbers to add the new states. */
        StateTransitionMatrix.incrementStateNumbers(stmatA);

        /** Add two new states for the start and for the end. */
        ArrayList<ArrayList<Integer>> startColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> firstColumn =
                stmatA.get(1);

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
                stmatA.get(stmatA.size() - 1);

        index = -1;
        for (ArrayList<Integer> c : lastColumn) {

            index++;
            changeCell = new ArrayList<Integer>();
            if (index == 0)
                changeCell.add(c.get(0) + 1);

            endColumn.add(changeCell);
        }

        for (ArrayList<ArrayList<Integer>> column : stmatA) {

            if (endStates.contains(column.get(0).get(0)))
                column.get(column.size() - 1).add(endColumn.get(0).get(0));
        }

        /** Add start and end column to main StateTransitionMatrix. */
        stmatA.add(1, startColumn);
        stmatA.add(stmatA.size(), endColumn);
    }

    /**
     * Builds the star StateTransitionMatrix.
     *
     * @param stmatA the StateTransitionMatrix A
     */
    static void buildStar(StateTransitionMatrix stmatA) {

        ArrayList<Integer> changeCell = null;

        /** Add the feed back epsilon arrow. */
        ArrayList<ArrayList<Integer>> columnEnd;
        columnEnd = stmatA.get(stmatA.size() - 1);

        ArrayList<Integer> cell = columnEnd.get(columnEnd.size() - 1);
        if (!cell.contains(1))
            cell.add(1);

        /** Increment state numbers to add the new states. */
        StateTransitionMatrix.incrementStateNumbers(stmatA);

        /** Add two new states for the start and for the end. */
        ArrayList<ArrayList<Integer>> startColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> firstColumn =
                stmatA.get(1);

        ArrayList<ArrayList<Integer>> endColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> lastColumn =
                stmatA.get(stmatA.size() - 1);

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

        /** Add start and end column to main StateTransitionMatrix. */
        stmatA.add(1, startColumn);
        stmatA.add(stmatA.size(), endColumn);
    }

    /**
     * Increment state numbers.
     *
     * @param stmat the input StateTransitionMatrix
     */
    private static void incrementStateNumbers(StateTransitionMatrix stmat) {

        Collections.reverse(stmat);
        for (ArrayList<ArrayList<Integer>> column : stmat) {

            Integer oldState = column.get(0).get(0);
            Integer newState = 1 + oldState;

            if (oldState == 0)
                continue;

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : stmat)
                for (ArrayList<Integer> cell : c)
                    if (cell.contains(oldState))
                        cell.set(cell.indexOf(oldState), newState);
        }
        Collections.reverse(stmat);
    }

    /**
     * Retrieve an specific cell in StateTransitionMatrix table
     * corresponding to letter and state.
     *
     * @param table of state transition matrix.
     * @param state the input state
     * @param letter the alphabet letter
     * @return the cell found in the StateTransitionMatrix table
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
     * Retrieve an specific column in StateTransitionMatrix table
     * corresponding to a state.
     *
     * @param table of state transition matrix.
     * @param state the input state
     * @return the column found in the StateTransitionMatrix table
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
    public ArrayList<Integer> getEpsilonClosure(Integer state,
            ArrayList<Integer> visitedStates) {

        ArrayList<Integer> eClosure = new ArrayList<Integer>();

        ArrayList<ArrayList<Integer>> retrievedColumn =
                StateTransitionMatrix.retrieveColumn(this, state);

        if (retrievedColumn.get(retrievedColumn.size() - 1).isEmpty() ||
                visitedStates.contains(state)) {

            /** Add the state itself. */
            eClosure.add(state);
            return eClosure;
        }

        /** Prevent a loop. */
        visitedStates.add(state);

        /** Add where the state can go using epsilon. */
        for (Integer s : retrievedColumn.get(retrievedColumn.size() - 1))
            eClosure.addAll(this.getEpsilonClosure(s, visitedStates));

        eClosure.add(state);

        /** Sort just  for more ordered states. */
        Collections.sort(eClosure);

        return eClosure;
    }
    /**
     * Calculate the union StateTransitionMatrix.
     *
     * @param stmats the array of StateTransitionMatrix
     * @return the final union StateTransitionMatrix
     */
    public static StateTransitionMatrix
    union(ArrayList<StateTransitionMatrix> stmats) {

        StateTransitionMatrix stmatMerged = stmats.get(0);

        for (StateTransitionMatrix stmat : stmats) {

            /** Skip the first one. */
            if (stmats.indexOf(stmat) == 0)
                continue;

            /** Rename the second one states as in continue of the first one. */
            StateTransitionMatrix.renameStates(stmatMerged, stmat);

            /** Add new alphabet letters to the second STM. */
            StateTransitionMatrix.expandAlphabets(stmatMerged, stmat);

            /** Calculates the union of two StateTransitionMatrix. */
            StateTransitionMatrix.buildUnion(stmatMerged, stmat);
        }

        return stmatMerged;
    }

    /**
     * Calculates the concatenation of an array of StateTransitionMatrix.
     *
     * @param stmats the input ArrayList of StateTransitionMatrix
     * @return the final StateTransitionMatrix
     */
    public static StateTransitionMatrix
    concat(ArrayList<StateTransitionMatrix> stmats) {

        StateTransitionMatrix stmatMerged = stmats.get(0);

        for (StateTransitionMatrix stmat : stmats) {

            /** Skip the first one. */
            if (stmats.indexOf(stmat) == 0)
                continue;

            /** Rename the second one states as in continue of the first one. */
            StateTransitionMatrix.renameStates(stmatMerged, stmat);

            /** Add new alphabet letters to the second table. */
            StateTransitionMatrix.expandAlphabets(stmatMerged, stmat);

            /** Calculates the concatenation of two StateTransitionMatrix. */
            StateTransitionMatrix.buildConcat(stmatMerged, stmat);
        }

        return stmatMerged;
    }

    /**
     * Calculates the star of a given StateTransitionMatrix.
     *
     * @param stmatA the stared StateTransitionMatrix
     */
    public static void star(StateTransitionMatrix stmat) {

        StateTransitionMatrix.buildStar(stmat);
    }
}
