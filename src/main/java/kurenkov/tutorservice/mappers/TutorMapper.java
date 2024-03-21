package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper
public interface TutorMapper {
    TutorMapper INSTANCE = Mappers.getMapper(TutorMapper.class);

    @Mapping(target = "specialisation", source = "specialisation")
    @Mapping(target = "price", source = "price", defaultValue = "0")
    @Mapping(target = "photo", source = "photoBytes")
    @Mapping(target = "address", source = "address")
    Tutor toTutor(String specialisation, Integer price, MultipartFile photo, Address address);

    default byte[] mapMultipartFileToBytes(MultipartFile file) {
        try {
            return file != null ? file.getBytes() : null;
        } catch (IOException e) {
            // Обработка ошибки чтения файла
            return null;
        }
    }
}
