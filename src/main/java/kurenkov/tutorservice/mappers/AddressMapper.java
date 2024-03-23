package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.dto.AddressDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toAddress(AddressDTO source);

    AddressDTO toAddressDTO(Address source);
}
