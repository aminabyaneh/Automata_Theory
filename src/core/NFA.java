package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entries.NFAEntry;
import utils.Chars;
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
public class NFA extends FSM {

    /** The task. */
    private Tasks task;

    /** The input data. */
    private NFAEntry inputData;

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
            requestedDFA.stmat.print(this.inputData.getStatesHM(),
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
     * Builds the.
     *
     * @param regex the regex
     */
    public void build(String regex) {

        /** Build the STM for desired regular expression. */
        this.stmat = this.buildRecursive(regex);
    }

    /**
     * Builds the NFA.
     * Recursively builds an NFA based on modified Thompson rules.
     *
     * @param regex the input RegEx to translate to NFA
     * @return the NFA
     */
    private StateTransitionMatrix buildRecursive(String regex) {

        /** Trivial case, NFA in this state is a basic NFA. */
        if (regex.length() == 1) {

            Phrase p = new Phrase(regex);
            NFA trivialNFA = new NFA(p);

            return trivialNFA.stmat;
        }

        ArrayList<Phrase> phrases = new ArrayList<Phrase>();
        ArrayList<StateTransitionMatrix> stmats =
                new ArrayList<StateTransitionMatrix>();

        /** Extract all the phrases and then call recursively on build. */
        phrases = RegEx.extractPhrases(regex);
        for (Phrase p : phrases) {

            stmats.add(this.buildRecursive(p.string));
        }

        /** Checking for star in phrases and applying it to NFA. */

        for (Phrase p : phrases) {

            int index = phrases.indexOf(p);
            StateTransitionMatrix.addEpsilon(stmats.get(index));

            if (p.hasStar)
                StateTransitionMatrix.star(stmats.get(index));
        }

        /** Start processing the operations. */
        Phrase p = phrases.get(0);

        if (p.nextOperation == Chars.concatenation) {

            return StateTransitionMatrix.concat(stmats);
        }
        else if (p.nextOperation == Chars.union) {

            return StateTransitionMatrix.union(stmats);
        }
        else if (p.nextOperation == Chars.none) {

            /** Only one phrase exists in this case so return the NFA. */
            return stmats.get(0);
        }

        return null;
    }

    /**
     * Creates the minimum DFA.
     */
    public DFA createMinimumDFA() {

        DFA dfa = new DFA();

        /** DFA builds a minimum DFA. */
        dfa.build(this);

        /** DFA builds a minimum DFA. */
        dfa.makeMin();

        /** The answer is minDFA. */
        return dfa;
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
     * Reverse hash map.
     *
     * @param hash the hash map needed to be reversed.
     * @return the reversed hash map
     */
    public static HashMap<Integer, String> reverseHash(
            HashMap<String, Integer> hash) {

        HashMap<Integer, String> reversedHash =
                new HashMap<Integer, String>();

        for (Map.Entry<String, Integer> entry : hash.entrySet()) {

            reversedHash.put(entry.getValue(), entry.getKey());
        }

        return reversedHash;
    }
}
