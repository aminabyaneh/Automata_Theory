package core;

import java.util.ArrayList;
import java.util.Collections;

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

            /** Add new alphabet letters to the second table. */
            NFA.expandAlphabets(nfaMerged, nfa);

            /** Add new states to first NFA. */
            NFA.expandStates(nfaMerged, nfa);
        }

        return nfaMerged;
    }

    public static void starNFA(NFA nfaA) {

        ArrayList<ArrayList<Integer>> columnEnd;
        columnEnd = nfaA.NFATable.get(nfaA.NFATable.size() - 1);

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

            if (nfaB.NFATable.indexOf(column) == 0)
                continue;

            ArrayList<Integer> cell = column.get(0);
            cell.set(0, maxStateNumber + nfaB.NFATable.indexOf(column));
        }
    }

    private static void expandAlphabets(NFA nfaA, NFA nfaB) {

        ArrayList<Integer> alphabetA = NFA.extractAlphabet(nfaA);
        ArrayList<Integer> alphabetB = NFA.extractAlphabet(nfaB);

        for (Integer letter : alphabetB) {

            if (alphabetA.contains(letter))
                continue;

            NFA.addLetter(nfaA, letter);
        }
        System.out.println("Alphabet: " + NFA.extractAlphabet(nfaA).toString());
    }

    private static void expandStates(NFA nfaA, NFA nfaB) {

        ArrayList<Integer> statesB = NFA.extractStates(nfaB);
        Collections.reverse(statesB);

        for (Integer state : statesB) {

            NFA.addState(nfaA, nfaB, state);
        }
        System.out.println("Alphabet: " + NFA.extractAlphabet(nfaA).toString());
    }

    private static ArrayList<Integer> extractAlphabet(NFA nfa) {

        ArrayList<Integer> alphabet = new ArrayList<Integer>();
        for (ArrayList<Integer> cell : nfa.NFATable.get(0)) {

            if (cell.get(0) == 0)
                continue;
            alphabet.add(cell.get(0));
        }
        return null;
    }

    private static ArrayList<Integer> extractStates(NFA nfa) {

        ArrayList<Integer> states = new ArrayList<Integer>();
        for (ArrayList<ArrayList<Integer>> column : nfa.NFATable) {

            if (column.get(0).get(0) == 0)
                continue;
            states.add(column.get(0).get(0));
        }
        return null;
    }

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

    private static void addState(NFA nfaA, NFA nfaB, int state) {

        ArrayList<ArrayList<Integer>> stateColumn;
        for (ArrayList<ArrayList<Integer>> column : nfaB.NFATable) {

            if (column.get(0).get(0) == state) {

                stateColumn = column;
                break;
            }
        }


    }
}
