package core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class DFA.
 */
public class DFA {

    /** The state transition matrix for normal DFA. */
    StateTransitionMatrix stmax;

    /** The minimum DFA state transition matrix. */
    StateTransitionMatrix minSTM;

    /** The NFA state transition matrix. */
    StateTransitionMatrix nfaSTM;

    /** The final states. */
    private ArrayList<Integer> finalStates;

    /** The final states. */
    private ArrayList<Integer> nfaFinalStates;

    /** The start state. */
    private Integer startState;

    /**
     * Instantiates a new DFA.
     */
    public DFA() {

        this.stmax = new StateTransitionMatrix();
        this.finalStates = new ArrayList<Integer>();
    }

    /**
     * Builds the DFA of an incoming NFA.
     *
     * @param the input NFA to convert to DFA
     */
    public void buildDFA(NFA nfa) {

        System.out.println("\n\nBuilding DFA....");
        this.nfaSTM = nfa.stmat;
        this.nfaFinalStates = nfa.getFinalStates();

        /** Initialize the DFA table. */
        this.initSTM();
        this.addNewColumn();
        System.out.println("STM initialized: " + this.stmax.toString());

        /** Get the first state e-closure. */
        ArrayList<Integer> eClosure = this.nfaSTM.
                getEpsilonClosure(this.nfaSTM.get(1).get(0).get(0));

        /** Add this as first state in DFA. */
        this.stmax.get(1).get(0).addAll(eClosure);
        System.out.println("STM first added: " + this.stmax.toString());

        /** See where the last state added goes, fill its column,
         * add the new states to state transition matrix as well.
         */
        int lastCheckedColumn = 0;

        /** Continue until there is no new state. */
        while (lastCheckedColumn < this.stmax.size() - 1) {

            /** Increase the last checked number. */
            lastCheckedColumn++;

            /** Fill the current column. */
            this.fillColumnFromNFA(lastCheckedColumn);

            /** Check whether a new state is found and add it. */
            this.addNewStates(lastCheckedColumn);
        }

        /** Set correct final and start states. */
        this.simplifySTM(this.stmax);
        System.out.println("Simplified stm: " + this.stmax.toString());
        System.out.println("Final states: " + this.getFinalStates().toString());
        System.out.println("Start state: " + this.getStartState());
    }

    /**
     * Simplify the state transition matrix.
     *
     * @param stm the simplified state transition matrix.
     */
    private void simplifySTM(StateTransitionMatrix stm) {

        Integer newName = 1;

        /** Set start state. */
        this.setStartState(newName);

        for (ArrayList<ArrayList<Integer>> col : stm) {

            if (col.get(0).get(0) == 0)
                continue;

            ArrayList<Integer> oldName = new ArrayList<Integer>();
            oldName.addAll(col.get(0));

            /** Set final states. */
            for (Integer finalState : this.nfaFinalStates) {

                if (oldName.contains(finalState)) {

                    this.getFinalStates().add(newName);
                }
            }

            /** Change state number in the entire table. */
            for (ArrayList<ArrayList<Integer>> c : stm) {

                for (ArrayList<Integer> cell : c) {

                    if (cell.equals(oldName)) {

                        cell.clear();
                        cell.add(newName);
                    }
                }
            }

            newName++;
        }
    }

    /**
     * Adds the new states to state transition matrix.
     *
     * @param lastCheckedColumn the last checked column
     */
    private void addNewStates(int lastCheckedColumn) {

        System.out.println("\nAdding new states...");
        ArrayList<ArrayList<Integer>> column =
                this.stmax.get(lastCheckedColumn);

        ArrayList<ArrayList<Integer>> previousStates =
                new ArrayList<ArrayList<Integer>>();

        for (ArrayList<ArrayList<Integer>> col : this.stmax) {

            if (this.stmax.indexOf(col) != 0)
                previousStates.add(col.get(0));
        }

        previousStates.forEach(element -> Collections.sort(element));
        column.forEach(element -> Collections.sort(element));
        System.out.println("Previous states: " + previousStates.toString());
        System.out.println("column states: " + column.toString());

        for (ArrayList<Integer> cell : column) {

            if ((!previousStates.contains(cell)) && (!cell.isEmpty())) {

                this.addNewColumn();
                this.stmax.get(this.stmax.size() - 1).get(0).addAll(cell);
            }
        }
        System.out.println("After addition: " + this.stmax.toString());
    }

    /**
     * Fill the column based on NFA state transmission mat.
     *
     * @param lastCheckedState the last checked state
     */
    private void fillColumnFromNFA(int lastCheckedColumn) {

        ArrayList<ArrayList<Integer>> column =
                this.stmax.get(lastCheckedColumn);

        ArrayList<Integer> state = column.get(0);
        ArrayList<Integer> nextState;
        ArrayList<Integer> nfaCell = new ArrayList<Integer>();
        ArrayList<Integer> nfaCellClosure = new ArrayList<Integer>();
        System.out.println("\nFilling column...");
        for (ArrayList<Integer> cell : this.stmax.get(0)) {
            if (cell.get(0) == 0)
                continue;

            nextState = new ArrayList<Integer>();

            for (Integer s : state) {
                System.out.println("State: " + s.toString() + "Letter: " +
                        cell.get(0));

                nfaCell = StateTransitionMatrix.retrieveCell(this.nfaSTM,
                        s, cell.get(0));

                System.out.println("NFA Cell: " + nfaCell.toString());
                for (Integer dstState : nfaCell) {

                    System.out.println("Dst State: " + dstState.toString() + " " +
                            nextState.toString());

                    if (!nextState.contains(dstState)) {

                        nextState.add(dstState);
                        nfaCellClosure = this.nfaSTM.
                                getEpsilonClosure(dstState);
                        System.out.println("nfaCellClosure: " + nfaCellClosure.toString());
                        for (Integer closureState : nfaCellClosure) {
                            System.out.println("Closure State: " + closureState.toString());
                            if (!nextState.contains(closureState))
                                nextState.add(closureState);
                        }
                    }

                    System.out.println("Next state: " + nextState.toString());
                }
            }
            System.out.println("Final nextState: " + nextState.toString() + "\n");
            column.get(this.stmax.get(0).indexOf(cell)).addAll(nextState);
        }
    }

    /**
     * Initializes the STM using nfaSTM and adds the first column.
     *
     */
    private void initSTM() {

        ArrayList<ArrayList<Integer>> newColumn =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> newCell;

        for (ArrayList<Integer> cell : this.nfaSTM.get(0)) {

            if (cell.get(0) == NFA.epsilon)
                continue;

            newCell = new ArrayList<Integer>();
            newCell.add(cell.get(0));
            newColumn.add(cell);
        }
        this.stmax.add(newColumn);
    }

    /**
     * Adds the new column based on NFA alphabet length.
     *
     */
    private void addNewColumn() {

        ArrayList<ArrayList<Integer>> newColumn =
                new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> newCell;

        for (ArrayList<Integer> cell : this.nfaSTM.get(0)) {

            if (cell.get(0) == NFA.epsilon)
                continue;

            newCell = new ArrayList<Integer>();
            newColumn.add(newCell);
        }

        this.stmax.add(newColumn);
    }

    /**
     * Makes the minimum DFA out of an NFA.
     *
     * @param nfa the input NFA which is to be minimized
     */
    public void makeMin() {

        /** Create first partition. */
        Partition partition  = new Partition();

        partition.getSets().add(this.getFinalStates());
        partition.getSets().add(this.getNonFinalStates());

        /** Try to build P_k while P_k and P_k-1 are different. */
        partition = this.maximumPartitioning(partition);
        System.out.println("Final Partition: " + partition.getSets().toString());

        /** Convert final partition to DFA. */
        this.partitionToDFA(partition);
    }

    /**
     * Gets the non final states.
     *
     * @return the non final states
     */
    private ArrayList<Integer> getNonFinalStates() {

        ArrayList<Integer> nonFinalStates = new ArrayList<Integer>();

        for (ArrayList<ArrayList<Integer>> column : this.stmax) {

            int state = column.get(0).get(0);
            if (state == 0)
                continue;

            if (!(this.getFinalStates().contains(state)))
                nonFinalStates.add(state);
        }

        return nonFinalStates;
    }

    /**
     * Gets the final states.
     *
     * @return the final states
     */
    private ArrayList<Integer> getFinalStates() {

        return this.finalStates;
    }

    /**
     * Creates the DFA corresponding to final partitioning.
     *
     * @param partition the final partition
     */
    private void partitionToDFA(Partition partition) {

        /** First column is the same as NFA except for epsilon. */
        //        ArrayList<ArrayList<Integer>> newColumn =
        //                new ArrayList<ArrayList<Integer>>();
        //        ArrayList<Integer> newCell;
        //
        //        for (ArrayList<Integer> cell : this.nfaSTM.get(0)) {
        //
        //            if (cell.get(0) == NFA.epsilon)
        //                continue;
        //
        //            newCell = new ArrayList<Integer>();
        //            newCell.add(cell.get(0));
        //            newColumn.add(cell);
        //        }
        //        this.stmax.add(newColumn);
        //
        //        /** Initialize the DFA state transition matrix. */
        //        for (ArrayList<Integer> set : partition.getSets()) {
        //
        //            newColumn = new ArrayList<ArrayList<Integer>>();
        //
        //            for (ArrayList<Integer> c : this.nfaSTM.get(0)) {
        //
        //                if (c.get(0) == NFA.epsilon)
        //                    continue;
        //
        //                newCell = new ArrayList<Integer>();
        //                newColumn.add(newCell);
        //            }
        //
        //            this.stmax.add(newColumn);
        //        }

        /** Fill the state transition matrix based on partitions. */
        //        int index = 0;
        //        for (ArrayList<ArrayList<Integer>> col : stmat) {
        //
        //            if (col.get(0).get(0) == 0)
        //                continue;
        //
        //            String [] str = this.getStateTransitionMat().
        //                    get(index).split("\\s+");
        //
        //            int cellIndex = 0;
        //            for (String s : str) {
        //
        //                cellIndex++;
        //                if (s.charAt(0) == Chars.none) {
        //
        //                    col.get(cellIndex).clear();
        //                }
        //                else {
        //
        //                    String [] st = s.split(",");
        //                    col.get(cellIndex).addAll(this.
        //                            convertStatesToIntegers(st));
        //                }
        //            }
        //
        //            index++;
        //        }
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
                retrieveColumn(this.stmax, state1);
        ArrayList<ArrayList<Integer>> col2 = StateTransitionMatrix.
                retrieveColumn(this.stmax, state2);

        System.out.println("#Col1: " + col1.toString());
        System.out.println("#Col2: " + col2.toString());

        for (int index = 1; index < col1.size(); index++) {

            if (!(partition.haveSimilarSets(col1.get(index),
                    col2.get(index))))
                return true;
        }

        return false;
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
