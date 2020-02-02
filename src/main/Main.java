package main;

import java.util.ArrayList;

import core.CFG;
import core.NFA;
import core.RegEx;
import entries.CFGEntry;
import entries.NFAEntry;
import entries.RegExEntry;
import utils.IO;

public class Main {

    public static void main(String []argc) {

        automata();
    }

    /**
     * Automata, the main entry point to the huge state machine.
     *
     */
    private static void automata() {

        IO ioHandler = new IO();

        switch (ioHandler.getDataType()) {

        case RegEx:
            RegEx regEx = new RegEx((RegExEntry)ioHandler.getData());
            regEx.taskHandler();
            break;

        case NFA:
            NFA nfa = new NFA((NFAEntry)ioHandler.getData());
            nfa.taskHandler();
            break;

        case CFG:
            CFG cfg = new CFG((CFGEntry)ioHandler.getData());
            cfg.taskHandler();
            break;

        default:
            break;
        }

    }

    @SuppressWarnings("unused")
    private static void test() {

        System.out.println("Test sequence initiated...");
        ArrayList<ArrayList<Integer>> s = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        Integer aa = 1;
        a.add(aa);
        a.add(2);

        s.add(a);
        b.add(1);
        b.add(2);


        System.out.println(s.contains(b));
    }

}
