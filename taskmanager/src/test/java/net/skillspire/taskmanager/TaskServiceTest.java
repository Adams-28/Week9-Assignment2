package net.skillspire.taskmanager;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// Testing the Service layer via retrieving saved tasks
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    // Setting up the Mock annotations
    @Mock
    private TaskRepository repo;

    @InjectMocks
    private TaskService service;


    // Testing with Mockito
    @Test
    void returnSavedTasks() {

        Task task1 = new Task(
                "Test service task 1",
                "Test service task 1",
                LocalDate.now().plusDays(5),
                false
        );

        // Adding Mockito method to return the saved task
        when(repo.save(task1)).thenReturn(task1);


        Task result = service.addTask(task1);

        // Setting up the expected results for the test
        assertEquals(task1, result);

        // Verifying interaction dependency
        verify(repo).save(task1);
    }


    // Testing second service method
    @Test
    void returnTasksById() {


        Long taskId = 1L;

        Task task1 = new Task(
                "Test service task 1 method 2",
                "Test service task 1 method 2",
                LocalDate.now().plusDays(5),
                false
        );

        task1.setId(taskId);

        when(repo.findById(taskId)).thenReturn(Optional.of(task1));

        Task result = service.getTaskById(taskId);

        assertEquals(taskId, result.getId());
        assertEquals("Test service task 1 method 2", result.getTitle());

        verify(repo).findById(taskId);


    }

    // Creating test for error handling when looking up tasks that do not exist
    @Test
    void returnTasksByIdThrowsException() {

        Long taskId = 99L;

        when(repo.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> {
            service.getTaskById(taskId);
        });

        verify(repo).findById(taskId);
    }


    // Testing updating existing tasks
    @Test
    void updateExistingTasks() {

        Long taskId = 1L;

        Task existingTask = new Task(
                "Test title",
                "Test description",
                LocalDate.now(),
                false
        );

        existingTask.setId(taskId);

        Task updatedTask = new Task(
                "Updated title",
                "Updated description",
                LocalDate.now().plusDays(5),
                true
        );

        when(repo.findById(taskId)).thenReturn(Optional.of(existingTask));

        when(repo.save(existingTask)).thenReturn((existingTask));

        Task result = service.updateTask(taskId, updatedTask);


        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals(updatedTask.getDueDate(), result.getDueDate());
        assertTrue(result.getCompleted());

        verify(repo).findById((taskId));
        verify(repo).save(existingTask);


    }


    // Testing when updating a task that is not found
    @Test
    void updateTaskWhenNotFound() {

        Long taskID = 99L;

        Task updatedTask = new Task(
                "Test title",
                "Test description",
                LocalDate.now().plusDays(5),
                true
        );

        when(repo.findById(taskID)).thenReturn((Optional.empty()));

        assertThrows(TaskException.class, () -> {
            service.updateTask(taskID, updatedTask);

        });


        verify(repo).findById(taskID);
        verify(repo, never()).save(any(Task.class));
    }

}
