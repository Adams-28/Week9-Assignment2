package net.skillspire.taskmanager;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    // Add a task
    public Task addTask(Task t) {
        return repo.save(t);
    }

    // Update a task
    public Task updateTask(Long id, Task updatedTask) {

        Task exisitingTask = getTaskById(id);

        exisitingTask.setTitle(updatedTask.getTitle());
        exisitingTask.setDescription(updatedTask.getDescription());
        exisitingTask.setDueDate(updatedTask.getDueDate());
        exisitingTask.setCompleted(updatedTask.getCompleted());

        return repo.save(exisitingTask);
    }

    // Delete a task
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        repo.delete(task);
    }

    // View all tasks
    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    // Get task by ID
    public Task getTaskById(Long id) {
        return repo.findById(id).orElseThrow(() -> new TaskException(id));
    }

    public List<Task> getTasksByCompleted(boolean completed) {
        return repo.findByCompleted(completed);
    }



}
