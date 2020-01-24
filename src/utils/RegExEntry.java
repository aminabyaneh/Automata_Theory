package utils;

/**
 * The Class RegExEntry.
 * This class keeps the input data in RegEx entry mode.
 */

public class RegExEntry {

    /** The String. */
    private String inputRegEx;

    /** The task. */
    private Tasks task;

    /**
     * Gets the input of regular expression.
     *
     * @return the input RegEx.
     */
    public String getInputRegEx() {

        return inputRegEx;
    }

    /**
     * Sets the input regular expression.
     *
     * @param inputRegEx the new input RegEx
     */
    public void setInputRegEx(String inputRegEx) {

        this.inputRegEx = inputRegEx;
    }

    /**
     * Gets the task.
     *
     * @return the task
     */
    public Tasks getTask() {

        return task;
    }

    /**
     * Sets the task.
     *
     * @param task the new task
     */
    public void setTask(Tasks task) {

        this.task = task;
    }
}
