package kurenkov.tutorservice.mappers;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.dto.OrderDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderListMapper {
    OrderListMapper INSTANCE = Mappers.getMapper(OrderListMapper.class);

    /*@Mapping(target = "seekerData", ignore = true)
    @Mapping(target = "tutorData", ignore = true)*/
    Order orderDataDtoToOrder(OrderDataDTO orderDataDTO);

    @Mapping(target = "seekerData", ignore = true)
    @Mapping(target = "tutorData", ignore = true)
    OrderDataDTO orderToOrderDataDto(Order order);
}
