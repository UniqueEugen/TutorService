package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.OrderChat;
import kurenkov.tutorservice.entities.dto.ChatDTO;
import kurenkov.tutorservice.entities.dto.OrderDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatListMapper {
    ChatListMapper INSTANCE = Mappers.getMapper(ChatListMapper.class);


    @Mapping(target = "seekerData", ignore = true)
    @Mapping(target = "tutorData", ignore = true)
    ChatDTO chatToChatDto(OrderChat chat);
}
