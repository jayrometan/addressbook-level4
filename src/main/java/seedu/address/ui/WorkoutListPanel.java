package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.workout.Workout;


/**
 *  Panel containing a list of workout
 */

public class WorkoutListPanel extends UiPart<Region> {

    private static final String FXML = "WorkoutListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WorkoutListPanel.class);

    @javafx.fxml.FXML
    private ListView<Workout> workoutListView;

    public WorkoutListPanel(ObservableList<Workout> workoutList, ObservableValue<Workout> selectedWorkout,
                         Consumer<Workout> onSelectedWorkoutChange) {
        super(FXML);
        workoutListView.setItems(workoutList);
        workoutListView.setCellFactory(listView -> new WorkoutListViewCell());
        workoutListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            onSelectedWorkoutChange.accept(newValue);
        });
        selectedWorkout.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected person changed to: " + newValue);

            if (Objects.equals(workoutListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                workoutListView.getSelectionModel().clearSelection();
            } else {
                int index = workoutListView.getItems().indexOf(newValue);
                workoutListView.scrollTo(index);
                workoutListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Workout} using a {@code WorkoutCard}.
     */
    class WorkoutListViewCell extends ListCell<Workout> {
        @Override
        protected void updateItem(Workout workout, boolean empty) {
            super.updateItem(workout, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkoutCard(workout, getIndex() + 1).getRoot());
            }
        }
    }

}

