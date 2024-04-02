package kurenkov.tutorservice.mappers;

import kurenkov.tutorservice.controllers.profile.ProfileController;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.Statuses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "date", target = "date", qualifiedByName = "stringToDate")
    @Mapping(source = "time", target = "time", qualifiedByName = "stringToTime")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatus")
    Order toOrder(ProfileController.OrderData orderData);

    @Named("mapStatus")
    default String mapStatus(Statuses status) {
        return switch (status) {
            case PENDING -> "PENDING";
            case CONFIRMED -> "CONFIRMED";
            case CANCELED -> "CANSELED";
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }
    @Named("stringToDate")
    default Date stringToDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            return format.parse(date);
        } catch (ParseException e) {
            // Обработка ошибки преобразования строки в дату
            e.printStackTrace();
            return null;
        }
    }
    @Named("stringToTime")
    default Time stringToTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            java.util.Date parsedDate = format.parse(time);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            // Обработка ошибки преобразования строки в время
            e.printStackTrace();
            return null;
        }
    }
}
