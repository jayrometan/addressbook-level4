package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class VersionedTaskList extends TaskList {
    private final List<ReadOnlyTaskList> taskListStateList;
    private int currentStatePointer;

    public VersionedTaskList(ReadOnlyTaskList initialState){
        super(initialState);
        taskListStateList = new ArrayList<>();
        taskListStateList.add(new TaskList(initialState));
        currentStatePointer = 0;
    }
    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        taskListStateList.add(new TaskList(this));
        currentStatePointer++;
        indicateModified();
    }

    public void resetData(ReadOnlyTaskList newData) {
        requireNonNull(newData);
        setTasks(newData.getTaskList());
    }

    private void removeStatesAfterCurrentPointer() {
        taskListStateList.subList(currentStatePointer + 1, taskListStateList.size()).clear();
    }

}