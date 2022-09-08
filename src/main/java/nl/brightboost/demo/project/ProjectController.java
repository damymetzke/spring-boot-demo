package nl.brightboost.demo.project;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "projects")
public class ProjectController {

    @GetMapping
    public Collection<Project> listEmployees() {
        return List.of(new Project(1, "Hello World", "Example Project", "0.1.0", ProjectStatus.PROTOTYPE));
        // return SERVICE.getEmployees();
    }

    @PostMapping
    public Map<String, String> createEmployee(@Validated @RequestBody Project employee) {
        return Map.of("resource_uri", "/projects/1");
//        return Map.of(
//                "resource_uri", "/projects/" + SERVICE.storeEmployee(employee).getId());
    }

    @GetMapping(path = "{id}")
    public Project getUser(@PathVariable long id) {
        return new Project(1, "Hello World", "Example Project", "0.1.0", ProjectStatus.PROTOTYPE);
        //return SERVICE.getEmployeeById(id);
    }

    @PutMapping(path = "{id}")
    public Map<String, String> editUser(@PathVariable long id, @Validated @RequestBody Project employee) {
        return Map.of(
                "resource_uri", "/projects/1");
//        return Map.of(
//                "resource_uri", "/projects/" + SERVICE.updateEmployee(id, employee).getId());
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable long id) {
        //SERVICE.deleteEmployee(id);
    }
}
