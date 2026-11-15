## Week 9 Assignment 2: Unit Testing a Spring Application (Task Manager)

Created 16 tests using JUnit 5, Mockito, Spring Data JPA, and MockMvc.

### Test Classes Createed:

TaskTest.java (4 tests): Tests the Task entity logic, including past dates, future dates, today's date, and invalid dates.

TaskRepositoryTest.java (2 tests): Uses @DataJpaTest and H2 to verify custom repository queries for task completion status and due dates.

TaskServiceTest.java (5 tests): Uses Mockito to test task creation, retrieval, updating, and exception handling without needing a database.

TaskControllerTest.java (4 tests): Uses @WebMvcTest, @MockitoBean, and MockMvc to verify HTTP requests, Thymeleaf views, model attributes, and redirects.

TaskmanagerApplicationTests.java (1 test): Verifies that the Spring Boot application loads successfully.

### Testing 

Used the following command to verify all tests passed:

```powershell
.\mvnw.cmd clean test
```

### Test Results

All tests passed and a screenshot of the test results will be uploaded in a Word doc to the LMS assignment turn-in
