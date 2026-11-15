package net.skillspire.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    void taskSetup() {
        task = new Task(
                "Test task 1",
                "Test task 1",
                LocalDate.now().minusDays(5),
                false
        );
    }

    // Testing to ensure an overdue task returns true
    @Test
    void overdueTaskReturnsTrue() {

        boolean result = task.taskOverdue();

        assertTrue(result);
    }

    // Testing to ensure a future task returns false
    @Test
    void futureTaskReturnsFalse() {

        task.setDueDate(LocalDate.now().plusDays(5));

        boolean result = task.taskOverdue();

        assertFalse(result);
    }

    // Testing to make sure that a task that is due today returns false
    @Test
    void todayTaskReturnsFalse() {

        task.setDueDate(LocalDate.now());

        boolean result = task.taskOverdue();

        assertEquals(false, result);
    }

    // Testing invalid task date error handling
    @Test
    void invalidTaskDate() {

        task.setDueDate(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> task.taskOverdue()
        );


        assertEquals(
                "There is no specified date!",
                exception.getMessage()
        );
    }

}
