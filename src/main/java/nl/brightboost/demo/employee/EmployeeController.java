package nl.brightboost.demo.employee;

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

import nl.brightboost.demo.project.Project;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {
    private final EmployeeService SERVICE;

    public EmployeeController(EmployeeService service) {
        SERVICE = service;
    }

    @GetMapping
    public Collection<Employee> listEmployees() {
        return SERVICE.getEmployees();
    }

    @PostMapping
    public Map<String, String> createEmployee(@Validated @RequestBody Employee employee) {
        return Map.of(
                "resource_uri", "/employees/" + SERVICE.storeEmployee(employee).getId());
    }

    @GetMapping(path = "{id}")
    public Employee getEmployee(@PathVariable long id) {
        return SERVICE.getEmployeeById(id);
    }

    @PutMapping(path = "{id}")
    public Map<String, String> editEmployee(@PathVariable long id, @Validated @RequestBody Employee employee) {
        return Map.of(
                "resource_uri", "/employees/" + SERVICE.updateEmployee(id, employee).getId());
    }

    @DeleteMapping(path = "{id}")
    public void deleteEmployee(@PathVariable long id) {
        SERVICE.deleteEmployee(id);
    }

    @GetMapping(path = "{id}/projects")
    public Collection<Project> getEmployeesAssignedToProject(@PathVariable long id) {
        return SERVICE.getEmployeeById(id).getProjectEntities();
    }

    @PostMapping(path = "{id}/projects")
    public void assignEmployeeToProjects(@PathVariable long id, @RequestBody Set<Long> projectIds) {
        SERVICE.assignEmployeeToProjects(id, projectIds);
    }

    @DeleteMapping(path = "{id}/projects/{projectId}")
    public void removeEmployeeFromProject(@PathVariable long id, @PathVariable long projectId) {
        SERVICE.removeEmployeeFromProject(id, projectId);
    }
}
