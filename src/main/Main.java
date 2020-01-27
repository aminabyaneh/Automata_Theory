package main;

import java.util.ArrayList;

import core.NFA;
import core.RegEx;
import utils.IO;
import utils.RegExEntry;

public class Main {

    public static void main(String []argc) {


        automata();
        //        test();
    }

    private static void automata() {

        IO ioHandler = new IO();

        switch (ioHandler.getDataType()) {

        case RegEx:
            RegEx nfa = new RegEx((RegExEntry)ioHandler.getData());
            break;

        default:
            break;
        }

    }

    private static void test() {

        System.out.println("Test sequence initiated...");

        ArrayList<ArrayList<ArrayList<Integer>>> NFATable = new ArrayList<ArrayList<ArrayList<Integer>>>();

        ArrayList<ArrayList<Integer>> column = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(0);
        column.add(cell);
        cell = new ArrayList<Integer>();
        cell.add(1);
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
        column = NFATable.get(0);

        System.out.println(column.get(column.size() - 1).contains(NFA.epsilon));
    }

}
