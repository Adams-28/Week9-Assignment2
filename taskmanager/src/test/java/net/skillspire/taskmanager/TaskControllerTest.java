package net.skillspire.taskmanager;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    //Creating a MockMvc to simulate HTTP requests
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    // Testing the GET request
    @GetMapping
    public String getAllTasks(
            @RequestParam(required = false) Boolean completed, Model model
    ) {

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


    @Test
    void getAllTasksTaskListView() throws Exception {

        Task task = new Task(
                "Test task",
                "Test task",
                LocalDate.now().plusDays(5),
                false
        );

        when(service.getAllTasks()).thenReturn(List.of(task));

        // Simulating the HTTP request
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-list"))
                .andExpect(model().attributeExists("tasks"));

        // Verify the interaction
        verify(service).getAllTasks();
    }


    // Testing the POST request for creating a task
    @PostMapping
    public String createTask(
    @ModelAttribute("task") Task task,
    RedirectAttributes redirectAttributes) {

    service.addTask(task);

    redirectAttributes.addFlashAttribute(
            "successMessage",
            "Task created!"
    );

    return "redirected:/tasks";
    }


    @Test
    void createTaskRedirectTasksList() throws Exception {

        mockMvc.perform(post("/tasks")
                .param("title", "Test title")
                .param("description", "Test description")
                .param("dueDate", "2026-11-20")
                .param("complelted", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"))
                .andExpect(flash().attribute(
                        "successMessage", "Task created!"
                ));

        verify(service).addTask(any(Task.class));
    }


    //Testing GET request for filtered Tasks
    @Test
    void getCompletedTasksFiltered() throws Exception {

        Task completedTask = new Task(
                "Test title",
                "Test description",
                LocalDate.now(),
                true
        );


        when(service.getTasksByCompleted(true)).thenReturn(List.of(completedTask));

        mockMvc.perform(get("/tasks")
                .param("completed", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-list"))
                .andExpect(model().attribute("tasks", List.of(completedTask)));


        verify(service).getTasksByCompleted(true);
        verify(service, never()).getAllTasks();

    }


    // Testing GET request for a specific task ID
    @Test
    void getTaskByIdTaskDetailView() throws Exception {

        Long taskId = 1L;

        Task task = new Task(
                "Test title",
                "Test description",
                LocalDate.now().plusDays(5),
                false
        );

        task.setId(taskId);

        when(service.getTaskById(taskId))
                .thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(view().name("task-detail"))
                .andExpect(model().attribute("task", task));

        verify(service).getTaskById(taskId);
    }




}
