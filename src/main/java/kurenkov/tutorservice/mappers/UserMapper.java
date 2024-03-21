package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    User toUser(String login, String password);
}