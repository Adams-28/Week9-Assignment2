package net.skillspire.taskmanager;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @GetMapping
    public String getAllTasks(@RequestParam(required = false) Boolean completed, Model model) {

        if (completed == null) {
            model.addAttribute("tasks", service.getAllTasks());
        } else {
            model.addAttribute(
                    "tasks",
                    service.getTasksByCompleted(completed)
            );
        }

        return "task-list";
    }



    // Enter a new task within HTML
    @GetMapping("/new")
    public String showTaskForm(Model model) {

        model.addAttribute("task", new Task());

        return "task-form";
    }

    @GetMapping("/{id}")
    public String getTaskById(
    @PathVariable Long id,
    Model model) {

        model.addAttribute("task",  service.getTaskById(id));

        return "task-detail";

    }


    // Endpoint for displaying the edit task form
    @GetMapping("/{id}/edit")
    public String EditTaskForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("task", service.getTaskById(id));

        return "task-form";
    }

    // Endpoint to create a new task
    @PostMapping
    public String createTask(
            @ModelAttribute("task") Task task,
            RedirectAttributes redirectAttributes) {

        service.addTask(task);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task created!"
        );

        return "redirect:/tasks";
    }


    // Endpoint to edit a task
    @PostMapping("/{id}/edit")
    public String updateTask(
            @PathVariable Long id,
            @ModelAttribute("task") Task task,
            RedirectAttributes redirectAttributes) {

        service.updateTask(id, task);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task has been updated!"
        );

        return "redirect:/tasks";
    }



    //Endpoint to delete a task

    @PostMapping("/{id}/delete")
    public String deleteTask(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        service.deleteTask(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task was deleted!"
        );

        return "redirect:/tasks";
    }



    // Method for showing customer errors
    @ExceptionHandler(TaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String TaskNotFound(
            TaskException exception, Model model) {

        model.addAttribute("errorMessage", exception.getMessage());

        return "task-error";
    }




}
