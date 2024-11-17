package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Mapper(uses = {AddressMapper.class, PhotoMapper.class})
public interface TutorDataMapper {
    TutorDataMapper INSTANCE = Mappers.getMapper(TutorDataMapper.class);

    @Mapping(source = "tutor.specialisation", target = "specialisation")
    @Mapping(source = "tutor.price", target = "price")
    @Mapping(source = "tutor.description", target = "description")
    @Mapping(source = "tutor.address", target = "address")
    @Mapping(source = "tutor.photo", target = "photo")
    @Mapping(source = "tutor.id", target = "tutorId")
    TutorDataDTO userDataToTutorDataDTO(UserData userData);

    @AfterMapping
    default void excludeNullTutors(List<UserData> userDataList, @MappingTarget List<TutorDataDTO> tutorDataDTOList) {
        tutorDataDTOList.removeIf(tutorDataDTO -> tutorDataDTO.getSpecialisation() == null);
    }

    List<TutorDataDTO> userDataListToTutorDataDTOList(List<UserData> userList);
}
