package nl.brightboost.demo.project;

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

import nl.brightboost.demo.employee.Employee;
import nl.brightboost.demo.employee.EmployeeRepository;

@Service
public class ProjectService {
    private final ProjectRepository REPOSITORY;
    private final EmployeeRepository EMPLOYEE_REPOSITORY;
    private final Logger LOGGER;

    public ProjectService(ProjectRepository repository, EmployeeRepository employeeRepository) {
        REPOSITORY = repository;
        EMPLOYEE_REPOSITORY = employeeRepository;
        LOGGER = LoggerFactory.getLogger(ProjectService.class);
    }

    public Collection<Project> getProjects() {
        return REPOSITORY.findAll();
    }

    public Project getProjectById(long id) {
        Optional<Project> result = REPOSITORY.findById(id);

        if (!result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
        }

        return result.get();
    }

    public Project storeProject(Project project) {
        LOGGER.info("Stored new project: {}", project);
        return REPOSITORY.save(project);
    }

    @Transactional
    public Project updateProject(long id, Project project) {
        Optional<Project> databaseProject = REPOSITORY.findById(id);

        if (!databaseProject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
        }

        Project result = databaseProject.get();

        if (!Objects.equals(project.getTitle(), result.getTitle())) {
            result.setTitle(project.getTitle());
        }

        if (!Objects.equals(project.getDescription(), result.getDescription())) {
            result.setDescription(project.getDescription());
        }

        if (!Objects.equals(project.getVersion(), result.getVersion())) {
            result.setVersion(project.getVersion());
        }

        if (project.getStatus() != result.getStatus()) {
            result.setStatus(project.getStatus());
        }

        LOGGER.info("Updated existing project with id {}: {}", id, result);
        return result;
    }

    public void deleteProject(long id) {
        if (!REPOSITORY.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
        }
        REPOSITORY.deleteById(id);
        LOGGER.info("Removed existing project with id {}", id);
    }

    @Transactional
    public Project assignEmployeesToProject(long id, Set<Long> employeeIds) {
        Optional<Project> databaseProject = REPOSITORY.findById(id);

        if (!databaseProject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
        }

        Project project = databaseProject.get();
        project.setEmployeeEntities(new HashSet<>());

        employeeIds.stream().map(employeeId -> {
            Optional<Employee> databaseEmployee = EMPLOYEE_REPOSITORY.findById(employeeId);

            if (!databaseEmployee.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
            }

            return databaseEmployee.get();
        }).forEach(employee -> {
            project.getEmployeeEntities().add(employee);
        });

        return project;
    }

    @Transactional
    public void removeEmployeeFromProject(long id, long employeeId) {
        Optional<Project> databaseProject = REPOSITORY.findById(id);

        if (!databaseProject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No project with id " + id + " found.");
        }

        Project project = databaseProject.get();

        Employee employee = project.getEmployeeEntities().stream()
                .filter(possibleEmployee -> possibleEmployee.getId() == employeeId)
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No employee with id " + id + " found."));

        project.getEmployeeEntities().remove(employee);
        employee.getProjectEntities().remove(project);
    }
}
