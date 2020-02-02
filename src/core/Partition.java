package core;

import java.util.ArrayList;

/**
 * The Class Partition.
 */
public class Partition {

    /** The sets. */
    private ArrayList<ArrayList<Integer>> sets;

    /**
     * Instantiates a new partition.
     */
    public Partition() {

        sets = new ArrayList<ArrayList<Integer>>();
    }

    /**
     * Gets the sets.
     *
     * @return the sets
     */
    public ArrayList<ArrayList<Integer>> getSets() {

        return sets;
    }

    /**
     * Sets the sets.
     *
     * @param sets the new sets
     */
    public void setSets(ArrayList<ArrayList<Integer>> sets) {

        this.sets = sets;
    }

    /**
     * Adds the new set.
     *
     * @param set the set
     */
    public void addSet(ArrayList<Integer> set) {

        this.sets.add(set);
    }

    /**
     * Checks for state whether exists in any set.
     *
     * @param state the state
     * @return true, if successful
     */
    public boolean hasState(Integer state) {

        for (ArrayList<Integer> set : this.sets) {

            if (set.contains(state))
                return true;
        }

        return false;
    }


    /**
     * Have similar sets.
     *
     * @param cell1 the cell 1
     * @param cell2 the cell 2
     * @return true, if successful
     */
    public boolean haveSimilarSets(ArrayList<Integer> cell1,
            ArrayList<Integer> cell2) {

        if (cell1.size() != cell2.size())
            return false;

        for (Integer state1 : cell1) {

            for (Integer state2 : cell2) {

                if (this.setNumber(state1) != this.setNumber(state2))
                    return false;
            }
        }

        return true;
    }

    /**
     * Returns the set number.
     *
     * @param state the state
     * @return the integer
     */
    private Integer setNumber(Integer state) {
        for (ArrayList<Integer> set : this.sets) {

            if (set.contains(state))
                return this.sets.indexOf(set);
        }
        return null;
    }

    /**
     * Make the next partitioning.
     *
     * @param stmat the state transition matrix
     * @return the next partition
     */
    Partition makeNextPartitioning(StateTransitionMatrix stmat) {

        boolean anyDistinguishable = false;
        Partition newPartition = new Partition();

        for (ArrayList<Integer> set : this.getSets()) {

            for (Integer state : set) {

                if (newPartition.hasState(state))
                    continue;

                ArrayList<Integer> newSet = new ArrayList<Integer>();
                newSet.add(state);

                for (int i = set.indexOf(state) + 1; i < set.size(); i++) {

                    if (this.areDistinguished(state, set.get(i), stmat)) {

                        anyDistinguishable = true;
                    }
                    else {

                        newSet.add(set.get(i));
                    }
                }
                newPartition.addSet(newSet);
            }
        }

        /** Compare the acquired partition and previous one. */
        if (anyDistinguishable == false)
            newPartition = this;

        return newPartition;
    }


    /**
     * Checks whether two states are indistinguishable or not.
     *
     * @param state1 the state 1
     * @param state2 the state 2
     * @param stmat the state transition matrix
     * @return true, if successful
     */
    private boolean areDistinguished(Integer state1, Integer state2,
            StateTransitionMatrix stmat) {

        ArrayList<ArrayList<Integer>> col1 = StateTransitionMatrix.
                retrieveColumn(stmat, state1);
        ArrayList<ArrayList<Integer>> col2 = StateTransitionMatrix.
                retrieveColumn(stmat, state2);

        System.out.println("col1: " + col1.toString());
        System.out.println("col2: " + col2.toString());

        for (int index = 1; index < col1.size(); index++) {

            if (!(this.haveSimilarSets(col1.get(index),
                    col2.get(index))))
                return true;
        }

        return false;
    }

}
