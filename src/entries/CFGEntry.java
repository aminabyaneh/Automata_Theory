package entries;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class CFGEntry.
 */
public class CFGEntry extends Entry {

    /** The start symbol. */
    private String startSymbol;

    /** The variables. */
    private String[] variables;

    /** The terminals. */
    private String[] terminals;

    /** The state transition matrix. */
    private HashMap<String, ArrayList<String>> rules;

    /**
     * Instantiates a new NFA entry.
     */
    public CFGEntry() {

        this.rules = new HashMap<String, ArrayList<String>>();
    }

    /**
     * Gets the start symbol.
     *
     * @return the start symbol
     */
    public String getStartSymbol() {
        return startSymbol;
    }

    /**
     * Sets the start symbol.
     *
     * @param startSymbol the new start symbol
     */
    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    /**
     * Gets the variables.
     *
     * @return the variables
     */
    public String[] getVariables() {
        return variables;
    }

    /**
     * Sets the variables.
     *
     * @param strings the new variables
     */
    public void setVariables(String[] strings) {
        this.variables = strings;
    }

    /**
     * Gets the terminals.
     *
     * @return the terminals
     */
    public String[] getTerminals() {
        return terminals;
    }

    /**
     * Sets the terminals.
     *
     * @param strings the new terminals
     */
    public void setTerminals(String[] strings) {
        this.terminals = strings;
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public HashMap<String, ArrayList<String>> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param rules the new rules
     */
    public void setRules(HashMap<String, ArrayList<String>> rules) {
        this.rules = rules;
    }
}
