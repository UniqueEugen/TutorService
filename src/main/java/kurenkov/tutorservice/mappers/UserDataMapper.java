package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDataMapper {
    UserDataMapper INSTANCE = Mappers.getMapper(UserDataMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "secName", source = "secName")
    @Mapping(target = "eMail", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "tutor", source = "tutor")
    @Mapping(target = "seeker", source = "seeker")
    @Mapping(target = "user", ignore = true)
    UserData toUserData(String name, String surname, String secName, String email, String phone, Tutor tutor, Seeker seeker);

   /* default Long mapIdToId(Long id) {
        return id != null ? id : 0;
    }*/
}
