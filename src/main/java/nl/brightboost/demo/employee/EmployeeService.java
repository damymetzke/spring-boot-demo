package nl.brightboost.demo.employee;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import nl.brightboost.demo.project.Project;
import nl.brightboost.demo.project.ProjectRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository REPOSITORY;
    private final ProjectRepository PROJECT_REPOSITORY;
    private final Logger LOGGER;

    public EmployeeService(EmployeeRepository repository, ProjectRepository projectRepository) {
        REPOSITORY = repository;
        PROJECT_REPOSITORY = projectRepository;
        LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    }

    public Collection<Employee> getEmployees() {
        return REPOSITORY.findAll();
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> result = REPOSITORY.findById(id);

        if (!result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        return result.get();
    }

    public Employee storeEmployee(Employee employee) {
        LOGGER.info("Stored new employee: {}", employee);
        return REPOSITORY.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long id, Employee employee) {
        Optional<Employee> databaseEmployee = REPOSITORY.findById(id);

        if (!databaseEmployee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        Employee result = databaseEmployee.get();

        if (!Objects.equals(employee.getName(), result.getName())) {
            result.setName(employee.getName());
        }

        if (!Objects.equals(employee.getEmail(), result.getEmail())) {
            result.setEmail(employee.getEmail());
        }

        if (employee.isActive() != result.isActive()) {
            result.setActive(employee.isActive());
        }

        LOGGER.info("Updated existing employee with id {}: {}", id, result);
        return result;
    }

    public void deleteEmployee(long id) {
        if (!REPOSITORY.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }
        REPOSITORY.deleteById(id);
        LOGGER.info("Removed existing employee with id {}", id);
    }

    @Transactional
    public Employee assignEmployeeToProjects(long id, Set<Long> projectIds) {
        Optional<Employee> databaseEmployee = REPOSITORY.findById(id);

        if (!databaseEmployee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        Employee employee = databaseEmployee.get();
        employee.setProjectEntities(new HashSet<>());

        projectIds.stream().map(projectId -> {
            Optional<Project> databaseProject = PROJECT_REPOSITORY.findById(projectId);

            if (!databaseProject.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
            }

            return databaseProject.get();
        }).forEach(project -> {
            employee.getProjectEntities().add(project);
        });

        return employee;
    }

    @Transactional
    public void removeEmployeeFromProject(long id, long employeeId) {
        Optional<Employee> databaseEmployee = REPOSITORY.findById(id);

        if (!databaseEmployee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        Employee employee = databaseEmployee.get();

        Project project = employee.getProjectEntities().stream()
                .filter(possibleProject -> possibleProject.getId() == employeeId)
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No project with id " + id + " found."));

        project.getEmployeeEntities().remove(employee);
        employee.getProjectEntities().remove(project);
    }
}
