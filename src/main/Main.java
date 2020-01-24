package main;

import core.RegEx;
import utils.IO;
import utils.RegExEntry;

public class Main {

    public static void main(String []argc) {

        automata();
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

}
