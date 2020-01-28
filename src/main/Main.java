package main;

import java.util.ArrayList;

import core.RegEx;
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

        cell.add(cell.size(), 0);
        System.out.println(cell.toString());
    }

}
