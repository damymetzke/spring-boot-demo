package nl.brightboost.demo.project;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
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
