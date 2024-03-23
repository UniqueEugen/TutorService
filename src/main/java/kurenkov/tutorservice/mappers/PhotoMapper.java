package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Photo;
import kurenkov.tutorservice.entities.dto.PhotoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    @Mapping(target = "content", source = "content", qualifiedByName = "toBase64")
    Photo toPhoto(PhotoDTO source);

    @Mapping(target = "content", source = "content", qualifiedByName = "fromBase64")
    PhotoDTO toPhotoDTO(Photo source);

    @Named("toBase64")
    default byte[] toBase64(String content) {
        if (content != null) {
            return Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    @Named("fromBase64")
    default String fromBase64(byte[] content) {
        if (content != null) {
            byte[] decodedBytes = Base64.getDecoder().decode(content);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        }
        return null;
    }
}
