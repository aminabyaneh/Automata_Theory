package main;

import java.util.ArrayList;

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

        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(1);
        cell.add(2);
        cell.add(3);
        cell.add(4);
        cell.add(5);

        cell.add(1, 0);
        cell.add(1, 6);
        cell.add(1, 8);
        System.out.println(cell.toString());
    }

}
