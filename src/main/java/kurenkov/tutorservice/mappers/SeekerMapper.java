package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.dto.SeekerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SeekerMapper {
    SeekerMapper INSTANCE = Mappers.getMapper(SeekerMapper.class);

    Seeker toSeekerFromDTO(SeekerDTO source);

    SeekerDTO toSeekerDTO(Seeker source);
}
