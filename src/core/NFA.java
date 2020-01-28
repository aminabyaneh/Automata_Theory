package core;

import java.util.ArrayList;
import java.util.logging.Logger;

import utils.Phrase;

/**
 * The Class NFA.
 *
 */
public class NFA {

    /** The NFA table. */
    Table nfaTable;

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

        this.nfaTable = new Table();
        nfaTable.table = new ArrayList<ArrayList<ArrayList<Integer>>>();

        ArrayList<ArrayList<Integer>> column = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(0);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add((int)p.string.charAt(0));
        column.add(cell);

        nfaTable.table.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(1);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);

        nfaTable.table.add(column);

        column = new ArrayList<ArrayList<Integer>>();
        cell = new ArrayList<Integer>();
        cell.add(2);
        column.add(cell);
        cell = new ArrayList<Integer>();
        column.add(cell);

        nfaTable.table.add(column);
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
            Table.renameStates(nfaMerged, nfa);

            /** Add new alphabet letters to the second table. */
            Table.expandAlphabets(nfaMerged, nfa);

            /** Calculates the union of two NFAs. */
            Table.buildUnionNFA(nfaMerged, nfa);
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
            Table.renameStates(nfaMerged, nfa);

            /** Add new alphabet letters to the second table. */
            Table.expandAlphabets(nfaMerged, nfa);

            /** Calculates the concatenation of two NFAs. */
            Table.buildConcatNFA(nfaMerged, nfa);
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
        columnEnd = nfaA.nfaTable.table.get(nfaA.nfaTable.table.size() - 1);

        ArrayList<Integer> cell = columnEnd.get(columnEnd.size() - 1);
        if (!cell.contains(1))
            cell.add(1);
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

            if (element.nfaTable.table.size() > maxSeen) {

                maxSeen = element.nfaTable.table.size();
                largest = element;
            }
        }

        return largest;
    }
}
