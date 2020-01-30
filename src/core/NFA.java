package core;

import java.util.ArrayList;
import java.util.logging.Logger;

import entries.NFAEntry;
import utils.Phrase;
import utils.Tasks;

/**
 * The Class NFA.
 *
 * TODO: make stmat private and pass only tables in functions.
 * TODO: create s super class state machine for NFA and DFA and
 *  move common elements there.
 * TODO: remove static functions in NFA and make it look more like DFA.
 */
public class NFA {

    /** The NFA table. */
    StateTransitionMatrix stmat;

    /** The final states. */
    private ArrayList<Integer> finalStates;

    /** The start state. */
    private Integer startState;

    /** The task. */
    private Tasks task;

    /** The input data. */
    private NFAEntry inputData;

    /** Logger is initiated. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER =
    Logger.getLogger(RegEx.class.getName());

    /** The epsilon is represented by -1. */
    public static int epsilon = -1;

    /**
     * Instantiates a new empty NFA class.
     */
    public NFA() {

        super();
    }

    /**
     * Instantiates a new trivial NFA.
     *
     * @param p the phrase on which an NFA is built.
     */
    public NFA(Phrase p) {

        this.stmat = new StateTransitionMatrix();

        ArrayList<ArrayList<Integer>> column = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(0);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add((int)p.string.charAt(0));
        column.add(cell);

        stmat.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(1);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);

        stmat.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);
        cell = new ArrayList<Integer>();
        column.add(cell);

        stmat.add(column);
    }

    /**
     * Instantiates a new NFA using entry data.
     *
     * @param nfaEntry the NFA entry
     */
    public NFA(NFAEntry data) {

        this.inputData = data;
        this.task = this.inputData.getTask();
        this.stmat = this.inputData.convertToSTMat();

        this.setFinalStates(data.
                convertStatesToIntegers(data.getFinalStates()));
        this.setStartState(data.
                convertStateToInteger(data.getStartState()));
    }

    /**
     * Task handler.
     */
    public void taskHandler() {

        switch (this.task) {

        case RegEx:
            //TODO: implement later.
            break;

        case DFA:
            DFA requestedDFA = this.createMinimumDFA();
            requestedDFA.stmax.print(this.inputData.getStatesHM(),
                    this.finalStates);
            break;

        case NFA:
            this.stmat.print(this.inputData.getStatesHM(),
                    this.finalStates);
            break;

        case PDA:
        default:
            break;
        }
    }

    /**
     * Creates the minimum DFA.
     */
    public DFA createMinimumDFA() {

        DFA dfa = new DFA();

        /** DFA builds a minimum DFA. */
        dfa.buildDFA(this);

        /** DFA builds a minimum DFA. */
        dfa.makeMin();

        /** The answer is minDFA. */
        return dfa;
    }

    /**
     * Calculate the union NFA.
     *
     * @param nfas the array of NFAs
     * @return the final union NFA
     */
    public static NFA unionNFA(ArrayList<NFA> nfas) {

        NFA nfaLargest = NFA.getLargestNFA(nfas);
        NFA nfaMerged = nfaLargest;

        for (NFA nfa : nfas) {

            /** Skip the first one. */
            if (nfas.indexOf(nfa) == nfas.indexOf(nfaLargest))
                continue;

            /** Rename the second one states as in continue of the first one. */
            StateTransitionMatrix.renameStates(nfaMerged, nfa);

            /** Add new alphabet letters to the second table. */
            StateTransitionMatrix.expandAlphabets(nfaMerged, nfa);

            /** Calculates the union of two NFAs. */
            StateTransitionMatrix.buildUnionNFA(nfaMerged, nfa);
        }

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
            StateTransitionMatrix.renameStates(nfaMerged, nfa);

            /** Add new alphabet letters to the second table. */
            StateTransitionMatrix.expandAlphabets(nfaMerged, nfa);

            /** Calculates the concatenation of two NFAs. */
            StateTransitionMatrix.buildConcatNFA(nfaMerged, nfa);
        }

        return nfaMerged;
    }

    /**
     * Calculates the star of a given NFA.
     *
     * @param nfaA the stared NFA
     */
    public static void starNFA(NFA nfaA) {

        StateTransitionMatrix.buildStarNFA(nfaA);
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

            if (element.stmat.size() > maxSeen) {

                maxSeen = element.stmat.size();
                largest = element;
            }
        }

        return largest;
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
     * Gets the non final states.
     *
     * @return the non final states
     */
    public ArrayList<Integer> getNonFinalStates() {

        ArrayList<Integer> nonFinalStates = new ArrayList<Integer>();

        for (ArrayList<ArrayList<Integer>> column : this.stmat) {

            int state = column.get(0).get(0);
            if (state == 0)
                continue;

            if (!(this.getFinalStates().contains(state)))
                nonFinalStates.add(state);
        }

        return nonFinalStates;
    }

    /**
     * Sets the final states.
     *
     * @param finalStates the new final states
     */
    public void setFinalStates(ArrayList<Integer> finalStates) {

        this.finalStates = finalStates;
    }

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
}
