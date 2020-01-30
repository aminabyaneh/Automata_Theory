package entries;

/**
 * The Class RegExEntry.
 * This class keeps the input data in RegEx entry mode.
 */

public class RegExEntry extends Entry {

    /** The String. */
    private String input;

    /**
     * Gets the input of regular expression.
     *
     * @return the input RegEx.
     */
    public String getInput() {

        return this.input;
    }

    /**
     * Sets the input regular expression.
     *
     * @param inputRegEx the new input RegEx
     */
    public void setInput(String input) {

        this.input = input;
    }
}
