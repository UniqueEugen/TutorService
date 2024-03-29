package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Photo;
import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.dto.TutorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class, PhotoMapper.class}
)
public interface TutorMapper {
    TutorMapper INSTANCE = Mappers.getMapper(TutorMapper.class);

    Tutor toTutorFromDTO(TutorDTO source);

    TutorDTO toTutorDTO(Tutor source);
    /*@Mapping(target = "photo", ignore = true)
    @Mapping(target = "address", ignore = true)
    Tutor toEntity(TutorDTO tutorDTO, Photo photo, Address address);*/
}