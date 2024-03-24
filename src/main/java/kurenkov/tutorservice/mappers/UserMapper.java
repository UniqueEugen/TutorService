/*
package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Role;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.User;

import kurenkov.tutorservice.entities.dto.TutorDTO;
import kurenkov.tutorservice.entities.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "authorities", expression = "java(mapRolesToAuthorities(user.getRoles()))")
    UserDTO toUserDTO(User user);

    @Mapping(target = "roles", expression = "java(mapAuthoritiesToRoles(userDTO.getAuthorities()))")
    User toUserFromDTO(UserDTO userDTO);

    default Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    default Set<Role> mapAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new Role(Long.parseLong((String) authority.getAuthority())))
                .collect(Collectors.toSet());
    }
}*/
