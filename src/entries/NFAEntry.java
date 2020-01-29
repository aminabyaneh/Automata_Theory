package entries;

import java.util.ArrayList;
import java.util.HashMap;

import core.NFA;
import core.StateTransitionMatrix;
import utils.Chars;
import utils.Tasks;

/**
 * The Class NFAEntry.
 * This class parses the NFA input data.
 *
 */
public class NFAEntry {

    /** The input alphabet. */
    HashMap <String, Integer> alphabet;

    /** The states. */
    HashMap<String, Integer> statesHM;

    /** The states. */
    private String [] states;

    /** The start state. */
    private String startState;

    /** The final states. */
    private String[] finalStates;

    /** The state transition matrix. */
    private ArrayList<String> stateTransitionMat;

    /** The task. */
    private Tasks task;

    /**
     * Instantiates a new NFA entry.
     */
    public NFAEntry() {

        this.alphabet = new HashMap<String, Integer>();
        this.statesHM = new HashMap<String, Integer>();
        this.stateTransitionMat = new ArrayList<String>();
    }
    /**
     * Gets the alphabet.
     *
     * @return the alphabet
     */
    public HashMap <String, Integer> getAlphabet() {

        return alphabet;
    }

    /**
     * Gets the alphabet.
     *
     * @return the alphabet
     */
    public ArrayList<Integer> getAlphabetInt() {

        ArrayList<Integer> alphabetInt = new ArrayList<Integer>();
        this.alphabet.forEach((key, value) -> alphabetInt.add(value));

        return alphabetInt;
    }

    /**
     * Sets the alphabet.
     *
     * @param alphabet the new alphabet
     */
    public void setAlphabet(String[] alphabet) {

        for (String letter : alphabet) {

            this.alphabet.put(letter, (int)letter.charAt(0));
        }
    }

    /**
     * Gets the start state.
     *
     * @return the start state
     */
    public String getStartState() {

        return startState;
    }

    /**
     * Sets the start state.
     *
     * @param startState the new start state
     */
    public void setStartState(String startState) {

        this.startState = startState;
    }

    /**
     * Gets the final states.
     *
     * @return the final states
     */
    public String[] getFinalStates() {

        return finalStates;
    }

    /**
     * Sets the final states.
     *
     * @param finalStates the new final states
     */
    public void setFinalStates(String[] finalStates) {

        this.finalStates = finalStates;
    }

    /**
     * Gets the state transition mat.
     *
     * @return the state transition mat
     */
    public ArrayList<String> getStateTransitionMat() {

        return stateTransitionMat;
    }

    /**
     * Sets the state transition mat.
     *
     * @param stateTransitionMat the new state transition mat
     */
    public void setStateTransitionMat(ArrayList<String> stateTransitionMat) {

        this.stateTransitionMat = stateTransitionMat;
    }

    /**
     * Convert to state transition matrix with integers.
     *
     * @return the state transition matrix
     */
    public StateTransitionMatrix convertToSTMat() {

        StateTransitionMatrix stmat = new StateTransitionMatrix();

        /** Load alphabet and states. */
        ArrayList<Integer> intStates =
                this.convertStatesToIntegers(this.states);
        ArrayList<Integer> alphabet =
                this.getAlphabetInt();

        /** Add 0 and epsilon to alphabet. */
        alphabet.add(NFA.epsilon);
        alphabet.add(0, 0);

        ArrayList<ArrayList<Integer>> column =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cell;

        for (Integer letter : alphabet) {

            cell = new ArrayList<Integer>();
            cell.add(letter);
            column.add(cell);
        }
        stmat.table.add(column);

        /** Initialize the stmat. */
        for (Integer state : intStates) {

            column = new ArrayList<ArrayList<Integer>>();

            for (ArrayList<Integer> c : stmat.table.get(0)) {

                cell = new ArrayList<Integer>();
                if (c.get(0) == 0)
                    cell.add(state);

                column.add(cell);
            }

            stmat.table.add(column);
        }

        /** Fill the stmat. */
        int index = 0;
        for (ArrayList<ArrayList<Integer>> col : stmat.table) {

            if (col.get(0).get(0) == 0)
                continue;

            String [] str = this.getStateTransitionMat().
                    get(index).split("\\s+");

            int cellIndex = 0;
            for (String s : str) {

                cellIndex++;
                if (s.charAt(0) == Chars.none) {

                    col.get(cellIndex).clear();
                }
                else {

                    String [] st = s.split(",");
                    col.get(cellIndex).addAll(this.
                            convertStatesToIntegers(st));
                }
            }

            index++;
        }

        return stmat;
    }

    /**
     * Convert states to integers.
     *
     * @return the integer[]
     */
    public ArrayList<Integer> convertStatesToIntegers(String [] states) {

        ArrayList<Integer> intStates = new ArrayList<Integer>();

        for (String s : states) {

            intStates.add(this.convertStateToInteger(s));
        }
        return intStates;
    }

    /**
     * Convert state to integer.
     *
     * @return the integer
     */
    public Integer convertStateToInteger(String state) {


        return this.statesHM.get(state);
    }

    /**
     * Gets the task.
     *
     * @return the task
     */
    public Tasks getTask() {
        return task;
    }

    /**
     * Sets the task.
     *
     * @param task the new task
     */
    public void setTask(Tasks task) {

        this.task = task;
    }

    /**
     * Sets the states.
     *
     * @param states the new states
     */
    public void setStates(String[] states) {

        this.states = states;

        int i = 0;
        for (String state : states) {

            i++;
            this.statesHM.put(state, i);
        }
    }

    /**
     * Sets the states.
     *
     * @param states the new states
     */
    public String [] getStates() {

        return this.states;
    }
}
