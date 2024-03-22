package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.User;

import kurenkov.tutorservice.entities.dto.TutorDTO;
import kurenkov.tutorservice.entities.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Tutor toUserFromDTO(UserDTO source);

    UserDTO toUserDTO(User source);
}