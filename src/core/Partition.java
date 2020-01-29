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

        for (Integer state1 : cell1) {

            for (Integer state2 : cell2) {
                System.out.println("Checking: " + state1 + " " + state2);
                System.out.println("SC: " + this.setNumber(state1).toString() +
                        " " + this.setNumber(state2));
                if (this.setNumber(state1) != this.setNumber(state2))
                    return false;
            }
        }

        return true;
    }

    private Integer setNumber(Integer state) {
        for (ArrayList<Integer> set : this.sets) {

            if (set.contains(state))
                return this.sets.indexOf(set);
        }
        return null;
    }

}
