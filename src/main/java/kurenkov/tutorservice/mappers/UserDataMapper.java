/*
package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.User;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.SeekerDTO;
import kurenkov.tutorservice.entities.dto.TutorDTO;
import kurenkov.tutorservice.entities.dto.UserDTO;
import kurenkov.tutorservice.entities.dto.UserDataDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, TutorMapper.class, SeekerMapper.class}
)
public interface UserDataMapper {
    UserDataMapper INSTANCE = Mappers.getMapper(UserDataMapper.class);

    UserData toUserDataFromDTO(UserDataDTO source);

    UserDataDTO toUserDataDTO(UserData source);

}*/
