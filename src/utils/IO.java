package utils;

import java.util.Scanner;

public class IO {

    private Scanner sc;
    public IO() {

        // initiate scanner object
        sc = new Scanner(System.in);

        // read the input string's first line
        Functions func = readInput();

        // check the output for next action
        switch (func) {

        case CFG:
            readCFG();
        case NFA:
            readNFA();
        case RegEx:
            readRegEx();

        default:
            break;
        }
    }

    private Functions readInput() {

        // read first input
        String input = sc.nextLine();

        // map strings to corresponding enumerations
        switch (input) {

        case "CFG":
            return Functions.CFG;
        case "NFA":
            return Functions.NFA;
        case "RegEx":
            return Functions.RegEx;

        default:
            return Functions.Unknown;
        }
    }

    private void readCFG() {

        //TODO
    }

    private void readNFA() {

        //TODO
    }

    private void readRegEx() {


    }
}

