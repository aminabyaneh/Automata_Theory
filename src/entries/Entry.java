package entries;

import utils.Tasks;

/**
 * The Class Entry.
 * This is the abstract class for entry objects.
 *
 */
public abstract class Entry {


    /** The task. */
    protected Tasks task;

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
