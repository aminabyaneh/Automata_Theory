package main;

import core.NFA;
import core.RegEx;
import entries.NFAEntry;
import entries.RegExEntry;
import utils.IO;

public class Main {

    public static void main(String []argc) {


        automata();
        //        test();
    }

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
        default:
            break;
        }

    }

    private static void test() {

        System.out.println("Test sequence initiated...");
    }

}
