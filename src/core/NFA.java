package core;

import java.util.ArrayList;

import utils.Phrase;

/**
 * The Class NFA.
 */
public class NFA {

    /** The NFA table. */
    ArrayList<ArrayList<ArrayList<Integer>>> NFATable;

    public static int epsilon = -1;

    public NFA() {
        super();
    }

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

    public static NFA concatNFA(ArrayList<NFA> nfas) {

        NFA nfaMerged = nfas.get(0);

        for (NFA nfa : nfas) {

            /** Skip the first one. */
            if (nfas.indexOf(nfa) == 0)
                continue;

            /** Rename the second one states as in continue of the first one. */
            NFA.renameStates(nfaMerged, nfa);

            /**  */
        }

        return nfaMerged;
    }

    public static void starNFA(NFA nfaA) {

        ArrayList<ArrayList<Integer>> columnEnd;
        columnEnd = nfaA.NFATable.get(2);

        ArrayList<Integer> cell = columnEnd.get(columnEnd.size() - 1);
        if (!cell.contains(1))
            cell.add(1);
    }

    public static void addEpsilon(NFA nfaA) {

        if (!(NFA.hasLetter(nfaA, NFA.epsilon))) {

            NFA.addLetter(nfaA, NFA.epsilon);
        }
    }

    private static boolean hasLetter(NFA nfaA, int letter) {

        ArrayList<ArrayList<Integer>> column;
        column = nfaA.NFATable.get(0);

        for (ArrayList<Integer> cell : column) {

            if (cell.contains(letter))
                return true;
        }
        return false;
    }

    private static void renameStates(NFA nfaA, NFA nfaB) {

        int maxStateNumber = 0;
        for (ArrayList<ArrayList<Integer>> column : nfaA.NFATable) {

            if (column.get(0).get(0) >= maxStateNumber)
                maxStateNumber = column.get(0).get(0);
        }
        System.out.println("maxStateNumber: " + maxStateNumber);

        for (ArrayList<ArrayList<Integer>> column : nfaB.NFATable) {

            ArrayList<Integer> cell = column.get(0);
            cell.set(0, maxStateNumber + 1 + nfaB.NFATable.indexOf(column));
        }
    }

    private static void addLetter(NFA nfaA, int letter) {

        ArrayList<Integer> cell = new ArrayList<Integer>();
        for (ArrayList<ArrayList<Integer>> c : nfaA.NFATable) {

            if (nfaA.NFATable.indexOf(c) == 0) {
                cell.add(letter);
                c.add(cell);
            }
            else {
                cell = new ArrayList<Integer>();
                c.add(cell);
            }
        }
    }
}
