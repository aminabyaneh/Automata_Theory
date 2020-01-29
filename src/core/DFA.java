package core;

import java.util.ArrayList;

public class DFA {

    /** The state transition matrix. */
    StateTransitionMatrix stmax;

    /** The NFA state transition matrix. */
    StateTransitionMatrix nfaSTM;

    /**
     * Instantiates a new DFA.
     */
    public DFA() {

        super();
        this.stmax = new StateTransitionMatrix();
    }

    /**
     * Makes the minimum DFA out of an NFA.
     *
     * @param nfa the input NFA which is to be minimized
     */
    public void makeMin(NFA nfa) {

        /** Extract required info. */
        this.nfaSTM = nfa.stmat;

        /** Create first partition. */
        Partition partition  = new Partition();

        partition.getSets().add(nfa.getFinalStates());
        partition.getSets().add(nfa.getNonFinalStates());

        /** Try to build P_k while P_k and P_k-1 are different. */
        partition = this.maximumPartitioning(partition);
        System.out.println("Final Partition: " + partition.toString());

        /** Convert final partition to DFA. */
        this.createDFA(partition);
    }

    /**
     * Creates the DFA corresponding to final partitioning.
     *
     * @param partition the final partition
     */
    private void createDFA(Partition partition) {


    }

    /**
     * Performs the maximum partitioning.
     *
     * @param partition the array of all partitions
     * @return the partition
     */
    private Partition maximumPartitioning(Partition partition) {

        Partition nextPartition  = new Partition();

        do {

            System.out.println("Entering while...");
            nextPartition =
                    this.makeNextPartitioning(partition);

            if ((partition.equals(nextPartition)))
                return partition;
            else
                partition = nextPartition;

        } while (true);
    }

    /**
     * Make the next partitioning.
     *
     * @param partition the previous partition
     * @return the next partition
     */
    private Partition makeNextPartitioning(Partition partition) {

        boolean anyDistinguishable = false;
        Partition newPartition = new Partition();

        for (ArrayList<Integer> set : partition.getSets()) {
            System.out.println("*Set: " + set.toString());

            for (Integer state : set) {

                if (newPartition.hasState(state))
                    continue;

                ArrayList<Integer> newSet = new ArrayList<Integer>();
                newSet.add(state);

                for (int i = set.indexOf(state) + 1; i < set.size(); i++) {

                    if (this.areDistinguished(partition, state, set.get(i))) {

                        anyDistinguishable = true;
                    }
                    else {

                        newSet.add(set.get(i));
                    }
                }
                newPartition.addSet(newSet);
            }
        }

        System.out.println("$New Partition: " +
                newPartition.getSets().toString() + "->" + anyDistinguishable);

        /** Compare the acquired partition and previous one. */
        if (anyDistinguishable == false)
            newPartition = partition;

        return newPartition;
    }

    /**
     * Checks whether two states are indistinguishable or not.
     * @param partition
     *
     * @param state the state
     * @param integer the integer
     * @return true, if successful
     */
    private boolean areDistinguished(Partition partition,
            Integer state1, Integer state2) {

        ArrayList<ArrayList<Integer>> col1 = StateTransitionMatrix.
                retrieveColumn(this.nfaSTM, state1);
        ArrayList<ArrayList<Integer>> col2 = StateTransitionMatrix.
                retrieveColumn(this.nfaSTM, state2);

        System.out.println("#Col1: " + col1.toString());
        System.out.println("#Col2: " + col2.toString());

        for (int index = 1; index < col1.size(); index++) {

            if (!(partition.haveSimilarSets(col1.get(index),
                    col2.get(index))))
                return true;
        }

        return false;
    }

}
