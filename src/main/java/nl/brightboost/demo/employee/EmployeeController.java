package nl.brightboost.demo.employee;

import java.util.Collection;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/employees")
public class EmployeeController {
    @GetMapping
    public Collection<Employee> listEmployees() {
        return List.of(new Employee(1, "John", "john@example.com", true),
                new Employee(2, "Jane", "jane@example.com", true), new Employee(3, "Jill", "jill@example.com", false));
    }

    @PostMapping
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
        return employee;
    }

    @GetMapping(path = "{id}")
    public Employee getUser(@PathVariable long id) {
        return new Employee(id, "Jake", "jake@example.com", true);
    }

    @PatchMapping(path = "{id}")
    public Employee editUser(@PathVariable long id, @Validated @RequestBody Employee employee) {
        return employee;
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable long id) {
    }
}
