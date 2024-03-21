package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.repositories.AddressRepository;
import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.services.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void testGetAllAddresses() {
        // Создание нескольких адресов для проверки
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1"));
        addresses.add(new Address(2L, "Country 2", "City 2", "Street 2", "House 2", "Office 2"));

        // Указываем, что при вызове метода findAll() должен возвращаться список адресов
        when(addressRepository.findAll()).thenReturn(addresses);

        // Вызываем метод, который хотим протестировать
        List<Address> result = addressService.getAllAddresses();

        // Проверяем, что возвращенный результат соответствует ожидаемому
        assertEquals(addresses, result);

        // Проверяем, что метод findAll() был вызван ровно один раз
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    public void testGetAddressById() {
        // Создание адреса для проверки
        Address address = new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1");

        // Указываем, что при вызове метода findById() с аргументом 1L должен возвращаться адрес
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        // Вызываем метод, который хотим протестировать
        Address result = addressService.getAddressById(1L);

        // Проверяем, что возвращенный результат соответствует ожидаемому
        assertEquals(address, result);

        // Проверяем, что метод findById() был вызван ровно один раз с аргументом 1L
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveAddress() {
        // Создание адреса для сохранения
        Address address = new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1");

        // Указываем, что при вызове метода save() должен возвращаться сохраненный адрес
        when(addressRepository.save(address)).thenReturn(address);

        // Вызываем метод, который хотим протестировать
        Address result = addressService.saveAddress(address);

        // Проверяем, что возвращенный результат соответствует ожидаемому
        assertEquals(address, result);

        // Проверяем, что метод save() был вызван ровно один раз с аргументом address
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testDeleteAddress() {
        // Вызываем метод, который хотим протестировать
        addressService.deleteAddress(1L);

        // Проверяем, что метод deleteById() был вызван ровно один раз с аргументом 1L
        verify(addressRepository, times(1)).deleteById(1L);
    }
}
