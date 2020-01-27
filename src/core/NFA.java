package core;

import java.util.ArrayList;
import java.util.List;

import utils.Chars;
import utils.Phrase;

/**
 * The Class NFA.
 */
public class NFA {

    /** The NFA table. */
    private List<List<String>> NFATable;


    public NFA() {
        super();
    }

    public NFA(Phrase p) {

        NFATable = new ArrayList<List<String>>();

        ArrayList<String> column = new ArrayList<String>();
        column.add("Alphabet");
        column.add(p.string);

        NFATable.add(column);

        column.clear();
        column.add("1");
        column.add("2");

        NFATable.add(column);

        column.clear();
        column.add("2");
        column.add(Chars.none.toString());
    }

    public static NFA unionNFA(NFA nfaA, NFA nfaB) {

        NFA nfaMerged = new NFA();

        return nfaMerged;
    }

    public static NFA concatNFA(NFA nfaA, NFA nfaB) {

        NFA nfaMerged = new NFA();

        return nfaMerged;
    }

    public static NFA starNFA(NFA nfaA) {

        NFA nfaMerged = new NFA();

        return nfaMerged;
    }
}
