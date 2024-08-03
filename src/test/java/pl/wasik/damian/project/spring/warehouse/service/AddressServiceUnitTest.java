package pl.wasik.damian.project.spring.warehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wasik.damian.project.spring.warehouse.mapper.AddressMapper;
import pl.wasik.damian.project.spring.warehouse.repository.AddressRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AddressService Unit Test")
public class AddressServiceUnitTest {

    private static final Long ADDRESS_ID = 1L;
    private static final String STREET = "Gagarina 6";
    private static final String CITY = "Warszawa";
    private static final String POSTAL_CODE = "00-754";
    private static final String HOUSE_NUMBER = "6/116";

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    @Test
    @DisplayName("Given AddressDto When CreateAddress Then Return Created AddressDto")
    void givenAddressDto_whenCreateAddress_thenReturnCreatedAddressDto() {
        // Given
        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        when(addressMapper.toEntity(any(AddressDto.class))).thenReturn(addressEntity);
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(addressDto);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        // When
        AddressDto createdAddress = addressService.create(addressDto);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdAddress, "Expected created address to be not null"),
                () -> Assertions.assertEquals(ADDRESS_ID, createdAddress.getId(), "Addresses are not the same"),
                () -> Assertions.assertEquals(STREET, createdAddress.getStreet(), "The streets are not the same"),
                () -> Assertions.assertEquals(CITY, createdAddress.getCity(), "Cities are not the same"),
                () -> Assertions.assertEquals(POSTAL_CODE, createdAddress.getPostalCode(), "Postal codes are not the same"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, createdAddress.getHouseNumber(), "House numbers are not the same")
        );
    }

    @Test
    @DisplayName("Given List of AddressEntities When GetAllAddresses Then Return List of AddressDtos")
    void givenAddressEntitiesList_whenGetAllAddresses_thenReturnAddressDtoList() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        List<AddressEntity> addressEntities = Collections.singletonList(addressEntity);
        List<AddressDto> addressDtos = Collections.singletonList(addressDto);

        when(addressRepository.findAll()).thenReturn(addressEntities);
        when(addressMapper.toDto(addressEntity)).thenReturn(addressDto);

        // When
        List<AddressDto> foundAddress = addressService.getAll();

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundAddress, "Expected found address to be not null"),
                () -> Assertions.assertEquals(addressDtos.size(), foundAddress.size(), "Expected list size to match"),
                () -> Assertions.assertEquals(addressDtos.get(0), foundAddress.get(0), "Expected first address to match")
        );
    }

    @Test
    @DisplayName("Given AddressId When GetAddressById Then Return AddressDto")
    void givenAddressId_whenGetAddressById_thenReturnAddressDto() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        when(addressRepository.findById(ADDRESS_ID)).thenReturn(Optional.of(addressEntity));
        when(addressMapper.toDto(addressEntity)).thenReturn(addressDto);

        // When
        AddressDto foundAddress = addressService.read(ADDRESS_ID);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundAddress, "Expected user to be present"),
                () -> Assertions.assertEquals(addressDto, foundAddress, "Expected userDto to match the found userDto")
        );
    }
}
