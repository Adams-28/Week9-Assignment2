package net.skillspire.taskmanager;

public class TaskException extends RuntimeException {



    public TaskException(Long id) {
        super("Task: " + id + " not found!");
    }
}
