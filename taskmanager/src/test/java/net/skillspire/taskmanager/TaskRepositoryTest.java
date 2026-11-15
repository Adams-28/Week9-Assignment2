package net.skillspire.taskmanager;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TaskRepositoryTest {


    @Autowired
    private TaskRepository repository;



    //Testing first custom repository method
    @Test
    void findTaskByCompleted() {

        // Creating sample tasks for testing

        Task task1 = new Task(
                "Test task 1",
                "Test task 1",
                LocalDate.now().plusDays(2),
                true
        );

        Task task2 = new Task(
                "Test task 2",
                "Test task 2",
                LocalDate.now().plusDays(3),
                false
        );

        Task task3 = new Task(
                "Test task 3",
                "Test task 3",
                LocalDate.now().plusDays(4),
                true
        );


        // Saving the sample tasks to the database
        repository.save(task1);
        repository.save(task2);
        repository.save(task3);


        // Getting the complete tasks
        List<Task> completedTasks = repository.findByCompleted(true);



        //Verifying results
        assertEquals(2, completedTasks.size());

        assertTrue(completedTasks.stream().allMatch(Task::getCompleted));








    }


    // Testing the second custom repository methods
    @Test
    void findTaskByDueDateBefore() {

        Task task1 = new Task(
                "Test task 1 method 2",
                "Test task 1 method 2",
                LocalDate.now().minusDays(5),
                false
        );

        Task task2 = new Task(
                "Test task 2 method 2",
                "Test task 2 method 2",
                LocalDate.now(),
                false
        );

        Task task3 = new Task(
                "Test task 3 method 2",
                "Test task 3 method 2",
                LocalDate.now().plusDays(5),
                false
        );

        repository.save(task1);
        repository.save(task2);
        repository.save(task3);


        List<Task> pastTasks = repository.findByDueDateBefore(LocalDate.now());

        // Testing the expected results
        assertEquals(1, pastTasks.size());
        assertEquals("Test task 1 method 2", pastTasks.get(0).getTitle());


    }
}
