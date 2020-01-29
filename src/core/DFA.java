package core;

import java.util.ArrayList;

public class DFA {

    /** The state transition matrix. */
    StateTransitionMatrix stmax;

    public DFA() {

        super();
        this.stmax = new StateTransitionMatrix();
    }

    public void makeMin(NFA nfa) {

        /** Extract required info. */
        ArrayList<ArrayList<ArrayList<Integer>>> table =
                nfa.stmat.table;

        /** Create a partition array. */
        ArrayList<Partition> partitions = new ArrayList<Partition>();

        /** Create first two partitions. */


    }


}
