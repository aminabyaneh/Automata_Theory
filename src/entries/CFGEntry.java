package entries;

import java.util.ArrayList;

/**
 * The Class CFGEntry.
 */
public class CFGEntry {

    /** The start symbol. */
    private String startSymbol;

    /** The variables. */
    private String[] variables;

    /** The terminals. */
    private String[] terminals;

    /** The state transition matrix. */
    private ArrayList<String> rules;

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
     * @param variables the new variables
     */
    public void setVariables(String[] variables) {
        this.variables = variables;
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
     * @param terminals the new terminals
     */
    public void setTerminals(String[] terminals) {
        this.terminals = terminals;
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public ArrayList<String> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param rules the new rules
     */
    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }
}
