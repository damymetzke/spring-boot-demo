package nl.brightboost.demo.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Validated
public class Project {
    @Id
    @SequenceGenerator(name = "team_sequence", sequenceName = "team_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sequence")

    @JsonIgnore
    private long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Pattern(regexp = "^[0-9]+\\.[0-9]+\\.[0-9]+$", message = "The value must use the format '{MAJOR}.{MINOR}.{PATCH}'")
    private String version;

    @NotNull
    private ProjectStatus status;

    public Project() {
        id = 0;
        title = "";
        description = "";
        version = "0.0.0";
        status = ProjectStatus.INVALID;
    }

    public Project(long id, String title, String description, String version, ProjectStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.version = version;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getSelf() {
        return "/projects/" + id;
    }


}
