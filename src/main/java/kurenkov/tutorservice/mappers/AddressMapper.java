package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AddressMapper {

    Address toAddress(AddressDTO source);

    AddressDTO toAddressDTO(Address source);
}
