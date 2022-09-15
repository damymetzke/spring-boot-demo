package nl.brightboost.demo.project;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.brightboost.demo.employee.Employee;

@RestController
@RequestMapping(value = "projects")
public class ProjectController {

    private final ProjectService SERVICE;

    public ProjectController(ProjectService service) {
        SERVICE = service;
    }

    @GetMapping
    public Collection<Project> listProjects() {
        return SERVICE.getProjects();
    }

    @PostMapping
    public Map<String, String> createProject(@Validated @RequestBody Project employee) {
        return Map.of(
                "resource_uri", "/projects/" + SERVICE.storeProject(employee).getId());
    }

    @GetMapping(path = "{id}")
    public Project getProject(@PathVariable long id) {
        return SERVICE.getProjectById(id);
    }

    @PutMapping(path = "{id}")
    public Map<String, String> editProject(@PathVariable long id, @Validated @RequestBody Project employee) {
        return Map.of(
                "resource_uri", "/projects/" + SERVICE.updateProject(id, employee).getId());
    }

    @DeleteMapping(path = "{id}")
    public void deleteProject(@PathVariable long id) {
        SERVICE.deleteProject(id);
    }

    @GetMapping(path = "{id}/employees")
    public Collection<Employee> getEmployeesAssignedToProject(@PathVariable long id) {
        return SERVICE.getProjectById(id).getEmployeeEntities();
    }

    @PostMapping(path = "{id}/employees")
    public void assignEmployeesToProject(@PathVariable long id, @RequestBody Set<Long> employeeIds) {
        SERVICE.assignEmployeesToProject(id, employeeIds);
    }

    @DeleteMapping(path = "{id}/employees/{employeeId}")
    public void removeEmployeeFromProject(@PathVariable long id, @PathVariable long employeeId) {
        SERVICE.removeEmployeeFromProject(id, employeeId);
    }
}
