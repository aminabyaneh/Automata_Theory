package core;

import java.util.ArrayList;
import java.util.logging.Logger;

import utils.Chars;
import utils.Phrase;
import utils.RegExEntry;
import utils.Tasks;

/**
 * The Class RegEx.
 * This class handles related conversions
 * and optimizations to regular expression inputs.
 */
public class RegEx {

    /** The main string of RegEx. */
    private String regex;

    /** The task in which the code has to perform. */
    private Tasks task;

    /** Logger is initiated. */
    private static final Logger LOGGER =
            Logger.getLogger(RegEx.class.getName());

    /**
     * Instantiates a new RegEx class.
     *
     * @param data the data
     */
    public RegEx(RegExEntry data) {

        /** Set the main variables. */
        this.regex = data.getInputRegEx();
        this.task = data.getTask();

        /** Call the task to action function. */
        this.taskHandler();
    }

    private void taskHandler() {

        switch (this.task) {

        case RegEx:
            System.out.println(this.regex);
            break;
        case DFA:
            break;
        case NFA:
            this.createNFA();
            break;

        case PDA:
        default:
            LOGGER.warning("required task is not available.");
            break;
        }
    }

    /**
     * Creates the NFA. This function constructs and NFA recursively and
     * returns the result.
     *
     * TODO: add return statement.
     */
    private void createNFA() {

        this.regex = this.addConcatenation();
        this.regex = this.addParenthesis();
        System.out.println("Regex after concatenation: " + this.regex);
        NFA ret = this.buildNFA(this.regex);
        System.out.println("Final NFA: " + ret.NFATable.toString());
    }

    /**
     * Adds the concatenation to a row input string.
     * Concatenations are added as "." symbol to the main string.
     *
     * @return the string which includes "."
     */
    private String addConcatenation() {

        StringBuilder str = new StringBuilder(this.regex);

        for (int i = 0; i < str.toString().length() - 1; i++) {

            Character ch_i = str.toString().charAt(i);
            Character ch_i_next = str.toString().charAt(i + 1);

            if (ch_i.equals(Chars.concatenation))
                continue;

            if (Character.isLetter(ch_i) ||
                    ch_i.equals(Chars.close_parenthesis) ||
                    ch_i.equals(Chars.kleene_star)) {

                if (Character.isLetter(ch_i_next) ||
                        ch_i_next.equals(Chars.open_parenthesis)) {

                    str.insert(i + 1, Chars.concatenation);
                }
            }
        }

        return str.toString();
    }

    /**
     * Adds the parenthesis to an input RegEx.
     *
     * @return the string including the correct parenthesis.
     */
    private String addParenthesis() {

        StringBuilder str = new StringBuilder(this.regex);

        for (int i = 1; i < str.toString().length() - 1; i++) {

            Character ch_i = str.toString().charAt(i);
            Character ch_i_next = str.toString().charAt(i + 1);
            Character ch_i_prev = str.toString().charAt(i - 1);


            if (Character.isLetter(ch_i) &&
                    (ch_i_next != Chars.close_parenthesis ||
                    ch_i_prev != Chars.open_parenthesis)) {

                str.insert(i + 1, Chars.close_parenthesis);
                str.insert(i, Chars.open_parenthesis);
            }
        }

        Character ch_i_i = str.toString().charAt(0);
        if (Character.isLetter(ch_i_i)) {
            str.insert(0, Chars.open_parenthesis);
            str.insert(0 + 2, Chars.close_parenthesis);
        }

        Character ch_i_e = str.toString().charAt(str.toString().length() - 1);
        if (Character.isLetter(ch_i_e)) {
            str.insert(str.toString().length(), Chars.close_parenthesis);
            str.insert(str.toString().length() - 1 - 1, Chars.open_parenthesis);
        }

        return str.toString();
    }

    /**
     * Builds the NFA.
     * Recursively builds an NFA based on modified Thompson rules.
     *
     * @param regex the input RegEx to translate to NFA
     * @return the NFA
     */
    private NFA buildNFA(String regex) {

        /** Trivial case, NFA in this state is a basic NFA. */
        if (regex.length() == 1) {

            Phrase p = new Phrase(regex);
            NFA trivialNFA = new NFA(p);

            return trivialNFA;
        }

        ArrayList<Phrase> phrases = new ArrayList<Phrase>();
        ArrayList<NFA> nfas = new ArrayList<NFA>();

        /** Extract all the phrases and then call recursively on build. */
        phrases = this.extractPhrases(regex);
        for (Phrase p : phrases) {

            nfas.add(this.buildNFA(p.string));
        }

        NFA combinedNFA = new NFA();

        /** Checking for star in phrases and applying it to NFA. */
        for (Phrase p : phrases) {

            int index = phrases.indexOf(p);
            NFA.addEpsilon(nfas.get(index));

            if (p.hasStar) {

                NFA.starNFA(nfas.get(index));
                LOGGER.config("Star result: " +
                        nfas.get(index).NFATable.toString());
            }
        }

        /** Start processing the operations. */
        Phrase p = phrases.get(0);
        if (p.nextOperation == Chars.concatenation) {

            combinedNFA = NFA.concatNFA(nfas);
            LOGGER.config("Concat result: " + combinedNFA.NFATable.toString());
            return combinedNFA;
        }
        else if (p.nextOperation == Chars.union) {

        }
        else if (p.nextOperation == Chars.none) {

            /** Only one phrase exists in this case so return the NFA. */
            combinedNFA = nfas.get(0);
        }

        return combinedNFA;
    }

    /**
     * Extract phrases.
     *
     * @param regex
     * @return the array list of all phrases
     */
    private ArrayList<Phrase> extractPhrases(String regex) {

        ArrayList<Phrase> phrases = new ArrayList<Phrase>();

        for (int i = 0; i <= regex.length() - 1; i++) {

            Character ch_i = regex.charAt(i);

            if (ch_i.equals(Chars.open_parenthesis)) {

                int parenthesis = 1;
                int j = i;

                while (parenthesis >= 1) {

                    j += 1;
                    Character ch_j = regex.charAt(j);

                    if (ch_j.equals(Chars.close_parenthesis))
                        parenthesis -= 1;
                    if (ch_j.equals(Chars.open_parenthesis))
                        parenthesis += 1;
                }

                Phrase p = this.buildPhrase(regex, i, j);
                phrases.add(p);

                i = j;

                if (p.nextOperation.equals(Chars.none))
                    break;
            }
        }
        return phrases;
    }

    /**
     * Builds a phrase based on start and end index.
     *
     * @param regex
     * @param i the i initial index
     * @param j the j final index
     * @return the phrase
     */
    private Phrase buildPhrase(String regex, int i, int j) {

        Phrase p = new Phrase();
        p.string = regex.substring(i + 1, j);

        if (j == regex.length() - 1) {

            p.hasStar = false;
            p.nextOperation = Chars.none;

            if (i != 0)
                p.prevOperation = regex.charAt(i - 1);
            else
                p.prevOperation = Chars.none;

            return p;
        }

        p.hasStar = false;
        Character ch_jj = regex.charAt(j + 1);

        if (ch_jj.equals(Chars.kleene_star)) {

            p.hasStar = true;
            j += 1;
        }

        if (j != regex.length() - 1)
            p.nextOperation = regex.charAt(j + 1);
        else
            p.nextOperation = Chars.none;

        if (i != 0)
            p.prevOperation = regex.charAt(i - 1);
        else
            p.prevOperation = Chars.none;

        return p;
    }
}
