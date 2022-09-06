package nl.brightboost.demo.employee;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
    private final EmployeeRepository REPOSITORY;

    public EmployeeService(EmployeeRepository repository) {
        REPOSITORY = repository;
    }

    public Collection<Employee> getEmployees() {
        return REPOSITORY.findAll();
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> result = REPOSITORY.findById(id);

        if(!result.isPresent()) {
            // TODO: Add logging
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        return result.get();
    }

    public Employee storeEmployee(Employee employee) {
        // TODO: Add logging
        return REPOSITORY.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long id, Employee employee) {
        Optional<Employee> databaseEmployee = REPOSITORY.findById(id);

        if(!databaseEmployee.isPresent()) {
            // TODO: Add logging
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }

        Employee result = databaseEmployee.get();

        if (employee.getName() != null && employee.getName().length() > 0
                && employee.getName() != result.getName()) {
            result.setName(employee.getName());
        }

        if (employee.getEmail() != null && employee.getEmail().length() > 0
                && !Objects.equals(employee.getEmail(), result.getEmail())) {
            result.setEmail(employee.getEmail());
        }

        if (employee.isActive() != result.isActive()) {
            result.setActive(employee.isActive());
        }

        return result;
    }

    public void deleteEmployee(long id) {
        if(!REPOSITORY.findById(id).isPresent()) {
            // TODO: Add logging
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with id " + id + " found.");
        }
        REPOSITORY.deleteById(id);
    }
}
