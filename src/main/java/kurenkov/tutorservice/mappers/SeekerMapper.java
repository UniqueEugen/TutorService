package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Seeker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeekerMapper {
    SeekerMapper INSTANCE = Mappers.getMapper(SeekerMapper.class);

    @Mapping(target = "age", source = "age")
    @Mapping(target = "description", source = "description")
    Seeker toSeeker(Integer age, String description);
}
