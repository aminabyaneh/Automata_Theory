package main;

import core.RegEx;
import utils.Chars;
import utils.IO;
import utils.Phrase;
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
        Phrase p = new Phrase();
        p.hasStar = true;
        p.prevOperation = Chars.concatenation;
        p.nextOperation = Chars.concatenation;
        System.out.println(p.toString());
    }

}
