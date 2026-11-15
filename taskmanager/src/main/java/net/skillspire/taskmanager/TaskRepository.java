package net.skillspire.taskmanager;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {



    List<Task> findByTitle(String title);
    List<Task> findByDescription(String description);
    List<Task> findByDueDate(LocalDate dueDate);

    // Showing only completed tasks
    List<Task> findByCompleted(boolean completed);

    // Showing tasks before a specific date
    List<Task> findByDueDateBefore(LocalDate date);




}
