package nl.brightboost.demo.project;

import javax.persistence.AttributeConverter;

public class ProjectStatusConverter implements AttributeConverter<ProjectStatus, Byte>{

    @Override
    public Byte convertToDatabaseColumn(ProjectStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public ProjectStatus convertToEntityAttribute(Byte dbData) {
        return ProjectStatus.fromValue(dbData);
    }
}
