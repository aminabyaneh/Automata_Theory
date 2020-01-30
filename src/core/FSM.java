package core;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The Class FSM.
 * This class is parent to NFA and DFA class.
 *
 */
public abstract class FSM {

    /** The state transition matrix for normal DFA. */
    StateTransitionMatrix stmat;

    /** The final states. */
    ArrayList<Integer> finalStates;

    /** The start state. */
    Integer startState;


    /** The epsilon is represented by -1. */
    public static int epsilon = -1;

    /** Logger is initiated. */
    @SuppressWarnings("unused")
    static final Logger LOGGER =
    Logger.getLogger(RegEx.class.getName());

    /**
     * Gets the start state.
     *
     * @return the start state
     */
    public Integer getStartState() {
        return startState;
    }

    /**
     * Sets the start state.
     *
     * @param startState the new start state
     */
    public void setStartState(Integer startState) {
        this.startState = startState;
    }


    /**
     * Gets the final states.
     *
     * @return the final states
     */
    public ArrayList<Integer> getFinalStates() {
        return finalStates;
    }


    /**
     * Sets the final states.
     *
     * @param finalStates the new final states
     */
    public void setFinalStates(ArrayList<Integer> finalStates) {
        this.finalStates = finalStates;
    }

}
