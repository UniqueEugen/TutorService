package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.SeekerDTO;
import kurenkov.tutorservice.entities.dto.SeekerDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeekerMapper {
    SeekerMapper INSTANCE = Mappers.getMapper(SeekerMapper.class);


    @Mapping(source = "seeker.description", target = "description")
    @Mapping(source = "seeker.city", target = "city")
    SeekerDataDTO userDataToSeekerDataDTO(UserData userData);

    @AfterMapping
    default void excludeNullTutors(List<UserData> userDataList, @MappingTarget List<SeekerDataDTO> seekerDataDTOList) {
        seekerDataDTOList.removeIf(seekerDataDTO -> seekerDataDTO.getCity() == null);
    }

    List<SeekerDataDTO> userDataListToSeekerDataDTOList(List<UserData> userList);
}
