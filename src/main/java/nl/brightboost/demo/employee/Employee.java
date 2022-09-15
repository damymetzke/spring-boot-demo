package nl.brightboost.demo.employee;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.brightboost.demo.project.Project;

@Entity
@Validated
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")

    @JsonIgnore
    private long id;

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    private boolean active;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "projects_id"))
    private Set<Project> projectEntities;

    public Employee() {
        id = 0;
        name = "";
        email = "";
        active = false;
    }

    public Employee(long id, String name, String email, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Project> getProjectEntities() {
        return projectEntities;
    }

    public void setProjectEntities(Set<Project> projects) {
        this.projectEntities = projects;
    }

    public String getSelf() {
        return "/employees/" + id ;
    }

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public Set<String> getProjects() {
        return projectEntities.stream().map(project -> "/employees/" + project.getId()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Employee {"
                + " id: " + id
                + " name: \"" + name + '"'
                + " email: \"" + email + '"'
                + " active: \"" + active
                + " }";
    }


}
