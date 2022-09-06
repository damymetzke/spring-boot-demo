package nl.brightboost.demo.employee;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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

        // TODO: Handle not found case

        return result.get();
    }

    public Employee storeEmployee(Employee employee) {
        // TODO: Add logging
        return REPOSITORY.save(employee);
    }

    @Transactional
    public Employee updateEmployee(long id, Employee employee) {
        // TODO: Handle not found case
        Employee result = REPOSITORY.findById(id).get();

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
        REPOSITORY.deleteById(id);
        // TODO: Handle not found case
    }
}
